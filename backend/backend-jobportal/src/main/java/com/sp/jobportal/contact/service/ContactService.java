package com.sp.jobportal.contact.service;

import com.sp.jobportal.dto.ContactRequestDto;
import com.sp.jobportal.dto.ContactResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {

    public boolean saveContact(ContactRequestDto contactRequestDto);

    List<ContactResponseDto> fetchNewContactMsgs();

    List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortDir, String sortedBy);

    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(
            int pageNumber, int pageSize, String sortBy, String sortDir);

    public boolean closeContactMsg(Long id, String status);
}
