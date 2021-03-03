package study.datajpa.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//->JPA에서 요구하는 기본 생성자를 코드에 포함시켜주는 lombok 어노테이션
@ToString(of = {"id", "username", "age"}) //tip. 연관 관계 필드는 제외한다.
//->ToString 메소드 자동 생성해주는 lombok 어노테이션
//->객체가 가지고 있는 정보나 값들을 문자열로 만들어 이를 출력할 수 있게 해주는 함수(ToString)를 자동 생성

public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id") //db 테이블은 관례상 PK를 테이블명_id 로 많이 설정한다.
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계 설정
    //fetchtype 을 lazy로 설정한다.(지연로딩)
    //-> member를 조회할 때는 member만 가져오고 team 값을 사용할 때 team을 query로 가져온다.
    @JoinColumn(name = "team_id") //외래키 설정 (외래키는 "Many" 에서 지정한다.)
    private Team team;

    //JPA는 기본적으로 parameter없는 default생성자를 요구한다.
    //private(x) protected까지만 허용한다.
    //protected Member(){} -> NoArgsConstructor를 통해 생략가능

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }


    //(team-member)서로 연관관계를 세팅하는 메서드
    public void changeTeam(Team team) {
        this.team = team; //현재 member의 team을 설정해줌
        team.getMembers().add(this); //해당 team에 현재 member를 추가해줌
    }

}
