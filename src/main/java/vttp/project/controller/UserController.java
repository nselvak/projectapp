package vttp.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp.project.model.Search;
import vttp.project.model.User;
import vttp.project.repository.UserRepo;

@Controller
@RequestMapping(path="/")
public class UserController {

    @Autowired
    private UserRepo userRepo;
     
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());
     
        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        if (userRepo.checkUser(user) == 1) {
            return "register_failure";
        }
        userRepo.save(user);
     
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        model.addAttribute("user", new User());
     
        return "login";
    }

    @PostMapping("/process_login")
    public String processLogin(User user, Model model) {
     
        if (userRepo.checkUserPW(user) == 1) {
            model.addAttribute("user", user);
            model.addAttribute("search", new Search());


            return "MusicSearch";
        }
     
        return "login_failure";

    }


    
}
