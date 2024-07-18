package com.example.july08.day17;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private BoardPost boardPost;

}
