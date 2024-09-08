package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
//@IdClass(AuthorityKey.class)
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

//    @Id
//    @Column(nullable = false)
//    private String username;

    @Id
    @Column(nullable = false)
    private String authority;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<User> users;

    public Authority(String authority) {
        this.authority = authority;
    }

//    public Authority(String username, String authority) {
//        this.username = username;
//        this.authority = authority;
//    }


    //    public Authority(String authority, String username) {
//        this.authority = authority;
//        this.username = username;
//    }

//    public Authority(String authority) {
//        this.authority = authority;
//    }




}
