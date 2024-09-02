package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "mailings")
@Getter
@Setter
@NoArgsConstructor
public class Mailing {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailing_sequence")
    @SequenceGenerator(name = "mailing_sequence", sequenceName = "mailing_sequence", initialValue = 1003, allocationSize = 1)
    private Long id;
    @Column
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column
    private LocalDate date;







}
