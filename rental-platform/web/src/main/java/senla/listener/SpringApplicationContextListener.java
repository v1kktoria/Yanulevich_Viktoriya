package senla.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebListener
public class SpringApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        sce.getServletContext().setAttribute("applicationContext", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ApplicationContext context = (ApplicationContext) sce.getServletContext().getAttribute("applicationContext");

        if (context instanceof ClassPathXmlApplicationContext) {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }
}
