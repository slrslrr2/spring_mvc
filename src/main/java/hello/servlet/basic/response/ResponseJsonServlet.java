package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
HTTP 응답 데이터 보내는 방법
 1. 단순 텍스트 응답
    writer.println("ok");

 2. HTML 보내는 방법
    - content-type : text/html
         response.setContentType("text/html");
         response.setCharacterEncoding("utf-8");

         PrintWriter writer = response.getWriter();
         writer.println("<html>");
         writer.println(" <div>안녕?</div>");
         writer.println("</html>");

 3. JSON으로 보내기
    - content-type : application/json
     {"username":"kim","age":20}

     String result = objectMapper.writeValueAsString(new HelloDate("gbitkim", 20));
     response.getWriter().write(result);
 */
@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type: application/json
        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("utf-8");

        HelloData data = new HelloData();
        data.setUsername("kim");
        data.setAge(20);

        //{"username":"kim","age":20}
        String result = objectMapper.writeValueAsString(data);
        response.getWriter().write(result);
    } }
