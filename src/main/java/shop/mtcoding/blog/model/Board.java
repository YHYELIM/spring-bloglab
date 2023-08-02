package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column(nullable = false, length = 20, unique = true)
    // private String username;
    // @Column(nullable = false, length = 20)
    // private String password;
    // @Column(nullable = false, length = 20)
    // private String email;
    @Column(nullable = false, length = 100) // null일수 없는데
    private String title;
    @Column(nullable = true, length = 10000) // null일수 있다 ->제목은 있는데 내용은 없을 수 있다
    private String content;
    private Timestamp createdAt;
    // 누가 썼는지 알아야함 board와 user의 연관관계 필요하다
    // 한쪽만 보지 말고 반대쪽도 봐야함 1:n n이 FK

    // private Integer userId; 이렇게 fk를 만들어도되지만 class 만들면 더 편하다

    @ManyToOne
    private User user;
}
