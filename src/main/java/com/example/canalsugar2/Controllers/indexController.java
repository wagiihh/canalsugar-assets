package com.example.canalsugar2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController

@RequestMapping("")
public class indexController {

 
    @GetMapping("")
    public ModelAndView getlanding() {
        ModelAndView mav = new ModelAndView("LandingPage.html");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView gethome2() {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

}
