package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.FreelancerTakePost;
import com.threeChickens.homeService.entity.Post;
import com.threeChickens.homeService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreelancerTakePostRepository extends JpaRepository<FreelancerTakePost, String> {
    Optional<FreelancerTakePost> findByFreelancerAndPost(User freelancer, Post post);
}
