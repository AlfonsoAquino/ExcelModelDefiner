package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFCell;

import util.Functions;

/**
 * Servlet implementation class Serv
 */
@WebServlet(name = "Serv", urlPatterns = { "/Serv" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)

public class Serv extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<List<HSSFCell>> sheetDataStarter;
	private List<List<HSSFCell>> sheetDataModel;
	private int inteStarter;
	private int inteModel;
	public Serv() {
		super();
	}
	
	
	//Capire perchè legge model 2 volte
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		//retrieve starter file
//		Part filePart = request.getpa("starter");// Retrieves <input type="file" name="image">`
//		String filePath = filePart.getSubmittedFileName();// Retrieves complete file name with path and directories
//		Path p = Paths.get(filePath); // creates a Path object
//		String starterName = p.getFileName().toString();// Retrieves file name from Path object
//		InputStream streamStarter = filePart.getInputStream();
		
		inteStarter= Integer.parseInt(request.getParameter("inteStarter"));
		inteModel= Integer.parseInt(request.getParameter("inteModel"));
		
		HttpSession session= request.getSession(true);
		session.setAttribute("validate", true);
		int i=0;
		for (Part part : request.getParts()) {

			String contentDisp = part.getHeader("content-disposition");
			String[] tokens = contentDisp.split(";");
			for (String token : tokens) {
				
				//retrieve data from HTTP header content-disposition
				if (token.trim().contains("starter")) {
					InputStream streamStarter = part.getInputStream();
					sheetDataStarter = Functions.fillTheSheet(streamStarter,inteStarter);
					session.setAttribute("sheetDataStarter", sheetDataStarter);
					
				}else if(token.trim().contains("model")) {
					
					InputStream streamStarter = part.getInputStream();
					sheetDataModel = Functions.fillTheSheet(streamStarter,inteModel);
					session.setAttribute("sheetDataModel",sheetDataModel);
				}
			}
		}
		getServletContext().getRequestDispatcher("/modelConfig.jsp").forward(
                request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
