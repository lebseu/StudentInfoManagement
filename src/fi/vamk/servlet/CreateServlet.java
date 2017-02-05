package fi.vamk.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.vamk.utils.XMLOperation;

public class CreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	File dbXML = null;

	public void init() {

		String separator = System.getProperty("file.separator");
		String dbPath = getServletContext().getRealPath(separator) + separator + "db.xml";
		dbXML = new File(dbPath);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String nationality = request.getParameter("nationality");
		String telephone = request.getParameter("telephone");
		String major = request.getParameter("major");
		Student student = new Student(id, name, sex, birthday, nationality, telephone, major);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Create</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<h1 style=\"color:red\">Create A Student</h1><hr>");

		XMLOperation xo = new XMLOperation(dbXML);
		if(xo.validate(dbXML, student)){
			xo.create(student);
			out.println("<h2>Here is the student information you created just now:</h2>");
			out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Sex</th><th>Birthday</th><th>Nationality</th><th>Telephone</th><th>Major</th></tr>");
			out.println(student);
			out.println("</table>");
		}
		else {
			out.println("<h2>Please check your information!</h2>");
		}
		out.println("<br><input type='button' value='Back' onclick=\"location.href='index.html'\">");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
