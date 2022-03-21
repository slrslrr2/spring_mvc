package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
@Component("/springmvc/old-contrller")
위 방식은 예전 방식이니 참고 바람!

스프링 빈의 이름으로 Url을 매핑할 수 있다.
 url을 매핑하기 위해선,
 즉, DispatcherServlet이 해당 Url에 지정된 Controller를 찾기 위해선 2가지가 필요하다
 1. HandlerMapping
    @RequstMapping으로 등록 한 경우
        RequestMappingHandlerMapping
    @Component로 빈이름을 통해 경로를 등록한 경우  O
        BeanNameUrlHandlerMapping
 2. HandlerAdapter
    @RequestMapping으로 등록한 경우
        RequestMappingHandlerAdapter
    HttpRequestHandler
        HttpRequestHandlerAdapter
    SimpleControllerHandlerAdapter O
        위 소스 일부에서는 아래와 같이 ModelAndView를 반환하는 함수인 handle에
        아래 상속받은 implements Controller의 handleRequest를 실행 해 주는 부분이 존재한다.
            public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return ((Controller) handler).handleRequest(request, response);
            }
*/

/**
 * 정리
 * org.springframework.web.servlet.mvc.Controller를 상속받은 경우
 * HandlerMapping : BeanNameUrlHandlerMapping
 * HandlerAdapter : SimpleControllerHandlerAdapter
 * InternalResourceViewResolver -> InternalResourceView 안에 forward있다.
 *   이는 ViewName을 넣어준다.
 * 		RequestDispatcher rd = getRequestDispatcher(request, dispatcherPath);
 * 				rd.forward(request, response);
 *
 */

@Component("/sprimgmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}
