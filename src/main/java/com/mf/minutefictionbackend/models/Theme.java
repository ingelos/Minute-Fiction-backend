package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theme_sequence")
    @SequenceGenerator(name = "theme_sequence", sequenceName = "theme_sequence", initialValue = 1004, allocationSize = 1)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDate openDate;
    @Column
    private LocalDate closingDate;


    @OneToMany(mappedBy = "theme")
    private List<Story> stories;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public List<Story> getStories() {
        return stories;
    }
}
