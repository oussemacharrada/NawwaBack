package com.dev.nawwa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "category")
public class Category extends AbstractEntity {

    @NotNull
    private String name;

    private String categoryImageUrl;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,mappedBy = "category")
    private List<Services> services = new ArrayList<>();


}
