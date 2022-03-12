package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    /**
         1. http요청이 오면
            WAS(Servlet Container)가
            HttpServletRequest, HttpServletResponse를 만들어서 내려준다.

         2. Servlet에서
            - HttpServletRequest, HttpServletResponse를 받은 후
            - service() 메소드에 로직을 구현한다.
                - HttpServletRequest
                     - HttpServletRequest객체는 HTTP 요청 메시지를 파싱한 결과이다.

         3. WAS가 Response에 Header정보와 Body정보를 담아 웹 브라우저에게 내려준다.


     - Tip, Ctrl + O : 상속받은 객체의 메소드를 오버라이드 할 경우 사용하함.

     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // http://localhost:8080/hello?username=kim
        // 위 url로 할 경우 간편하게 파라미터를 받아준다
        String name = request.getParameter("username");
        System.out.println("name = " + name);

        // Content-Type : Header에 정보가 들어간다.
        response.setContentType("text/plain"); // TEXT형태로 화면에 표시된다.
        response.setCharacterEncoding("utf-8");

        // Body에 해당 문자가 들어간다
        response.getWriter().write("hello " + name);
    }
}
