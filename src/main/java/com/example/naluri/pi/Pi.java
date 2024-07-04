package com.example.naluri.pi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table
public class Pi {

    @Id
    @SequenceGenerator(
            name = "pi_sequence",
            sequenceName = "pi_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pi_sequence"
    )
    private int id;

    @Column(name = "summation", columnDefinition = "TEXT")
    private String summation;

    private int iteration;

    @Transient
    private String pi;

    @Transient
    private String sunCircumference;

    @Transient
    private String accuracyValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public String getSummation() {
        return summation;
    }

    public void setSummation(String summation) {
        this.summation = summation;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getSunCircumference() {
        return sunCircumference;
    }

    public void setSunCircumference(String sunCircumference) {
        this.sunCircumference = sunCircumference;
    }

    public String getAccuracyValue() {
        return accuracyValue;
    }

    public void setAccuracyValue(String accuracyValue) {
        this.accuracyValue = accuracyValue;
    }

    @Override
    public String toString() {
        return "Pi{" +
                "id=" + id +
                ", summation=" + summation +
                ", iteration=" + iteration +
                ", pi=" + pi +
                '}';
    }
}
