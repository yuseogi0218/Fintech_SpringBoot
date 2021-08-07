package spring.finEdu.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.finEdu.entity.FredData;

@Data // Data 에 NoArgsConstructor와 AllArgsConstructor이 없음
@NoArgsConstructor //Observation()
@AllArgsConstructor //Observation (String observationDate, Double value)
// DTO Service <-> Controller <-> DTO
public class Observation {

    String observationDate;
    Double value;

    // Entity Converter -> Entity 를 DTO로 변경해주는것
    public static Observation fromFredData(FredData fredData) {
        return new Observation(fredData.getObservationDate(), fredData.getValue());
    }

}
