package cn.madeai.teach_case_share_website_v2.dao;

import cn.madeai.teach_case_share_website_v2.entity.VideoInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VideoInfoRepository extends JpaRepository<VideoInfo,Integer> {
    Page<VideoInfo> findByUserId(Pageable pageable, Integer userId);
    @Query("select v from VideoInfo v")
    Page<VideoInfo> findAll(Pageable pageable);
    @Query("select v from VideoInfo v where v.videoName like %:videoName%")
    Page<VideoInfo> findByVideoName(Pageable pageable, String videoName);
    @Transactional
    @Modifying
    @Query("delete from VideoInfo where videoId = ?1")
    void deleteByVideoId(Integer videoId);

    VideoInfo findByVideoId(Integer videoId);

    @Transactional(timeout = 10)
    @Modifying
    @Query("update VideoInfo v  set v.hot=v.hot+1 where v.videoId=?1")
    void plusHotByVideoId(Integer videoId);
}
