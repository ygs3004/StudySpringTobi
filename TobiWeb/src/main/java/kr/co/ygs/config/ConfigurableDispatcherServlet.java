package kr.co.ygs.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Test 를 위한 DispatcherServlet
public class ConfigurableDispatcherServlet extends DispatcherServlet {

    // 클래스와 XML 파일 위치 모두를 통하여 DispatcherServlet 정보를 지정할수있도록 한다.
    public Class<?>[] classes;
    public String[] locations;

    private ModelAndView modelAndView;

    public ConfigurableDispatcherServlet(String[] locations){
        this.locations = locations;
    }

    public ConfigurableDispatcherServlet(Class<?> ...classes) {
        this.classes = classes;
    }

    public void setLocations(String ...locations) {
        this.locations = locations;
    }

    // 주어진 클래스로부터 상대전인 위치에 있는 설정파일을 지정할수 있게한다.
    public void setRelativeLocations(Class<?> clazz, String ...relativeLocations){
        String[] locations = new String[relativeLocations.length];
        String currentPath = ClassUtils.classPackageAsResourcePath(clazz) + "/";

        for(int i=0; i<relativeLocations.length; i++){
            locations[i] = currentPath + relativeLocations[i];
        }

        this.setLocations(locations);
    }

    public void setClasses(Class<?> ...classes){
        this.classes = classes;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        modelAndView = null;
        super.service(request, response);
    }

    @Override
    // DispatcherServlet 의 컨텍스트를 생성하는 메서드를 오버라이드, 테스트용 메타정보를 이용하여 서블릿 컨텍스트 생성
    protected WebApplicationContext createWebApplicationContext(ApplicationContext parent){
        AbstractRefreshableWebApplicationContext wac = new AbstractRefreshableWebApplicationContext() {
            @Override
            protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
                if (locations != null) {
                    XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanFactory);
                    xmlReader.loadBeanDefinitions(locations);
                }

                if (classes != null) {
                    AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
                    reader.register(classes);
                }
            }
        };

        wac.setServletContext(getServletContext());
        wac.setServletConfig(getServletConfig());
        wac.refresh();

        return wac;
    }

    @Override
    public void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.modelAndView = mv;
        super.render(mv, request, response);
    }

    public ModelAndView getModelAndView(){
        return this.modelAndView;
    }

}
