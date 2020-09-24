package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;

import constants.IOnlineBookStoreConstants;
import sql.IUserContants;

public class AdminLoginServlet extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(ServletRequest req, ServletResponse res) throws IOException, ServletException {
		PrintWriter pw = res.getWriter();
		res.setContentType(IOnlineBookStoreConstants.CONTENT_TYPE_TEXT_HTML);
		String uName = req.getParameter(IUserContants.COLUMN_USERNAME);
		String pWord = req.getParameter(IUserContants.COLUMN_PASSWORD);
		try {
			Connection con = DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + IUserContants.TABLE_USERS + " WHERE  "
					+ IUserContants.COLUMN_USERNAME + "=? AND " + IUserContants.COLUMN_PASSWORD + "=? AND "
					+ IUserContants.COLUMN_USERTYPE + "=1");
			ps.setString(1, uName);
			ps.setString(2, pWord);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				RequestDispatcher rd = req.getRequestDispatcher("Sample.html");
				
				Cookie ck = new Cookie("usertype","admin");
				
				rd.include(req, res);
				pw.println("<div class=\"tab\">Admin login Successful</div>");
				pw.println("<div class=\"tab\"><br/><a href=\"AddBook.html\">ADD BOOKS</a><br/></div>");
				pw.println("<div class=\"tab\"><br/><a href=\"RemoveBooks.html\">REMOVE BOOKS</a><br/></div>");
				pw.println("<div class=\"tab\"><br/><a href=\"viewbook\">VIEW BOOKS</a></div>");
			} else {

				RequestDispatcher rd = req.getRequestDispatcher("AdminLogin.html");
				rd.include(req, res);
				pw.println("<div class=\"tab\">Incorrect UserName or PassWord</div>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}