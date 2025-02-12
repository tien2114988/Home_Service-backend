package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Post;
import com.threeChickens.homeService.entity.Work;
import com.threeChickens.homeService.enums.PackageName;
import com.threeChickens.homeService.enums.PostStatus;
import com.threeChickens.homeService.enums.TakePostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    @Query(value = """
    SELECT p FROM Post p
    JOIN p.address a
    WHERE p.deleted = false AND p.work.id IN :workIds AND p.status = :status
    AND NOT EXISTS (
                  SELECT 1
                  FROM p.freelancerTakePosts ftp
                  WHERE ftp.freelancer.id = :freelancerId
                  AND ftp.status = :takePostStatus
              )
    ORDER BY 6371 
    * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) 
    * cos(radians(a.longitude) - radians(:longitude)) 
    + sin(radians(:latitude)) * sin(radians(a.latitude))) ASC
    """)
    Page<Post> findAllByWorkInAndStatusIsAndDeletedIsFalseAndSortByDistance(
            @Param("freelancerId") String freelancerId,
            @Param("takePostStatus") TakePostStatus takePostStatus,
            @Param("workIds") List<String> workIds,
            @Param("status") PostStatus status,
            @Param("latitude") float latitude,
            @Param("longitude") float longitude,
            Pageable pageable
    );

    Page<Post> findAllByWorkInAndStatusIsAndDeletedIsFalse(List<Work> works, PostStatus postStatus ,Pageable pageable);
    Page<Post> findAllByDeletedIsFalse(Pageable pageable);
    Page<Post> findAllByCustomerIdAndWorkIdAndDeletedIsFalse(String customerId, String workId, Pageable pageable);
    Page<Post> findAllByCustomerIdAndPackageNameInAndStatusInAndDeletedIsFalse(String customerId, List<PackageName> packageName, List<PostStatus> postStatuses, Pageable pageable);
    Page<Post> findAllByCustomerIdAndDeletedIsFalse(String customerId, Pageable pageable);
    Optional<Post> findByIdAndDeletedIsFalse(String id);
}
