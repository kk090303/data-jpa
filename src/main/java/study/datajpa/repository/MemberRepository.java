package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    //-> 문제점 : 파라미터가 3개이상이거나 너무 길어서 규칙을 맞추다보면 메소드 이름이 너무 길어짐


    //@Query, 리포지토리 메소드에 쿼리 정의하기
    @Query("Select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
