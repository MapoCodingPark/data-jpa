package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository { // 순수 jpa
    // jpa 는 update 필요 없음
    // entity 변경시 변경 감지로 data를 바꿈
    // 값 꺼내와서 바꾸면, 자동으로 인지해서 update query 날려버림
    // 마치 java collection

    @PersistenceContext // spring boot가 EntityManager 가져와줌 > jpa가 db에 저장, 가져오는거 해줌
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        // JPQL : 전체조회, 특정 조건 조회할 때 사용
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }


    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
