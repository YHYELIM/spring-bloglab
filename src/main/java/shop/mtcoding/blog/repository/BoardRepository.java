package shop.mtcoding.blog.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;

@Repository
public class BoardRepository {

    @Autowired
    private EntityManager em;

    // select id, title from board_tb
    // resultClass 안붙이고 직접 파싱하려면
    // Object[]로 리턴도미
    // object[0]=1
    // object[1]=제목1

    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete * from board_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

    public int count() {
        // 엔티티 타입이 아니어도 기본자료형 리턴 안되더라
        // 엔티티가 뭘까 컨트롤 클릭
        // 그룹바이 쿼리 스트링
        Query query = em.createNativeQuery("select count (*) from board_tb");
        // 원래는 Object 배열로 리턴 받는다 Object 배열은 칼럼의 연속이다
        // 그룹함수를 써서, 하나의 칼럼을 조회하면, Object 로 리턴된다
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();

    }

    // localhost:8080?page=0;
    public List<Board> findAll(int page) {
        final int SIZE = 4;// 상수는 대문자로 쓴다
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit :page,:size", Board.class);
        query.setParameter("page", page * SIZE);
        query.setParameter("size", SIZE);
        return query.getResultList();
    }

    @Transactional
    public void save(WriteDTO writeDTO, Integer userId) {
        Query query = em
                .createNativeQuery(
                        "insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, now())");

        query.setParameter("title", writeDTO.getTitle());
        query.setParameter("content", writeDTO.getContent());
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    // 한 방쿼리 넣는 곳
    public List<BoardDetailDTO> findByIdJoinReply(int boardId) {

        String sql = "select";
        sql += "b.id board_id, ";
        sql += "b.content board_content, ";
        sql += "b.title board_title, ";
        sql += "b.user_id board_user_id, ";
        sql += "r.id reply_id, ";
        sql += "r.comment reply_comment, ";
        sql += "r.user_id reply_user_id, ";
        sql += "ru.username reply_user_username, ";
        sql += "from board_tb b left outer join reply_tb r, ";
        sql += "on b.id = r.board_id, ";
        sql += "left outer join user_tb ru, ";
        sql += "on r.user_id = ru.id, ";
        sql += "where b.id = :boardId ";
        sql += "order by r.id desc";
        Query query = em.createNativeQuery(sql);
        query.setParameter("boardId", boardId);

        JpaResultMapper mapper = new JpaResultMapper();
        // → jparesultmapper: 자동으로 맵핑해줌(qlrm 라이브러리)
        List<BoardDetailDTO> dtos = mapper.list(query, BoardDetailDTO.class);
        return dtos;

    }

    public Board findById(Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        Board board = (Board) query.getSingleResult();
        return board;
    }// 상세보기 할때 얘로 썼음

    public void update(UpdateDTO updateDTO, Integer id) {
        Query query = em.createNativeQuery("delete from board_tb where id = :id");
        query.setParameter("id", id);
        query.setParameter("title", updateDTO.getTitle());
        query.setParameter("content", updateDTO.getContent());
        query.executeUpdate();

    }
}
