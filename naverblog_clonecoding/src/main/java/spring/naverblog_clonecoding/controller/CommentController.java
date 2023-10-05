package spring.naverblog_clonecoding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.naverblog_clonecoding.service.CommentService;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/detail/{no}")
    public String createComment(@PathVariable("no") Long boardNo,
                                @RequestParam("writer") String writer,
                                @RequestParam("content") String content) {
        commentService.createComment(boardNo, writer, content);
        return "redirect:/post/detail/" + boardNo;
    }

    @PostMapping("/post/detail/{no}/{commentId}")
    public String deleteComment(@PathVariable("no") Long boardNo, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/post/detail/" + boardNo;
    }
}
