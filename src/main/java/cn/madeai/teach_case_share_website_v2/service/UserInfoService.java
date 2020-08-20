package cn.madeai.teach_case_share_website_v2.service;

import cn.madeai.teach_case_share_website_v2.dao.UserInfoRepository;
import cn.madeai.teach_case_share_website_v2.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findByUsernameAndPassword(String username, String password){
        return userInfoRepository.findByUsernameAndPassword(username,password);
    }

    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    public Boolean modifyStatusByUsername(Integer status,String  username){
        return userInfoRepository.modifyStatusByUsername(status,username)==1;
    }

    public UserInfo findByEmailAndPassword(String email, String password) {
        return userInfoRepository.findByEmailAndPassword(email,password);
    }
}
