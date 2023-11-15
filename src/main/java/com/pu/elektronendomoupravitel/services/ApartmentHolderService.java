package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.model.ApartmentHolder;
import com.pu.elektronendomoupravitel.repositories.ApartmentHolderRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApartmentHolderService {

    private final ApartmentHolderRepo apartmentHolderRepo;

    public ApartmentHolderService(ApartmentHolderRepo apartmentHolderRepo) {
        this.apartmentHolderRepo = apartmentHolderRepo;
    }

    public List<ApartmentHolder> fetchApartmentHolders() {
        return apartmentHolderRepo.findAll();
    }

    public Optional<ApartmentHolder> getApartmentHolderById(long id) {
        return apartmentHolderRepo.findById(id);
    }

    public ApartmentHolder getApartmentHolderByName(String name) {
        return apartmentHolderRepo.findByName(name);
    }

    public ApartmentHolder newApartmentHolder(@RequestBody ApartmentHolder apartmentHolder) {
        return apartmentHolderRepo.save(apartmentHolder);
    }

    public Optional<ApartmentHolder> updateApartmentHolder(long id, @RequestBody ApartmentHolder apartmentHolder) {
        return apartmentHolderRepo.findById(id).map(updateApartHolder->{return apartmentHolderRepo.save(apartmentHolder);});
    }

    public void deleteApartmentHolderById(long id) {
        apartmentHolderRepo.deleteById(id);
    }

    public void deleteApartmentHolders() {
        apartmentHolderRepo.deleteAll();
    }

}
