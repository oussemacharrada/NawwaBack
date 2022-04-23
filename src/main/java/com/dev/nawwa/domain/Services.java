package com.dev.nawwa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Services extends AbstractEntity {



    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER, mappedBy = "service")
    private List<Reservation> reservation = new ArrayList<>();

    @NotNull
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private String serviceImageUrl;



    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY, mappedBy = "service")
    private List<Rating> ratings = new ArrayList<>();
    @NotNull
    private double serviceCost;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private ServiceProviderProfile serviceProvider;



}
