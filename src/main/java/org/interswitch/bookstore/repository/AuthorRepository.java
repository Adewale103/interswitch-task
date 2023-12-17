package org.interswitch.bookstore.repository;

import org.interswitch.bookstore.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
   Optional<Author> findByEmail(String email);
   Optional<Author> findByReference(String reference);
}
