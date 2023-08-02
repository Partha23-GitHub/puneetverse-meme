package com.puneetverse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.puneetverse.models.VideoModel;

@DataMongoTest
public class VideoModelTestRepository {
	@Autowired
	private VideoModelRepository videoModelRepository;
	
	Pageable p=null;
	VideoModel video=null;
	
	@BeforeEach
	public void create() {
		int pageNumber=0,pageSize=10;
		p=PageRequest.of(pageNumber, pageSize);
		
		video=new VideoModel();
	}
	
	@Test
	@Disabled("Avoid DB saving")
	public void uploadVideo() {
	    video.setId(10);
	    video.setCaptions("video caption");
	    video.setUrl("http://www.google.com");
	    video.setUploadDateAndTime("2023/07/21 12:00:06");
	    video.setViews(89L);
	    
	    // Call the method being tested
	    VideoModel savedVideo = videoModelRepository.save(video);

	    // Verify the returned object has the expected values
	    assertEquals(video.getUrl(), savedVideo.getUrl());
	    assertEquals(video.getCaptions(), savedVideo.getCaptions());
	  
	}
	
	@Test
	public void getAllVideos() {
		List<VideoModel> videos = videoModelRepository.findAll(p).getContent();
		
		assertEquals(true, videos.size()>0);
	}
	
	@Test
	public void searchVideosByCaption() {
		String caption="puneet";
		List<VideoModel> searchVideos = videoModelRepository.findByCaptionsRegex("(?i).*" + caption.toLowerCase() + ".*", p).getContent();
		
		assertEquals(true, searchVideos.size()>0);
	}
}
