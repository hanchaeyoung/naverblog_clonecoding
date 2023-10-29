package spring.naverblog_clonecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.naverblog_clonecoding.dto.BoardDto;
import spring.naverblog_clonecoding.entity.BoardEntity;
import spring.naverblog_clonecoding.repository.BoardRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 11;  // 블럭에 존재하는 페이지 번호 수 5
    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수 4

    /*게시글 목록 가져옴*/
    @Transactional
    public List<BoardDto> getBoardlist(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        return this.convertEntityToDto(boardEntity);
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .imgName(boardEntity.getImgName())
                .imgPath(boardEntity.getImgPath())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    @Transactional
    public List<BoardDto> getMyPosts(String username) {
        List<BoardEntity> boardEntities = boardRepository.findByWriter(username);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

//    /* 이미지 저장 */
//    public void savePhoto(BoardEntity boardEntity, MultipartFile imgFile) throws IOException {
//
//        String oriImgName = imgFile.getOriginalFilename();
//        String imgName = "";
//
//        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
//
//        // UUID 를 이용하여 파일명 새로 생성
//        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
//        UUID uuid = UUID.randomUUID();
//
//        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
//
//        imgName = savedFileName;
//
//        File saveFile = new File(projectPath, imgName);
//
//        imgFile.transferTo(saveFile);
//
//        boardEntity.setImgName(imgName);
//        boardEntity.setImgPath("/files/" + imgName);
//
//        boardRepository.save(boardEntity);
//    }

    public void savePhoto(BoardEntity boardEntity, MultipartFile imgFile, String existingImg) throws IOException {
        // 기존 이미지를 삭제하지 않고, 새 이미지가 업로드되면 그것을 사용하도록 변경
        String imgName = existingImg;

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        if (imgFile != null && !imgFile.isEmpty()) {
            String oriImgName = imgFile.getOriginalFilename();

            // UUID를 이용하여 파일명 생성
            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid + "_" + oriImgName;

            imgName = savedFileName;

            File saveFile = new File(projectPath, imgName);
            imgFile.transferTo(saveFile);
        }

        boardEntity.setImgName(imgName);
        boardEntity.setImgPath("/files/" + imgName);

        boardRepository.save(boardEntity);
    }
}