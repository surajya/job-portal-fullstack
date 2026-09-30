package com.sp.jobportal.contact.service.impl;

import com.sp.jobportal.constant.ApplicationConstants;
import com.sp.jobportal.contact.service.ContactService;
import com.sp.jobportal.dto.ContactRequestDto;
import com.sp.jobportal.dto.ContactResponseDto;
import com.sp.jobportal.entity.Contact;
import com.sp.jobportal.repository.ContactRepository;
import com.sp.jobportal.security.util.ApplicationUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        // TODO Auto-generated method stub
        Contact contact = contactRepository.save(transformToEntity(contactRequestDto));
        return contact.getId() != null;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgs() {
        List<Contact> contacts = contactRepository.findContactsByStatusOrderByCreatedAtAsc
                (ApplicationConstants.NEW_MESSAGE);
        return contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortDir, String sortedBy) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortedBy).descending()
                : Sort.by(sortedBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus
                (ApplicationConstants.NEW_MESSAGE, sort);
        return contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(
            int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Create Sort object based on sortBy and sortDir parameters
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        // Create Pageable object with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        // Fetch paginated and sorted contacts from repository
        Page<Contact> contactPage = contactRepository.findContactsByStatus(
                ApplicationConstants.NEW_MESSAGE, pageable);

        // Transform Contact entities to ContactResponseDto
        return contactPage.map(this::transformToDto);
    }

    @Override
    @Transactional
    public boolean closeContactMsg(Long id, String status) {
        int updatemessage = contactRepository.updateStatusById(id, ApplicationConstants.CLOSED_MESSAGE, ApplicationUtility.getLoggedInUser());
        return updatemessage > 0;
    }

    public Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto, contact);
        contact.setStatus("NEW");
        return contact;
    }

    private ContactResponseDto transformToDto(Contact contact) {
        return new ContactResponseDto(contact.getId(),
                contact.getName(), contact.getEmail(), contact.getUserType(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(), contact.getCreatedAt());
    }

}
