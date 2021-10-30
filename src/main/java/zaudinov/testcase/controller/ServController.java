package zaudinov.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteServiceById(
            @PathVariable("id") Long id, @RequestParam(name = "force",defaultValue = "false") boolean force
    ){
        if(!force){
            servService.deleteServ(id);
        }
        else{
            servService.deleteServForce(id);
        }
        return ResponseEntity.ok().build();
    }
}
