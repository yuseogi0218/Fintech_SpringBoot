package spring.finEdu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.finEdu.entity.FredData;
import spring.finEdu.repository.FredRepository;
import spring.finEdu.dto.Observation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FredService {

    // Thread-safe Values
    final String baseUrl = "https://api.stlouisfed.org";
    final public static ParameterizedTypeReference<Map<String, Object>> mapTypeReference = new ParameterizedTypeReference<>() {
    };
    final public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Get property from application.properties
    @Value("${fred.apikey}") // application.properties 의 값과 연결
    String fredApiKey;

    // Required Beans
    final FredRepository fredRepository;

    @Autowired
    public FredService(FredRepository fredRepository) {
        this.fredRepository = fredRepository;
    }

    // value Double 형 변환 처리
    private Double safeParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Throwable throwable) {
            return -999d;
        }
    }

    private Mono<Map<String, Object>> callUsBond10Y(String from, String to) {
        return WebClient.create(baseUrl)
                //Observation 객체를 흘려 보내준다. / WebClient 반환값
                .method(HttpMethod.GET)
                // GET 메소드 사용
                .uri(it -> it.path("/fred/series/observations")
                        // uri 빌더가 baseUrl 뒤에 붙을거를 적어 주도록 요청?
                        .queryParam("series_id","DGs10")
                        // parameter 추가
                        .queryParam("units","lin")
                        .queryParam("file_type","json")
                        .queryParam("api_key",fredApiKey)
                        .queryParam("observation_start",from)
                        .queryParam("observation_end",to)
                        .build())
                // build 완료
                .retrieve()
                // 값 가져온다고 예약하는 방법 2가지 retrieve / exchange
                .bodyToMono(mapTypeReference);
        // response body 를 Map 으로 변환 -> Jackson 을 이용
    }

    public Flux<Observation> getUsGovernmentBond10v() {
        Flux<Observation> data = callUsBond10Y("2021-07-01","2021-07-31")
                .flatMapMany(it -> {
                    // 한개에서 여러개 뽑아낼때
                    List<Map<String, String>> list = (List<Map<String, String>>) it.get("observations");
                        // 리스트 안에 자료형 이 Map<String, String> 이다.
                        // map.observations 값 갖고 옴. - json 데이터 구조
                            // 형 변환 필요
                    return Flux.fromStream(
                            list.stream().map(m -> new Observation(m.get("date"), safeParseDouble(m.get("value"))))
                    );
                        // Stream 을 Flux 로 변환
                        // stream()
                });
        return data;
    }

    // 1분마다 업데이트
    // @Scheduled(fixedDelay = 60000)
    public void updateFredDateRegularly() {
        log.info("Now updating FredData");
        String from = formatter.format(LocalDateTime.now().minusMonths(6)); // 6개월 동안의 데이터 수집
        String to = formatter.format(LocalDateTime.now());
        Map<String, Object> data = callUsBond10Y(from, to).block(Duration.ofMinutes(5));
        List<Map<String, String>> list = (List<Map<String, String>>) data.get("observations");
        fredRepository.saveAll(list.stream()
                .map(m -> new FredData("DGS10", m.get("date"), safeParseDouble(m.get("value"))))
                .collect(Collectors.toList()));
    }

//    @Scheduled(fixedDelay = 5000) // 5초마다 한번씩 스케줄
//    public void logSometimes() {
//        log.error("I love errors");
//    }

    public List<Observation> getStoredFredData(String seriesId, String from, String to) {
        return fredRepository.findAllBySeriesIdAndObservationDateAfterAndObservationDateBefore(seriesId, from, to)
                .stream().map(Observation::fromFredData) // Entity 를 DTO 로 변경 fromFredData 함수
                        // = map(fredDataEntity -> Observation.fromFredData(fredDataEntity))
                .collect(Collectors.toList());
    }
}
