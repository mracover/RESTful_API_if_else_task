package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

}
