package com.pu.elektronendomoupravitel.resources;

import com.pu.elektronendomoupravitel.model.Company;
import com.pu.elektronendomoupravitel.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyResource {

    private final CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping("/companies")
    public ModelAndView getCompanies(Model model) {
        ModelAndView mav = new ModelAndView("companies");
        List<Company> companies = companyService.fetchCompanies();
        model.addAttribute("companies", companies);

        return mav;
    }

    @RequestMapping("/companies/{id}")
    public ModelAndView getCompanyById(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("view_company");
        try {
            Optional<Company> companyById = companyService.getCompanyById(id);
            mav.addObject("company", companyById);
        } catch (Exception e) {
            mav.addObject("company", HttpStatus.NOT_FOUND.value());
        }
        return mav;
    }

    @RequestMapping("/create_company")
    public ModelAndView createCompany(Model model) {
        ModelAndView mav = new ModelAndView("create_company");
        Company company = new Company();
        model.addAttribute("company", company);
        return mav;
    }

    @RequestMapping(value="/companies", method = RequestMethod.POST)
    public ModelAndView postCompany(@ModelAttribute("company") Company company) {
        ModelAndView mav = new ModelAndView();
        try {
            companyService.newCompany(company);
            mav.setViewName("redirect:" + "/companies");
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

    @RequestMapping("/companies/{id}/update")
    public ModelAndView updateCompany(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("update_company");
        try {
            Optional<Company> companyById = companyService.getCompanyById(id);
            mav.addObject("company", companyById);
        } catch (Exception e) {
            mav.addObject("company", HttpStatus.NOT_FOUND.value());
        }

        return mav;
    }

    @RequestMapping(value="/companies/update")
    public ModelAndView putCompany(@ModelAttribute("company") Company company) {
        ModelAndView mav = new ModelAndView();
        try {
            companyService.updateCompany(company.getId(), company);
            mav.setViewName("redirect:" + "/companies");
        } catch (Exception e) {
            mav.addObject("exception", e);
            mav.setViewName("error");
        }

        return mav;
    }

    @RequestMapping("companies/{id}/delete")
    public ModelAndView deleteCompanyById(@PathVariable(name = "id") int id) {
        companyService.deleteCompanyById(id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + "/companies");

        return mav;
    }

    @RequestMapping("companies/all/delete")
    public ModelAndView deleteCompanies() {
        companyService.deleteCompanies();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + "/companies");

        return mav;
    }

}
