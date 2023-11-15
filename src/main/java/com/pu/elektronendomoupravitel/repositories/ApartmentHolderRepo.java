package com.pu.elektronendomoupravitel.repositories;

import com.pu.elektronendomoupravitel.model.ApartmentHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentHolderRepo extends JpaRepository<ApartmentHolder, Long> {
    ApartmentHolder findByName(String name);
}
