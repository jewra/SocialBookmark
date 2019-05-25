package vj.development.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vj.development.demo.domain.Bookmark;

import java.util.List;

@Repository
public interface BookmarkRepository  extends JpaRepository<Bookmark,Long> {

}
