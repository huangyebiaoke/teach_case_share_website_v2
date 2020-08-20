package cn.madeai.teach_case_share_website_v2.dao;

import cn.madeai.teach_case_share_website_v2.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findByVideoId(Pageable pageable,Integer videoId);
}
