package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.IssueBookRequestDto;
import com.backend.library_management_system.DTO.IssueBookResponseDto;
import com.backend.library_management_system.Entity.Book;
import com.backend.library_management_system.Entity.LibraryCard;
import com.backend.library_management_system.Entity.Transaction;
import com.backend.library_management_system.Enum.CardStatus;
import com.backend.library_management_system.Enum.TransactionStatus;
import com.backend.library_management_system.Repository.BookRepository;
import com.backend.library_management_system.Repository.CardRepository;
import com.backend.library_management_system.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        LibraryCard card;
        try {
            card = (LibraryCard) cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }

        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card Id");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid book Id.");
        }

        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getStatus() != CardStatus.ACTIVATED ){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            transaction.setMessage("Your card is not activated.");
            throw new Exception("Your card is not activated.");
        }

        if(book.getIsIssued() == true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry book is already issued.");
            transactionRepository.save(transaction);
            throw new Exception("Sorry book is already issued.");
        }

        book.setIsIssued(true);
        book.setCard(card);
        book.getTransaction().add(transaction);
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());

        return issueBookResponseDto;
    }
}
