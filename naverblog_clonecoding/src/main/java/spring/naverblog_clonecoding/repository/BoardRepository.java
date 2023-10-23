package spring.naverblog_clonecoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.naverblog_clonecoding.entity.BoardEntity;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
    List<BoardEntity> findByWriter(String writer);
}