package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.exception.NotFoundException;
import com.pu.elektronendomoupravitel.model.Apartment;
import com.pu.elektronendomoupravitel.repositories.ApartmentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ApartmentService {

    private final ApartmentRepo apartmentRepo;

    Logger logger = LoggerFactory.getLogger(ApartmentService.class);

    public ApartmentService(ApartmentRepo apartmentRepo) {
        this.apartmentRepo = apartmentRepo;
    }

    public Apartment getApartmentById(long id) throws NotFoundException {
        if (apartmentRepo.existsById(id)) {
            return apartmentRepo.findById(id).get();
        } else {
            logger.error("Apartment with id " + id + " was not found.");
            throw new NotFoundException("Apartment with id " + id + " was not found.");
        }
    }

    public Apartment newApartment(@RequestBody Apartment apartment) {
        return apartmentRepo.save(apartment);
    }

    public Optional<Apartment> updateApartment(long id, @RequestBody Apartment apartment) {
        return apartmentRepo.findById(id).map(updateApart->{return apartmentRepo.save(apartment);});
    }

    public void deleteApartment(long id) {
        apartmentRepo.deleteById(id);
    }

    public void deleteApartments() {
        apartmentRepo.deleteAll();
    }

}
