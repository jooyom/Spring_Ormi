package com.example.july08.day17;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //어노테이션을 통해 ioc 컨테이너에 등록
public class BoardPostService {
    List<BoardPost> boardPosts = new ArrayList<>();
    private Long nextPostId = 1L;
    private Long nextCommentId = 1L;

    public BoardPostDto createBoardPost(BoardPostDto boardPostDto) {
        BoardPost boardPost = convertToBoardPostEntity(boardPostDto);

    }

    private static BoardPost convertToBoardPostEntity(BoardPostDto boardPostDto) {
        BoardPost boardPost = new BoardPost();
        //setter가 잇어야돼요 인자로 바로 넣어도 되는데 갖고잇는게 너무 많아서 따로 작성??
        boardPost.setTitle(boardPostDto.getTitle());
        boardPost.setContent(boardPostDto.getContent());
        boardPost.setAuthor(boardPostDto.getAuthor());
        if (boardPostDto.getComments() != null) {
            //데이터ㅓ가 있다는 것
            boardPostDto.getComments().forEach(commentDto -> {
                Comment comment = convertTocommentEntity(commentDto);
                comment.setBoardPost(boardPost);
                boardPost.addComment(comment);
            });
        }
        return boardPost;
    }

    private static BoardPostDto convertToBoardPostDto(BoardPost boardPost) {
        BoardPostDto boardPostDto = new BoardPostDto();
        boardPostDto.setId(boardPost.getId());
        boardPostDto.setTitle(boardPost.getTitle());
        boardPostDto.setContent(boardPost.getContent());
        boardPostDto.setAuthor(boardPost.getAuthor());
        boardPostDto.setCreatedAt(boardPost.getCreatedAt());
        boardPostDto.setUpdateAt(boardPost.getUpdateAt());

        if(boardPost.getComments()!=null){
            boardPostDto.setComments(
                    boardPost.getComments().stream().map(this::convertToCommentDto)
                            .collect(Collectors.toList())
            );
        }
        return boardPostDto;
    }

}
