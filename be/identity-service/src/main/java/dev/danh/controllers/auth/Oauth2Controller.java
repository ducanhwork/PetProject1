package dev.danh.controllers.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {
    @PostMapping("/authorize/google")
    public String authorize() {

    }
}
