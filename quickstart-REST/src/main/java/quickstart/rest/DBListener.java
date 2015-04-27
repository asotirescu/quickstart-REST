package quickstart.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DBListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TeaDB teaDB = new TeaDB();
		sce.getServletContext().setAttribute("TeaDB", teaDB);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
