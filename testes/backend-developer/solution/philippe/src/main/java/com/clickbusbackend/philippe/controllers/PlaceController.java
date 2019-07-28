package com.clickbusbackend.philippe.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.clickbusbackend.philippe.models.*;
import com.clickbusbackend.philippe.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("places")
public class PlaceController {
    @Autowired private PlaceService service;

    @PostMapping("/save")
    public AppResponse save(@RequestBody Place place){
        try {
            place = service.create(place);
            return new AppResponse(true,"New place saved", place);
        } catch (Exception e){
            return new AppResponse(false, "Error while saving a new place", e.toString());
        }
    }

    @PutMapping("/update")
    public AppResponse update(@RequestBody Place place){
        try {
            place = service.update(place);
            return new AppResponse(true,String.format("The place %d was successfully updated", place.id), place);
        } catch (Exception e){
            return new AppResponse(false, String.format("Error while updating place %d", place.id), e);
        }
    }

    @GetMapping("/name/{placeName}")
    public AppResponse getByName(@PathVariable(name = "placeName") String placeName){
        try {
            List<Place> places = service.getByName(placeName);
            return new AppResponse(true, "", places);
        } catch (Exception e) {
            return new AppResponse(false, "", e);
        }
    }

    @GetMapping("/list")
    public AppResponse getAll(){
        try {
            List<Place> places = service.getAll();

            if(places.size() < 1){
                return new AppResponse(true,"The places list returns empty", places);
            }

            return new AppResponse(true,"Places listed with success", places);
        } catch (Exception e){
            return new AppResponse(false, "Some error occurred while getting places list", e);
        }
    }

    @GetMapping("/{id}")
    public AppResponse getById(@PathVariable(name="id") Integer id){
        try {
            Place place = service.getById(id);
            return new AppResponse(true,"Place found with success",place);
        }  catch (Exception e){
            return new AppResponse(false, String.format("Error to find place with id %d", id));
        }
    }

    @DeleteMapping("delete/{id}")
    public AppResponse delete(@PathVariable(name="id") Integer id){
        try {
            service.delete(id);
            return new AppResponse(true, String.format("Place %d was deleted", id));
        } catch (EmptyResultDataAccessException empty) {
            return new AppResponse(false, String.format("There is no place with id %d", id));
        } catch (Exception e){
            System.out.println(e);
            return new AppResponse(false, String.format("Error deleting the place %d", id));
        }
    }

}
