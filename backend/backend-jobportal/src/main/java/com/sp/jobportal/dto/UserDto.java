package com.sp.jobportal.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class UserDto {

	public long userId;

	public String username;

	public String email;

	public String role;

	public String mobileNumber;

	public String companyName;

	public String companyId;

	public Instant createdAt;

}
