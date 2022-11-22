package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional; // 얘가 기능 많은 transaction
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat; // static으로 assertions 안써도 Assertions.assertThat 됨

@SpringBootTest // spring container : spring bean 다 injection 받아서 쓸거임
@Transactional // jpa 의 모든 data 변경은 transaction 에서 일어나야함
@Rollback(false) // test 끝나고 rollback 하는거 방지 > test에서 일어난 db 변화 등을 볼 수 있고 (h2 console 가보면 남아있음), console log로도 보임 _ 실무에서는 없어야지/**/
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

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void update(){
        Member member1 = new Member("member1");
        memberJpaRepository.save(member1);

        // update 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        findMember1.setUsername("changed name");

        assertThat(memberJpaRepository.findById(member1.getId()).get().getUsername()).isEqualTo("changed name");
    }

}