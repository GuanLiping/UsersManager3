package com.hsp.service;

import java.sql.*;
import java.util.ArrayList;

import com.hsp.domain.User;
import com.hsp.util.SqlHelper;

public class UsersService {
	
	public int getPageCount(int pageSize){
		
		
		String sql="select count(*) from users";
		int rowCount=0;
		
		ResultSet rs=SqlHelper.executeQuery(sql, null) ;
		try {
			rs.next();
			rowCount=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		
		return (rowCount-1)/pageSize+1;
		
		
	}
	
	// get the ResultSet ->user object->Arraylist
	public ArrayList getUserByPage(int pageNow,int pageSize){
		
		ArrayList<User> al=new ArrayList<User>();
		
		String sql="select * from (select t.*,rownum rn from(select * from users order by id) t where rownum<="+pageSize*pageNow+") where rn>="+(pageSize*(pageNow-1)+1);
		
		//ResultSet rs=SqlHelper.executeQuery(sql, null) ;
		//二次封装  arraylist对象数组 --user对象 --arraylist 集合
		ArrayList al2=SqlHelper.executeQuery3(sql, null);
		
		
		try {
			for(int i=0;i<al2.size();i++){
				Object[] objs=(Object[]) al2.get(i);
				User user=new User();
				
					user.setId(Integer.parseInt(objs[0].toString()));
				    user.setName( objs[1].toString());
				    user.setEmail( objs[2].toString());
				    user.setGrade(Integer.parseInt(objs[3].toString()));
				    user.setPwd( objs[4].toString());
				al.add(user);
			}
			
			/*while(rs.next()){
				User u=new User();
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setGrade(rs.getInt(4));
				
				a1.add(u);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	public boolean delUser(String id){
		boolean b=true;
		String sql="delete from users where id=?";
		String parameters[]={id};
		try{
	    SqlHelper.executeUpdate(sql, parameters);
		}catch(Exception e){
			b=false;
		}
		return b;
	}
	
	public User getUserById(String id){
		
		User user=new User();
		String sql="select * from users where id=?";
		String parameters[]={id};
		ResultSet rs=SqlHelper.executeQuery(sql, parameters);
		
		try {
			if(rs.next()){
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				user.setPwd(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return user;
	} 
	
	public boolean updateUser(User user){
		boolean b=true;
		String sql="update users set username=?, email=? ,grade=? ,passwd=? where id=?";
		
		String parameters[]={user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd(),user.getId()+""};
	
	    try{
	    	SqlHelper.executeUpdate(sql, parameters);
	    }catch(Exception e){
	    	b=false;
	    }
	    return b;
	}
	
	
	
	public boolean addUser(User user){
		boolean b=true;
		String sql="insert into users values(users_seq.nextval,?, ? ,? ,?)";
		
		String parameters[]={user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd()};
	
	    try{
	    	SqlHelper.executeUpdate(sql, parameters);
	    }catch(Exception e){
	    	b=false;
	    }
	    return b;
	}
	
       //验证用户是否合法的函数
	public boolean checkUser(User user){
		
		boolean b=false;
		String sql="select * from users where id=? and passwd=?";
		String parameters[]={user.getId()+" ",user.getPwd()};
		//ResultSet rs=SqlHelper.executeQuery(sql, parameters);
		ArrayList al=SqlHelper.executeQuery3(sql, parameters);
		try {
			if(al.size()==1){
				b=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
