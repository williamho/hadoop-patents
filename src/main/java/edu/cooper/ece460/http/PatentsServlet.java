package edu.cooper.ece460.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

// Should probably be in a jsp
public class PatentsServlet extends HttpServlet {
	PrintWriter out;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);
		String patentNum = req.getParameter("patentnum");
		String citationFile = req.getParameter("citationfile");

		out = resp.getWriter();
		out.println("<link rel='stylesheet' href='style.css' type='text/css'>");
		out.println("<h1>Search patents</h1>");

		req.getRequestDispatcher("/patent_search.html").include(req, resp); 

		if (patentNum == null || citationFile == null)
			return;

		// Open file and try to find patent with that number
		out.println("<div style='float:left; width:400px; padding-left:10px;'>");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(citationFile));
		}
		catch (FileNotFoundException e) {
			out.println("File <tt>" + citationFile + "</tt> not found");
			return;
		}

		String line, foundLine = null;
		while ((line = br.readLine()) != null) {
			if (line.startsWith(patentNum)) {
				foundLine = line;
				break;
			}
		}
		br.close();

		// Patent not found
		out.println("Patent #" + patentNum + " ");
		if (foundLine == null) {
			out.println("not found in file <tt>" + citationFile + "</tt>");
			return;
		}

		String[] parts = foundLine.split("\\s+",2); // Split by whitespace

		if (parts.length == 1) {
			out.println("is not cited by any other patents");
		}
		else {
			String[] citedBy = parts[1].split(",");
			out.println("is cited by:<br /><ul>");

			for (String patent : citedBy) 
				out.println("<li><a href='?citationfile=" + citationFile + "&patentnum=" + patent + "'>" + patent + "</a></li>");
			out.println("</ul>");
		}
		out.println("</div>");
	}
}

