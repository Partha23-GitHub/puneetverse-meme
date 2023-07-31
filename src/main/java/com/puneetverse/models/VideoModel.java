package com.puneetverse.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "memes")
@Data
public class VideoModel{

	@Transient
	public static final String SEQUENCE_NAME="meme_sequence";
	
	@Id
    private Integer id;
	@NotBlank @NotNull
    private String captions;
	@NotBlank @NotNull
    private String url;
    //private String thumbnailUrl;
	@NotBlank @NotNull
    private String uploadDateAndTime;
    private Long views;
    
}
