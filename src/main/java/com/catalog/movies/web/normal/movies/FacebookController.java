package com.catalog.movies.web.normal.movies;

import com.catalog.movies.core.FacebookService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.io.IOException;
import java.util.Base64;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
@RequestMapping("/")
public class FacebookController {

    @Autowired
    FacebookService facebookService;


//    @GetMapping("/createFacebookAuthorization")
//    public String createFacebookAuthorization(){
//        return "redirect:/" + facebookService.createFacebookAuthorizationURL();
//    }

    @GetMapping("/createFacebookAuthorization")
    public RedirectView createFacebookAuthorization() {
        return new RedirectView(facebookService.createFacebookAuthorizationURL());
    }

    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        facebookService.createFacebookAccessToken(code);
//        return new RedirectView("/register");
    }


    @GetMapping("/getInfo")
    public String getInfoResponse(){

        return facebookService.getInfo();
    }

    @GetMapping("/getPic")
    public String getProfilePicResponse(){
        return Base64.getEncoder().encodeToString(facebookService.getProfilePic());
    }

}