package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.model.User;

//boardController , UserController, UserRepository
//EntityManager , HttpSession
@Repository
public class UserRepository {

    @Autowired // 레파지토리에 주입
    private EntityManager em; // 의존성 주입 해준다

    public User findByUsername(String username) {
        try {// 보통은 여기서 try를 잡지 않고 서비스에서 잡음
            Query query = em.createNativeQuery("select * from user_tb where username=:username",
                    User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;

        }

    }

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    @Transactional
    public void save(JoinDTO joinDTO) {

        // 어디까지 실행됐는지 궁금하면 번호를 곳곳에 찍어 본다
        // 앞에 테스트 했던 것들은 주석 처리 해줘야 한 눈에 알아볼수있다
        Query query = em
                .createNativeQuery("insert into user_tb(username,password,email) values(:username,:password,:email)");

        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        System.out.println("테스트1");

        query.executeUpdate();// 쿼리를 전송 (dbms 한테)
        System.out.println("테스트2");

    }

}
