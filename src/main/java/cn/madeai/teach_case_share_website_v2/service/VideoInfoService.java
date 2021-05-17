package cn.madeai.teach_case_share_website_v2.service;

import cn.madeai.teach_case_share_website_v2.dao.VideoInfoRepository;
import cn.madeai.teach_case_share_website_v2.entity.VideoInfo;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class VideoInfoService {
    @Autowired
    private VideoInfoRepository videoInfoRepository;
    public Page<VideoInfo> findAll(Pageable pageable){
        return videoInfoRepository.findAll(pageable);
    }
    public void deleteByVideoId(Integer videoId){
        videoInfoRepository.deleteByVideoId(videoId);
    }
    public void edit(VideoInfo videoInfo){
        videoInfoRepository.save(videoInfo);
    }

    private Configuration cfg = new Configuration(Region.region2());
    private UploadManager uploadManager= new UploadManager(cfg);
    @Value("${upload.qiniu.access-key}")
    private String assessKey;
    @Value("${upload.qiniu.secret-key}")
    private String secretKey;
    @Value("${upload.qiniu.bucket}")
    private String bucket;
    @Value("${upload.qiniu.domain}")
    private String domain;

    public Boolean store(MultipartFile multipartFile,Integer userId) {
        Auth auth = Auth.create(assessKey, secretKey);
        String token = auth.uploadToken(bucket);
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileKey = UUID.randomUUID().toString()+suffix;
        try {
            Response response = uploadManager.put(multipartFile.getInputStream(), fileKey, token, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                ex2.printStackTrace();
//                //ignore
//            }
//        }catch (UnsupportedEncodingException ex) {
//            ex.printStackTrace();
//            //ignore
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            videoInfoRepository.save(new VideoInfo(userId,domain+fileKey,multipartFile.getContentType(),originalFilename.substring(0,originalFilename.lastIndexOf("."))));
            return true;
        }
    }

    public VideoInfo findByVideoId(Integer videoId) {
        return videoInfoRepository.findByVideoId(videoId);
    }

    public void plusHotByVideoId(Integer videoId) {
        videoInfoRepository.plusHotByVideoId(videoId);
    }

    public Page<VideoInfo> findByVideoName(Pageable pageable, String videoName) {
        return videoInfoRepository.findByVideoName(pageable,videoName);
    }

    public Page<VideoInfo> findByUserId(Pageable pageable, Integer userId) {
        return videoInfoRepository.findByUserId(pageable,userId);
    }
}
