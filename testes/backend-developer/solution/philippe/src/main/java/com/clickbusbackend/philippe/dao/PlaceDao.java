package com.clickbusbackend.philippe.dao;

import com.clickbusbackend.philippe.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceDao extends JpaRepository<Place, Integer> {
    List<Place> findAllByName(String name);
}
