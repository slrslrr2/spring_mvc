package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    /**
     * 1. String 반환
     *      Spring은 Return을 String으로 View이름으로 반환해도되고
     * 2. ModelAndView 반환
     *      return new ModelAndView("new-form")으로 ModelAndView를 만들어서 반환해도된다.
     */
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam("username") String username
                       , @RequestParam("age") int age
                       , Model model){
        Member member = new Member(username, age);
        memberRepository.save(member);
        model.addAttribute("member", member);

        return "save";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }
}
