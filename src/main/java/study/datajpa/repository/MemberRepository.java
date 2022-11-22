package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;

// extends에서 받은 기능 어마무시하게 많음 => spring data JPA
public interface MemberRepository extends JpaRepository<Member, Long> { // ctrl + p 누르면 뭐 넣어야 하는지 나옴, Type & ID
    // interface 상속받은 interface 이고 구현체 (기능들 작성한거) 없음

    // 1. 쿼리 메서드 : 메서드 이름만 보고 해줌
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 2. 쿼리 메서드 : named query
//    @Query(name = "Member.findByUsername")
    // entity에서 @NamedQuery 의 name
//    List<Member> findByUsername(@Param("username") String username); // @NamedQuery 의 where절 param 쓴 경우 @Param 써줘야함

    // 3. 쿼리 메서드 : query 직접 작성
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUserQuery(@Param("username") String username, @Param("age") int age);


    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
        // dto는 new operation 써줘야 함
    List<MemberDto> findMemberDto();


    // param 바인딩 - collection 타입으로 in 절 지원
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    // 반환타입
//    List<Member> findByUsername(String name); //컬렉션
//    Member findByUsername(String name); //단건
//    Optional<Member> findByUsername(String name); //단건 Optional

    // 페이징, 정렬
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용
//    Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByUsername(String name, Sort sort);


    // 벌크성 수정 쿼리
    @Modifying(clearAutomatically = true) // 변경하는 어노테이션 꼭 넣어 줘야함
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    // @EntityGraph
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


    @Override // 위 findMemberFetchJoin 와 같은 동작
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();


    //JPQL + 엔티티 그래프
//    @EntityGraph(attributePaths = {"team"})
//    @Query("select m from Member m")
//    List<Member> findMemberEntityGraph();
    // NamedEntityGraph 사용
    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();


    //메서드 이름 + 앤티티 그래프 (특히 편함)
    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(String username);


    // jpa hint
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);

}
