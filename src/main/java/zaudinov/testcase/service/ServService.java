package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.domain.User;
import zaudinov.testcase.exception.CantDeleteServiceException;
import zaudinov.testcase.exception.ServNotExistsException;
import zaudinov.testcase.repository.ServRepository;
import zaudinov.testcase.repository.UserRepository;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class ServService {

    private ServRepository servRepository;
    private UserRepository userRepository;

    @Autowired
    public ServService(ServRepository servRepository, UserRepository userRepository) {
        this.servRepository = servRepository;
        this.userRepository = userRepository;
    }


    public Set<Serv> getHierarchy() {
       return servRepository.findByParent(null);
    }


    public Serv findServiceById(Long id) {
        Serv serv = servRepository.findById(id).orElseThrow(() -> new ServNotExistsException("there is no service with provided id"));
        return serv;
    }


    public Set<Serv> getServiceWithChildrenDeepSet(Long id) {
        Deque<Serv> servicesToRetrieveChildren = new LinkedList<>();
        Set<Serv> services = new HashSet<>();

        servicesToRetrieveChildren.add(servRepository.findById(id)
                .orElseThrow(() -> new ServNotExistsException("there is no service with provided id")));

        while(!servicesToRetrieveChildren.isEmpty()){
            Serv s = servicesToRetrieveChildren.poll();
            services.add(s);
            servicesToRetrieveChildren.addAll(s.getChildren());
        }
        return services;
    }

    @Transactional
    public void deleteServ(Long id) {
        //check that provided service is a valid service
        Serv servToBeDeleted = servRepository.findById(id)
                .orElseThrow(() -> new ServNotExistsException("there is no service with provided id"));

        //if service has at least one child, it can't be deleted
        if(servToBeDeleted.getChildren() != null){
            throw new CantDeleteServiceException("Can't delete the service, " +
                    "it has one or more child service");
        }


        //if service has at least one subscriber to it, it can't be deleted
        User user = userRepository.findTop1ByServ(servToBeDeleted);

        if(user != null){
            throw new CantDeleteServiceException ("Can't delete the service, " +
                    "it has one or more subscriber");
        }

        //if there are no child services or subscribers to this service, delete it
        servRepository.delete(servToBeDeleted);



    }

    public void deleteServForce(Long id, Pageable pageable) {

    }
}
