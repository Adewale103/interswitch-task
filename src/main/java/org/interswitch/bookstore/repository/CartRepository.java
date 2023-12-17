package org.interswitch.bookstore.repository;

import org.interswitch.bookstore.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {
   Optional<ShoppingCart> findByUserId(String userId);
}
