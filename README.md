클라이언트가 **https://www.google.com:443/search?q=hello&hl=ko** 라는 요청을 보내면<br>클라이언트의 **웹브라우저**에서는 **DNS서버**를 통해 google.com의 **IP**를 확인한다.<br>  - 참고로 https로 요청하였으니 port는443이다. <br>  - www.google.com의 IP를 찾기 위해 DNS를 관리해주는 ISP(인터넷 서비스 제공업체)가 바로 KT, SK브로드밴드, LG U+이다.

<img width="1488" alt="image-20220320231836131" src="https://user-images.githubusercontent.com/58017318/174438731-8edeb02d-6c80-4972-a5e7-d44ded0216a2.png">



클라이언트의 웹브라우저는 도착지의 정보(IP와 PORT)를 갖고 아래와 같은 **HTTP메시지**를 만든다.<br>
<img width="764" alt="image-20220320232059448" src="https://user-images.githubusercontent.com/58017318/174438738-518577b1-a211-473e-8b98-a2757f1fa747.png">



웹브라우저 내에 존재하는 **웹소켓** 라이브러리를 통해 **TCP/IP** 계층에 **HTTP메시지**를 전달한다.<br>  - TCP계층 : 출발지/목적지 PORT, 데이터 패킷의 순서보장, **TCP 3 way handshake(가상연결)**<br>                     https://www.youtube.com/watch?v=ikDVGYp5dhg&t=809s<br>  - IP계층     : 출발지와 목적지 IP정보

```
7계층 : 응용 			[HTTP]
6계층 : 표현 
5계층 : 세션 
4계층 : 전송 			[TCP(우리브라우저와 서버 연결), UDP]
3계층 : 네트워크 	 [ARP, IP(멀리있을 떄 사용), ICMP]
2계층 : 데이터링크	 [Ethernet(가까운곳 통신)]
1계층 : 물리 계층
```



<img width="1160" alt="image-20220321000135414" src="https://user-images.githubusercontent.com/58017318/174438743-3a2e0ace-3af4-40ae-bbfd-37e2c4d8308d.png">

<img width="775" alt="image-20220321000432262" src="https://user-images.githubusercontent.com/58017318/174438745-d79b4dbe-a28d-49cc-96f2-00c9bd677b40.png">

<img width="730" alt="image-20220613232950963" src="https://user-images.githubusercontent.com/58017318/174438834-9f486d22-fd2e-43f2-88ba-f0242b10976c.png">패킷에 관련된 내용은 아래 참조<br>[따라學IT] 02. 네트워크의 기준! 네트워크 모델 유튜브에 검색!

**OSI 7 계층 단계**<br><img width="699" alt="image-20220320235139167" src="https://user-images.githubusercontent.com/58017318/174438740-678fab19-ad0c-4a45-84ba-fd8a2912c163.png"> 



<img width="737" alt="image-20220613232329120" src="https://user-images.githubusercontent.com/58017318/174438854-62cab91f-971a-4b3a-af85-d3fd9f6a0798.png">



위와 같은 과정을 지나 노드를 지나 해당 패킷이 전달이 되었다면,<img width="1242" alt="image-20220321001735069" src="https://user-images.githubusercontent.com/58017318/174438747-279ed99b-5a19-48a5-8f01-f1d9d5b10736.png">

> Tracers 구글아이피!
>
> 여러 네트워크(LAN)를 지나 원하는 네트워크에 도착함<br>방화벽이 있는 경우 *로 표시되어 IP주소 표시는 안하되 네트워크 연결은 OK<br>[따라學IT] 01. 네트워크란 무엇인가? - 실습1(tracert) 유튜브 참조



패킷이 드디어 목적지 서버에 도착하게 되는데 이 그림은 저번주에 배웠던 **서블릿컨테이너**입니다.<br>Http요청이 해당 서버로 들어오면 **쓰레드가 Servlet을 호출**시킵니다.

> 참고로 쓰레드는 Tomcat에서 멀티쓰레드와, maxThreads로 max thread pool size를 설정할 수 있다.

<img width="544" alt="image-20220320225529246" src="https://user-images.githubusercontent.com/58017318/174438966-55212f43-fc9b-4d21-9c69-9cb7bc0e3afa.png">

> Tomcat 역시 Java언어로 이루어져있고 Tomcat을 직접 까보면서 어떻게 SpringFramework랑 연결되어있는지 확인해보면 좋을듯,, (start.sh 인가~~)

web.xml SpringFramework<br> https://midas123.tistory.com/222



- JVM

https://thinkground.studio/%EC%9E%90%EB%B0%94-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EA%B5%AC%EC%A1%B0-java-memory-structure/



만약 **Servlet이 생성이 안되어있는 경우 init**함수를 통해 아래 메소드를 실행시킵니다.<br>DispatcherServlet은 싱글톤으로 실행되기에 Servlet이 존재하지 않을 경우 딱 한번만 init합니다.<br>이때 실행되는 부분이 다음과 같습니다.

