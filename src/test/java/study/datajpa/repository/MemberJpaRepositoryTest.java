package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.*;
//assertJ란 assertion을 제공하는 자바 라이브러리이다.
//Junit에서 제공하는 assertion보다 가독성이 좋고 많은 기능을 제공한다.
//에러 메시지와 테스트 코드의 가독성을 매우 높여준다.
//assert 메소드 중 assertThat은 검사할 대상 데이터와 matcher를 비교하여 테스트 통과 여부를 알려준다.

@SpringBootTest
@Transactional
@Rollback(false) //Test가 끝나고도 rollback이 진행되지 않음 (commit 됌(database에 반영됌))
public class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }
}