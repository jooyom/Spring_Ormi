package com.example.july08.day17;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardPost {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private List<Comment> comments = new ArrayList<>();//코멘트는 여러개할 수 있으므로


    public void addComment(Comment comment){
        comments.add(comment);
        comment.setBoardPost(this); //BoardPost에 comment추가,
        //Comment 클래스에 BoardPost를 갖고있음 comment를 받아와서 BoardPost에 넣음 서로 아는 상태임
        //?ㅋㅋ
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setBoardPost(null);
    }
}
