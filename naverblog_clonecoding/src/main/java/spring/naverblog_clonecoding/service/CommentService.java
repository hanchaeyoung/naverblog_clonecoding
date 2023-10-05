package spring.naverblog_clonecoding.service;

import org.springframework.stereotype.Service;
import spring.naverblog_clonecoding.dto.CommentDto;
import spring.naverblog_clonecoding.entity.BoardEntity;
import spring.naverblog_clonecoding.entity.CommentEntity;
import spring.naverblog_clonecoding.repository.BoardRepository;
import spring.naverblog_clonecoding.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    // ...

    public void createComment(Long boardId, String writer, String content) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        CommentEntity comment = new CommentEntity();
        comment.setWriter(writer);
        comment.setContent(content);
        comment.setBoard(board);

        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(comment);
    }

    public List<CommentDto> getCommentsByBoardId(Long no) {
        List<CommentEntity> comments = commentRepository.findByBoardId(no); // 데이터베이스에서 게시글 ID에 해당하는 댓글 조회
        List<CommentDto> commentDtos = new ArrayList<>(); // CommentDto로 변환된 결과를 저장할 리스트

        for (CommentEntity comment : comments) {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setContent(comment.getContent());
            commentDto.setWriter(comment.getWriter());

            commentDtos.add(commentDto);
        }

        return commentDtos;
    }
}
