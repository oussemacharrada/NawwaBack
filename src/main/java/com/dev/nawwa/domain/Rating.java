package com.dev.nawwa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating extends AbstractEntity {



    @ManyToOne(fetch = FetchType.EAGER)
    private Services service;


    private int rating;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserProfile client;


    private String comment;

}
