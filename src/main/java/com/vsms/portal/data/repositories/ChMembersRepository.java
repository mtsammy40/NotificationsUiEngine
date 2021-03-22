package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.ChurchMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChMembersRepository extends JpaRepository<ChurchMembers, Long> {
}
