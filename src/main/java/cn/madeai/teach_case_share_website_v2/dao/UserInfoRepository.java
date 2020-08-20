package cn.madeai.teach_case_share_website_v2.dao;

import cn.madeai.teach_case_share_website_v2.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    @Query("select u from UserInfo u")
    Page<UserInfo> findAll(Pageable pageable);
    @Query("select u from UserInfo u where u.username=?1 and u.password=?2 and u.status=1")
    UserInfo findByUsernameAndPassword(String username,String password);
    @Transactional(timeout = 10)
    @Modifying
    @Query("update UserInfo set status = ?1 where username = ?2")
    int modifyStatusByUsername(Integer status,String  username);
    @Query("select u from UserInfo u where u.email=?1 and u.password=?2 and u.status=1")
    UserInfo findByEmailAndPassword(String email, String password);
}
