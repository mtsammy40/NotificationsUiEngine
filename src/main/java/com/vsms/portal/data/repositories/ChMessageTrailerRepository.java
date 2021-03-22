package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.MessageTrailer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChMessageTrailerRepository extends JpaRepository<MessageTrailer, Long> {
}
