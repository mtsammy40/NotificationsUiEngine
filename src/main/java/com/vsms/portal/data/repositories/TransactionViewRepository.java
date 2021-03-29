package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.TransactionsReportView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionViewRepository extends JpaRepository<TransactionsReportView, Long>, JpaSpecificationExecutor<TransactionsReportView> {
}
