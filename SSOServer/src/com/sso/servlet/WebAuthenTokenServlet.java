package com.sso.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sso.user.UserInfo;


@WebServlet(name = "WebAuthenTokenServlet", urlPatterns = "/WebAuthenTokenServlet")
public class WebAuthenTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WebAuthenTokenServlet() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenToken(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		authenToken(request, response);
	}
	public void authenToken(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String contextFrom = (String)request.getAttribute("contextFrom");
		
		if(contextFrom==null || !contextFrom.equals("webapp2")) {
			String tokenServer = request.getParameter("tokenServerKey");
			if(!tokenServer.isEmpty()) {
				request.setAttribute("tokenServerKey", tokenServer);
				request.setAttribute("contextFromWebapp3", "webapp3");
				ServletContext otherContext = request.getServletContext().getContext("/AgentSSO");
				RequestDispatcher rd = otherContext.getRequestDispatcher("/AuthenServlet");
				rd.forward(request, response);
			}else {
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				out.print("NOT FOUND TOKEN SERVER");
			}
		}else {
			RequestDispatcher rd = null;
			UserInfo userinfo = new UserInfo();
			String jsonUserinfo = (String)request.getAttribute("userinfo");
			
			userinfo = new Gson().fromJson(jsonUserinfo, UserInfo.class);
			if(userinfo != null){
				request.setAttribute("isVidTokenServer", true);
				request.setAttribute("name", userinfo.getName());
				request.setAttribute("surname", userinfo.getSurname());
				request.setAttribute("dob", userinfo.getDob());
				request.setAttribute("sex", userinfo.getSex());
				request.setAttribute("username", userinfo.getUsername());
			}else{
				request.setAttribute("isVidTokenServer", false);
			}
			
			ServletContext otherContext = request.getServletContext().getContext("/Portal");
			rd = otherContext.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
	}

}
