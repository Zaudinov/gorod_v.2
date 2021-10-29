package zaudinov.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zaudinov.testcase.domain.User;
import zaudinov.testcase.exception.UserNotExistsException;
import zaudinov.testcase.repository.projections.UserView;
import zaudinov.testcase.service.ServService;
import zaudinov.testcase.service.UserService;

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
    public ResponseEntity<Page<User>> getAllUsers(
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

}
