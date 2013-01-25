package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
	
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		
		//return the view
		out.println("<img src='imgs/1.jpg' /><hr/>");
		out.println("<h1>用户登录</h1>");
		out.println("<form action='/UsersManager3/LoginCLServlet' method='post'>");
		out.println("用户id：<input type='text' name='id'/><br/>");
		out.println("密　码：<input type='password' name='password'/><br/>");
		out.println("<input type='submit' value='登陆'/><br/>");
		out.println("</form>");
		
		String errInof=(String) request.getAttribute("err");
		if(errInof!=null){
		out.println("<font color='red'>"+errInof+"</font>");
		}
		
		out.println("<hr/><img src='imgs/2.jpg' />");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
