package com.pu.elektronendomoupravitel.resources;

import com.pu.elektronendomoupravitel.model.Apartment;
import com.pu.elektronendomoupravitel.model.ApartmentHolder;
import com.pu.elektronendomoupravitel.model.Building;
import com.pu.elektronendomoupravitel.services.ApartmentHolderService;
import com.pu.elektronendomoupravitel.services.ApartmentService;
import com.pu.elektronendomoupravitel.services.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class BuildingResource {

    private final BuildingService buildingService;
    private final ApartmentService apartmentService;
    private final ApartmentHolderService apartmentHolderService;

    public BuildingResource(BuildingService buildingService, ApartmentService apartmentService, ApartmentHolderService apartmentHolderService) {
        this.buildingService = buildingService;
        this.apartmentService = apartmentService;
        this.apartmentHolderService = apartmentHolderService;
    }

    @RequestMapping("/buildings")
    public ModelAndView getBuildings(Model model) {
        ModelAndView mav = new ModelAndView("buildings");
        List<Building> buildings = buildingService.fetchBuildings();
        model.addAttribute("buildings", buildings);

        return mav;
    }

    @RequestMapping("/buildings/{id}")
    public ModelAndView getBuildingById(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("view_building");
        try {
            Building buildingById = buildingService.getBuildingById(id);
            mav.addObject("building", buildingById);
        } catch (Exception e) {
            mav.addObject("building", HttpStatus.NOT_FOUND.value());
        }
        return mav;
    }

    @RequestMapping("/create_building")
    public ModelAndView createBuilding(Model model) {
        ModelAndView mav = new ModelAndView("create_building");
        Building building = new Building();
        model.addAttribute("building", building);
        return mav;
    }

    @RequestMapping(value="/buildings", method = RequestMethod.POST)
    public ModelAndView postBuilding(@ModelAttribute("building") Building building) {
        ModelAndView mav = new ModelAndView();
        try {
            buildingService.newBuilding(building);
            mav.setViewName("redirect:" + "/buildings");
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

    @RequestMapping("/buildings/{id}/update")
    public ModelAndView updateBuilding(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("update_building");
        try {
            Building buildingById = buildingService.getBuildingById(id);
            mav.addObject("building", buildingById);
        } catch (Exception e) {
            mav.addObject("building", HttpStatus.NOT_FOUND.value());
        }
        return mav;
    }

    @RequestMapping(value="/buildings/update")
    public ModelAndView putBuilding(@ModelAttribute("building") Building building) {
        ModelAndView mav = new ModelAndView();
        try {
            buildingService.updateBuilding(building.getId(), building);
            mav.setViewName("redirect:" + "/buildings");
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

    @RequestMapping("/buildings/{id}/delete")
    public ModelAndView deleteBuildingById(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView();
        try {
            Building buildingById = buildingService.getBuildingById(id);
            List<Apartment> apartments = buildingById.getApartments();
            for (Apartment apartment : apartments) {
                if (apartment.getApartmentHolder() != null) {
                    ApartmentHolder apartmentHolder =
                            apartmentHolderService.getApartmentHolderByName(
                                    apartment
                                            .getApartmentHolder()
                                            .getName()
                            );
                    List<Apartment> apartmentsHolder = apartmentHolder.getApartments();
                    apartmentsHolder.remove(apartment);
                    apartmentHolder.setApartments(apartmentsHolder);
                    apartmentHolderService.updateApartmentHolder(apartmentHolder.getId(), apartmentHolder);
                }
                apartment.setBuilding(null);
                apartment.setApartmentHolder(null);
                apartment.setSold(false);
                apartmentService.deleteApartment(apartment.getId());
            }
            buildingService.deleteBuildingById(id);
            mav.setViewName("redirect:" + "/buildings");
        } catch (Exception e) {
            mav.setViewName("redirect:" + "/");
        }

        return mav;
    }

    @RequestMapping("/buildings/all/delete")
    public ModelAndView deleteBuildings() {
        ModelAndView mav = new ModelAndView();
        try {
            List<Building> buildings = buildingService.fetchBuildings();
            for (Building building : buildings) {
                Building buildingById = buildingService.getBuildingById(building.getId());
                List<Apartment> apartments = buildingById.getApartments();
                for (Apartment apartment : apartments) {
                    if (apartment.getApartmentHolder() != null) {
                        ApartmentHolder apartmentHolder =
                                apartmentHolderService.getApartmentHolderByName(
                                        apartment
                                                .getApartmentHolder()
                                                .getName()
                                );
                        List<Apartment> apartmentsHolder = apartmentHolder.getApartments();
                        apartmentsHolder.remove(apartment);
                        apartmentHolder.setApartments(apartmentsHolder);
                        apartmentHolderService.updateApartmentHolder(apartmentHolder.getId(), apartmentHolder);
                    }
                    apartment.setBuilding(null);
                    apartment.setApartmentHolder(null);
                    apartmentService.updateApartment(apartment.getId(), apartment);
                }
            }
            apartmentService.deleteApartments();
            buildingService.deleteBuildings();
            mav.setViewName("redirect:" + "/buildings");
        } catch (Exception e) {
            mav.setViewName("redirect:" + "/");
        }

        return mav;
    }



}
