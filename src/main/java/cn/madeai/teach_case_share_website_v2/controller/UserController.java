package cn.madeai.teach_case_share_website_v2.controller;

import cn.madeai.teach_case_share_website_v2.entity.UserInfo;
import cn.madeai.teach_case_share_website_v2.entity.VideoInfo;
import cn.madeai.teach_case_share_website_v2.service.MailService;
import cn.madeai.teach_case_share_website_v2.service.UserInfoService;
import cn.madeai.teach_case_share_website_v2.service.VideoInfoService;
import cn.madeai.teach_case_share_website_v2.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public String Login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes attributes) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringUtils.isEmptyOrWhitespace(password)||StringUtils.isEmptyOrWhitespace(email)){
            attributes.addFlashAttribute("message", "用户名或密码不能为空！");
            return "redirect:/error-page";
        }else{
//            UserInfo user=userInfoService.findByUsernameAndPassword(username,password);
            UserInfo user=userInfoService.findByEmailAndPassword(email,StringUtil.encodeByMD5(password));
            if(null!=user){
                user.setPassword(null);
                session.setAttribute("user",user);
                return "redirect:/admin";
            }else{
                attributes.addFlashAttribute("message", "用户名或密码错误！");
                return "redirect:/error-page";
            }
        }
    }

    @Autowired
    private MailService mailService;
    @Value("${server.address.customize}")
    private String localhostName;
    @PostMapping("/register")
    public String register(UserInfo user, RedirectAttributes attributes) {
       try{
           user.setRegTime(new Date());
           user.setPassword(StringUtil.encodeByMD5(user.getPassword()));
           userInfoService.save(user);
           attributes.addFlashAttribute("message","注册账号成功，已经给您的邮箱发送一条激活邮件，请查收!");
           mailService.sendHtmlMail(user.getEmail(),"教学案例共享平台","<h1>恭喜您注册成功！</h1><p>点击以下链接激活账号</p><a href='"+localhostName+"/register/active?username="+user.getUsername()+"&status=1'>点击此链接激活</a>");
           return "redirect:/register";
       }catch (Exception e){
           attributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/error-page";
       }
    }

    @GetMapping("/register/active")
    public void active(@RequestParam String username, @RequestParam Integer status, HttpServletResponse response) throws IOException {
//        乱码问题;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type","text/html;chartset=UTF-8");
//        active success;
        if(userInfoService.modifyStatusByUsername(status,username)){
            response.getWriter().println("激活成功，3秒后跳转到首页！");
            response.setHeader("refresh","3;/teach");
        }else {
            response.getWriter().println("激活失败，3秒后跳转到注册页面！");
            response.setHeader("refresh","3;/teach/register");
        }
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
//        different between return "/" & return "redirect:/"
        return "redirect:/";
    }


    @RequestMapping("/text-case")
    public String textCase(){
        return "text-case";
    }

    @RequestMapping("/case-specialist")
    public String caseSpecialist(){
        return "case-specialist";
    }

    @RequestMapping("/case-enterprise")
    public String caseEnterprise(){
        return "case-enterprise";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/error-page")
    public String errorPage(){
        return "/error/error-page";
    }


    @Autowired
    private VideoInfoService videoInfoService;
    @GetMapping("/admin")
    public String admin(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer size, Model model,HttpSession session){
//        Sort sort =new Sort(Sort.Direction.DESC, "videoId");
        Pageable pageable = PageRequest.of(page, size,Sort.Direction.DESC,"videoId");
        Page<VideoInfo> videoInfos=videoInfoService.findByUserId(pageable,((UserInfo)session.getAttribute("user")).getUserId());
        model.addAttribute("videoInfos",videoInfos);
        return "admin";
    }

    @PostMapping("/admin/videos")
    public String addVideos(@RequestParam("file") MultipartFile[] files, HttpSession session) {
        for (MultipartFile multipartFile:files){
            videoInfoService.store(multipartFile,((UserInfo)session.getAttribute("user")).getUserId());
        }
        return "redirect:/admin";
    }

    @GetMapping("/video-case")
    public String videoCase(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "videoId") String properties,@RequestParam(defaultValue = "10") Integer size, Model model){
        Pageable pageable = PageRequest.of(page, size,Sort.Direction.DESC,properties);
        Page<VideoInfo> videoInfos=videoInfoService.findAll(pageable);
        model.addAttribute("videoInfos",videoInfos);
        return "/video-case";
    }
//    todo:add PostMaping for video-case to queryByVideoName;
    @PostMapping("/video-case")
    public String videoCase(@RequestParam() String videoName,@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer size, Model model){
        Pageable pageable = PageRequest.of(page, size,Sort.Direction.DESC,"videoId");
        Page<VideoInfo> videoInfos=videoInfoService.findByVideoName(pageable,videoName);
        model.addAttribute("videoInfos",videoInfos);
        return "/video-case";
    }

    @GetMapping("/video-case/video-detail")
    public String videoDetial(@RequestParam(value = "video_id") Integer videoId,Model model){
        VideoInfo videoInfo=videoInfoService.findByVideoId(videoId);
        model.addAttribute("videoInfo",videoInfo);
        videoInfoService.plusHotByVideoId(videoId);
        return "/video-detail";
    }
}
