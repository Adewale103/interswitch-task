package org.interswitch.bookstore.repository;

import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.entities.PurchaseHistory;
import org.interswitch.bookstore.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long>, JpaSpecificationExecutor<PurchaseHistory> {
   Optional<List<PurchaseHistory>> findByUserId(String userId);
}
