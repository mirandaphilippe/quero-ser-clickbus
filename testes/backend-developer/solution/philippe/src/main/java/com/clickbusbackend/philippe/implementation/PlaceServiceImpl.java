package com.clickbusbackend.philippe.implementation;

import com.clickbusbackend.philippe.dao.PlaceDao;
import com.clickbusbackend.philippe.models.Place;
import com.clickbusbackend.philippe.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Override
    public Place create(Place place){
        place.createdAt = new Date();
        place.updatedAt = new Date();
        return placeDao.save(place);
    }

    @Override
    public Place update(Place place){
        place.updatedAt = new Date();
        return placeDao.saveAndFlush(place);
    }

    @Override
    public List<Place> getAll(){
        return placeDao.findAll();
    }

    @Override
    @Query("SELECT p FROM Place p WHERE p.name LIKE :name")
    public List<Place> getByName(@Param("name") String name) {
        return placeDao.findAllByName(name);
    }

    @Override
    public Place getById(Integer placeId){
        return placeDao.getOne(placeId);
    }

    @Override
    public void delete(Integer placeId){
        placeDao.deleteById(placeId);
    }

}
