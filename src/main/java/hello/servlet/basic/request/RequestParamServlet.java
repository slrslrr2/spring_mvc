package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
    1. [HTTP 요청메시지를 클라이언트 -> 서버로 전송하는 방법]
        가. GET 쿼리 파라미터
        - HTTP 메시지 바디를 사용하지 않기 때문에 Content-Type가 없다.
        - url?username=hello&age=20
        - 메시지에 바디없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
        - 검색, 필터, 페이징 등에서 많이 사용한다.

        나. POST - HTML Form 방식
        - HTTP 메시지 바디를 사용하기 때문에
            Content-Type : application/x-www-form-urlencoded    // 번외 만약 form이 아닌 text만 보낸다면 text/plain
            message body : username=hello&age=20

        디. Http message Body에 데이터를 직접 담아서 요청
        - HTTP API에서 주로 사용, JSON, XML TEXT
            JSON 결과를 파싱해서 자바에서 사용할 수 있는 객체로 변하려면
            Jackson, Gson과 같은 JSON변환 객체를 사용해여한다

                - Jackson 예시
                     // jackson객체
                     private ObjectMapper objectMapper = new ObjectMapper();

                     @Override
                     protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                         ServletInputStream inputStream = request.getInputStream();

                         // {'username': "gbitkim", 'age': 22}
                         // JSON으로 전송하였다고 하더라도 Byte를 String으로 변환해준것이기에 String 한줄로 표시된다.
                         String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                         Hello hello = objectMapper.readValue(messageBody, Hello.class);
                     }

     2. 파라미터 조회 방법
     String username = request.getParameter("username"); //단일 파라미터 조회
     Enumeration<String> parameterNames = request.getParameterNames(); //파라미터 이름들 모두 조회
     Map<String, String[]> parameterMap = request.getParameterMap(); //파라미터를 Map 으로 조회
     String[] usernames = request.getParameterValues("username"); //복수 파라미터 조회
 */

/**
 KeyWord

 @WebServlet
 @ServletComponentScan
 @SpringBootApplication

 HttpServlet, HttpServletRequest, HttpServletResponse

 GET - 메시지바디 X
 POST - 메시지바디에 데이터 담음 Content-Type 있음
 JSON - Jackson or Gson 객체 사용해서 자바에서 사용 가능한 코드로 변환해줘야함

 Content-Type : text/plain, application/x-www-form-urlencoded, application/json
 */
@WebServlet(name="requestParamServlet", urlPatterns = "/RequestParamServlet")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
            1. 파라미터 전송 기능
            http://localhost:8080/request-param?username=hello&age=20

            2. 동일한 파라미터 전송 가능
            http://localhost:8080/request-param?username=hello&username=kim&age=20
         */

        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        System.out.println("request.getParameter(username) = " + username);
        String age = request.getParameter("age");
        System.out.println("request.getParameter(age) = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        System.out.println("request.getParameterValues(username)");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username=" + name);
        }

    }
}
