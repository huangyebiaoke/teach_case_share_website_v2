package cn.madeai.teach_case_share_website_v2.service;

import cn.madeai.teach_case_share_website_v2.dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
}
