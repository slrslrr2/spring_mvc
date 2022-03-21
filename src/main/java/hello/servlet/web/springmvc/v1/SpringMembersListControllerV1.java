package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class SpringMembersListControllerV1{
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/members")
    public ModelAndView process() throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("members", members);
        mv.setViewName("members");

        return mv;
    }
}