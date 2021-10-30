package zaudinov.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zaudinov.testcase.domain.Serv;
import zaudinov.testcase.domain.User;
import zaudinov.testcase.repository.projection.UserView;
import zaudinov.testcase.service.ServService;
import zaudinov.testcase.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ServService servService;

    @Autowired
    public UserController(UserService userService, ServService servService) {
        this.userService = userService;
        this.servService = servService;
    }

    @GetMapping
    public ResponseEntity<Page<UserView>> getAllUsers(
            @PageableDefault(size = 20, sort = {"account"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter
    ){
        if(filter != null){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/user/filter/account/" + filter);
            return new ResponseEntity(headers, HttpStatus.FOUND) ;
        }
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("filter/account/{account}")
    public ResponseEntity<Page<UserView>> getUserByAccount(
            @PathVariable String account,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<UserView> users = userService.getByAccount(account, pageable);

        return ResponseEntity.ok(users);
    }

    @Transactional
    @GetMapping("/service/{id}")
    public ResponseEntity<Page<UserView>> getSubscriberByServiceId(
            @PathVariable("id") Long id,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Serv service = servService.findServiceById(id);

        Page<UserView> subscribers = userService.getAllByService(service, pageable);

        return ResponseEntity.ok(subscribers);

    }

    @Transactional
    @GetMapping("/service/all/{id}")
    public ResponseEntity<Page<UserView>> getSubscriberByServiceIdWithChildren(
            @PathVariable("id") Long id,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Set<Serv> services = servService.getServiceWithChildrenDeepSet(id);

        Page<UserView> subscribers = userService.getAllByServicesIn(services, pageable);

        return ResponseEntity.ok(subscribers);

    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody User user) throws URISyntaxException {
        Long createdUserId = userService.create(user);

        if(createdUserId == null){
            return ResponseEntity.notFound().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUserId)
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdUserId);
    }

}
