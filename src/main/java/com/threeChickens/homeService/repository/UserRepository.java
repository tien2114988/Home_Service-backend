package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.FreelancerWorkService;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailAndDeletedIsFalse(String email);
    boolean existsByEmailAndRoleAndDeletedIsFalse(String email, UserRole role);
    Optional<User> findByEmailAndDeletedIsFalse(String email);
    Optional<User> findByIdAndDeletedIsFalse(String id);
    Optional<User> findByIdAndRoleAndDeletedIsFalse(String id, UserRole role);
    //user_info
    Page<User> findAllByDeletedIsFalse(Pageable pageable);

    @Query(value="""
            SELECT u FROM User u
            WHERE u.deleted = false AND u.status != :status AND u.role = :role 
            AND EXISTS (
                SELECT 1
                FROM u.freelancerWorkServices fws
                WHERE fws.work.id = :workId AND fws.status = :workStatus
            )
            """)
    Page<User> findFreelancersByPostId(@Param("workId") String workId, @Param("workStatus") FreelancerWorkStatus workStatus, @Param("status") UserStatus status, @Param("role") UserRole role, Pageable pageable);
}
