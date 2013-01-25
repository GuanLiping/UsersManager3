package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsp.domain.User;
import com.hsp.service.UsersService;


public class ManagerUsers extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPageNow(){var pageNow=document.getElementById('pageNow');window.open('/UsersManager2/ManagerUsers?pageNow='+pageNow.value,'_self')}" +
				"function confirmOper(){return window.confirm('真的要删除吗？')} ");
		out.println("</script>");
		out.println("<img src='imgs/1.jpg' />欢迎登陆 <a href ='/UsersManager3/MainFrame'>返回主界面</a> <a href ='/UsersManager3/LoginServlet'>安全退出</a><hr/>");
		out.println("<h1>管理用户</h1>");
		
		
		
		int pageNow=1;   //当前页
		int pageCount=0;
		
		String sPageNow=request.getParameter("pageNow");
		if(sPageNow!=null){
		pageNow=Integer.parseInt(sPageNow);
		}
		int pageSize=3;  //每页显示几个
	
								
		try{
			
			 UsersService usersService=new UsersService();
			 
			 pageCount=usersService.getPageCount(pageSize);
					
			//创建prepared statement
			
			
			 ArrayList<User> a1=usersService.getUserByPage(pageNow, pageSize);
			 
			out.println("<table border=1px bordercolor=green cellspacing=0 width=500px>");
			out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>删除用户</th><th>修改用户</th></tr>");
			
			for(User u:a1){
			out.println("<tr><td>"+u.getId()+
					"</td><td>"+u.getName()+
					"</td><td>"+u.getEmail()+
					"</td><td>"+u.getGrade()+
					"</td><td><a onClick='return confirmOper();' href='/UsersManager3/UserCLServlet?type=del&id="+u.getId()+"'>删除用户</a>"+
					"</td><td><a href='/UsersManager3/UserCLServlet?type=gotoUpdateView&id="+u.getId()+"'>修改用户</a>"+"</td></tr>");
			}
			out.println("</table></br>");
			
			if(pageNow!=1){
			out.println("<a href='/UsersManager3/ManagerUsers?pageNow="+(pageNow-1)+"'>上一页</a>");
			}
			for(int i=1;i<=pageCount;i++){
				out.println("<a href='/UsersManager3/ManagerUsers?pageNow="+i+"'><"+i+"></a>");
			}
			
			if(pageNow!=pageCount){
			out.println("<a href='/UsersManager3/ManagerUsers?pageNow="+(pageNow+1)+"'>下一页</a>");
			}
			
			out.println("&nbsp;&nbsp;&nbsp;当前页"+pageNow+"/总页数"+pageCount+"</br>");
			
			out.println("跳转到<input type='text' id='pageNow' name='pageNow'/><input type='button' onClick='gotoPageNow()' value='跳'/>");
			
			out.println("<hr/><img src='imgs/2.jpg' />");
		}catch(Exception e){
			e.printStackTrace();
		}
					
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
