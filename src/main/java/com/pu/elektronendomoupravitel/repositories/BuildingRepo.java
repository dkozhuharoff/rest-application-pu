package com.pu.elektronendomoupravitel.repositories;

import com.pu.elektronendomoupravitel.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepo extends JpaRepository<Building, Long> {
}
