#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ContextLoaderListener: 
* ConnectionManager: DB의 host, user, pw 등 연결 정보 및 DataSource 관리
* ResourceDatabasePopulator: 외부 리소스에 정의된 SQL 문을 사용하는 DB를 populate, initialize, clean up
* DatabasePopulatorUtils: DatabasePopulator 를 실행시키는데 도움되는 유틸리티 메서드
* 
* ContextLoaderListener
* ResourceDatabasePopulator 객체를 생성하고 초기 SQL 문(create, insert)을 설정한다.
* DatabasePopulatorUtils.execute(populator, datasource): 설정해둔 DataSource(h2) 에 맞춰 DB를 초기화한다.
* 
* ResourceFilter
* css, js, fonts, ico 등 리소스는 DefaultDispatchServlet 이 바로 forward 할 수 있게 필터링한다.
* 
* DispatcherServlet
* @WebServlet 어노테이션으로 일단 모든 URL 에 해당 클래스가 service 할 수 있게 설정한다.
* RequestMapping 객체를 생성하고 Client Request 에 대응할 수 있도록 URI 와 Controller 를 매핑시켜 놓는다.
* 
* 

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 소스코드의 호출 순서 및 흐름을 설명하라.
* ResourceFilter.doFilter() 
* DispatcherServlet.service()
* RequestMapping.findController()
* HomeController.execute()
* ModelAndView.getView().render()
* 추가적인 css, js 등 요청에 따라 defaultDispatchServlet.forward()

#### 7. next.web.qna package 의 ShowController 는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 모르겠다
