package teamproject.skycode.login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Member;

public class MemberServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws SecurityException, IOException, ServletException {
        MemberEntity member = new MemberEntity(); // 예시로 새로운 Member 객체 생성 (데이터베이스 연동 필요)
        request.setAttribute("member", member);

        RequestDispatcher dispatcher = request.getRequestDispatcher("memberForm.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String phoneNum = request.getParameter("phoneNum");
        String birthday = request.getParameter("birthday");

        response.sendRedirect("successPage.jsp");
    }


}
