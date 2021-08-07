package spring.finEdu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import spring.finEdu.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository //Bean에 등록하여 사용
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

    Optional<Member> findBySeqAndName(Long key, String name); // 메소드 이름을 보고 쿼리문 자동 생성 해준다. 규칙 --> spring data 문서에 있음.

    @Query("SELECT m.org, count(m.seq) FROM Member m where m.active = ?1 group by m.org")
    List<Object> countOrgGroup(Boolean isActive);
}
