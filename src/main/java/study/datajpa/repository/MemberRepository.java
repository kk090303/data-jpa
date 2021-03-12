package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

//JpaRepository 인터페이스를 상속받음
//MemberRepository 인터페이스를 사용할 경우
//spring data jpa가 인터페이스를 보고 프록시 객체 구현 클래스를 만들어서 injection을 해준다.

public interface MemberRepository extends JpaRepository<Member,Long> {

    // - 메소드 이름만으로 쿼리 생성
    //인터페이스만 제공해도 spring data jpa로 인터페이스만 만들어도 함수의 세부 내용이 제공됌
    //공통 인터페이스 뿐만 아니라 메소드 이름만으로 코드 생성 가능
    //Username & Age , Age & GreaterThan 이라는 조건으로 함수를 제공
    //다음과 같은 형식을 맞추지 않으면 안됌
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
