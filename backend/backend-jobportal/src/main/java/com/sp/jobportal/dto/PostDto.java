package com.sp.jobportal.dto;

public record PostDto(
        long userId,
        long id,
        String title,
        String body
) {
}
