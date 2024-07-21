package com.example.Library.repositories;

import com.example.Library.models.Book;
import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBooksListRepository extends JpaRepository<BorrowedBooksList,Long> {
    List<BorrowedBooksList> findByUser(User user);
    @Query("SELECT b FROM BorrowedBooksList b JOIN b.bookList bk WHERE b.user = :user AND bk = :book")
    List<BorrowedBooksList> findByUserAndBook(@Param("user") User user, @Param("book") Book book);
}
