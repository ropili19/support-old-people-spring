package supportoldpeoplespring.supportoldpeoplespring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import supportoldpeoplespring.supportoldpeoplespring.documents.TimeClock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeClockRepository extends MongoRepository<TimeClock, String> {

    Optional<TimeClock> findFirst1ByUserOrderByClockinDateDesc(String id);

    List<TimeClock> findByClockinDateBetweenOrderByClockinDateDesc(LocalDateTime dateFrom, LocalDateTime dateTo);

    List<TimeClock> findByClockinDateBetweenAndUserOrderByClockinDateDesc(LocalDateTime dateFrom, LocalDateTime dateTo, String id);
}
