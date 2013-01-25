package com.hsp.util;


import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqlHelper {
    
	private static Connection ct=null;
	private static PreparedStatement ps=null;
	private static ResultSet rs=null;
	private static CallableStatement cs=null;

	private static String url="";
	private static String username="";
	private static String driver="";
	private static String password="";

	private static Properties pp=null;
	private static InputStream fis=null;

    static{
    	try{
    		pp=new Properties();
    		//fis=new FileInputStream("dbinfo.properties");   		
    		fis=SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
    		pp.load(fis);
    		url=pp.getProperty("url");
    		username=pp.getProperty("username");
    		driver=pp.getProperty("driver");
    		password=pp.getProperty("password");
    		Class.forName(driver);
    		   		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			fis.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		fis=null;
    	}
    }

    public static Connection getConnection(){
    	try{
    		ct=DriverManager.getConnection(url, username, password);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return ct;
    }

    public static ResultSet executeQuery2(){
    	return null;
    }
    
   public static CallableStatement callPro2(String sql,String [] inparameters,Integer [] outparameters){
    	
	   try{
		   
		   ct=getConnection();
		   cs=ct.prepareCall(sql);
		   if(inparameters!=null){
			   for(int i=0;i<inparameters.length;i++){
				   cs.setObject(i+1, inparameters[i]);
			   }
		   }
		   
		   if(outparameters!=null){
			   for(int i=0;i<outparameters.length;i++){
				cs.registerOutParameter(inparameters.length+1+i,outparameters[i]);
			   }
		   }
		   
		   cs.execute();
	   }catch(Exception e){
   		e.printStackTrace();
   		throw new RuntimeException(e.getMessage());
     	}finally{
     		
     	}
	   return cs;
    }
   
   public static void callPro1(String sql,String [] parameters){
	   try{
		   ct=getConnection();
		   cs=ct.prepareCall(sql);
		   
		   if(parameters!=null){
			   for(int i=0;i<parameters.length;i++){
				   cs.setObject(i+1, parameters[i]);
			   }			   
		   }
		   cs.execute();
	   }catch(Exception e){
	   		e.printStackTrace();
	   		throw new RuntimeException(e.getMessage());
	   		}finally{
	   			
	   			close(rs,cs,ct);
	   		}
   }
   
   
   //查询语句升级,满足  哪里用资源  哪里关闭资源
   public static ArrayList executeQuery3(String sql, String []params){
	  
	   PreparedStatement pstmt=null;
	   Connection conn=null;
	   ResultSet rs=null;
	   	   
	   try{
		  
		   conn=getConnection();
		   pstmt=conn.prepareStatement(sql);
		   //prepareCommand(pstmt,params);
		   //对问号赋值
		   if(params!=null&&!params.equals("")){
			   for(int i=0;i<params.length;i++){
				   pstmt.setString(i+1 ,params[i]);
			   }			   
		   }
		   
		   rs=pstmt.executeQuery();
		   
		   ArrayList al=new ArrayList();
		   ResultSetMetaData rsmd=rs.getMetaData();
		   int column=rsmd.getColumnCount();
		   
		   while(rs.next()){
			   Object[] ob=new Object[column];
			   for(int i=0;i<column;i++){
				   ob[i]=rs.getObject(i+1);
			   }
			   al.add(ob);
		   }
		   
		   return al;	  
		   
		   }catch(Exception e){
		   		e.printStackTrace();
		   		throw new RuntimeException("executeSqlResultSet 方法时出错："+e.getMessage());
		   		}finally{   			
		   				close(rs,pstmt,conn);			
		   		}
	      
   }
   
   
   
   
   
   public static ResultSet executeQuery(String sql, String []parameters){
	   try{
		   ct=getConnection();
		   ps=ct.prepareStatement(sql);
		   
		   if(parameters!=null&&!parameters.equals("")){
			   for(int i=0;i<parameters.length;i++){
				   ps.setString(i+1 ,parameters[i]);
			   }			   
		   }
		   
		   rs=ps.executeQuery();
		   
		   }catch(Exception e){
		   		e.printStackTrace();
		   		throw new RuntimeException(e.getMessage());
		   		}finally{
		   			
		   		}
	       return rs;
   }
   
   public static void exexuteUpdate2(String sql[],String [][] parameters){
	  try{
		  ct=getConnection();
		 ct.setAutoCommit(false);
		 
		 for(int i=0;i<parameters.length;i++){
			 if(parameters[i]!=null){
				 ps=ct.prepareStatement(sql[i]);
				 for(int j=0;j<parameters[i].length;j++){
					 ps.setString(j+1, parameters[i][j]);
				 }
				 ps.executeUpdate();
			 }
		 }
		 		 
		 ct.commit();
	  }catch(Exception e){
	   		e.printStackTrace();
	   		
	   		try{
	   			ct.rollback();
	   		}catch(SQLException e1){
	   			e1.printStackTrace();
	   		}
	   		
	   		throw new RuntimeException(e.getMessage());
	   		}finally{
	   			close(rs,ps,ct);
	   		}
   }
   
   public static void executeUpdate(String sql,String [] parameters){
	      try{
	    	  ct=getConnection();
	    	  ps=ct.prepareStatement(sql);
	    	  
	    	  if(parameters!=null){
	    		  for(int i=0;i<parameters.length;i++){
	    			  ps.setString(i+1, parameters[i]);
	    		  }
	    	  }
	    	  ps.executeUpdate();
	      }catch(Exception e){
	    	  e.printStackTrace();
	    	  throw new RuntimeException(e.getMessage());
	      }finally{
	    	  close(rs,ps,ct);
	      }
   }
   
   public static void close(ResultSet rs,Statement ps,Connection ct){
	   if(rs!=null){
		   try{
			   rs.close();
		   }catch(Exception e){
		    	  e.printStackTrace();
		    }
		   rs=null;
	   }
	   
	   if(ps!=null){
		   try{
			   ps.close();
		   }catch(Exception e){
		    	  e.printStackTrace();
		    }
		   ps=null;
	   }
	   
	   if(ct!=null){
		   try{
			   ct.close();
		   }catch(Exception e){
		    	  e.printStackTrace();
		    }
		   
		  ct=null;
	   }
   }
   
	   public static Connection getCt(){
		   return ct;
	   }
	   
	   public static PreparedStatement getPs(){
		   return ps;
	   }
	   
	   public static ResultSet getRs(){
		   return rs;
	   }
	   
	   public static CallableStatement getCs(){
		   return cs;
	   }
   }

