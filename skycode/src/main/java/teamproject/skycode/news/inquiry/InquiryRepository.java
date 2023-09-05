package teamproject.skycode.news.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableJpaRepositories
public interface InquiryRepository extends JpaRepository<Inquiry, Long > {

    @Query("SELECT i FROM Inquiry i ORDER BY i.regTime DESC")
    List<Inquiry> findAllOrderByRegistrationTimeDesc();

    List<Inquiry> findAllByOrderByViewCountDesc();

    List<Inquiry> findByInquiryTitleContaining(String searchValue);

    List<Inquiry> findByInquiryContentContaining(String searchValue);

    List<Inquiry> findByIdContaining(String searchValue);




//    List<Inquiry> findByUserNicknameContaining(String searchValue);

//    List<Inquiry> findByHashtagsContaining(String searchValue);





}
