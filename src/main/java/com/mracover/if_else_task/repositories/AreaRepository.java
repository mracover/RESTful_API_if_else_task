package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.areaModels.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

}
