package edu.cooper.ece460.http;

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.Process;
import java.lang.ProcessBuilder;

public class RunHadoopServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>Hello Servlet</h1>");
		response.getWriter().println("session=" + request.getSession(true).getId());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);
		String inputDir = req.getParameter("input");
		String outputCite = req.getParameter("outcite");
		String outputCount = req.getParameter("outcount");
		String outputHist = req.getParameter("outhist");
		String jar = req.getParameter("jar");
		String mainClass = "edu.cooper.ece460.patents.HadoopPatents";

		Process pb = new ProcessBuilder(
			"hadoop",
			"jar",
			jar,
			mainClass,
			inputDir,
			outputCite,
			outputCount,
			outputHist
		).start();
		String hadoopOutput = convertStreamToString(pb.getInputStream());
		String hadoopError = convertStreamToString(pb.getErrorStream());
		
		// Display output and error messages
		PrintWriter out = resp.getWriter();
		out.println("<div>");
		out.println("<div style='float:left; width:50%;'>");
		out.println("<h2>Output</h2><hr><tt>");
		out.println(nl2br(hadoopOutput));
		out.println("</tt></div>");
		out.println("<div style='float:right; width:50%;'>");
		out.println("<h2>Errors</h2><hr><tt>");
		out.println(nl2br(hadoopError));
		out.println("</tt></div>");
		out.println("</div>");
	}

	public String nl2br(String s) {
		return s.replaceAll("(\r\n|\n)", "<br />");
	}

	public String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}


