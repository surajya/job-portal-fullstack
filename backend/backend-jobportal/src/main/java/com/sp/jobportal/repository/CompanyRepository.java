package com.sp.jobportal.repository;

import com.sp.jobportal.constant.ApplicationConstants;
import com.sp.jobportal.entity.Company;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    //JPQL Query
    @Query("SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j where j.status = :status")
    List<Company> findAllWithJobs(@Param("status") String status);

    //Native/SQL Query
    @Query(value = "SELECT DISTINCT c.* FROM companies c JOIN jobs j ON c.id = j.company_id where j.status = ?1",
            nativeQuery = true)
    List<Company> findAllWithJobsNative(String status);

    @CacheEvict(value = "companies", allEntries = true)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    int updateCompanyDetails(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("logo") String logo,
            @Param("industry") String industry,
            @Param("size") String size,
            @Param("rating") BigDecimal rating,
            @Param("locations") String locations,
            @Param("founded") Integer founded,
            @Param("description") String description,
            @Param("employees") Integer employees,
            @Param("website") String website
    );

    @CacheEvict(value = "companies", allEntries = true)
    @Override
    void deleteById(Long id);

    @CacheEvict(value = "companies", allEntries = true)
    @Override
    Company save(Company entity);

}
