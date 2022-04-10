package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.InvoiceDao;
import model.Invoice;

/**
 * Servlet implementation class InvoiceServlet
 */
@WebServlet("/")
public class InvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InvoiceDao invoiceDao;
	private Gson gson;
	private String JSONresponse;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
        this.invoiceDao = new InvoiceDao();
        this.gson  = new Gson();
        this.JSONresponse = null;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getServletPath();
		
			switch(action) {
			case "/insert":
				try {
					insertInvoice(request, response);
				} catch (ServletException | IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
			
			case "/fetch":
				try {
					fetchInvoice(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case "/edit":
				try {
					editInvoice(request, response);
				} catch (ServletException | IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
				
			case "/delete":
				try {
					deleteInvoice(request, response);
				} catch (ServletException | IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
				
			default:
				break;
			}
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().append(JSONresponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 //TODO Auto-generated method stub
		doGet(request, response);
	}
	
	 private void insertInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 String business_code = request.getParameter("business_code");
		 int cust_number = Integer.parseInt(request.getParameter("cust_number"));
		 String clear_date = toDate(request.getParameter("clear_date"));
		 int buisness_year = Integer.parseInt(request.getParameter("buisness_year"));
		 String doc_id = request.getParameter("doc_id");
		 String posting_date = toDate(request.getParameter("posting_date"));
		 String document_create_date = toDate(request.getParameter("document_create_date"));
		 String due_in_date = toDate(request.getParameter("due_date"));
		 String invoice_currency = request.getParameter("invoice_currency");
		 String document_type = request.getParameter("document_type");
		 int posting_id = Integer.parseInt(request.getParameter("posting_id"));
		 double total_open_amount = Double.parseDouble(request.getParameter("total_open_amount"));
		 String baseline_create_date = toDate(request.getParameter("baseline_create_date"));
		 String cust_payment_terms = request.getParameter("cust_payment_terms");
		 int invoice_id = Integer.parseInt(request.getParameter("invoice_id"));
		 
		 Invoice newInvo = new Invoice(business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, due_in_date,
				 invoice_currency, document_type, posting_id, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id);
		 
		 
		 boolean isInserted = invoiceDao.insertInvoice(newInvo);
		 JSONresponse = gson.toJson(isInserted);
		 System.out.println(JSONresponse);
	 }
	 
	 private void fetchInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 ArrayList<Invoice> in = null;
		 in = invoiceDao.fetchInvoice();
		 JSONresponse = gson.toJson(in);
	 }
	 
	 private void editInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 int sl_no = Integer.parseInt(request.getParameter("sl_no"));
		 String invoice_currency = request.getParameter("invoice_currency");
		 String cust_payment_terms = request.getParameter("cust_payment_terms");
		 
		 boolean isEdited = invoiceDao.editInvoice(sl_no, invoice_currency, cust_payment_terms);
		 JSONresponse = gson.toJson(isEdited);
	 }
	 
	 private void deleteInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 String[] sl_no = (request.getParameter("sl_no")).split(",");
		 int[] list = new int[sl_no.length];
		 
		 for(int a = 0; a < sl_no.length; a++) {
			 list[a] = Integer.parseInt(sl_no[a]);
		 }
		 for(int x : list)
			 System.out.println("this is int: "+x);
		 
		 boolean isDeleted = invoiceDao.deleteInvoice(list);
		 JSONresponse = gson.toJson(isDeleted);
	 }
	 
	 private String toDate(String mydate) {
		 String m = mydate.substring(4,7);
		 String d = mydate.substring(8,10);
		 String y = mydate.substring(11,15);
		 
		 switch(m) {
		 case "Jan":
			 m = "01";
			 break;
		 case "Feb":
			 m = "02";
			 break;
		 case "Mar":
			 m = "03";
			 break;
		 case "Apr":
			 m = "04";
			 break;
		 case "May":
			 m = "05";
			 break;
		 case "Jun":
			 m = "06";
			 break;
		 case "Jul":
			 m = "07";
			 break;
		 case "Aug":
			 m = "08";
			 break;
		 case "Sep":
			 m = "09";
			 break;
		 case "Oct":
			 m = "10";
			 break;
		 case "Nov":
			 m = "11";
			 break;
		 case "Dec":
			 m = "12";
			 break;
		 default:
			 break;
		 }
		 String newD = y + "-" + m + "-" + d;
		 
		 return newD; 
	 }

}
