// Author: Ming Jin
// Contact: jinming0106g@gmail.com
// Created Date: 22th Aug, 2021

package ming.task;
 
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String uri = request.getRequestURI();
        if (uri.equals("/data")) {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            RequestDispatcher view = request.getRequestDispatcher("data-list.html");
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }
}
