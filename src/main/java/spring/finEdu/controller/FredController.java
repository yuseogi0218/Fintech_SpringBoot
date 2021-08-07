package spring.finEdu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import spring.finEdu.service.FredService;
import spring.finEdu.dto.Observation;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/fred")
@RequiredArgsConstructor
@Slf4j
public class FredController {

    final FredService fredService;

    @GetMapping(path = "/usbond10y")
    public Flux<Observation> getUsBond10y() {
        return fredService.getUsGovernmentBond10v();
    }

    // WebFlux 참조
    @GetMapping(value = "/usbond10y_stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Observation> getUsBond10YStream() {
        return Flux.interval(Duration.ofMillis(1000))
                .zipWith(fredService.getUsGovernmentBond10v()) // 스트임 두개를 합침
                .map(t -> t.getT2()); // 합친 스트림(interval 값, Observation 객체) 두번째 것 취사 선택하는 mapper
    }

    @GetMapping(path="/interval", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Long> interval() {
        return Flux.interval(Duration.ofMillis(300));
    }

    @GetMapping(path="/query")
    public List<Observation> query(
            @RequestParam(required = false, defaultValue = "DGS10") String seriesId,
            @RequestParam String from, @RequestParam String to) {
        return fredService.getStoredFredData(seriesId, from, to);
    }
}
