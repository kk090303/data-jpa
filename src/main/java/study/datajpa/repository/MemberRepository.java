package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

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
    //컴파일 에러를 통해 문법 오류를 모두 잡아줄 수 있다는 장점이 있음
    @Query("Select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();


    //new 를 통해 객체와 연결시켜주어야 한다
    //Dto 로 반환하기 위한 @Quert 를 통한 조회 함수
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();


    //파라미터 바인딩 - 이름 기반
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    //스프링 데이터 JPA 는 유연한 반환 타입을 지원한다.
    // - 메소드 이름만으로 쿼리 생성을 통한 다양한 반환 타입
    //컬랙션
    List<Member> findListByUsername(String username);
    //단건
    Member findMemberByUsername(String username);
    //단건 Optional
    Optional<Member> findOptionalByUsername(String username);
}
