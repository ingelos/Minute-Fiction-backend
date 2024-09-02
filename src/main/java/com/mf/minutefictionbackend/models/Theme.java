package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "themes")
@Getter
@Setter
@NoArgsConstructor
public class Theme {

    @Setter(AccessLevel.NONE)
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

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "theme")
    private List<Story> stories;


}
