package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.model.LivingPerson;
import com.pu.elektronendomoupravitel.repositories.LivingPersonRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LivingPersonService {

    private final LivingPersonRepo livingPersonRepo;

    public LivingPersonService(LivingPersonRepo livingPersonRepo) {
        this.livingPersonRepo = livingPersonRepo;
    }

    public List<LivingPerson> fetchLivingPeople() {
        return livingPersonRepo.findAll();
    }

    public Optional<LivingPerson> getLivingPersonById(long id) {
        return livingPersonRepo.findById(id);
    }

    public LivingPerson newLivingPerson(@RequestBody LivingPerson livingPerson) {
        return livingPersonRepo.save(livingPerson);
    }

    public Optional<LivingPerson> updateLivingPerson(long id, @RequestBody LivingPerson livingPerson) {
        return livingPersonRepo.findById(id).map(updateLivingPerson->{return livingPersonRepo.save(livingPerson);});
    }

    public void deleteLivingPersonById(long id) {
        livingPersonRepo.deleteById(id);
    }

    public void deleteLivingPeople() {
        livingPersonRepo.deleteAll();
    }

}
