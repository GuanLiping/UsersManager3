package com.hsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsp.domain.User;
import com.hsp.service.UsersService;


public class LoginCLServlet extends HttpServlet {
	
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
				
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		UsersService usersservice=new UsersService();
		User user=new User();
		
		user.setId(Integer.parseInt(id));
		user.setPwd(password);
		
			//根据结果处理
			if(usersservice.checkUser(user)){
				//save user object to session
				HttpSession session=request.getSession();
				session.setAttribute("loginuser", user);
				
				request.getRequestDispatcher("/MainFrame").forward(request, response);
				
			}else{
				request.setAttribute("err", "用户名或密码有误");
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
			
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
