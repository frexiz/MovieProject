package com.catalog.movies.web.normal.movies;

import com.catalog.movies.core.actors.ActorService;
import com.catalog.movies.core.categories.CategoryService;
import com.catalog.movies.core.comments.AddCommentDTO;
import com.catalog.movies.core.comments.Comment;
import com.catalog.movies.core.comments.CommentService;
import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.movies.MovieDTO;
import com.catalog.movies.core.movies.MovieEntityFactory;
import com.catalog.movies.core.movies.MovieService;
import com.catalog.movies.core.users.User;
import com.catalog.movies.core.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/movie")
@PreAuthorize("isAuthenticated()")
public class MovieController {

    public static final String MOVIE_MOVIE_PAGE_FORWARD = "movies/movie";
    public static final String HOME_PAGE_REDIRECT = "redirect:/";
    public static final String MOVIE_EDIT_PAGE_FORWARD = "movies/edit";
    public static final String MOVIE_INFO_PAGE_FORWARD = "movies/info";
    public static final String MOVIE_INFO_PAGE_REDIRECT = "redirect:/movies/infoById/";
    public static final String MOVIE_EDIT_PAGE_REDIRECT = "redirect:/movies/edit";
    public static final String MOVIE_ADD_PAGE_FORWARD = "movies/add";

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

    @GetMapping("/info/{title}")
    public String details(Model model, @PathVariable String title) {
        String name = title.replaceAll("\\+", " ");
        Movie movie = movieService.findByName(name);
        if (movie == null)
            return HOME_PAGE_REDIRECT;


        model.addAttribute("movie", movie);
        return MOVIE_MOVIE_PAGE_FORWARD;
    }
//    @GetMapping("/favourites/for/userId=/{id}")
//            public String favouritesList(Model model, @PathVariable Integer id) {
//
//    Movie movie = movieService.findByName(name);
//        if (movie == null)
//            return HOME_PAGE_REDIRECT;
//
//
//        model.addAttribute("movie", movie);
//        return MOVIE_MOVIE_PAGE_FORWARD;
//}


    @GetMapping("/edit/{title}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable String title) {
        String name = title.replaceAll("\\+", " ");

        model.addAttribute("movie", movieService.findByName(name));
        return MOVIE_EDIT_PAGE_FORWARD;
    }
//    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String editById(Model model, @PathVariable Object id) {
//
//     //   model.addAttribute("movie", movieService.findOne(Integer.parseInt(id)));
//        return MOVIE_EDIT_PAGE_FORWARD;
//    }


    @RequestMapping(path = "/modify/{id}", method = RequestMethod.GET)
    public String displayPageForModification(@PathVariable final Integer id, final Model model) {
        model.addAttribute("movie", movieService.findOne(id));
        int count = this.movieService.findOne(id).getComments().size();
        model.addAttribute("comments_count", count);
        Integer lala = 1;

        return MOVIE_EDIT_PAGE_FORWARD;
    }


    @RequestMapping(path = "/infoById/{id}", method = RequestMethod.GET)
    public String displayInfoPage(@PathVariable final Integer id, Model model) {
        Movie movie = this.movieService.findOne(id);
        model.addAttribute("movie", movie);
        int commentsCounter = movie.getComments().size();
        model.addAttribute("comments_count", commentsCounter);
        return MOVIE_INFO_PAGE_FORWARD;
    }

    @RequestMapping(path = "/add/comment", method = RequestMethod.POST)
    public String addComment(AddCommentDTO addCommentDTO, final Model model, Principal principal) {
        if (this.commentService.validate(addCommentDTO) != null) {
            return HOME_PAGE_REDIRECT;
        }
        Movie movie = this.movieService.findOne(addCommentDTO.getMovieId());

        Comment comment = new Comment(movie,
                this.userService.findByEmail(principal.getName()), addCommentDTO.getContent());

        this.commentService.saveAndFlush(comment);
        movie.addComment(comment);
        model.addAttribute("movie", movie);
        this.movieService.saveAndFlush(movie);


        return MOVIE_INFO_PAGE_REDIRECT + movie.getId();
    }

    @RequestMapping(path = "/delete/comment/{id}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable final Integer id) {
        Comment comment = this.commentService.findOne(id);
        int movieId = comment.getMovie().getId();
        this.commentService.delete(this.commentService.findOne(id));
        return MOVIE_INFO_PAGE_REDIRECT + movieId;

    }

//    @PostMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String editPOST(Model model, @PathVariable Integer id, MovieDTO movieDTO) {
//        if (id == null){
//            return MOVIE_EDIT_PAGE_REDIRECT;
//        }
//        Movie movie = movieService.findOne(id);
//
////        model.addAttribute("movie", movieService.findByName(name));
//        return MOVIE_EDIT_PAGE_FORWARD;
//    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("actors", actorsService.findAll());
        return MOVIE_ADD_PAGE_FORWARD;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addPost(MovieDTO movieDTO, final Model model, Principal principal) {

        Movie movie = MovieEntityFactory.transferDTOtoEntity(movieDTO);
        movie.addActor(actorsService.findByFinalName(movieDTO.getActors()));
        movie.addCategory(categoryService.findByName(movieDTO.getCategory()));

        movieService.saveAndFlush(movie);
        return HOME_PAGE_REDIRECT;
    }
}
