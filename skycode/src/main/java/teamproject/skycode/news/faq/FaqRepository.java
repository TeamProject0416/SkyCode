package teamproject.skycode.news.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {

}
