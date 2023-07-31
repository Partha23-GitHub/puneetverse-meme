package com.puneetverse.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "db_sequence")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sequence {
    @Id
    private String id;
    private int seq;
}
