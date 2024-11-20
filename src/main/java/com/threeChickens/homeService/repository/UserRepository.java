package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.FreelancerWorkService;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailAndDeletedIsFalseAndAccountType(String email, AccountType accountType);
    boolean existsByEmailAndRoleAndAccountTypeAndDeletedIsFalse(String email, UserRole role, AccountType accountType);
    Optional<User> findByEmailAndAccountTypeAndDeletedIsFalse(String email, AccountType accountType);
    Optional<User> findByIdAndDeletedIsFalse(String id);
    Optional<User> findByIdAndRoleAndDeletedIsFalse(String id, UserRole role);
    //user_info
    List<User> findAllByDeletedIsFalse();
}
