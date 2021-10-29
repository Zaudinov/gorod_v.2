package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.exception.ServNotExistsException;
import zaudinov.testcase.repository.ServRepository;
import zaudinov.testcase.repository.projections.ServWithoutChildrenView;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class ServService {

    private ServRepository servRepository;

    @Autowired
    public ServService(ServRepository servRepository) {
        this.servRepository = servRepository;
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
}
