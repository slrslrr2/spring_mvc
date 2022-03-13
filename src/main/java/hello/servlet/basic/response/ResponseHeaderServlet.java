package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 HttpServletResponse - 사용법
 Header에 다음을 정의할 수 있다.
 - Content-Type, 쿠키, Redirect(Location)
 */


@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK);

        // [response-header]
        content(response);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        cookie(response);

        // [message-body]
        PrintWriter witer = response.getWriter();
        witer.println("ok");
    }

    // Header - ContentType 설정
    // content-type, encding, content-length(자동생성)
    private void content(HttpServletResponse response) {
        /**
             Content-Type: text/plain;charset=utf-8
             Content-Length: 2
         */

        // response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        response.setContentLength(2); //(생략시 자동 생성)
    }

    /**
    해당 페이지에 쿠키를 등록하면
        Response-headers에 아래 내용이 등록된다
        Set-Cookie: myCookie=good; Max-Age=600; Expires=Sun, 13-Mar-2022 05:41:27 GMT

     또한 해당 페이지를 새로고침 하면 유효한 시간설정 내에 해당 쿠키는
        웹브라우저에서 서버로 쿠키를 보내면서
        Request Header에 들어가있다.
        Cookie: myCookie=good
   **/
    private void cookie(HttpServletResponse response) {
        /** Set-Cookie: myCookie=good; Max-Age=600; **/
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");

        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        /**
            Status Code 302
            Location: /basic/hello-form.html
        */

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }

}
