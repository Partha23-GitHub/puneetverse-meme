package com.puneetverse.repository;


import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.puneetverse.models.VideoModel;

@Repository
public interface VideoModelRepository extends MongoRepository<VideoModel, Integer>{

	Page<VideoModel> findByCaptionsRegex(@Param("captions") String captions,Pageable pageable);
	List<VideoModel> findByCaptionsIgnoreCaseRegex(@Param("captions") String captions);
}
