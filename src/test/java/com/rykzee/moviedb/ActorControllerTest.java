package com.rykzee.moviedb;

import com.rykzee.moviedb.shallow.ShallowActor;
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

import java.util.List;

import static com.rykzee.moviedb.utils.TestHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActorControllerTest {

    @Autowired
    private MockMvc mvc;
    private final String BASE_URL = "/moviedb/actors";

    @Test
    public void getAllActorsTest() throws Exception {
        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(11)));
    }

    @Test
    public void getSpecificActorTest() throws Exception {
        mvc.perform(get(BASE_URL + "/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8"))
                .andExpect(jsonPath("$.name").value("Anne Hathaway"))
                .andExpect(jsonPath("$.movies", hasSize(2)));
    }

    @Test
    public void createActorTest() throws Exception {
        ShallowActor actorToCreate = new ShallowActor(0, "Leonardo DiCaprio");
        mvc.perform(post(BASE_URL)
                    .content(asJsonString(actorToCreate))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("12"));

        mvc.perform(get(BASE_URL + "/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Leonardo DiCaprio"));
    }

   @Test
   public void addActorsToMovieTest() throws Exception {
       ShallowActor actor1 = new ShallowActor(0, "Leonardo DiCaprio");
       ShallowActor actor2 = new ShallowActor(0, "Kate Winslet");

       ShallowMovie movie = new ShallowMovie(0, "Titanic", 1997);
       String createMovieURL = "/moviedb/movies";
       mvc.perform(post(BASE_URL)
                       .content(asJsonString(actor1))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

       mvc.perform(post(BASE_URL)
                       .content(asJsonString(actor2))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

       mvc.perform(post(createMovieURL)
                       .content(asJsonString(movie))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

       mvc.perform(put(BASE_URL + "/addActorsToMovie/5")
                       .content(asJsonString(List.of("12", "13")))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Titanic"))
               .andExpect(jsonPath("$.actors", hasSize(2)));
   }
}
