package teamproject.skycode.news.notion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotionViewCountRepository extends JpaRepository<NotionViewCount, Long> {
    NotionViewCount findByNotionId(Long notionId);
}
