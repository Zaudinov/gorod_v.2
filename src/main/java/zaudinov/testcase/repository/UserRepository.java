package zaudinov.testcase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zaudinov.testcase.domain.User;
import zaudinov.testcase.repository.projections.UserView;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u")
    Page<UserView> getAll (Pageable pageable);

    Page<UserView> getByAccountLike(String filter, Pageable pageable);
}
