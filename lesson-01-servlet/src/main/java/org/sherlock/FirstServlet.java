package org.sherlock;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(urlPatterns = "/first_servlet")
public class FirstServlet implements Servlet {

    private ServletConfig config;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        /*

        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("utf-8");

         */
        servletResponse.getWriter().println("<h1>Привет от сервлета</h1>");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
