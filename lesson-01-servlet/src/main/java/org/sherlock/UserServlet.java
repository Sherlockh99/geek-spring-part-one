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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {


    /**
     * URL парсят с помощью регулярных выражений
     *  compile - позволяет получить предварительно скомпилированную версипаттерна,
     *  которую в дальнейшем можно использовать
     *  URL начинается с "/", т.к. "/" зарезтрвтрованный символ,
     *  то его необходимо заескейпить в "\"
     *  далее должна идти последовательность из одной или более цифр
     *  "\\d+" - означает, что после "\" должна идти какая-то последовательность цифр
     */
    private static final Pattern PARAM_PATERN = Pattern.compile("\\/(\\d+)");

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
        if(req.getPathInfo()==null || req.getPathInfo().equals("/")) {
            PrintWriter wr = resp.getWriter();
            wr.println("<table>");
            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Username</th>");
            wr.println("</tr>");

            for (User user : userRepository.findAll()) {

                wr.println("<tr>");
                wr.println("<th>" + user.getId() + "</th>");
                wr.println("<th>" + user.getUsername() + "</th>");
                wr.println("</tr>");

            }
            wr.println("</table>");
        } else {
            Matcher matcher = PARAM_PATERN.matcher(req.getPathInfo());

            /**
             * проверяем, если строка соответствует регулярному выражению
              */
            if(matcher.matches()){
                /**
                 * распарсим строку
                 * и получим первое значение,
                 * которое указано в первых скобках в переменной PARAM_PATERN
                 * group(0) - Это вся строка целиком
                 * если больше скобок, то указывая номер скобки, получим это выражение
                 */
                long id = Long.parseLong(matcher.group(1));
                User user = userRepository.findById(id);
                if(user == null){
                    resp.getWriter().println("User not found");
                    resp.setStatus(404);
                    return;
                }
                resp.getWriter().println("<p>Id: " + user.getId() + "</p>");
                resp.getWriter().println("<p>Username: " + user.getUsername() + "</p>");
            }else{
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }
    }
}
