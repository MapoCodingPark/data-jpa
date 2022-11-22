package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

// extends에서 받은 기능 어마무시하게 많음 => spring data JPA
public interface MemberRepository extends JpaRepository<Member, Long> { // ctrl + p 누르면 뭐 넣어야 하는지 나옴, Type & ID
    // interface 상속받은 interface 이고 구현체 (기능들 작성한거) 없음

}
