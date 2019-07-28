package com.clickbusbackend.philippe;

import com.clickbusbackend.philippe.controllers.PlaceController;
import com.clickbusbackend.philippe.models.Place;
import com.clickbusbackend.philippe.services.PlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PlacesControllerTests {

    private MockMvc mock;


    @InjectMocks
    private PlaceController placeController;

    @Mock
    private PlaceService placeService;

    @Before
    public void setup(){
        mock = MockMvcBuilders.standaloneSetup(placeController)
                .build();
    }

    @Test
    public void savePlace_shouldReturnNewPlace() throws Exception {
        Place place = new Place();
        place.name = "Test Case";
        place.city = "São Paulo";
        place.slug = "http://test";
        place.state = "São Paulo";

        Place added = place;
        added.id = 1;
        added.updatedAt = new Date();
        added.createdAt = new Date();

        when(placeService.create(any(Place.class))).thenReturn(added);

        mock.perform(post("/places/save")
                        .content(toJson(place))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("New place saved")))
                .andExpect(jsonPath("$.data.id", Matchers.is(1)))
                .andReturn();
    }

    @Test
    public void updatePlace_shouldReturnUpdatedPlace() throws Exception{
        Place place = new Place();
        place.id = 1;
        place.createdAt = new Date();
        place.updatedAt = new Date();
        place.name = "shouldReturnUpdatedPlace";
        place.city = "São Paulo";
        place.slug = "http://test";
        place.state = "São Paulo";

        when(placeService.update(any(Place.class))).thenReturn(place);

        Place request = place;
        request.name = "Update Test Case";

        mock.perform(put("/places/update")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is(String.format("The place %d was successfully updated", request.id))))
                .andExpect(jsonPath("$.data.name", Matchers.is("Update Test Case")))
                .andReturn();
    }

    @Test
    public void getPlaces_shouldReturnEmptyList() throws Exception{
        mock.perform(get("/places/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("The places list returns empty")))
                .andExpect(jsonPath("$.data", Matchers.hasSize(0)))
                .andReturn();
    }

    @Test
    public void getPlaces_shouldReturnNonEmptyList() throws Exception {
        List<Place> places = new ArrayList<>();

        for (int i=0; i < 10; i++) {
            Place place = new Place();
            place.id = i;
            place.name = String.format("Place $d from tests", i);
            place.state = "state " + i;
            place.slug = "http://testcases/" + i;
            place.city = "City " + i;
            place.createdAt = new Date();
            place.updatedAt = new Date();
            places.add(place);
        }

        when(placeService.getAll()).thenReturn(places);

        mock.perform(get("/places/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Places listed with success")))
                .andExpect(jsonPath("$.data", Matchers.hasSize(10)))
                .andReturn();
    }

    @Test
    public void placeById_shoudReturnOnePlace() throws Exception {
        mock.perform(get("/places/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Place found with success")))
                .andExpect(jsonPath("$.data", Matchers.isEmptyOrNullString()))
                .andReturn();
    }

    @Test
    public void deletePlace_shouldNotFoundPlace() throws Exception {
        mock.perform(delete("/places/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Place 1 was deleted")))
                .andReturn();
    }

    public static String toJson(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