```java
// DispatcherServlet.class
protected void initStrategies(ApplicationContext context) {
    this.initMultipartResolver(context);
    this.initLocaleResolver(context);
    this.initThemeResolver(context);
    this.initHandlerMappings(context); // HandlerMapping 모두 담는다
    this.initHandlerAdapters(context); // HandlerAdapter 모두 담는다
    this.initHandlerExceptionResolvers(context);
    this.initRequestToViewNameTranslator(context);
    this.initViewResolvers(context);
    this.initFlashMapManager(context);
}
```

이렇게 HadlerMapping과 HandlerAdapter등을 만들어 놓은 후

서블릿이 실행될 경우 FrameworkServlet Class의 **service**를 실행시키는데 이는 **HttpServlet에서 상속받아 Override**한 것입니다.

> DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로( urlPatterns="/" )에 대해서 매핑한다.

<img width="924" alt="image-20220322201423131" src="https://user-images.githubusercontent.com/58017318/174439031-acce0877-7d5c-4a3f-824e-23d22f9bbc6e.png">

이 후 SpringMVC 흐름이 시작되는데 이는**섹션 5. 스프링 MVC -구조이해**에서 배웠던 내용입니다.

------------------

섹션5이후 설명!!!!!!!!!!!!

-------------





<img width="809" alt="image-20220320225219527" src="https://user-images.githubusercontent.com/58017318/174439083-4c12be78-26ee-4509-b833-392b5e08fb69.png">

DispatcherServlet을 통해 URL을 매핑하면

1. 핸들러 조회

   - 핸들러 매핑을 통해 요청된 URL에 매핑된 **handler**를 조회합니다.

     - HandlerMapping 
       0 = RequestMappingHandlerMapping
             : 애노테이션 기반의 컨트롤러인 
               @RequestMapping에서 사용
       1 = BeanNameUrlHandlerMapping
             : 스프링 빈의 이름으로 핸들러를 찾는다.

     - ```java
       // DispatcherServlet
       // Determine handler for the current request.
       mappedHandler = getHandler(processedRequest);
       if (mappedHandler == null) {
         noHandlerFound(processedRequest, response);
         return;
       }
       ```

       

2. 핸들러 어댑터 조회

   - 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.

     - HandlerAdapter
       0 = RequestMappingHandlerAdapter
             : 애노테이션 기반의 컨트롤러인
               @RequestMapping에서 사용

       1 = HttpRequestHandlerAdapter
            : HttpRequestHandler 처리

       2 = SimpleControllerHandlerAdapter
             : 과거에 사용하던 org.springframework.web.servlet.mvc.Controller를 상속받은 경우

       ```java
       // DispatcherServlet
       // Determine handler adapter for the current request.
       HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
       ```

       

3. 핸들러 어댑터를 실행한다.<br><br><br><br><br><img width="1702" alt="image-20220327122652045" src="https://user-images.githubusercontent.com/58017318/174439040-f9ce6e6e-d8e1-4432-8242-a69b4b9b5591.png">

   요청(ArgumentResolver)온거 응답(ValueHandler)온거 한번에 다 처리할래 ! <br><br><br><br><br>

   ```java
   // DispatcherServlet
   // Actually invoke the handler.
   mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
   ```

   

4. 핸들러가 실제 핸들러(Controller)를 실행한다.

   ```java
   // public class HttpRequestHandlerAdapter implements HandlerAdapter 
   
   @Override
   @Nullable
   public ModelAndView handle(
     HttpServletRequest request, HttpServletResponse response, Object handler)
     throws Exception {
   
     ((HttpRequestHandler) handler).handleRequest(request, response);
     return null;
   }
   ```

   - ```java 
     // DispatcherServlet
     processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
     ```

   - Controller -> Service -> DB

   

5. ModelAndView 반환

   - 핸들러어댑터는 핸들러가 반환하는 정보를 ModelAndView로 반환한다.
     - model객체 데이터에 담기

   

6. viewResolver 호출

   - 뷰 리졸버를 찾고 실행한다.

   - ```java
     // Did the handler return a view to render?
     if (mv != null && !mv.wasCleared()) {
       render(mv, request, response);
       if (errorView) {
         WebUtils.clearErrorRequestAttributes(request);
       }
     }
     ```

   - ~~~java
     protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
      ///// 생략 /////
     
       View view;
       String viewName = mv.getViewName();
       if (viewName != null) {
         // We need to resolve the view name.
         view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
       }
     ~~~

   - 

7. View를 반환

   - 뷰 리졸버는 뷰의 논리 이름을 물리이름으로 바꾸고, 렌더링하는 역할을 담담하는 뷰 객체를 반환한다.
   - JSP의 경우: InternalResourceView(JstlView)를 반환하는데, 내부에 forward()로직이 있다.

8. 뷰 렌더링

   - 뷰를 통해 뷰를 렌더링한다.

   - ```java
     view.render(mv.getModelInternal(), request, response);
     ```

