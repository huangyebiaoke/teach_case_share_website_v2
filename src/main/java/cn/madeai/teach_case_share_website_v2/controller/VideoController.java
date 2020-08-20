package cn.madeai.teach_case_share_website_v2.controller;

import cn.madeai.teach_case_share_website_v2.entity.VideoInfo;
import cn.madeai.teach_case_share_website_v2.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {
    @Autowired
    private VideoInfoService videoInfoService;
    @DeleteMapping("admin/videos")
    public String deleteById(@RequestParam Integer videoId){
        videoInfoService.deleteByVideoId(videoId);
        return "delete success";
    }

    @PutMapping("admin/videos")
    public String edit(@RequestBody VideoInfo video){
        videoInfoService.edit(video);
        return "edit success";
    }
}
