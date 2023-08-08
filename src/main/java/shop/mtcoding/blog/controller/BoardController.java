package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // 주소 두개 쓰려면 중괄호로 맵핑
    // 리퀘스트 파람 디폴트 값을 0으로 설정하려고
    // 최초에 무조건 0이 들어간다

    // 1.PathVariable 값 받기
    // 2.인증검사(로그인페이지 보내기)
    // ->session에 접근해서 sessionUser 키값을 가져오세요
    // null 이면 로그인페이지로 보내고
    // null 아니면 3번 실행하세요
    // 3.모델에 접근해서 삭제 delete from board_tb where id = :id
    // boardRepository.deleteById(id);호출

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, UpdateDTO updateDTO) {
        // 1. 인증검사
        // 2. 권한체크
        // 3. 핵심로직
        // update board_tb set title = :title, content = :content where id = :id
        boardRepository.update(updateDTO, id);

        return "redirect:/board/" + id;
        // 응답해줄 뷰

    }

    @GetMapping("board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {

        // 1. 인증 검사

        // 2. 권한 체크

        // 3.핵심 로직
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);

        return "board/updateForm";

    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        boardRepository.deleteById(id);

        return "redirect:/";
    }

    // 페이징 하는 법
    @GetMapping({ "/", "/board" })
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        // 1. 유효성 검사 x - 받을 데이터가 없으니까
        // 2. 인증검사 x
        List<Board> boardList = boardRepository.findAll(page); // page=1

        int totalCount = boardRepository.count();// totalCount=5
        int totalPage = totalCount / 3;// totalPage=1
        if (totalCount % 3 > 0) {
            totalPage = totalPage + 1;// totalPage= 2
        }
        boolean last = totalPage - 1 == page;

        // System.out.println("테스트 :" + boardList.size());
        // System.out.println("테스트 :" + boardList.get(0).getTitle());

        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalCount", totalCount);

        return "index";
    }

    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // validation check (유효성 검사)
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirect:/40x";
        }
        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }

        // 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        boardRepository.save(writeDTO, sessionUser.getId());
        return "redirect:/";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) { // C
        User sessionUser = (User) session.getAttribute("sessionUser");// 세션 접근
        List<BoardDetailDTO> dtos = boardRepository.findByIdJoinReply(id); // M

        boolean pageOwner = false;
        if (sessionUser != null) {
            pageOwner = sessionUser.getId() == dtos.get(0).getBoardUserId();
        }

        request.setAttribute("dtos", dtos);

        request.setAttribute("pageOwner", pageOwner);

        return "board/detail";// V
    }

    @ResponseBody
    @GetMapping("/test/reply")
    public List<Reply> test2() {
        List<Reply> replys = replyRepository.findByBoardId(1);
        return replys;
    }

}
