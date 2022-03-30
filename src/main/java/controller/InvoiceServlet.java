package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InvoiceDao;
import model.Invoice;

/**
 * Servlet implementation class InvoiceServlet
 */
@WebServlet("/")
public class InvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InvoiceDao invoiceDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
        this.invoiceDao = new InvoiceDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			} catch (ServletException | IOException | SQLException e) {
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
		
		// TODO Auto-generated method stub
		//doGet(request, response);
	}
	
	 private void insertInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 
		 String business_code = request.getParameter("business_code");
		 int cust_number = Integer.parseInt(request.getParameter("cust_number"));
		 Date clear_date = Date.valueOf(request.getParameter("clear_date"));
		 int buisness_year = Integer.parseInt(request.getParameter("buisness_year"));
		 int doc_id = Integer.parseInt(request.getParameter("doc_id"));
		 Date posting_date = Date.valueOf(request.getParameter("posting_date"));
		 Date document_create_date = Date.valueOf(request.getParameter("document_create_date"));
		 Date due_in_date = Date.valueOf(request.getParameter("due_in_date"));
		 String invoice_currency = request.getParameter("invoice_currency");
		 String document_type = request.getParameter("document_type");
		 int posting_id = Integer.parseInt(request.getParameter("posting_id"));
		 double total_open_amount = Double.parseDouble(request.getParameter("total_open_amount"));
		 Date baseline_create_date = Date.valueOf(request.getParameter("baseline_create_date"));
		 String cust_payment_terms = request.getParameter("cust_payment_terms");
		 int invoice_id = Integer.parseInt(request.getParameter("invoice_id"));
		 
		 Invoice newInvo = new Invoice(business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, due_in_date,
				 invoice_currency, document_type, posting_id, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id);
		 invoiceDao.insertInvoice(newInvo);
	 }
	 
	 private void fetchInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 invoiceDao.fetchInvoice();
	 }
	 
	 private void editInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 int sl_no = Integer.parseInt(request.getParameter("sl_no"));
		 String invoice_currency = request.getParameter("invoice_currency");
		 String cust_payment_terms = request.getParameter("cust_payment_terms");
		 
		 invoiceDao.editInvoice(sl_no, invoice_currency, cust_payment_terms);
	 }
	 
	 private void deleteInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		 int sl_no = Integer.parseInt(request.getParameter("sl_no"));
		 invoiceDao.deleteInvoice(sl_no);
	 }

}
