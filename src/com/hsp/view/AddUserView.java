package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;


public class AddUserView extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8"); 
		
		PrintWriter out=response.getWriter();
	    
		User user=(User) request.getAttribute("userinfo");
		out.println("<img src='imgs/1.jpg' /><hr/>");
		out.println("<h1>修改用户</h1>");
		out.println("<form action='/UsersManager3/UserCLServlet?type=add' method='post'>");
		out.println("<table border=1px bordercolor=green cellspacing=0 width=250px>");
	    
	    out.println("<tr><td>用户名</td><td><input type='text' name='username'/></td></tr>");
	    out.println("<tr><td>email</td><td><input type='text' name='email'/></td></tr>"); 
	    out.println("<tr><td>级别</td><td><input type='text' name='grade'/></td></tr>");
	    out.println("<tr><td>密码</td><td><input type='text' name='passwd'/></td></tr>");
		out.println("<tr><td><input type='submit' value='添加用户'</td><td><input type='reset' value='重新填写'</td></tr>");
	    out.println("</table>");
	    out.println("</form>");
		out.println("<hr/><img src='imgs/2.jpg' />");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
