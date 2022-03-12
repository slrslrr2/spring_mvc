package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.Hello;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**

 */
@WebServlet(name="requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // jackson객체
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Request MessageBody에 있는 데이터를 읽어온다
        // request.getInputStream(); 는 값이 바이트 코드이다.
        ServletInputStream inputStream = request.getInputStream();

        // 바이트코드를 Sting으로 변환한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("messageBody = " + messageBody); // {'username': "gbitkim", 'age': 22}

        // 객체로 변환하여 가져올 수 있다.
        Hello hello = objectMapper.readValue(messageBody, Hello.class);
        System.out.println("hello.getUsername() = " + hello.getUsername());
    }
}
