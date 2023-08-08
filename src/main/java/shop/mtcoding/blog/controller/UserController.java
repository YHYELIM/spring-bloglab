package shop.mtcoding.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.JoinDTO;
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

    // localhost:8080/check?username=ssar
    // 중복되는게 있다

    @GetMapping("/check")
    public ResponseEntity<String> check(String username) {
        // responsebody 어노테이션 안붙여도 데이터 응답하고 string으로 응답
        // 중복됨은 string이니까 <string> 해준다
        // 내가 만약에 1을 응답하고 싶으면 <Integer> 해줌
        // 데이터를 응답해줌
        // 만약에 뷰를 응답해주려면 걍 String 적어줌
        // 자스로 요청해서 뷰가 아닌 데이터만 받는것 : ajax통신
        // -> 부분적으로 그려낼 수 있음
        // 아까 자스 코드랑 똑같음
        User user = userRepository.findByUsername(username);
        if (user != null) {

            return new ResponseEntity<String>("중복됨", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("중복되지 않음", HttpStatus.OK);

        // 상태코드 보낼때 사용
        // 스프링은 기본적으로 200을 던져줌
        // 내가 400 던져줄거라고 잡음
        // 응답을 해줄때 상태코드가 중요함 그거가지고 코딩하니까
        // 실패, 성공->http 상태코드로
    }

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
        // System.out.println("테스트 : username : " + loginDTO.getUsername());
        // System.out.println("테스트 : password : " + loginDTO.getPassword());

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

        // templates/
        // .mustache
        // templates//user/joinForm.mustache
        return "user/joinForm"; // ViewResolver
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

    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        // validation check (유효성 검사)
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }
        // DB에 해당 username이 있는지 체크해보기
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            return "redirect:/50x";
        }
        userRepository.save(joinDTO); // 핵심 기능
        return "redirect:/loginForm";
    }// 얘도 중복확인을 해야하는게 포스트맨으로 공격받을수 있다
     // 예외처리 해봄? 웹프로그램 만들때 프론트 쪽에서 예외처리를 다 했는데
     // 생각을 해보니 포스트맨 공격을 받을수 있어서 백 쪽에서도 예외처리를 해야겠더라고여

}
