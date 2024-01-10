package com.rykzee.moviedb;

import com.rykzee.moviedb.shallow.ShallowMovie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.rykzee.moviedb.utils.TestHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;
    private final String BASE_URL = "/moviedb/movies";

    @Test
    public void getAllMoviesTest() throws Exception {
        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getSpecificMovieTest() throws Exception {
        mvc.perform(get(BASE_URL + "/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("Interstellar"))
                .andExpect(jsonPath("$.year").value(2014))
                .andExpect(jsonPath("$.actors", hasSize(4)));
    }

    @Test
    public void createActorTest() throws Exception {
        ShallowMovie movieToCreate = new ShallowMovie(0, "Titanic", 1997);
        mvc.perform(post(BASE_URL)
                        .content(asJsonString(movieToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("5"));

        mvc.perform(get(BASE_URL + "/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Titanic"))
                .andExpect(jsonPath("$.year").value(1997));
    }
}
