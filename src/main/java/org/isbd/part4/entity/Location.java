package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

@Data
@javax.persistence.Entity
public class Location {
    @GeneratedValue
    @Id
    private int id;
    @ManyToMany
    private Set<Location> nearLocations;
    private String name;





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Objects.equals(nearLocations, location.nearLocations) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nearLocations, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", nearLocations=" + nearLocations +
                ", name='" + name + '\'' +
                '}';
    }
}
