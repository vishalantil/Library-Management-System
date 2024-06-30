package com.backend.library_management_system.Service;

import com.backend.library_management_system.DTO.IssueBookRequestDto;
import com.backend.library_management_system.DTO.IssueBookResponseDto;
import com.backend.library_management_system.DTO.ReturnBookRequestDto;
import com.backend.library_management_system.DTO.ReturnBookResponseDtu;
import com.backend.library_management_system.Entity.Book;
import com.backend.library_management_system.Entity.LibraryCard;
import com.backend.library_management_system.Entity.Transaction;
import com.backend.library_management_system.Enum.CardStatus;
import com.backend.library_management_system.Enum.TransactionStatus;
import com.backend.library_management_system.Repository.BookRepository;
import com.backend.library_management_system.Repository.CardRepository;
import com.backend.library_management_system.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private JavaMailSender emailSender;

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

        String text = "Congrats! "+ card.getStudent().getName() + " You have been issued "+ book.getTitle() +" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendserver5402@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book notification");
        message.setText(text);
        emailSender.send(message);

        return issueBookResponseDto;
    }

    public ReturnBookResponseDtu returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        Book book;
        try{
            book = bookRepository.findById(returnBookRequestDto.getBookId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid book Id.");
        }

        LibraryCard card;
        try {
            card = book.getCard();
        }

        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card Id");
        }

        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getStatus() != CardStatus.ACTIVATED ){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            transaction.setMessage("Your card is not activated.");
            throw new Exception("Your card is not activated.");
        }

        if(book.getIsIssued() == false){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry book is already returned.");
            transactionRepository.save(transaction);
            throw new Exception("Sorry book is already returned.");
        }

        book.setIsIssued(false);
        book.setCard(null);
        book.getTransaction().add(transaction);
        card.getTransactionList().add(transaction);
        card.getBooksIssued().remove(book);

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        ReturnBookResponseDtu returnBookResponseDtu = new ReturnBookResponseDtu();
        returnBookResponseDtu.setTransactionId(transaction.getTransactionNumber());
        returnBookResponseDtu.setBookName(book.getTitle());
        returnBookResponseDtu.setTransactionStatus(transaction.getTransactionStatus());

        String text =  card.getStudent().getName() + " returned "+ book.getTitle();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendserver5402@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Return Book notification");
        message.setText(text);
        emailSender.send(message);

        return returnBookResponseDtu;

    }
}
