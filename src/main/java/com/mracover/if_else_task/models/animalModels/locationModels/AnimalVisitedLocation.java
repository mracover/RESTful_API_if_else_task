package com.mracover.if_else_task.models.animalModels.locationModels;

import javax.persistence.*;

import com.mracover.if_else_task.models.animalModels.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    private String dateTimeOfVisitLocationPoint;

    @OneToOne (cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
            })
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "locationPointId")
    private LocationPoint locationPointId;

    @ManyToOne(
            cascade = {
                    CascadeType.REFRESH,
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
