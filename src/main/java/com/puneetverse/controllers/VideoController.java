package com.puneetverse.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.puneetverse.constants.PaginationConstants;
import com.puneetverse.models.VideoModel;
import com.puneetverse.payloads.PaginatedMemesList;
import com.puneetverse.services.VideoService;

@RestController
@RequestMapping("puneetverse")
public class VideoController {
	@Autowired private VideoService videoService;
	
	//for uploading the video
	@PostMapping("/upload")
	public ResponseEntity<VideoModel>uploadVideo(@RequestParam(value = "file") MultipartFile video,@RequestParam(value = "caption")String caption) {
		
		//send for upload
		VideoModel uploadVideoDetails = this.videoService.uploadVideo(video,caption);
		
		return new ResponseEntity<VideoModel>(uploadVideoDetails,HttpStatus.CREATED);
	}
	
	//for downloading the video
	@GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@Param("fileName") String fileName) {
        byte[] data = videoService.downloadVideo(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
	
	//search videos by captions
	@GetMapping("/search")
	public ResponseEntity<PaginatedMemesList>searchBycaption(@RequestParam("caption") String caption,@RequestParam(value="pageNumber",defaultValue=PaginationConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=PaginationConstants.PAGE_SIZE,required = false)Integer pageSize) {
		if (caption == null) {
	        return ResponseEntity.badRequest().build(); // Return a 400 Bad Request response if the parameter is null
	    }
		
		PaginatedMemesList searchedVideos = this.videoService.searchVideosByCaption(caption,pageNumber, pageSize);
		return new ResponseEntity<PaginatedMemesList>(searchedVideos,HttpStatus.OK);
   }
	
	//get all videos randomly
	@GetMapping("/memes")
	public ResponseEntity<PaginatedMemesList>getAllVideos(@RequestParam(value="pageNumber",defaultValue=PaginationConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=PaginationConstants.PAGE_SIZE,required = false)Integer pageSize) {
		
		PaginatedMemesList allVideos = this.videoService.getAllVideos(pageNumber, pageSize);
		
		return new ResponseEntity<PaginatedMemesList>(allVideos,HttpStatus.OK);
   }
	
	//get memes based on views
	@GetMapping("/trendingmemes")
	public ResponseEntity<PaginatedMemesList>getTrendingVideos(@RequestParam(value="pageNumber",defaultValue=PaginationConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=PaginationConstants.PAGE_SIZE,required = false)Integer pageSize) {
		
		PaginatedMemesList trendingVideos = this.videoService.getTrendingVideos(pageNumber, pageSize);
		
		return new ResponseEntity<PaginatedMemesList>(trendingVideos,HttpStatus.OK);
   }
	
	//get letest uploded memes
	@GetMapping("/latestmemes")
	public ResponseEntity<PaginatedMemesList>getLatestVideos(@RequestParam(value="pageNumber",defaultValue=PaginationConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=PaginationConstants.PAGE_SIZE,required = false)Integer pageSize) {
		
		PaginatedMemesList trendingVideos = this.videoService.getLatestVideos(pageNumber, pageSize);
		
		return new ResponseEntity<PaginatedMemesList>(trendingVideos,HttpStatus.OK);
   }
	
	//delete a video
	@DeleteMapping("/delete")
	public ResponseEntity<?>deleteVideo(@Param("id")Integer id) {
		
		this.videoService.deleteVideo(id);
		
		return new ResponseEntity<Map<String,Boolean>>(Map.of("deleted",true),HttpStatus.OK);
	}
	
	// update views of a video
	@PatchMapping("/addviews")
	public ResponseEntity<?>addViewsToVideo(@RequestParam("id") Integer id) {
		Long updateViews = this.videoService.updateViews(id);

		return new ResponseEntity<Map<String,Long>>(Map.of("views",updateViews),HttpStatus.OK);
	}
	
}
