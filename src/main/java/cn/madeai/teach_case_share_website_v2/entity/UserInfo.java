package cn.madeai.teach_case_share_website_v2.entity;
import com.sun.istack.Nullable;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue
    private Integer userId;
    @Column(length = 20)
    private String username;
    @Column(length = 50)
    private String password;
    @Temporal(TemporalType.DATE)
    private Date regTime;
    @Column(length = 50)
    private String email;
    @Column(nullable = false, columnDefinition = "int(1) default 0")
    private Integer status=0;
    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private Boolean isAdmin=false;

    public UserInfo(){
    }

    public UserInfo(Integer userId, String username, String password, Date regTime, String email, Integer status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.regTime = regTime;
        this.email = email;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date time) {
        this.regTime = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}