package com.catalog.movies.web.normal.actors;

import com.catalog.movies.core.actors.Actor;
import com.catalog.movies.core.actors.ActorDTO;
import com.catalog.movies.core.actors.ActorService;
import com.catalog.movies.core.categories.CategoryService;
import com.catalog.movies.core.comments.AddCommentDTO;
import com.catalog.movies.core.comments.Comment;
import com.catalog.movies.core.comments.CommentService;
import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.movies.MovieDTO;
import com.catalog.movies.core.movies.MovieEntityFactory;
import com.catalog.movies.core.movies.MovieService;
import com.catalog.movies.core.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/actor")
@PreAuthorize("isAuthenticated()")
public class ActorController {

    public static final String HOME_PAGE_REDIRECT = "redirect:/";
    public static final String ACTOR_ADD_PAGE_FORWARD = "actors/add";
    public static final String ACTOR_LIST_PAGE_FORWARD = "actors/list";

    @Autowired
    MovieService movieService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    ActorService actorsService;

    @GetMapping("/listAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String listAll(Model model) {
        model.addAttribute("actors", this.actorsService.findAll());
        return ACTOR_LIST_PAGE_FORWARD;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(Model model) {
        return ACTOR_ADD_PAGE_FORWARD;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addPost(ActorDTO actorDTO, final Model model, Principal principal) {

        Actor actor = new Actor(actorDTO.getFirstName() + " " + actorDTO.getLastName());
        actorsService.saveAndFlush(actor);

        return HOME_PAGE_REDIRECT;
    }
}
