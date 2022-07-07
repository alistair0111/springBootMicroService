package com.microservice.ewallet.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    private String email;

    private String mobile;


}
