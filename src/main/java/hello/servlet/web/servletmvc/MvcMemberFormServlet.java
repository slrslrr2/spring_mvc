package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // /WEB-INF여기 아래 있는 자원들은 url에 입력하는게 불가능하고
        // 무조건 controller에서 거쳐서 이동할 수 있다
        String viewPath = "/WEB-INF/views/new-form.jsp";

        // Controller 에서 View로 이동될 때 사용됨
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);

        // 다른 서블릿이나 jsp로 이동할 수 있는 기능이다.
        // 서버 내부에서 다시 호출이 발생한다.
        dispatcher.forward(request, response);

    }
}
