package zaudinov.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.service.ServService;

@RestController
@RequestMapping("/service")
public class ServController {

    private ServService servService;

    @Autowired
    public ServController(ServService servService) {
        this.servService = servService;
    }

    @GetMapping
    public Iterable<Serv> getHierarchy(){
        Iterable<Serv> all = servService.getHierarchy();

        return all;
    }
}
