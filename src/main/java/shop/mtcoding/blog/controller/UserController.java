package shop.mtcoding.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.joinDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;
    // request 가방 session 서랍
    // session에 데이터 보관 가능

    // 로그인 에러
    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        // validation check (유효성 검사)
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        // 핵심 기능
        System.out.println("테스트 : username : " + loginDTO.getUsername());
        System.out.println("테스트 : password : " + loginDTO.getPassword());

        try {
            User user = userRepository.findByUsernameAndPassword(loginDTO);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/exLogin";
        }
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout") // 세션만 날리면 된다
    public String logout() {
        session.invalidate();// 세션 무효화(내 서랍을 비우는 것)
        return "redirect:/";
    }

    @PostMapping("/join")
    public void join(joinDTO joinDTO, HttpServletResponse response) throws IOException {
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            response.sendRedirect("/40x");
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            response.sendRedirect("/40x");
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            response.sendRedirect("/40x");
        }
        userRepository.save(joinDTO);
        response.sendRedirect("/loginForm");
    }

}
