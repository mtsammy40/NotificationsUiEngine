package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.TransactionsReportView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionViewRepository extends JpaRepository<TransactionsReportView, Long> {
}
