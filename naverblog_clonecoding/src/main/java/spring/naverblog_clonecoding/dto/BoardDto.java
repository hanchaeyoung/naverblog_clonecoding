package spring.naverblog_clonecoding.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import spring.naverblog_clonecoding.entity.BoardEntity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String imgName; // 이미지 파일명
    private String imgPath; // 이미지 조회 경로
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    private List<CommentDto> comments;

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .imgName(imgName)
                .imgPath(imgPath)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, String imgName, String imgPath, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
