package com.mracover.if_else_task.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animalVisitedLocations")
public class AnimalVisitedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private OffsetDateTime dateTimeOfVisitLocationPoint;

    @Column
    private Integer dateTimeOfVisitLocationPointNano;

    @OneToOne (cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "locationPointId")
    private LocationPoint locationPointId;

    @ManyToOne(
            cascade = {
                    CascadeType.REFRESH,
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }, fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
