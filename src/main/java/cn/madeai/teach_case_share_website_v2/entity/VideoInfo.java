package cn.madeai.teach_case_share_website_v2.entity;

import javax.persistence.*;

@Entity
public class VideoInfo {
    @Id
    @GeneratedValue
    private Integer videoId;
    private Integer userId;
    private String url;
    private String description;
    @Column(length = 15)
    private String type;
    @Column(length = 50)
    private String videoName;
    @Column(columnDefinition = "int(10) default 0")
    private Integer hot=0;

    public VideoInfo() {
    }

    public VideoInfo(Integer videoId, Integer userId, String url, String description, String type, String videoName, Integer hot) {
        this.videoId = videoId;
        this.userId = userId;
        this.url = url;
        this.description = description;
        this.type = type;
        this.videoName = videoName;
        this.hot = hot;
    }

    public VideoInfo(Integer userId, String url, String type, String videoName) {
        this.userId=userId;
        this.url=url;
        this.type=type;
        this.videoName=videoName;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer mediaId) {
        this.videoId = mediaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}
