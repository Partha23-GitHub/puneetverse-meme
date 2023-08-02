package com.puneetverse.servicesImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.puneetverse.constants.PaginationConstants;
import com.puneetverse.models.VideoModel;
import com.puneetverse.payloads.PaginatedMemesList;
import com.puneetverse.processes.VideoProcess;
import com.puneetverse.repository.VideoModelRepository;
import com.puneetverse.services.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired VideoProcess videoProcess;
	@Autowired private SequenceGenerator sequenceGenerator;
	@Autowired private VideoModelRepository videoModelRepository;
	
	@Override
	public VideoModel uploadVideo(MultipartFile file,String caption) {
		
		VideoModel videoModel = new VideoModel();
		
		videoModel.setId(this.sequenceGenerator.getSequenceNumber(VideoModel.SEQUENCE_NAME));
		
		//upload video and get the fileName
		String uploadedFileName = this.videoProcess.uploadToS3(file);
		
		//set the s3 bucket link for streaming
		videoModel.setUrl(this.videoProcess.getS3VideoUrl(uploadedFileName));
		videoModel.setCaptions(caption.toLowerCase());
		videoModel.setUploadDateAndTime(this.getCurrentDateAndTime());
		videoModel.setViews((long)ThreadLocalRandom.current().nextInt(30, 100 + 1));	//setting initial views between 30 to 100
		
		return this.videoModelRepository.save(videoModel);
	}
	
	@Override
	public byte[] downloadVideo(String fileName) {
		
		return this.videoProcess.downloadFile(fileName);
	}
	
	@Override
	public PaginatedMemesList getAllVideos(Integer pageNumber,Integer pageSize) {
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<VideoModel> videosInPages = this.videoModelRepository.findAll(p);
		List<VideoModel> allVideos = videosInPages.getContent();
		//shuffled all videos in the list
		List<VideoModel> shuffeledVideoList = allVideos.stream()
        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.shuffle(list);
            return list;
        }));
		PaginatedMemesList memeList= new PaginatedMemesList();
		
		memeList.setVideos(shuffeledVideoList);
		memeList.setPageNumber(videosInPages.getNumber());
		memeList.setPageSize(videosInPages.getSize());
		memeList.setTotalElements((int)videosInPages.getTotalElements());
		memeList.setTotalPages(videosInPages.getTotalPages());
		memeList.setLastPage(videosInPages.isLast());
		
		return memeList;
	}


	@Override
	public PaginatedMemesList getLatestVideos(Integer pageNumber,Integer pageSize) {
		Sort sort=Sort.by(PaginationConstants.UPLOAD_DATE_AND_TIME).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<VideoModel> videosInPages = this.videoModelRepository.findAll(p);
		
		List<VideoModel> allVideos = videosInPages.getContent().stream().collect(Collectors.toList());
		//sort the videos by uploadDateAndTime in descending order
		Collections.sort(allVideos, Comparator.comparing(VideoModel::getUploadDateAndTime)
                .reversed());
		
		PaginatedMemesList memeList= new PaginatedMemesList();
		
		memeList.setVideos(allVideos);
		memeList.setPageNumber(videosInPages.getNumber());
		memeList.setPageSize(videosInPages.getSize());
		memeList.setTotalElements((int)videosInPages.getTotalElements());
		memeList.setTotalPages(videosInPages.getTotalPages());
		memeList.setLastPage(videosInPages.isLast());
		
		return memeList;
	}

	@Override
	public PaginatedMemesList getTrendingVideos(Integer pageNumber,Integer pageSize) {
		Sort sort=Sort.by(PaginationConstants.SORT_BY_VIEWS).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<VideoModel> videosInPages = this.videoModelRepository.findAll(p);
		
		List<VideoModel> allVideos = videosInPages.getContent();
		
		PaginatedMemesList memeList= new PaginatedMemesList();
		
		memeList.setVideos(allVideos);
		memeList.setPageNumber(videosInPages.getNumber());
		memeList.setPageSize(videosInPages.getSize());
		memeList.setTotalElements((int)videosInPages.getTotalElements());
		memeList.setTotalPages(videosInPages.getTotalPages());
		memeList.setLastPage(videosInPages.isLast());
		
		return memeList;
	}
	
	@Override
	public PaginatedMemesList searchVideosByCaption(String caption,Integer pageNumber,Integer pageSize) {
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<VideoModel> videosInPages = this.videoModelRepository.findByCaptionsRegex("(?i).*" + caption.toLowerCase() + ".*", p);
		
		List<VideoModel> allVideos = videosInPages.getContent();
		//shuffled all videos in the list
				List<VideoModel> shuffeledVideoList = allVideos.stream()
		        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
		            Collections.shuffle(list);
		            return list;
		        }));
		
		PaginatedMemesList memeList= new PaginatedMemesList();
		
		memeList.setVideos(shuffeledVideoList);
		memeList.setPageNumber(videosInPages.getNumber());
		memeList.setPageSize(videosInPages.getSize());
		memeList.setTotalElements((int)videosInPages.getTotalElements());
		memeList.setTotalPages(videosInPages.getTotalPages());
		memeList.setLastPage(videosInPages.isLast());
		
		return memeList;
	}
	
	@Override
	public boolean deleteVideo(Integer id) {
		VideoModel video = this.videoModelRepository.findById(id).orElseThrow(()->new RuntimeException("Video Not Found"));
		
		String videoUrl=video.getUrl();
		String videoName=videoUrl.substring(videoUrl.lastIndexOf("/")+1);
		boolean isDeleted = this.videoProcess.deleteFileFromS3(videoName);
		if(isDeleted) {
			try {
				this.videoModelRepository.delete(video);
				isDeleted=true;
			}catch (Exception e) {
				throw new RuntimeException("Something went wrong Video is not deleted");
			}
		}
			
		return isDeleted;
	}
	
	@Override
	public Long updateViews(Integer id) {
		VideoModel video = this.videoModelRepository.findById(id).orElseThrow(()-> new RuntimeException("Video Not Found"));
		video.setViews(video.getViews()+2);
		
		VideoModel updatedVideo = this.videoModelRepository.save(video);
		return updatedVideo.getViews();
	}
	
	public String getCurrentDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

}
