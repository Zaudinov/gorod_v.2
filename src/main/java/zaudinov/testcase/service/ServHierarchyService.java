package zaudinov.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zaudinov.testcase.repository.ServHierarchyRepository;

@Service
public class ServHierarchyService {

    private ServHierarchyRepository servHierarchyRepository;

    @Autowired
    public ServHierarchyService(ServHierarchyRepository servHierarchyRepository) {
        this.servHierarchyRepository = servHierarchyRepository;
    }
}
