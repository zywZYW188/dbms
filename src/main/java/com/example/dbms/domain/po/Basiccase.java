package com.example.dbms.domain.po;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "basic_case")
public class Basiccase implements Serializable {
    private static final long serialVersionUID = -339516038496531953L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "patient_id", nullable = false)
    private int patient_id;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @Column(name = "smoke_level")
    private int smoke_level;

    @Column(name = "drink_level")
    private int drink_level;

    @Column(name = "genetic_history")
    private String genetic_history;

    @Column(name = "drug_history")
    private String drug_history;

    public Basiccase() {
    }
    public Basiccase(int id, int patient_id, int height, int weight, int smoke_level,
                     int drink_level, String genetic_history, String drug_history) {
        this.id = id;
        this.patient_id = patient_id;
        this.height = height;
        this.weight = weight;
        this.smoke_level = smoke_level;
        this.drink_level = drink_level;
        this.genetic_history = genetic_history;
        this.drug_history = drug_history;
    }
    public Basiccase(int patient_id, int height, int weight, int smoke_level,
                     int drink_level, String genetic_history, String drug_history) {
        this.patient_id = patient_id;
        this.height = height;
        this.weight = weight;
        this.smoke_level = smoke_level;
        this.drink_level = drink_level;
        this.genetic_history = genetic_history;
        this.drug_history = drug_history;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSmoke_level() {
        return smoke_level;
    }

    public void setSmoke_level(int smoke_level) {
        this.smoke_level = smoke_level;
    }

    public int getDrink_level() {
        return drink_level;
    }

    public void setDrink_level(int drink_level) {
        this.drink_level = drink_level;
    }

    public String getGenetic_history() {
        return genetic_history;
    }

    public void setGenetic_history(String genetic_history) {
        this.genetic_history = genetic_history;
    }

    public String getDrug_history() {
        return drug_history;
    }

    public void setDrug_history(String drug_history) {
        this.drug_history = drug_history;
    }
}
