package cn.madeai.teach_case_share_website_v2.entity;

import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer commentId;
    @Column(nullable = false)
    private String content;
    @Temporal(TemporalType.TIME)
    private Date commentTime;
    private Integer userId;
    private Integer videoId;
}
