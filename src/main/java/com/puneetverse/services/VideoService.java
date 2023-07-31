package com.puneetverse.services;


import org.springframework.web.multipart.MultipartFile;

import com.puneetverse.models.VideoModel;
import com.puneetverse.payloads.PaginatedMemesList;

public interface VideoService {
	VideoModel uploadVideo(MultipartFile file,String caption);
    
    byte[] downloadVideo(String fileName);
    
    PaginatedMemesList getAllVideos(Integer pageNumber,Integer pageSize);
    
    PaginatedMemesList getLatestVideos(Integer pageNumber,Integer pageSize);
    PaginatedMemesList getTrendingVideos(Integer pageNumber,Integer pageSize);
    
    PaginatedMemesList searchVideosByCaption(String caption,Integer pageNumber,Integer pageSize);
    
    boolean deleteVideo(Integer id);
    
    Long updateViews(Integer id);
    
}
