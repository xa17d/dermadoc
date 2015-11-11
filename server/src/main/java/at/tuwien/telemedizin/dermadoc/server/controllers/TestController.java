package at.tuwien.telemedizin.dermadoc.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daniel on 11.11.2015.
 */
@RestController
public class TestController {
    @RequestMapping(value = "/")
    public String helloWorld() {
        return "Hello Derma Doc";
    }
}
