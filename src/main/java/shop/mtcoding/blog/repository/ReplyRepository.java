package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

//우리가 직접 띄운거
//UserController, BoardController, ReplyController, ErrorController
//repository 다
//스프링이 띄워준거
//EntityManager, HttpSession
//이 모든것들을 Autowired가 띄움
@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    public List<Reply> findByBoardId(Integer boardId) {
        Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }

    @Transactional
    public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {
        Query query = em
                .createNativeQuery(
                        "insert into reply_tb(comment, board_id, user_id) values(:comment,:boardId,:userId)");
        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("userId", userId);
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        // 쿼리완성

        query.executeUpdate();

        // 쿼리 전송
    }

    public void update(ReplyWriteDTO replyWriteDTO, Integer id) {
        Query query = em.createNativeQuery("delete from reply_tb where id = :id");
        query.setParameter("id", id);
        query.setParameter("board_id", replyWriteDTO.getBoardId());
        query.setParameter("comment", replyWriteDTO.getComment());
        query.executeUpdate();

    }

}
