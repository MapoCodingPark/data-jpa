package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional; // 얘가 기능 많은 transaction
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat; // static으로 assertions 안써도 Assertions.assertThat 됨

@SpringBootTest // spring container : spring bean 다 injection 받아서 쓸거임
@Transactional // jpa 의 모든 data 변경은 transaction 에서 일어나야함
@Rollback(false) // test 끝나고 rollback 하는거 방지 > test에서 일어난 db 변화 등을 볼 수 있고, console log로도 보임 _ 실무에서는 없어야지/**/
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member); // ctrl + alt + v : 변수명으로 꺼내기

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // jpa 에서는 transaction 안에서는 같은 애는 같은 instance > 1차 캐시 공부해봐
    }

}