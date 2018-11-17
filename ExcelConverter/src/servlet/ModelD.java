package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;

import util.Collegamento;
import util.Functions;

/**
 * Servlet implementation class ModelDefiner
 */

@WebServlet(name = "ModelD", urlPatterns = { "/ModelD" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ModelD extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private List<Collegamento> collega;
	private List<List<HSSFCell>> sheetDataModel= null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModelD() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		String fileName = null;
//		int indiceCol=0;
//        //Get all the parts from request and write it to the file on server
//        for (Part part : request.getParts()) {
//        	String contentDisp = part.getHeader("content-disposition");
//			String[] tokens = contentDisp.split(";");
//			for (String token : tokens) {
//				//retrieve data from HTTP header content-disposition
//				if (token.trim().contains("collegaModel")) {
//					indiceCol++;
//					System.out.println(part.getInputStream().toString());
//				}
//			}
//        }

		String fileName = request.getParameter("fileName");
		String[] collegaModel = request.getParameterValues("collegaModel");
		String[] ordineAcc = request.getParameterValues("ordineAcc");
		String [] limite= request.getParameterValues("limit");
		collega = new ArrayList<Collegamento>();
//		String ordinamento= request.getParameter("ordinamento");
		int indiceCol = 0;
		
		HttpSession sessione = request.getSession();
		
		if (sessione.getAttribute("sheetDataModel") != null) {
			
			sheetDataModel = (List<List<HSSFCell>>) request.getSession().getAttribute("sheetDataModel");
		} else {
			
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}
		for (String item : collegaModel) {
			if (item.isEmpty()) {
			
				getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			} else {
				
				Collegamento col=new Collegamento(Integer.parseInt(item.toString()),Integer.parseInt(ordineAcc[indiceCol]),Integer.parseInt(limite[indiceCol]));
				collega.add(col);
				indiceCol++;
			}
		}
		if(!collega.isEmpty()) {
			Functions.mergeTheSheet(collega,sheetDataModel,fileName);
			request.getSession().invalidate();
		}else {
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}

		request.getSession().invalidate();
		getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
