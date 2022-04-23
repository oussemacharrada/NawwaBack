package com.dev.nawwa.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "serviceproviderprofile")
public class ServiceProviderProfile  extends AbstractEntity {
    @JsonBackReference
    @OneToOne
    private User account;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    private String email;

    private String phoneNumber;

    private String pictureLink;

    private String about;

    private String CertificatePictureLink;

    private Long numberOfbids;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY, mappedBy = "serviceProvider")
    private List<Services> services = new ArrayList<>();


}
