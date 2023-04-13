package com.mracover.if_else_task.models.animalModels;

import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;
import com.mracover.if_else_task.models.userModels.Account;
import com.mracover.if_else_task.models.animalModels.locationModels.LocationPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

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
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "chipperId")
    private Account chipperId;

    @ManyToOne(
            cascade = {
                CascadeType.REFRESH,
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.PERSIST
            })
    @JoinColumn(name = "chippingLocationId")
    private LocationPoint chippingLocationId;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "animal",
            orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<AnimalVisitedLocation> animalVisitedLocationList;

    @Column
    private OffsetDateTime deathDateTime;

    @Column
    private Integer deathDateTimeNano;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id.equals(animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
