package com.dev.nawwa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private UserProfile client;
    @ManyToOne(fetch = FetchType.EAGER)
    private Services service;
    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER, mappedBy = "reservation")
    private List<Location> locations = new ArrayList<>();
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private DateTime serviceTime;
}
