package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

//JpaRepsository<타입, PK>

//@Repository 생략 가능
//-> spring data jpa 가 자동으로 처리
public interface TeamRepository extends JpaRepository<Team, Long> {
}
