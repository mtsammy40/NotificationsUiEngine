package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.ChurchTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChTransactionsRepository extends JpaRepository<ChurchTransactions, Long> {
}
