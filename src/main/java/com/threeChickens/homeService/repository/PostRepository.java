package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByDeletedIsFalse();
    List<Post> findAllByCustomerIdAndDeletedIsFalse(String customerId);
    Optional<Post> findByIdAndDeletedIsFalse(String id);
}
