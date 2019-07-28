package com.clickbusbackend.philippe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "places")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "slug")
    public String slug;

    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state;

    @Column(name = "created_at")
    public Date createdAt;

    @Column(name = "updated_at")
    public Date updatedAt;

    public Place() {

    }
    public Place(String name, String slug, String state, String city){
        this.name = name;
        this.slug = slug;
        this.state = state;
        this.city = city;
    }
}
