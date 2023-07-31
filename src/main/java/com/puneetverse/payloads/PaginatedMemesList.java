package com.puneetverse.payloads;

import java.util.List;

import com.puneetverse.models.VideoModel;

import lombok.Data;

@Data
public class PaginatedMemesList{
	
	private Integer pageNumber;
	private Integer pageSize;
	private List<VideoModel>videos;
	private Integer totalElements;
	private Integer totalPages;
	private boolean isLastPage;
}
