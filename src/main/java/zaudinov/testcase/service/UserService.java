package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.exception.UserNotExistsException;
import zaudinov.testcase.repository.UserRepository;
import zaudinov.testcase.repository.projections.UserView;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserView> getAllUsers(Pageable pageable){
        Page<UserView> all = userRepository.getAll(pageable);

        return all;
    }

    public Page<UserView> getByAccount(String filter, Pageable pageable) {
        Page<UserView> byAccountLike = userRepository.getByAccountLike(filter, pageable);
        if(byAccountLike.getTotalElements() == 0){
            throw new UserNotExistsException("There is no user with such account");
        }
        return byAccountLike;
    }

    public Page<UserView> getAllByService(Serv service, Pageable pageable) {
        Page<UserView> users = userRepository.getAllByService(service, pageable);
        if (users == null){
            throw new UserNotExistsException("There are no users with provided service");
        }
        return users;
    }
}
