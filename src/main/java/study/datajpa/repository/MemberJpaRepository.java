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

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }


    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByUsername(String username) {
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }


    // 페이징 정렬
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                        .setParameter("age", age)
                        .setFirstResult(offset) // 어디서 부터 가져올꺼야 ! (페이지 정보)
                        .setMaxResults(limit) // 몇개 가져 올꺼야 !
                        .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }


    // 벌크성 수정 쿼리
    public int bulkAgePlus(int age) {
        return em.createQuery(
                        "update Member m set m.age = m.age + 1" +
                                "where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate(); // 업데이트 한다는 것. 응답 값에 개수 리턴
    }

}
