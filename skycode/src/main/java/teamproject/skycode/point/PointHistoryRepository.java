package teamproject.skycode.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.skycode.login.MemberEntity;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistoryEntity, Long> {

    List<PointHistoryEntity> findByMemberPointEntity_MemberEntityId(Long memberEntityId);

}

