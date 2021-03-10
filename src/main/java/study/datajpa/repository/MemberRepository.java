package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

//JpaRepository 인터페이스를 상속받음
//MemberRepository 인터페이스를 사용할 경우
//spring data jpa가 인터페이스를 보고 프록시 객체 구현 클래스를 만들어서 injection을 해준다.

public interface MemberRepository extends JpaRepository<Member,Long> {
}
