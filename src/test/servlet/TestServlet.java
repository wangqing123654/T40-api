package test.servlet;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import com.dongyang.util.FileTool;

public class TestServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String name = request.getParameter("name");
        System.out.println("name=" + name);
        byte data[] = FileTool.getByte("D:\\topteam\\BBS\\webserver\\images\\146.gif");
        System.out.println(data.length);
        FileTool.setByte(response.getOutputStream(),data);
    }

}
