package com.agenda.consultapp.controller;

import javax.validation.Valid;

import com.agenda.consultapp.model.User;
import com.agenda.consultapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registrar", method = RequestMethod.GET)
    public ModelAndView registrar(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registrar");
        return modelAndView;
    }
    
    
    @RequestMapping(value="/registrarMedico", method = RequestMethod.GET)
    public ModelAndView registrarMedico(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registrarMedico");
        return modelAndView;
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Este email ja esta em uso");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registrar");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Usuário registrado com sucesso");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registrar");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    
    @RequestMapping(value={"/agendamentos"}, method = RequestMethod.GET)
    public ModelAndView agendamentos(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("agendamentos");
        return modelAndView;
    }
    
    
    @RequestMapping(value={"/confirmarAtendimento"}, method = RequestMethod.GET)
    public ModelAndView confirmarAtendimento(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("confirmarAtendimento");
        return modelAndView;
    }
    
    @RequestMapping(value={"/historicoAgendamento"}, method = RequestMethod.GET)
    public ModelAndView historicoAgendamento(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("confirmarAtendimento");
        return modelAndView;
    }

    
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("index");
        return modelAndView;
    }
    
    
    
    
    
    

}
