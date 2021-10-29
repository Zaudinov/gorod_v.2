package zaudinov.testcase.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zaudinov.testcase.domain.Serv;

@Repository
public interface ServRepository extends CrudRepository<Serv, Long> {
}
