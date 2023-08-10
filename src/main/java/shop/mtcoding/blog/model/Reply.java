package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//User(1)- Reply(n)
//Board(1)-Reply(n)
//n->fk
@Getter
@Setter
@Entity
@Table(name = "reply_tb")

public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false, length = 100) // null값 허용 안함
    private String comment;// 댓글내용

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;// fk user_id 생김

    @ManyToOne
    private Board board;
    // fk board_id 생김

}
