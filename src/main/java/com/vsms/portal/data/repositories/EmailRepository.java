package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailRepository extends JpaRepository<Emails, Long>, JpaSpecificationExecutor<Emails> {
}
