package org.sherlock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/first_http_servlet_new/*")
public class FirstHttpServletNew extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * http://localhost:8080/servlet-app/first_http_servlet_new
         * http://localhost:8080/servlet-app/first_http_servlet_new/aaafgh/lll
         * http://localhost:8080/servlet-app/first_http_servlet_new/aaafgh/lll?var1=aaa&var2=bbb
         * http://localhost:8080/servlet-app/first_http_servlet_new/aaafgh/lll?param1=aaa&param2=bbb
         */
        resp.getWriter().println("<p>contextPath: " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>servletPath: " + req.getServletPath() + "</p>");
        resp.getWriter().println("<p>pathInfo: " + req.getPathInfo() + "</p>");
        resp.getWriter().println("<p>queryString: " + req.getQueryString() + "</p>");
        resp.getWriter().println("<p>param1: " + req.getParameter("param1") + "</p>");
        resp.getWriter().println("<p>param2: " + req.getParameter("param2") + "</p>");


    }
}
