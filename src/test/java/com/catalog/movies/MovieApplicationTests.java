package com.catalog.movies;

import com.catalog.movies.core.categories.Category;
import com.catalog.movies.core.categories.CategoryRepository;
import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.movies.MovieRepository;
import com.catalog.movies.core.users.User;
import com.catalog.movies.core.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieApplicationTests {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	public void contextLoads() {

		List<Movie> movieList = movieRepository.findAll();
//		List<User> userList = userRepository.findAll();
//		Category category = categoryRepository.findOne(2);
//		List<Movie> movieList1 = movieRepository.findAllByCategory(category);
		int sd = 0;
	}

}
