package teamproject.skycode.news.notion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotionRepository extends JpaRepository<Notion, Long> {

}
