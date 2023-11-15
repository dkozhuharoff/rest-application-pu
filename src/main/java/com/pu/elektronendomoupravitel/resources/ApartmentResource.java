package com.pu.elektronendomoupravitel.resources;

import com.pu.elektronendomoupravitel.model.Apartment;
import com.pu.elektronendomoupravitel.model.ApartmentHolder;
import com.pu.elektronendomoupravitel.model.Building;
import com.pu.elektronendomoupravitel.services.ApartmentHolderService;
import com.pu.elektronendomoupravitel.services.ApartmentService;
import com.pu.elektronendomoupravitel.services.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ApartmentResource {

    private final ApartmentService apartmentService;
    private final ApartmentHolderService apartmentHolderService;
    private final BuildingService buildingService;

    public ApartmentResource(ApartmentService apartmentService, ApartmentHolderService apartmentHolderService, BuildingService buildingService) {
        this.apartmentService = apartmentService;
        this.apartmentHolderService = apartmentHolderService;
        this.buildingService = buildingService;
    }

    @RequestMapping("/buildings/{id}/apartments")
    public ModelAndView getApartments(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("view_apartments");
        try {
            Building buildingById = buildingService.getBuildingById(id);
            List<Apartment> apartments = buildingById.getApartments();
            mav.addObject("apartments", apartments);
        } catch (Exception e) {
            mav.addObject("apartments", HttpStatus.NOT_FOUND.value());
        }
        return mav;
    }

    @RequestMapping("/apartments/{id}")
    public ModelAndView getApartmentById(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("view_apartment");
        try {
            Apartment apartmentById = apartmentService.getApartmentById(id);
            mav.addObject("apartment", apartmentById);
        } catch (Exception e) {
            mav.addObject("apartment", HttpStatus.NOT_FOUND.value());
        }
        return mav;
    }

    @RequestMapping("/apartments/{id}/buy")
    public ModelAndView buyApartment(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("buy_apartment");
        ApartmentHolder apartmentHolder = new ApartmentHolder();
        Apartment apartment = new Apartment();
        apartment.setId(id);
        mav.addObject("apartment", apartment);
        mav.addObject("apartmentHolder", apartmentHolder);
        return mav;
    }

    @RequestMapping("/apartments/{id}/set_holder")
    //change request body with Model Attribute annotation
    public ModelAndView setHolder(@ModelAttribute("apartmentHolder") ApartmentHolder apartmentHolder, @PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView();
        try {
            ApartmentHolder apartmentHolderByName = apartmentHolderService.getApartmentHolderByName(apartmentHolder.getName());
            if (apartmentHolderByName == null) {
                ApartmentHolder newApartmentHolder = apartmentHolderService.newApartmentHolder(apartmentHolder);
                Apartment apartment = apartmentService.getApartmentById(id);
                apartment.setApartmentHolder(newApartmentHolder);
                apartment.setSold(true);
                apartmentService.updateApartment(apartment.getId(), apartment);
            } else {
                Apartment apartment = apartmentService.getApartmentById(id);
                apartment.setApartmentHolder(apartmentHolderByName);
                apartment.setSold(true);
                apartmentService.updateApartment(apartment.getId(), apartment);
            }
            mav.setViewName("redirect:" + "/apartments/" + id);
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

    @RequestMapping("/apartments/{id}/sold")
    public ModelAndView soldApartment(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + "/apartments/" + id);
        return mav;
    }

    @RequestMapping("/apartments/{id}/remove_holder")
    public ModelAndView removeHolder( @PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView();
        try {
            Apartment apartment = apartmentService.getApartmentById(id);
            apartment.setApartmentHolder(null);
            apartment.setSold(false);
            apartmentService.updateApartment(apartment.getId(), apartment);
            mav.setViewName("redirect:" + "/apartments/" + id);
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

}
