package com.catalog.movies.web.rest;

import com.catalog.movies.core.actors.Actor;
import com.catalog.movies.core.actors.ActorService;
import com.catalog.movies.core.categories.Category;
import com.catalog.movies.core.categories.CategoryService;
import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.movies.MovieDTO;
import com.catalog.movies.core.movies.MovieEntityFactory;
import com.catalog.movies.core.movies.MovieService;
import com.catalog.movies.core.users.User;
import com.catalog.movies.core.users.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rest")
public class DataController {
    @Autowired
    MovieService movieService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ActorService actorService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/greeting")
    public String Greeting() {
        String result = "Message From SpringBoot Service - Hello World!" + new Date().toString();
        return result;
    }

    @GetMapping("/add/favourite/{id}")
    public String[] addFavourite(@PathVariable Integer id, Model model, Principal principal) {
        Movie movie = movieService.findOne(id);
        String[] result = new String[2];
        User user = userService.findByEmail(principal.getName());
        if (user.getFavourites().contains(movie)) {
            user.getFavourites().remove(movie);
            userService.saveAndFlush(user);
            result[0] = movie.getName();
            result[1] = " successfully removed from favourites";
            return result;

        }
        user.addMovie(movie);
        userService.saveAndFlush(user);
        result[0] = movie.getName();
        result[1] = " successfully added to favourites";
        return result;
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/favourites", method = RequestMethod.GET)
    public Page<Movie> favouriteList(Model model, Principal principal, @RequestParam("page") Integer page,
                                     @RequestParam(name = "size", defaultValue = "8") Integer size) throws IOException {
        User currentUser = userService.findByEmail(principal.getName());
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC, "id");

        return this.movieService.findAllByUsers_Id(userService.findByEmail(principal.getName()).getId(), pageRequest);
    }

    @GetMapping("/count/movies/{id}")
    public Integer asd(@PathVariable Integer id) {

        return movieService.countMoviesByUsers_Id(id);
    }

    @GetMapping("/info/{title}")
    public Movie details(@PathVariable String title) {
        title = title.replaceAll("\\+", " ");
        return movieService.findByName(title);
    }

    @GetMapping("/movie/delete/{id}")
    public String[] details(@PathVariable Integer id) {
//        Integer tmpId = Integer.parseInt(id.replaceAll("=", ""));
        String[] result = new String[2];

        Movie movie = movieService.findOne(id);
        List<User> allUsers = userService.findAll();
        for (User user : allUsers) {
            if (user.getFavourites().contains(movie)) {
                user.getFavourites().remove(movie);
            }
        }

        movieService.delete(id);
        result[0] = movie.getName();
        result[1] = " successfully deleted";
        return result;
    }

    @GetMapping(value = "/movies")
    public Page<Movie> movies(@RequestParam("actorName") final String actorName, @RequestParam("categoryName") final String categoryName, @RequestParam("page") Integer page,
                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "id");

        if (actorName.isEmpty() && categoryName.isEmpty()){
            return movieService.findAll(pageRequest);
        }
        if (actorName.isEmpty() || actorName == null) {
            return movieService.findAllByCategories_Name(categoryName, pageRequest);
        } else if (categoryName.isEmpty() || categoryName == null) {
            return movieService.findAllByActors_Name(actorName, pageRequest);
        }

        return movieService.findAll(pageRequest);
    }

    @GetMapping(value = "/category")
    public Set<Movie> categories(@RequestParam("name") String name) {
        return categoryService.findByName(name).getMovies();
    }

    @GetMapping("/change-rating")
    public String change() {
        List<Movie> movies = this.movieService.findAll();

        for (Movie movie : movies) {
            Random r = new Random();
            double randomValue = 0.0 + (10.0 - 0.0) * r.nextDouble();
            movie.setRating(randomValue);
            this.movieService.saveAndFlush(movie);
        }

        return "done";
    }

    @GetMapping("/test")
    public Page<Movie> testMethod(@RequestParam("page") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC, "id");

//        return movieService.findAllByCategory(categoryService.findByName("Comedy"), pageRequest);
        return movieService.findAll(pageRequest);
    }


    //    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @RequestMapping(value = "/test3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void pehso(@RequestBody final MovieDTO movie) throws IOException {
        Movie movieEntity = MovieEntityFactory.transferDTOtoEntity(movie);
        if (movie.getCategory() != null) {
            String[] categoriesNames = movie.getCategory().split(", ");
            for (String categoryName : categoriesNames) {
                if (categoryService.findByName(categoryName) == null) {
                    Category category = categoryService.saveAndFlush(new Category(categoryName));
                    movieEntity.addCategory(category);
                } else {
                    movieEntity.addCategory(categoryService.findByName(categoryName));
                }
            }
        }
        if (movie.getActors() != null) {
            String[] actorsArr = movie.getActors().split(", ");
            for (String actName : actorsArr) {
                if (actorService.findByFinalName(actName) == null) {
                    Actor actor = actorService.saveAndFlush(new Actor(actName));
                    movieEntity.addActor(actor);
                } else {
                    movieEntity.addActor(actorService.findByFinalName(actName));
                }
            }
        }


        movieEntity.setTrailer_url(getMovieTrailer(movieEntity.getName()));
        movieService.saveAndFlush(movieEntity);
        int pesho = 0;
    }

    @GetMapping("/test2")
    public static String getMovieDB(@RequestParam("title") String title) throws Exception {
        String inputLine = "";
        title = title.replaceAll(" ", "+");
        URL yahoo = new URL("http://www.omdbapi.com/?t=" + title + "&apikey=BanMePlz");

        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));


        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            //    jsonObj = new JSONObject(inputLine);}
            //  String test = jsonObj.getString("title");
            //    System.out.println(test);}
            return inputLine;


        }
        return inputLine;

    }

    public String getMovieTrailer(String filmName) throws IOException {
        filmName = filmName.replaceAll(" ", "+");
        filmName = filmName + "+trailer";
        String inputLine = "";
        String line = "";

        String urlYOUTUBE = "https://www.youtube.com/results?search_query=" + filmName;
        URL youtube = new URL(urlYOUTUBE);
        URLConnection yc = youtube.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        while ((line = in.readLine()) != null) {
            inputLine += line;
        }

        inputLine = inputLine.split("context-item-id=\"")[1];
        inputLine = inputLine.split("\"")[0];
        return inputLine;
    }
}
