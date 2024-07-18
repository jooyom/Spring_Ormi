package com.example.july08.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service //어노테이션을 통해 ioc 컨테이너에 등록
public class BoardPostService {
    private static final Logger log = LoggerFactory.getLogger(BoardPostService.class);
    List<BoardPost> boardPosts = new ArrayList<>();
    private Long nextPostId = 1L;
    private Long nextCommentId = 1L;

    public BoardPostDto createBoardPost(BoardPostDto boardPostDto) {
        BoardPost boardPost = convertToBoardPostEntity(boardPostDto);
        boardPost.setId(nextPostId++);
        boardPost.setCreatedAt(LocalDateTime.now());
        boardPosts.add(boardPost);
        return convertToBoardPostDto(boardPost);
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
                Comment comment = convertToCommentEntity(commentDto);
                comment.setBoardPost(boardPost);
                boardPost.addComment(comment);
            });
        }
        return boardPost;
    }

    private static Comment convertToCommentEntity(CommentDto commentDto) {
        //DTO를 받아서 entity로 변환
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setAuthor(commentDto.getAuthor());
        return comment;
    }

    private BoardPostDto convertToBoardPostDto(BoardPost boardPost) {
        BoardPostDto boardPostDto = new BoardPostDto();
        boardPostDto.setId(boardPost.getId());
        boardPostDto.setTitle(boardPost.getTitle());
        boardPostDto.setContent(boardPost.getContent());
        boardPostDto.setAuthor(boardPost.getAuthor());
        boardPostDto.setCreatedAt(boardPost.getCreatedAt());
        boardPostDto.setUpdateAt(boardPost.getUpdateAt());

        if (boardPost.getComments() != null) {
            boardPostDto.setComments(
                    boardPost.getComments().stream().map(BoardPostService::convertToCommentDto)
                            .collect(Collectors.toList())
            );
        }
        return boardPostDto;
    }

    private static CommentDto convertToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentDto.getId());
        commentDto.setContent(commentDto.getContent());
        commentDto.setAuthor(commentDto.getAuthor());
        commentDto.setCreatedAt(commentDto.getCreatedAt());
        return commentDto;
    }

    public List<BoardPostDto> getAllBoardPosts() {
        return boardPosts.stream()
                .map(this::convertToBoardPostDto)
                //map인 이유 BoardPostDto만 리턴해야하는데 각각 객체가 DTO가 아니므로 각 데이터를 DTO로 변환시켜줌
                .collect(Collectors.toList());
    }
    public BoardPostDto getBoardPostDtoById(Long id) {
        log.info("우아랑ㄹ미");
        return boardPosts.stream()
                .filter(post -> post.getId().equals(id))
                .map(this::convertToBoardPostDto)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 글을 찾을 수 없습니다."));
    }

    public void deleteBoardPost(Long id) {
        //여기서 지워주는 역할을 할겁니다.
        BoardPost boardPost = findBoardPostById(id);
        boardPosts.remove(boardPost);
        log.info("삭제 성공하였습니다.");
    }

    private BoardPost findBoardPostById(Long id) {
        //id값을 찾아서 BoardPost로 반환
        //id로 찾을 일이 많기 때문에 빼준 것 (remove로 해도 되지만. )
        //찾는건 여기서하고
        return boardPosts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 글을 찾을 수 없습니다."));
    }

    public BoardPostDto updateBoardPost(Long id, BoardPostDto updateBoardPostDto) {
        BoardPost boardPost = findBoardPostById(id);
        boardPost.setTitle(updateBoardPostDto.getTitle());
        boardPost.setContent(updateBoardPostDto.getContent());
        boardPost.setUpdateAt(LocalDateTime.now());
        return convertToBoardPostDto(boardPost);
    }

    public CommentDto createComment(Long postId, CommentDto createCommentDto) {
        BoardPost boardPost = findBoardPostById(postId);
        Comment comment = convertToCommentEntity(createCommentDto); //두개 다 엔티티
        comment.setId(nextCommentId++);
        comment.setCreatedAt(LocalDateTime.now());
        boardPost.addComment(comment);
        return convertToCommentDto(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        BoardPost boardPost = findBoardPostById(postId);
        Comment comment = findCommentById(commentId, boardPost);

        boardPost.removeComment(comment);

    }

    private static Comment findCommentById(Long commentId, BoardPost boardPost) {
        return boardPost.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }
}
