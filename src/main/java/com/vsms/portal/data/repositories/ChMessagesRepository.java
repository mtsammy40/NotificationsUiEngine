package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.ChMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChMessagesRepository extends JpaRepository<ChMessages, Long> {
}
