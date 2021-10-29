package zaudinov.testcase.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zaudinov.testcase.domain.ServHierarchy;

@Repository
public interface ServHierarchyRepository extends CrudRepository<ServHierarchy, Long> {
}
