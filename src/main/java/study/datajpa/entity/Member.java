package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue // 값 순차적으로 넣어줌
    private Long id;
    private String username;

    protected Member() {} // entity는 default 생성자가 있어야 함, private으로 만들면 안됨 (protect로 열어놔야함 _ 보호해야함)

    public Member(String username) {
        this.username = username;
    }

}
