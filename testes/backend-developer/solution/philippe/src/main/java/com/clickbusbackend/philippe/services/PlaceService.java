package com.clickbusbackend.philippe.services;

import com.clickbusbackend.philippe.models.Place;
import java.util.List;

public interface PlaceService {
    Place create(Place place);
    Place update(Place place);
    List<Place> getAll();
    List<Place> getByName(String name);
    Place getById(Integer placeId);
    void delete(Integer placeId);

}
