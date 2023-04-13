package com.mracover.if_else_task.models.areaModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ares")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="areaPoints",
            joinColumns=@JoinColumn(name="area_id")
    )
    private List<AreaPoint> areaPoints = new ArrayList<>();



}
