package com.example.Library.repositories;

import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBooksListRepository extends JpaRepository<BorrowedBooksList,Long> {
    List<BorrowedBooksList> findByUser(User user);
}
