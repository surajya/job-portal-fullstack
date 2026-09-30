package com.sp.jobportal.dto;

public record TodoDto(
        long userId,
        long id,
        String title,
        boolean completed
) {
}
