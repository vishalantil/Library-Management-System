package com.backend.library_management_system.Controller;

import com.backend.library_management_system.DTO.IssueBookRequestDto;
import com.backend.library_management_system.DTO.IssueBookResponseDto;
import com.backend.library_management_system.DTO.ReturnBookRequestDto;
import com.backend.library_management_system.DTO.ReturnBookResponseDtu;
import com.backend.library_management_system.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto) throws Exception {

        IssueBookResponseDto issueBookResponseDto;

        try{
            issueBookResponseDto = transactionService.issueBook(issueBookRequestDto);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(issueBookResponseDto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/return")
    public ResponseEntity issueBook(@RequestBody ReturnBookRequestDto returnBookRequestDto) throws Exception {

        ReturnBookResponseDtu returnBookResponseDtu;

        try{
            returnBookResponseDtu = transactionService.returnBook(returnBookRequestDto);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(returnBookResponseDtu,HttpStatus.ACCEPTED);
    }
}
