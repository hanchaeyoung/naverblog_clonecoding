package spring.naverblog_clonecoding.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.naverblog_clonecoding.dto.BoardDto;
import spring.naverblog_clonecoding.dto.CommentDto;
import spring.naverblog_clonecoding.entity.BoardEntity;
import spring.naverblog_clonecoding.service.BoardService;
import spring.naverblog_clonecoding.service.CommentService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    @Autowired
    private HttpSession httpSession;

    private BoardService boardService;
    private CommentService commentService;

    /* 게시글 목록 */
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list.html";
    }

    /* 게시글 상세 */
    @GetMapping("/post/detail/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        /* 댓글 데이터 가져오기 */
        List<CommentDto> comments = commentService.getCommentsByBoardId(no);
        boardDTO.setComments(comments);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }


    /* 게시글 쓰기 */
    @GetMapping("/post/write")
    public String write() {
        return "board/write.html";
    }

//    @PostMapping("/post/write")
//    public String write(BoardEntity boardEntity, Authentication authentication, MultipartFile imgFile) {
//        try {
//            String writer = authentication.getName();
//            boardEntity.setWriter(writer);
//            boardService.savePhoto(boardEntity, imgFile);
//            return "redirect:/";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "error_page";
//        }
//    }

    @PostMapping("/post/write")
    public String write(BoardEntity boardEntity, Authentication authentication, MultipartFile imgFile) {
        try {
            String writer = authentication.getName();
            boardEntity.setWriter(writer);

            // 기존 이미지 파일명을 빈 문자열로 초기화
            String existingImg = "";

            // 이미지를 업로드한 경우, 기존 이미지 파일명을 가져옴
            if (boardEntity.getId() != null) {
                BoardDto existingBoardDto = boardService.getPost(boardEntity.getId());
                existingImg = existingBoardDto.getImgName();
            }

            boardService.savePhoto(boardEntity, imgFile, existingImg);
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "error_page";
        }
    }


    /* 게시글 수정 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

//    @PostMapping("/post/edit/{no}")
//    public String update(@PathVariable("no") Long no, BoardDto boardDto) {
//        boardDto.setId(no);
//
//        boardService.savePost(boardDto);
//
//        return "redirect:/post/detail/{no}";
//    }

    @PostMapping("/post/edit/{no}")
    public String update(@PathVariable("no") Long no, BoardDto boardDto, @RequestParam("imgFile") MultipartFile imgFile) {
        boardDto.setId(no);

        // 기존 이미지 파일명을 가져옴
        BoardDto existingBoardDto = boardService.getPost(no);
        String existingImg = existingBoardDto.getImgName();

        // 이미지 수정 메소드 호출
        try {
            boardService.savePhoto(boardDto.toEntity(), imgFile, existingImg);
            return "redirect:/post/detail/" + no;
        } catch (IOException e) {
            e.printStackTrace();
            return "error_page";
        }
    }

    /* 게시글 삭제 */
    @PostMapping("/post/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }


    /* 게시글 검색 */
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }

    /* 내 게시글 */
    @GetMapping("/board/my")
    public String myPage(Authentication authentication, Model model) {
        String username = authentication.getName(); // 현재 인증된 사용자의 이름을 가져옴
        List<BoardDto> myPosts = boardService.getMyPosts(username);
        model.addAttribute("myPosts", myPosts);
        return "board/myList.html";
    }
}