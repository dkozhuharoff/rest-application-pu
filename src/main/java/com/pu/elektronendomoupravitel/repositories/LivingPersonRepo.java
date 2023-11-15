package com.pu.elektronendomoupravitel.repositories;

import com.pu.elektronendomoupravitel.model.LivingPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivingPersonRepo extends JpaRepository<LivingPerson, Long> {
}
