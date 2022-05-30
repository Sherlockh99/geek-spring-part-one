package org.sherlock;

import org.sherlock.persists.User;
import org.sherlock.persists.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/userNew/*")
public class UserServletNew extends HttpServlet {


    /**
     * URL парсят с помощью регулярных выражений
     *  compile - позволяет получить предварительно скомпилированную версипаттерна,
     *  которую в дальнейшем можно использовать
     *  URL начинается с "/", т.к. "/" зарезтрвтрованный символ,
     *  то его необходимо заескейпить в "\"
     *  далее должна идти последовательность из одной или более цифр
     *  "\\d+" - означает, что после "\" должна идти какая-то последовательность цифр
     */
    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");

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
            req.setAttribute("users",userRepository.findAll());
            getServletContext().getRequestDispatcher("/user.jsp").forward(req,resp); //для дальнейшей обработки перекинь на user.jsp
        }else {
            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
            if(matcher.matches()){
                long id = Long.parseLong(matcher.group(1));
                User user = userRepository.findById(id);
                if(user == null){
                    resp.getWriter().println("User not found");
                    resp.setStatus(404);
                    return;
                }
                req.setAttribute("user",user);
                req.setAttribute("contentPath",req.getContextPath());
                getServletContext().getRequestDispatcher("/user_form.jsp").forward(req,resp); //для дальнейшей обработки перекинь на user_form.jsp
            }else{
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }
    }
}
