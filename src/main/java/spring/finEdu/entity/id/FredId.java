package spring.finEdu.entity.id;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class FredId implements Serializable {

    @Transient // 직렬화 포함 안함
    String seriesId;
    String observationDate;
}
