package senla.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import senla.Main;
import senla.dicontainer.DIContainer;

@WebListener
public class DIContainerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DIContainer.startApplication(Main.class);
    }
}
