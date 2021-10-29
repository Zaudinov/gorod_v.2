package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaudinov.testcase.repository.ServRepository;

@Service
public class ServService {

    private ServRepository servRepository;

    @Autowired
    public ServService(ServRepository servRepository) {
        this.servRepository = servRepository;
    }
}
