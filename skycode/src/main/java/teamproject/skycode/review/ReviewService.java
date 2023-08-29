package teamproject.skycode.review;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }
    public Review show(Long id) {
        return reviewRepository.findById(id).orElseThrow(NullPointerException::new);
    }



}
