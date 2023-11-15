package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.exception.InitializationException;
import com.pu.elektronendomoupravitel.exception.NotFoundException;
import com.pu.elektronendomoupravitel.model.Apartment;
import com.pu.elektronendomoupravitel.model.Building;
import com.pu.elektronendomoupravitel.repositories.BuildingRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BuildingService {

    private final BuildingRepo buildingRepo;
    private final ApartmentService apartmentService;

    Logger logger = LoggerFactory.getLogger(BuildingService.class);

    public BuildingService(BuildingRepo buildingRepo, ApartmentService apartmentService) {
        this.buildingRepo = buildingRepo;
        this.apartmentService = apartmentService;
    }

    public List<Building> fetchBuildings() {
        return buildingRepo.findAll();
    }

    public Building getBuildingById(long id) throws NotFoundException {
        if (buildingRepo.existsById(id)) {
            return buildingRepo.findById(id).get();
        } else {
            logger.error("Building with id " + id + " was not found.");
            throw new NotFoundException("Building with id " + id + " was not found.");
        }
    }

    public Building newBuilding(@RequestBody Building building) throws InitializationException {
        if (building.getAddress().isEmpty()) {
            logger.error(building.getClass() + " address is empty.");
            throw new InitializationException(building.getClass() + " address is empty.");
        }
        if (building.getFloorsNumber() == 0 || building.getFloorsNumber() < 0) {
            logger.error(building.getClass() + " can not have zero or negative number floors.");
            throw new InitializationException(building.getClass() + " can not have zero or negative number floors.");
        }
        if (building.getApartmentsNumber() == 0 || building.getApartmentsNumber() < 0 || building.getApartmentsNumber() < building.getFloorsNumber()) {
            logger.error(building.getClass() + " can not have zero, negative number or number of apartments smaller than number of floors.");
            throw new InitializationException(building.getClass() + " can not have zero, negative number or number of apartments smaller than number of floors.");
        }
        if (building.getArea() == 0 || building.getArea() < 0) {
            logger.error(building.getClass() + " can not have zero or negative number occupied area.");
            throw new InitializationException(building.getClass() + " can not have zero or negative number occupied area.");
        }

        Building newBuilding = buildingRepo.save(building);

        List<Apartment> apartments = new ArrayList<>();
//        Apartment apartment = new Apartment();
        if (building.getApartmentsNumber() % building.getFloorsNumber() == 0) {
            for (int i = 0; i < building.getFloorsNumber(); i++) {
                for (int j = 0; j < building.getApartmentsNumber() / building.getFloorsNumber(); j++) {
                    Apartment apartment = new Apartment();
//                    int finalI = i;
//                    int finalJ = j;
//                    buildingRepo.findById(newBuilding.getId()).map(build -> {
//                        apartment.setNumber(Integer.parseInt((finalI +1) + "" + (finalJ +1)));
//                        apartment.setArea(building.getArea() / building.getApartmentsNumber());
//                        apartment.setFloor((short) (finalI + 1));
//                        apartment.setBuilding(build);
////                        apartmentService.newApartment(apartment);
//                        apartments.add(apartmentService.newApartment(apartment));
//                        return null;
//                    });
                    apartment.setNumber(Integer.parseInt((i+1) + "" + (j+1)));
                    apartment.setArea(building.getArea() / building.getApartmentsNumber());
                    apartment.setFloor((short) (i+1));
                    apartment.setBuilding(building);
                    apartments.add(apartmentService.newApartment(apartment));
                }
            }
        } else {
            int residue = building.getApartmentsNumber() % building.getFloorsNumber();
            for (int i = 0; i < residue; i++) {
                Apartment apartment = new Apartment();
//                int finalI = i;
//                buildingRepo.findById(building.getId()).map(build -> {
//                    apartment.setNumber(Integer.parseInt((1) + "" + ((building.getApartmentsNumber() - residue) % building.getFloorsNumber() + finalI)));
//                    apartment.setArea(building.getArea() / building.getApartmentsNumber());
//                    apartment.setFloor((short) (finalI + 1));
//                    apartment.setBuilding(build);
//                    apartmentService.newApartment(apartment);
////                    apartments.add(apartmentService.newApartment(apartment));
//                    return null;
//                });
                apartment.setNumber(Integer.parseInt((1) + "" + ((building.getApartmentsNumber() - residue) % building.getFloorsNumber() + i)));
                apartment.setArea(building.getArea() / building.getApartmentsNumber());
                apartment.setFloor((short) 1);
                apartment.setBuilding(building);
                apartments.add(apartmentService.newApartment(apartment));
            }
            for (int i = 0; i < building.getFloorsNumber(); i++) {
                for (int j = 0; j < (building.getApartmentsNumber() - residue) / building.getFloorsNumber(); j++) {
                    Apartment apartment = new Apartment();
//                    int finalI = i;
//                    int finalJ = j;
//                    buildingRepo.findById(building.getId()).map(build -> {
//                        apartment.setNumber(Integer.parseInt(String.valueOf(finalI +1) + "" + (finalJ +1)));
//                        apartment.setArea(building.getArea() / building.getApartmentsNumber());
//                        apartment.setFloor((short) (finalI + 1));
//                        apartment.setBuilding(build);
//                        apartmentService.newApartment(apartment);
////                        apartments.add(apartmentService.newApartment(apartment));
//                        return null;
//                    });
                    apartment.setNumber(Integer.parseInt((i+1) + "" + (j+1)));
                    apartment.setArea(building.getArea() / building.getApartmentsNumber());
                    apartment.setFloor((short) (i+1));
                    apartment.setBuilding(building);
                    apartments.add(apartmentService.newApartment(apartment));
                }
            }

        }
        building.setApartments(apartments);


        return newBuilding;
    }

    public Optional<Building> updateBuilding(long id, @RequestBody Building building) throws InitializationException, NotFoundException {
        if (buildingRepo.existsById(id)) {
            if (building.getAddress().isEmpty()) {
                logger.error(building.getClass() + " address is empty.");
                throw new InitializationException(building.getClass() + " address is empty");
            }
            if (building.getFloorsNumber() == 0 || building.getFloorsNumber() < 0 || building.getFloorsNumber() > building.getApartmentsNumber()) {
                logger.error(building.getClass() + " can not have zero, negative number floors or number of floors higher than number of apartments.");
                throw new InitializationException(building.getClass() + " can not have zero, negative number floors or number of floors higher than number of apartments.");
            }
            if (building.getApartmentsNumber() == 0 || building.getApartmentsNumber() < 0 || building.getApartmentsNumber() < building.getFloorsNumber()) {
                logger.error(building.getClass() + " can not have zero, negative number or number of apartments smaller than number of floors.");
                throw new InitializationException(building.getClass() + " can not have zero, negative number or number of apartments smaller than number of floors.");
            }
            if (building.getArea() == 0 || building.getArea() < 0) {
                logger.error(building.getClass() + " can not have zero or negative number occupied area.");
                throw new InitializationException(building.getClass() + " can not have zero or negative number occupied area.");
            }

            return buildingRepo.findById(id).map(updateBuild->{return buildingRepo.save(building);});
        } else {
            throw new NotFoundException("Building with id " + id + " was not found.");
        }
    }

    public void deleteBuildingById(long id) {
//        for ()
        buildingRepo.deleteById(id);
    }

    public void deleteBuildings() {
        buildingRepo.deleteAll();
    }

}
