package com.backend.library_management_system.Repository;

import com.backend.library_management_system.Entity.LibraryCard;
import com.backend.library_management_system.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction,Integer> {


}
