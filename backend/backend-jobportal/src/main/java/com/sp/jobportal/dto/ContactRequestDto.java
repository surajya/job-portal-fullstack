package com.sp.jobportal.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactRequestDto(

		@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,

		@NotBlank(message = "Message is required") @Size(min = 10, max = 1000,
				message = "Message must be between 10 and 1000 characters") String message,

		@NotBlank(message = "Name is required") @Size(min = 2, max = 100,
				message = "Name must be between 2 and 100 characters") String name,

		@NotBlank(message = "Subject is required") @Size(min = 5, max = 200,
				message = "Subject must be between 5 and 200 characters") String subject,

		@NotBlank(message = "User type is required") @Pattern(regexp = "^(Job Seeker|Employer|Other)$",
				message = "User type must be either 'Job Seeker', 'Employer', or 'Other'") String userType)
		implements Serializable {

}
