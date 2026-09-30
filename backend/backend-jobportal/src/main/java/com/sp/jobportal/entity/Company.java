package com.sp.jobportal.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Company.findAllWithJobs", query =
                "SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status = :status"),
        @NamedQuery(name = "Company.updateCompanyDetails",
                query =
                        """
                                        UPDATE Company c SET
                                                                    c.name = :name,
                                                                    c.logo = :logo,
                                                                    c.industry = :industry,
                                                                    c.size = :size,
                                                                    c.rating = :rating,
                                                                    c.locations = :locations,
                                                                    c.founded = :founded,
                                                                    c.description = :description,
                                                                    c.employees = :employees,
                                                                    c.website = :website
                                                                WHERE c.id = :id
                                """
        )})
public class Company extends BaseEntity {

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Job> jobs = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "LOGO", length = 500)
    private String logo;

    @Column(name = "INDUSTRY", nullable = false, length = 100)
    private String industry;

    @Column(name = "SIZE", nullable = false, length = 50)
    private String size;

    @Column(name = "RATING", nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "LOCATIONS", length = 1000)
    private String locations;

    @Column(name = "FOUNDED", nullable = false)
    private Integer founded;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "EMPLOYEES")
    private Integer employees;
	
    @Column(name = "WEBSITE", length = 500)
    private String website;

}
