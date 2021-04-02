package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        //JpaRepository 에서 제공되는 save

        Member findMember = memberRepository.findById(savedMember.getId()).get();
        //JpaRepository 에서 제공되는 findById

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }


    //spring data jpa 에서 제공하는 crud 함수들이 모두 통과하는 모습을 볼 수 있다.
    //spring data jpa 를 사용하면 순수 jpa 를 사용하여 만든 class와 거의 유사한 함수들을 알아서 만들어 준다.
    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단권 조회 검색
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 =  memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQuery(){
        Member m1 = new Member("AAA", 10);
        memberRepository.save(m1);
        List<Member> result = memberRepository.findUser("AAA",10);
        assertThat(result.get(0)).isEqualTo(m1);

    }

    @Test
    public void testFindUsernameList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        assertThat(usernameList.get(0)).isEqualTo("AAA");
        assertThat(usernameList.get(1)).isEqualTo("BBB");
    }

    @Test
    public void findMemberDto(){

        Team t1 = new Team("teamA");
        teamRepository.save(t1);
        Member m1 = new Member("AAA", 10, t1);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for(MemberDto dto : memberDto)
        {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA","BBB"));
        for(Member member : result){
            System.out.println("member = " + member);
        }
    }

    //스프링 데이터 JPA 는 유연한 반환 타입을 지원한다.
    @Test
    public void returnType(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        //List는 결과가 없더라도 null이 아니다. (aaa1.size() = 0)
        List<Member> aaa1 = memberRepository.findListByUsername("AAA");
        //single result는 결과가 없으면 null이다.
        //spring data jpa는 결과가 없다면 null값을 반환한다.
        Member findMember = memberRepository.findMemberByUsername("AAA");

        //Optional을 사용하면 데이터가 있을지 없을지 모를 때 사용하기 좋다.
        //Optional은 결과가 없으면 Optional.empty 이다.
        Optional<Member> aaa2 = memberRepository.findOptionalByUsername("AAA");

        //한 건만 조회하였는데 결과가 2개 이상이면 예외가 발생한다.  (NonUniqueResultException)
    }
}