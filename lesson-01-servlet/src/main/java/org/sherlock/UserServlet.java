package org.sherlock;

import org.sherlock.persists.User;
import org.sherlock.persists.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private UserRepository userRepository;
    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
        userRepository.insert(new User("User 1"));
        userRepository.insert(new User("User 2"));
        userRepository.insert(new User("User 3"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        wr.println("<table>");
        wr.println("<tr>");
        wr.println("<th>Id</th>");
        wr.println("<th>Username</th>");
        wr.println("</tr>");

        for(User user: userRepository.findAll()){

            wr.println("<tr>");
            wr.println("<th>" + user.getId() + "</th>");
            wr.println("<th>" + user.getUsername() + "</th>");
            wr.println("</tr>");

        }
        wr.println("</table>");
    }
}
