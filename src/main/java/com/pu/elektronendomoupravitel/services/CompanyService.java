package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.exception.InitializationException;
import com.pu.elektronendomoupravitel.exception.NotFoundException;
import com.pu.elektronendomoupravitel.model.Company;
import com.pu.elektronendomoupravitel.repositories.CompanyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepo companyRepo;

    Logger logger = LoggerFactory.getLogger(CompanyService.class);

    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public List<Company> fetchCompanies() {
        return companyRepo.findAll();
    }

    public Optional<Company> getCompanyById(long id) throws NotFoundException {
        if (companyRepo.existsById(id)) {
            return companyRepo.findById(id);
        } else {
            logger.error("Company with id " + id + " was not found.");
            throw new NotFoundException("Company with id " + id + " was not found.");
        }
    }

    public Company newCompany(@RequestBody Company company) throws InitializationException {
        if (company.getName().isEmpty()) {
            logger.error(company.getClass() + " name is empty.");
            throw new InitializationException(company.getClass() + " name is empty.");
        }

        return companyRepo.save(company);
    }

    public Optional<Company> updateCompany(long id, @RequestBody Company company) throws InitializationException, NotFoundException {
        if (companyRepo.existsById(id)) {
            if (company.getName().isEmpty()) {
                logger.error(company.getClass() + " name is empty.");
                throw new InitializationException(company.getClass() + " name is empty.");
            }
        } else {
            logger.error("Company with id " + id + " was not found.");
            throw new NotFoundException("Company with id " + id + " was not found.");
        }

        return companyRepo.findById(id).map(updateCompany->{return companyRepo.save(company);});
    }

    public void deleteCompanyById(long id) {
        companyRepo.deleteById(id);
    }

    public void deleteCompanies() {
        companyRepo.deleteAll();
    }

}
