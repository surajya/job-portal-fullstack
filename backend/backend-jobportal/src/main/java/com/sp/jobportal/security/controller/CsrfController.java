package com.sp.jobportal.security.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/csrf-token")
@Slf4j
public class CsrfController {

	@GetMapping(path = "/public", version = "1.0")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		log.info("Received request for CSRF token");
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		return csrfToken;
	}
}
