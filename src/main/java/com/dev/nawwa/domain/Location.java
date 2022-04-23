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
@Table(name = "location")
public class Location extends AbstractEntity {


    @ManyToOne(fetch = FetchType.EAGER)
    private Reservation reservation;

    private String country;

    private String city;

    private String street;

    private String number;

    private double longitude;

    private double latitude;
}
