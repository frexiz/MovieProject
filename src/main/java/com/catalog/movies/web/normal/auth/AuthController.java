package com.catalog.movies.web.normal.auth;

import com.catalog.movies.core.FacebookService;
import com.catalog.movies.core.roles.Role;
import com.catalog.movies.core.roles.RoleRepository;
import com.catalog.movies.core.users.User;
import com.catalog.movies.core.users.UserRegisterDTO;
import com.catalog.movies.core.users.UserRepository;
import com.catalog.movies.core.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller()
@RequestMapping("/auth")
public class AuthController {
    public static final String LOGIN_PAGE_FORWARD = "auth/login";
    public static final String LOGIN_PAGE_REDIRECT = "redirect:/auth/login";
    public static final String REGISTER_PAGE_FORWARD = "auth/register";


    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    FacebookService facebookService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home(Principal user) {
        return LOGIN_PAGE_FORWARD;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Principal user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(servletRequest, servletResponse, auth);

        }
        return "redirect:/auth/login?logout";
    }


    @GetMapping("/register")
    public String register(Model model) {
        return REGISTER_PAGE_FORWARD;
    }


    @PostMapping("/register")
    public String registerProcess(UserRegisterDTO userRegisterDTO) throws IOException {
        String error = userService.validateUserRegisterDTO(userRegisterDTO);

        if (!error.isEmpty()){
            return LOGIN_PAGE_REDIRECT;
        }
//
//            if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
//                return "redirect:/register";
//
//            }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(userRegisterDTO.getEmail(), userRegisterDTO.getFullName(), bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);
        userRepository.saveAndFlush(user);

        return LOGIN_PAGE_REDIRECT;
    }
//
//
//
//    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
//    public String profilePage(Model model) {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        jovasoft.blog.entity.User user = this.userRepository.findByEmail(principal.getUsername());
//
//        String image;
//        if (user.getPhoto() == null) {
//
//            image = "http://www.cdn.innesvienna.net//Content/user-default.png";
//        } else {
//            image = "data:image/png;base64," + user.getPhoto();
//        }
//        model.addAttribute("image", image);
//        model.addAttribute("user", user);
//        model.addAttribute("view", "user/profile");
//
//        return "base-layout";
//    }


}
