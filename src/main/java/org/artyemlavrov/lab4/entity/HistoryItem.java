package org.artyemlavrov.lab4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@javax.persistence.Entity
@Table(name="history_items")
public class HistoryItem {
    @Id
    @GeneratedValue
    private Long id;

    @Min(value = -2, message = "Значение не должно быть меньше -2!")
    @Max(value = 2, message = "Значение не должно быть больше 2!")
    private Double x = 0.0;

    @Min(value = -5, message = "Значение Y не должно быть меньше -3!")
    @Max(value = 3, message = "Значение не должно быть больше 3!")
    private Double y = 0.0;

    @Min(value = 1, message = "Значение не должно быть меньше 1!")
    @Max(value = 4, message = "Значение не должно быть больше 4!")
    private Double r = 1.0;

    @Column(name = "is_hit")
    private boolean isHit;

    @ManyToOne
    @JsonIgnore
    private Account user;

    public HistoryItem() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Double getR() {
        return r;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getY() {
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}

