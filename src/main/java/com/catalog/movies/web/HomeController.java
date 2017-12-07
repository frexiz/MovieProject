package com.catalog.movies.web;

import com.catalog.movies.core.categories.CategoryService;
import com.catalog.movies.core.movies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("isAuthenticated()")
public class HomeController {

    public static final String HOME_PAGE_FORWARD = "home/index";

    @Autowired
    MovieService movieService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("movies", movieService.findTop6ByOrderByRatingDesc());
        return HOME_PAGE_FORWARD;
    }
}
