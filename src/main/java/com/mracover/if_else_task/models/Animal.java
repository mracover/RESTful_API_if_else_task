package com.mracover.if_else_task.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(name = "animal_types_manyToMany",
            joinColumns = @JoinColumn(name = "animalType_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private List<AnimalType> animalTypeList;

    @Column
    private float weight;

    @Column
    private float length;

    @Column
    private float height;

    @Column
    private String gender;

    @Column
    private String lifeStatus;

    @Column
    private OffsetDateTime chippingDateTime;

    @Column
    private Integer chippingDateTimeNano;

    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "chipperId")
    private Account chipperId;

    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "chippingLocationId")
    private LocationPoint chippingLocationId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animal", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AnimalVisitedLocation> animalVisitedLocationList;

    @Column
    private OffsetDateTime deathDateTime;

    @Column
    private Integer deathDateTimeNano;
}
