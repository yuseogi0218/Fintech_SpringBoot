package spring.finEdu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.finEdu.entity.FredData;
import spring.finEdu.entity.id.FredId;

import java.util.List;

@Repository
public interface FredRepository extends CrudRepository<FredData, FredId> {
    public List<FredData> findAllBySeriesIdAndObservationDateAfterAndObservationDateBefore(String seriesId, String from, String to);
}
