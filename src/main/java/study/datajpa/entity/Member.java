package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 lombok => protected Member()
@ToString(of = {"id", "username", "age"}) // 객체 찍을 때 출력되는거, 연관관계 있는 거는 찍으면 무한루프 탈 수 있으니 조심 (team x)
@NamedQuery( // entity에다가 직접 작성하는구나...
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member {

    @Id
    @GeneratedValue // 값 순차적으로 넣어줌
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 성능을 위해 Lazy 걸어둬야함 (member 조회할 때 team은 가짜로 갖고 있다가, 필요할 때 실제로 조회해옴)
    @JoinColumn(name = "team_id") // FK
    private Team team;

//    protected Member() {} // entity는 default 생성자가 있어야 함, private으로 만들면 안됨 (protect로 열어놔야함 _ 보호해야함)

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age){
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            this.team = team;
            team.getMembers().add(this);
        }
    }

    public void changTeam(Team team) {
        team.getMembers().remove(this); // 원래 팀에서 빼줘야 하지 않나 ?
        this.team = team;
        team.getMembers().add(this);
    }
}
