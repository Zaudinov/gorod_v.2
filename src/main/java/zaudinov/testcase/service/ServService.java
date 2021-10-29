package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.repository.ServRepository;

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
}
