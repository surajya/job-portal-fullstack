package com.sp.jobportal.repository;

import com.sp.jobportal.entity.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPortalUserRepository extends JpaRepository<JobPortalUser, Long> {

    Optional<JobPortalUser> findJobPortalUserByEmail(String email);

    Optional<JobPortalUser> readUserByEmailOrMobileNumber(String email, String mobileNumber);

}
