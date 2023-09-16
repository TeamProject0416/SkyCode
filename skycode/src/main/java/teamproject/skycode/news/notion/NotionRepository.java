package teamproject.skycode.news.notion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface NotionRepository extends JpaRepository<Notion, Long> {
    @Query("SELECT i FROM Notion i ORDER BY i.regTime DESC")
    List<Notion> findAllOrderNotionByRegistrationTimeDesc();


    List<Notion> findAllByOrderByCountViewDesc();

    Page<Notion> findAll(Pageable pageable);
}
