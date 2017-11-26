package edu.demon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test" )
public class SimpleController {
        @RequestMapping(value = "hello", method = RequestMethod.GET)
        @ResponseBody
        public String helloWorld(@RequestParam ("user") String userName) {
               return "Hello " + userName + " !" ;
       }
}