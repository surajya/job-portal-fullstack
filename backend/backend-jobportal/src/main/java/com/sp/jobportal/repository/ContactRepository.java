package com.sp.jobportal.repository;

import com.sp.jobportal.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findContactsByStatus(String status);

    List<Contact> findContactsByStatusOrderByCreatedAtAsc(String status);

    List<Contact> findContactsByStatus(String status, Sort sort);

    Page<Contact> findContactsByStatus(String status, Pageable pageable);

    @Modifying
    int updateStatusById(@Param("id") Long id, @Param("status") String status, @Param("updatedBy") String updatedBy);
}
