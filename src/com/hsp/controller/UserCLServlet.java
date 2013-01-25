package com.hsp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;
import com.hsp.service.UsersService;


public class UserCLServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		UsersService usersService=new UsersService();
		
		String type=request.getParameter("type");
		if("del".equals(type)){
		String id=request.getParameter("id");
		
		if(usersService.delUser(id)){
			request.setAttribute("info", "É¾³ý³É¹¦£¡");
			request.getRequestDispatcher("/Ok").forward(request, response);
		}else{
			request.setAttribute("info", "É¾³ýÊ§°Ü£¡");
			request.getRequestDispatcher("/Err").forward(request, response);
		}
	  }
		
		
		else if("gotoUpdateView".equals(type)){
			String id=request.getParameter("id");
			User user=usersService.getUserById(id);
		
		    request.setAttribute("userinfo", user);
		
		    request.getRequestDispatcher("/UpdateUserView")
		    .forward(request, response);
		}
		
		else if("update".equals(type)){
			String id=request.getParameter("id");
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			String grade=request.getParameter("grade");
			String password=request.getParameter("passwd");
			
			User user=new User(Integer.parseInt(id),username,email,Integer.parseInt(grade),password);
			
			if(usersService.updateUser(user)){
				request.setAttribute("info", "ÐÞ¸Ä³É¹¦£¡"); 
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				request.setAttribute("info", "ÐÞ¸ÄÊ§°Ü£¡");
				request.getRequestDispatcher("/Err").forward(request, response);
			}
			
		}
		
		else if("gotoadd".equals(type)){
			
			request.getRequestDispatcher("/AddUserView").forward(request, response);
		}
		
		else if("add".equals(type)){
					
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			String grade=request.getParameter("grade");
			String password=request.getParameter("passwd");
			
			User user=new User();
			user.setName(username);
			user.setEmail(email);
			user.setGrade(Integer.parseInt(grade));
			user.setPwd(password);
			
			if(usersService.addUser(user)){
				request.setAttribute("info", "ÐÞ¸Ä³É¹¦£¡");
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				request.setAttribute("info", "ÐÞ¸ÄÊ§°Ü£¡");
				request.getRequestDispatcher("/Err").forward(request, response);
			}
			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
