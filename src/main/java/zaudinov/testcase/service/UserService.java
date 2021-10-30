package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.domain.User;
import zaudinov.testcase.exception.ServNotExistsException;
import zaudinov.testcase.exception.UserAlreadyExistsException;
import zaudinov.testcase.exception.UserNotExistsException;
import zaudinov.testcase.repository.ServRepository;
import zaudinov.testcase.repository.UserRepository;
import zaudinov.testcase.repository.projection.UserView;

import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private ServRepository servRepository;

    @Autowired
    public UserService(UserRepository userRepository, ServRepository servRepository) {
        this.userRepository = userRepository;
        this.servRepository = servRepository;
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

    public Page<UserView> getAllByServicesIn(Set<Serv> services, Pageable pageable) {
        Page<UserView> users = userRepository.getAllByServiceIn(services, pageable);
        if (users == null){
            throw new UserNotExistsException("There are no users with provided service");
        }
        return users;
    }

    @Transactional
    public Long create(User user) {
        Long CreatedUserId = user.getId();

        //Check if there is existing subscriber with provided id
        User userInDb = null;
        if(CreatedUserId != null){
            userInDb = userRepository.findById(CreatedUserId).orElse(null);
        }
        if(userInDb != null){
            throw new UserAlreadyExistsException("There is an existing user with provided id");
        }

        //Check that provided service is a valid services
        Serv createdUserServ = user.getService();
        if(createdUserServ == null){
            throw new ServNotExistsException("Can't create user with no service");
        }

        Serv servFromDB = servRepository.findById(createdUserServ.getId())
                .orElseThrow(() -> new ServNotExistsException("invalid service is provided"));

        // Check that provided service has same id and name as service from DB
        if(!createdUserServ.equals(servFromDB)){
            throw new ServNotExistsException("invalid service is provided");
        }

        //Substitute provided service with service from DB
        user.setService(servFromDB);

        Long savedUserId = userRepository.save(user).getId();

        return savedUserId;
    }
}
