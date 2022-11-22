package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // join 연결해주면 한쪽에 mappedBy 걸어줘야함 / FK 없는 쪽 (N:1 인 경우 1쪽) 에 걸면 좋다
    private List<Member> members = new ArrayList<>();

    public Team(String name){
        this.name = name;
    }
}
