package com.sso.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sso.service.AuthenUserManager;
import com.sso.user.UserInfo;

import sun.net.www.protocol.http.AuthenticationInfo;

@WebServlet(name = "AuthenServlet", urlPatterns = "/AuthenServlet")
public class AuthenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public AuthenServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		authenticationInfo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void authenticationInfo(HttpServletRequest request, HttpServletResponse response) {

		try {
			UserInfo user = null;
			String tokenServer = null;

			AuthenUserManager authen = new AuthenUserManager();
			String contextFrom = request.getParameter("contextFrom");
			String contextFromWebapp3 = (String) request.getAttribute("contextFromWebapp3");
			RequestDispatcher rd = null;
			if (contextFrom.equals("webapp1") && contextFromWebapp3 == null) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				if (!username.isEmpty() || !password.isEmpty()) {
					user = authen.authenUser(username, password);
					if (user != null) {
						tokenServer = authen.generateTokenServer(user);
					}
				}
				request.setAttribute("tokenServerKey", tokenServer);
				//System.out.println("Test"+user.getUsername());
				request.setAttribute("username", user.getUsername());
				request.setAttribute("password", user.getPassword());
				
				ServletContext context = request.getServletContext().getContext("/Portal");
				rd=context.getRequestDispatcher("/index.jsp");
			}else if(contextFromWebapp3.equals("webapp3")){
				tokenServer = (String)request.getAttribute("tokenServerKey"); 
				UserInfo userinfo = authen.verifyToken(tokenServer);
				String jsonUserinfo = new Gson().toJson(userinfo);
				
				request.setAttribute("userinfo", jsonUserinfo);
				request.setAttribute("contextFrom", "webapp2");
				ServletContext otherContext = request.getServletContext().getContext("/SSOServer");
				rd = otherContext.getRequestDispatcher("/WebAuthenTokenServlet");
			}
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
