package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

// extends에서 받은 기능 어마무시하게 많음 => spring data JPA
public interface MemberRepository extends JpaRepository<Member, Long> { // ctrl + p 누르면 뭐 넣어야 하는지 나옴, Type & ID
    // interface 상속받은 interface 이고 구현체 (기능들 작성한거) 없음

    // 쿼리 메서드 : 메서드 이름만 보고 해줌
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername") // entity의 @NamedQuery 의 name
    List<Member> findByUsername(@Param("username") String username); // @NamedQuery 의 where절 param 쓴 경우 @Param 써줘야함
}
