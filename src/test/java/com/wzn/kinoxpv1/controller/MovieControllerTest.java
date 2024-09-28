package com.wzn.kinoxpv1.controller;



import com.wzn.kinoxpv1.entity.Movie;
import com.wzn.kinoxpv1.exception.MovieNotFoundException;
import com.wzn.kinoxpv1.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class) // Denne annotation tester kun web-laget, specifikt MovieController-klassen.
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Bruges til at simulere HTTP-anmodninger til controlleren.

    @MockBean
    private MovieService movieService; // Mock MovieService, så vi kan kontrollere dens opførsel i testene.

    @Test
    public void testGetAllMovies() throws Exception {
        Movie movie1 = new Movie(1L, "Inception", "Sci-Fi", 148, 13);
        Movie movie2 = new Movie(2L, "The Godfather", "Crime", 175, 17);
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Simulerer, at movieService.getAllMovies() returnerer listen af film.
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);

        // Simulerer en GET-anmodning til "/api/movies" og forventer et JSON-svar.
        mockMvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)) // Angiver, at vi forventer JSON som svarformat.
                .andExpect(status().isOk()) // Forventer HTTP-status 200 (OK).
                .andExpect(jsonPath("$[0].title").value("Inception")) // Tjekker, at den første films titel er "Inception".
                .andExpect(jsonPath("$[1].title").value("The Godfather")); // Tjekker, at den anden films titel er "The Godfather".
    }
    @Test
    public void testGetMovieById_MovieExists() throws Exception {
        // Arrange: Set up mock movie
        Movie movie = new Movie(1L, "Inception", "Sci-Fi", 148, 13);
        Mockito.when(movieService.getMovieById(1L)).thenReturn(movie);

        // Act & Assert: Perform GET and verify 200 OK and movie details
        mockMvc.perform(get("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(jsonPath("$.title").value("Inception"))  // Check title
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))  // Check genre
                .andExpect(jsonPath("$.duration").value(148))  // Check duration
                .andExpect(jsonPath("$.ageLimit").value(13));  // Check age limit
    }

    @Test
    public void testGetMovieById_MovieNotFound() throws Exception {
        // Arrange: Set up mock to throw MovieNotFoundException
        Mockito.when(movieService.getMovieById(1L)).thenThrow(new MovieNotFoundException("Movie not found with id: 1"));

        // Act & Assert: Perform GET and verify 404 Not Found
        mockMvc.perform(get("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Expect 404 Not Found
    }
}
