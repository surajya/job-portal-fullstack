package com.sp.jobportal.security.util;

import com.sp.jobportal.constant.ApplicationConstants;
import com.sp.jobportal.dto.JobApplicationDto;
import com.sp.jobportal.dto.JobDto;
import com.sp.jobportal.dto.ProfileDto;
import com.sp.jobportal.entity.Job;
import com.sp.jobportal.entity.JobApplication;
import com.sp.jobportal.entity.JobPortalUser;
import com.sp.jobportal.entity.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtility {

    public static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return ApplicationConstants.SYSTEM;
        }
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof JobPortalUser jobPortalUser) {
            username = jobPortalUser.getEmail();
        } else {
            username = principal.toString(); // fallback
        }
        return username;
    }

    public static JobDto transformJobToDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getCompany().getLogo(),
                job.getLocation(),
                job.getWorkType(),
                job.getJobType(),
                job.getCategory(),
                job.getExperienceLevel(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getSalaryCurrency(),
                job.getSalaryPeriod(),
                job.getDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getApplicationsCount(),
                job.getFeatured(),
                job.getUrgent(),
                job.getRemote(),
                job.getStatus()
        );
    }

    public static JobApplicationDto transformJobApplicationToDto(JobApplication jobApplication) {
        return new JobApplicationDto(
                jobApplication.getId(),
                jobApplication.getUser().getId(),
                jobApplication.getUser().getName(),
                jobApplication.getUser().getEmail(),
                jobApplication.getUser().getMobileNumber(),
                transformProfileToDto(jobApplication.getUser().getProfile()),
                transformJobToDto(jobApplication.getJob()),
                jobApplication.getAppliedAt(),
                jobApplication.getStatus(),
                jobApplication.getCoverLetter(),
                jobApplication.getNotes()
        );
    }

    public static ProfileDto transformProfileToDto(Profile profile) {
        return new ProfileDto(
                profile.getId(),
                profile.getUser().getId(),
                profile.getName(),
                profile.getJobTitle(),
                profile.getLocation(),
                profile.getExperienceLevel(),
                profile.getProfessionalBio(),
                profile.getPortfolioWebsite(),
                profile.getProfilePicture(),
                profile.getProfilePictureName(),
                profile.getProfilePictureType(),
                profile.getResume(),
                profile.getResumeName(),
                profile.getResumeType(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

}
