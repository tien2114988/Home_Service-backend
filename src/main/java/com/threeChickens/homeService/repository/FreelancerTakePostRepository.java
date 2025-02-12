package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.FreelancerTakePost;
import com.threeChickens.homeService.entity.Post;
import com.threeChickens.homeService.entity.User;
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
public interface FreelancerTakePostRepository extends JpaRepository<FreelancerTakePost, String> {
    @Query(value="""
            SELECT ftp FROM FreelancerTakePost ftp
            WHERE ftp.status = :status
            AND ftp.post.deleted = false
            AND ftp.post.status = :postStatus
            AND ftp.freelancer.id = :freelancerId
            ORDER BY ftp.updatedAt DESC
            """)
    Page<FreelancerTakePost> findAllPostsByTakePostStatus(@Param("freelancerId") String freelancerId,@Param("status") TakePostStatus status, @Param("postStatus") PostStatus postStatus, Pageable pageable);

    @Query(value="""
            SELECT ftp FROM FreelancerTakePost ftp
            WHERE ftp.post.deleted = false
            AND ftp.status != :status
            AND ftp.freelancer.id = :freelancerId
            ORDER BY ftp.updatedAt DESC
            """)
    Page<FreelancerTakePost> findAllPostsByDifferentStatus(@Param("freelancerId") String freelancerId, @Param("status") TakePostStatus status, Pageable pageable);

    @Query(value="""
            SELECT ftp FROM FreelancerTakePost ftp
            WHERE ftp.post.work.id = :workId
            AND ftp.post.deleted = false
            AND ftp.freelancer.id = :freelancerId
            ORDER BY ftp.post.updatedAt DESC
            """)
    Page<FreelancerTakePost> findAllPostsByWorkId(@Param("freelancerId") String freelancerId, @Param("workId") String workId, Pageable pageable);

    @Query(value="""
            SELECT ftp FROM FreelancerTakePost ftp
            WHERE ftp.post.status IN :statuses
            AND ftp.freelancer.id = :freelancerId
            AND ftp.post.packageName IN :packageNames
            AND ftp.post.deleted = false
            AND ftp.status = :takePostStatus
            ORDER BY ftp.post.updatedAt DESC
            """)
    Page<FreelancerTakePost> findAllPostsByPackageName(@Param("freelancerId") String freelancerId,
                                @Param("packageNames") List<PackageName> packageNames, @Param("takePostStatus") TakePostStatus takePostStatus,
                                                                                        @Param("statuses") List<PostStatus> statuses,
                                                                         Pageable pageable);


    Optional<FreelancerTakePost> findByFreelancerAndPost(User freelancer, Post post);

    Page<FreelancerTakePost> findAllByPost(Post post, Pageable pageable);

    Page<FreelancerTakePost> findAllByPostAndStatus(Post post, TakePostStatus status ,Pageable pageable);
}
