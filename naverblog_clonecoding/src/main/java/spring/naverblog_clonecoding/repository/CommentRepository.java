package spring.naverblog_clonecoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.naverblog_clonecoding.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardId(Long no);
//    List<CommentEntity> findByBoard(BoardEntity board);
//    List<CommentEntity> findByUser(UserEntity name);
}