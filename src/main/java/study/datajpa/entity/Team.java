package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","name"})
public class Team {

    @Id
    @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //일대다 관계 설정, mappedBy를 통해 team과 연관 되어 있는 것을 설정
    //mappedBy는 외래키가 없는 One 에 속하는 클래스에 설정해주는 것이 좋다.
    private List<Member> members = new ArrayList<>();
    //하나의 team에 대하여 일대다 관계인 member를 List로 갖는다.

    public Team(String name){
        this.name = name;
    }
}
