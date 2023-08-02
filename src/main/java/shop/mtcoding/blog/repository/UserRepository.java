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

    @Transactional
    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    @Transactional
    public void save(JoinDTO joinDTO) {
        Query query = em
                .createNativeQuery("insert into user_tb(username,password,email) values(:username,:password,:email)");
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate();
    }

}
