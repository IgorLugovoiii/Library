package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import com.example.Library.repositories.BorrowedBooksListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBooksListService {
    private final BorrowedBooksListRepository borrowedBooksListRepository;

    @Autowired
    public BorrowedBooksListService(BorrowedBooksListRepository borrowedBooksListRepository){
        this.borrowedBooksListRepository = borrowedBooksListRepository;
    }
    public BorrowedBooksList save(BorrowedBooksList borrowedBooksList){
        return borrowedBooksListRepository.save(borrowedBooksList);
    }
    public List<BorrowedBooksList> findAll(){
        return borrowedBooksListRepository.findAll();
    }
    public void deleteById(Long id){
        borrowedBooksListRepository.deleteById(id);
    }
    public List<BorrowedBooksList> findByReader(User user){
        return borrowedBooksListRepository.findByUser(user);
    }
    public List<BorrowedBooksList> findByUserAndBook(User user, Book book){
        return borrowedBooksListRepository.findByUserAndBook(user,book);
    }
    public void delete(BorrowedBooksList borrowedBooksList){borrowedBooksListRepository.delete(borrowedBooksList);}

}
