package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;


public class MainFrame extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u=(User) request.getSession().getAttribute("loginuser");
		
		if(u==null){
			request.setAttribute("err", "请输入用户名和密码输入");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		    return;  //important
		
		}
		
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		
		Cookie [] cookies=request.getCookies();
		boolean b=false;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				String name=cookie.getName();
				if("lastname".equals(name)){
		out.println("<img src='imgs/1.jpg' />欢迎登陆  上次登录时间是" +cookie.getValue()+
				" <a href ='/UsersManager3/LoginServlet'>返回重新登陆</a><hr/>");
		    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		    String nowTime=simpleDateFormat.format(new java.util.Date());
		    cookie.setValue(nowTime);
		    cookie.setMaxAge(7*3600*24);
		    response.addCookie(cookie);
		    b=true;
		    break;
		                    }
		          }
		}
			
		if(!b){
			out.println("<img src='imgs/1.jpg' />欢迎登陆  第一次登陆" +
					" <a href ='/UsersManager3/LoginServlet'>返回重新登陆</a><hr/>");
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String nowTime=simpleDateFormat.format(new java.util.Date());
			Cookie cookie=new Cookie("lasttime",nowTime);
			cookie.setMaxAge(7*3600*24);
			response.addCookie(cookie);
		
		}
	
	    out.println("<h3>请选择操作</h3>");
	    out.println("<a href='/UsersManager3/ManagerUsers'>管理用户</a><br/>");
	    out.println("<a href='/UsersManager3/UserCLServlet?type=gotoadd'>添加用户</a><br/>");
	    out.println("<a href=''>查找用户</a><br/>");
	    out.println("<a href=''>退出系统</a><br/>");
	    
	    out.println("<hr/><img src='imgs/2.jpg' />");
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
