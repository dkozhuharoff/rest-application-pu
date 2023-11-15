package com.pu.elektronendomoupravitel.repositories;

import com.pu.elektronendomoupravitel.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepo extends JpaRepository<Apartment, Long> {
}
