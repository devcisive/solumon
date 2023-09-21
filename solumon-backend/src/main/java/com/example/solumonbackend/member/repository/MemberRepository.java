package com.example.solumonbackend.member.repository;

import com.example.solumonbackend.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByNickname(String nickName);

  Optional<Member> findByMemberId(Long id);

  // Member 의 Ban 상태 해제 (나중에 스케줄러에 넣을것)
  // 호출 순서: releaseBan() -> removeBan()
  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value =
          "UPDATE member SET role = 'ROLE_GENERAL' WHERE member_id IN " +
              "(SELECT member_id FROM ban b WHERE banned_at <= DATE_SUB(NOW(), INTERVAL 1 WEEK));"
  )
  void releaseBan();


}
