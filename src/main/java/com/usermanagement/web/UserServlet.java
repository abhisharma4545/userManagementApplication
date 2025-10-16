package com.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usermanage.dao.UserDao;
import com.usermanagement.bean.User;

@WebServlet("/")
public class UserServlet  extends HttpServlet{
	
      private static final long	serialVersionId=1L;
      private UserDao ud;
      
      
     @Override
    public void init() throws ServletException {
    	  ud= new UserDao();
    	
    } 
     
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
	
     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String action = req.getServletPath();
    	
    	switch(action) {
    	   
    	case "/new":
    		showNewForm(req, resp);
    		break;
    		
    	case "/insert":
    		try {
				insertUser(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		break;
    		
    	case "/delete":
    		deleteUser(req, resp);
    		break;
    		
    	case "/edit":
    		showEditForm(req, resp);
    		break;
    		
    	case "/update":
    		updateUser(req, resp);
    	  break;
    	  
    	  default:
    		  listUsers(req, resp);
    		  break;
    	
    	}
    	 
     }
     
     private void showNewForm(HttpServletRequest req,HttpServletResponse res)
			   throws ServletException,IOException{
		   RequestDispatcher dispatcher = req.getRequestDispatcher("user-form.jsp");
		   dispatcher.forward(req, res);
		   
                    }
     private void insertUser(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException, SQLException {
         // TODO: Add logic to insert user into DB
    	 String name = req.getParameter("name");
    	 String email = req.getParameter("email");
    	 String country = req.getParameter("country");
    	 
    	 User  newUser =new User(name, email, country);
    	 
    	 
    	 ud.insertUser(newUser);
    	 res.sendRedirect("list");
     }

     private void deleteUser(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {
         // TODO: Add logic to delete user
    	 int id = Integer.parseInt(req.getParameter("id"));
    	 try {
    		 
    		 ud.deleteUser(id);
    	 }catch (Exception e) {
			// TODO: handle exception
		}
    	 res.sendRedirect("list");
    	    
     }

     private void showEditForm(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {
         // TODO: Show edit form
    	 int id = Integer.parseInt(req.getParameter("id"));
    	 User exitingUser;
    	 try {
    		 exitingUser = ud.selectUser(id);
    		 RequestDispatcher dispatcher = req.getRequestDispatcher("user-form.jsp");
    		req.setAttribute("user", exitingUser);
    		dispatcher.forward(req, res);

    		 
    	 }catch (Exception e) {
			// TODO: handle exception
    		 e.printStackTrace();
		}
     }

     private void updateUser(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {
         // TODO: Update user data
    	 int id = Integer.parseInt(req.getParameter("id"));
    	 String name = req.getParameter("name");
    	 String email = req.getParameter("email");
    	 String country = req.getParameter("country");
    	 User user = new User(id,name, email, country);
    	 ud.UpdateUser(user);
    	 res.sendRedirect("list");
     }

     private void listUsers(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {
         // TODO: Display all users in a JSP
    	 try {
    	 List<User> listUser = ud.selectAllUser();
    	 req.setAttribute("listUser", listUser);
    	 RequestDispatcher dispatcher = req.getRequestDispatcher("user-list.jsp");
    	 dispatcher.forward(req, res);
    	 }catch (Exception e) {
			// TODO: handle exception
    		 e.printStackTrace();
		}
     }
}
