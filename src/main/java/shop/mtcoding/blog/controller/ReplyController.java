package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class ReplyController {
    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyRepository replyRepository;

    // private static String TAG = "ReplyController : ";

    // 댓글 삭제
    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId) {
        // 바디데이터에 보드아이디 받아옴
        // 인증체크
        if (boardId == null) {
            return "redirect:/40x";

        }
        // System.out.println(TAG + "1");

        // 포스트맨이 공격할수도 있으니까
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 주소로만 데이터 보내는건 유효성 체크 필요없다

        // 권한체크
        Reply reply = replyRepository.findById(id);
        if (reply.getUser().getId() != sessionUser.getId()) {
            return "redirect:/40x"; // 403
        }
        // 핵심로직
        replyRepository.deleteById(id);

        return "redirect:/board/" + boardId;

    }

    @PostMapping("/reply/save")
    public String save(ReplyWriteDTO replyWriteDTO) {// x-www폼으로 들어옴
        // comment 유효성 검사: 값이 있는지 없는지
        if (replyWriteDTO.getBoardId() == null) {
            return "redirect:/40x";
        }
        if (replyWriteDTO.getComment() == null || replyWriteDTO.getComment().isEmpty()) {
            return "redirect:/40x";
        }
        // 인증 검사

        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 댓글 쓰기
        replyRepository.save(replyWriteDTO, sessionUser.getId());

        return "redirect:/board/" + replyWriteDTO.getBoardId();
        // board_id를 받아야 insert 할수있다 -> 화면에 다시 돌아감
    }
}
// 보드 아이디가 null인지 공백이 있는지
//
