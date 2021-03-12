package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
        //실제 데이터베이스에서 삭제해줌
    }

    public List<Member> findAll(){
        //JPQL
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    //Optional : null이 될 수 있는 객체를 감싸고 있는 래퍼 클래스
    // -> NPE 를 유발하는 null을 직접 다룰 필요가 없다.
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }

    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age){
        return em.createQuery("select m from Member m where m.username = :username and m.age> : age")
                .setParameter("username", username)
                .setParameter("age",age)
                .getResultList();
    }

}
