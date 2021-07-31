package spring.finEdu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import spring.finEdu.entity.Member;

@Repository //Bean에 등록하여 사용
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

}
