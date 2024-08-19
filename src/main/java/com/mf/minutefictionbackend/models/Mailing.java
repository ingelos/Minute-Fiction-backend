package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "mailings")
public class Mailing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailing_sequence")
    @SequenceGenerator(name = "mailing_sequence", sequenceName = "mailing_sequence", initialValue = 1002, allocationSize = 1)
    private Long id;
    @Column
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column
    private LocalDate date;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
