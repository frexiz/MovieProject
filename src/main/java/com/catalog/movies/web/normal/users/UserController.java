package com.catalog.movies.web.normal.users;

import com.catalog.movies.core.movies.MovieService;
import com.catalog.movies.core.roles.RoleService;
import com.catalog.movies.core.users.User;
import com.catalog.movies.core.users.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
public class UserController {
    public static final String USERS_FAVOURITES_FORWARD = "users/favourites";
    public static final String USERS_PROFILE_FORWARD = "users/profile";
    public static final String USERS_PROFILE_EDIT_FORWARD = "users/edit";
    public static final String HOME_PAGE_FORWARD = "home/index";
    public static final String USERS_PROFILE_REDIRECT = "redirect:/users/profile";
    public static final String LOGIN_PAGE_FORWARD = "auth/login";
    public static final String LOGIN_PAGE_REDIRECT = "redirect:/auth/login";
    public static final String REGISTER_PAGE_FORWARD = "auth/register";


    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    MovieService movieService;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String home(Model model, Principal principal) throws IOException {
        byte[] b = IOUtils.toByteArray((new URL("http://itschool.dev.bg/application/uploads/tutorials/gallery/maqpen4eva/6/1.jpg")).openStream()); //idiom
        String photo = new String(Base64.encodeBase64(b), "UTF-8");
        model.addAttribute("image", "data:image/png;base64," + photo);
        User currentUser = userService.findByEmail(principal.getName());
        model.addAttribute("user", currentUser);
        model.addAttribute("roleName", currentUser.getRoles().iterator().next().getName().
                substring(currentUser.getRoles().iterator().next().getName().indexOf("_")+1));
        return USERS_PROFILE_FORWARD;
    }

    @RequestMapping(value = "/favourites", method = RequestMethod.GET)
    public String favouriteList(Model model, Principal principal) throws IOException {
        byte[] b = IOUtils.toByteArray((new URL("http://itschool.dev.bg/application/uploads/tutorials/gallery/maqpen4eva/6/1.jpg")).openStream()); //idiom
        String photo = new String(Base64.encodeBase64(b), "UTF-8");
        model.addAttribute("image", "data:image/png;base64," + photo);
        User currentUser = userService.findByEmail(principal.getName());
        model.addAttribute("user", currentUser);
        return USERS_FAVOURITES_FORWARD;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!userService.exists(id)) {
            return USERS_PROFILE_REDIRECT;
        }

        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("roles", roleService.findAll());

        return USERS_PROFILE_EDIT_FORWARD;
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id) {
        return USERS_PROFILE_REDIRECT;
    }



//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String home(Principal user) {
//        return LOGIN_PAGE_FORWARD;
//    }
//
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Principal user) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(servletRequest, servletResponse, auth);
//
//        }
//        return "redirect:/auth/login?logout";
//    }
//
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        return REGISTER_PAGE_FORWARD;
//    }
//
//
//    @PostMapping("/register")
//    public String registerProcess(UserRegisterDTO userRegisterDTO) throws IOException {
//        String error = userService.validateUserRegisterDTO(userRegisterDTO);
//
//        if (!error.isEmpty()){
//            return LOGIN_PAGE_REDIRECT;
//        }
////
////            if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
////                return "redirect:/register";
////
////            }
//
//
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        User user = new User(userRegisterDTO.getEmail(), userRegisterDTO.getFullName(), bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
//        Role userRole = this.roleRepository.findByName("ROLE_USER");
//        user.addRole(userRole);
//        userRepository.saveAndFlush(user);
//
//        return LOGIN_PAGE_REDIRECT;
//    }
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
