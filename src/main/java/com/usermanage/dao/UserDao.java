package com.usermanage.dao;

//import java.awt.desktop.UserSessionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.bean.User;

public class UserDao {
	
	   private String jdbcurl = "jdbc:mysql://localhost:3306/userdb?useSSL=false&allowPublicKeyRetrieval=true";
	   private String jdbcusername ="root";
	   private String pass = "@Abhi4545b";
	   private String jdbcdriver = "com.mysql.cj.jdbc.Driver";
	   
	   public static final String INSERT_USERS_SQL  = "INSERT INTO users"+"(name,email,country) VALUES (?,?,?)";
	   
	   public static final String SELECT_USERS_BY_ID  = "SELECT id ,name,email,country from users  where id =?";
	   
	  
	   public static final String SELECT_ALL_USERS  = "select * from users";

	   public static final String DELETE_USERS_SQL  = "delete from users where id = ?";
	   
	
	   public static final String UPDATE_USERS_SQL  = "update users set name = ?,email =?,country = ? where id = ?";
	   
	   public UserDao() {
		   
	   }
	   
	   protected Connection getConnection() {
		   Connection con = null;
		   try {
			   Class.forName(jdbcdriver);
			  con = DriverManager.getConnection(jdbcurl,jdbcusername,pass);
			   
		   }catch (SQLException e) {
			// TODO: handle exception
			   e.printStackTrace();
			   
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		   return con;
		
	   }
	   
	   //insert user method
	   
	     public  void insertUser(User user) throws SQLException {
	    	 System.out.println("INSERT_USERS_SQL");
	    	 try {
	    		 Connection con = getConnection();
	    		 PreparedStatement preparestatment = con.prepareStatement(INSERT_USERS_SQL);
	    		 preparestatment.setString(1, user.getName());
	    		 preparestatment.setString(2, user.getEmail());
	    		 preparestatment.setString(3, user.getCountry());
	    		 System.out.println(preparestatment);
	    		 preparestatment.executeUpdate();
	    		 
	    	 }catch (SQLException e) {
				// TODO: handle exception
	    		 e.printStackTrace();
			}
	    	 
	     }
	   //select user by id
	      public User selectUser(int id) {
	    	  User user = null;
	    	  try {
	    		  Connection con = getConnection();
	    		  PreparedStatement preparestmt = con.prepareStatement(SELECT_USERS_BY_ID);
	    		  preparestmt.setInt(1, id);
	    		  System.out.println(preparestmt);
	    		  ResultSet rs = preparestmt.executeQuery();
	    		  while(rs.next()) {
	    			  String name = rs.getString("name");
	    			  String email = rs.getString("email");
	    			  String country = rs.getString("country");
	    			  user  = new User(name,email,country);
	    		  }
	    		  
	    	  }catch (SQLException e) {
				// TODO: handle exception
	    		  e.printStackTrace();
			}
	    	  return user;
	      }
	     
	   // select all user 
	      
	      public List<User> selectAllUser() {
	    	  List<User> users = new ArrayList();
	    	  
	      try {
	    	  Connection con  = getConnection();
	    	  PreparedStatement pres = con.prepareStatement(SELECT_ALL_USERS);
	    	  System.out.println(pres);
	    	  ResultSet rs= pres.executeQuery();
	    	  
	    	  while(rs.next()) {
	    		  int id = rs.getInt("id");
	    		  String name = rs.getString("name");
    			  String email = rs.getString("email");
    			  String country = rs.getString("country");
    			  users.add(new User(id, name, email, country));
	    	  }
	      
	    	  }catch (SQLException e) {
				// TODO: handle exception
	    		  e.printStackTrace();
			}
	         return users;
	      }
	  
	   //update user
	           
	           public boolean UpdateUser(User user) {
	        	   boolean rwoupdated = false;
	           try {
	        	   Connection con = getConnection();
	        	   PreparedStatement pres = con.prepareStatement(UPDATE_USERS_SQL);
	        	   pres.setString(1, user.getName());
	        	   pres.setString(2, user.getEmail());
	        	   pres.setString(3, user.getCountry());
	        	   pres.setInt(4, user.getId());
	        	   
	        	   rwoupdated = pres.executeUpdate() >0;
	        			   
	           }catch (Exception e) {
				// TODO: handle exception
	        	   e.printStackTrace();
			}
	            return rwoupdated;
	           
	           }
	           
	           //delete user
	 	      
	           public boolean deleteUser(int id) {
	        	   boolean  rowdelet=false;
	           try {
	        	   Connection connection = getConnection();
	        	   PreparedStatement pres = connection.prepareStatement(DELETE_USERS_SQL);
	        	   
	        	   pres.setInt(1, id);
	        	   rowdelet = pres.executeUpdate() >0;
	           }catch (Exception e) {
				// TODO: handle exception
			}
	           
	            return rowdelet;
	           }
}

