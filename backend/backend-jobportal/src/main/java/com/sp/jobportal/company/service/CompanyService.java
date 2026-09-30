package com.sp.jobportal.company.service;

import com.sp.jobportal.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAllCompanies();

    List<CompanyDto> getAllCompaniesForAdmin();

    void deleteCompanyById(Long id);

    boolean updateCompanyDetails(Long id, CompanyDto companyDto);

    boolean createCompany(CompanyDto companyDto);

}
