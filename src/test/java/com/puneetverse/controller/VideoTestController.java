package com.puneetverse.controller;

import com.puneetverse.controllers.VideoController;
import com.puneetverse.models.VideoModel;
import com.puneetverse.payloads.PaginatedMemesList;
import com.puneetverse.services.VideoService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VideoController.class)
@ExtendWith(SpringExtension.class)
public class VideoTestController {
	@Autowired private MockMvc mockMvc;
	@Mock private VideoService videoService;
	
	@InjectMocks
	private VideoController videoController;
	
	VideoModel video=null;
			
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(mockMvc);
		
		video=new VideoModel();
		video.setId(10);
	    video.setCaptions("video caption");
	    video.setUrl("http://www.google.com");
	    video.setUploadDateAndTime("2023/07/21 12:00:06");
	    video.setViews(89L);
	}
	@Test
	public void videoSearchByCaption() throws Exception{
		List<VideoModel>videoList=new ArrayList<>();
		videoList.add(video);
		
		PaginatedMemesList memesList=new PaginatedMemesList();
		memesList.setVideos(videoList);
		
		when(videoService.searchVideosByCaption("puneet", 1, 10)).thenReturn(memesList);
		System.out.println(videoService.searchVideosByCaption("puneet", 1, 10));
		 // Testing the controller endpoint
        mockMvc.perform(get("/puneetverse/search"))
        	.andExpect(status().isOk());
	}
}
