package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Invoice;

public class InvoiceDao {
	private String url = "jdbc:mysql://localhost:3306/grey_goose?allowPublicKeyRetrieval=true&useSSL=false";
	private String username = "admin";
	private String password = "asad";
	
	private static final String INSERT_SQL = "INSERT INTO winter_internship" + " (business_code, cust_number, clear_date,"
			+ " buisness_year, doc_id, posting_date, document_create_date, due_in_date, invoice_currency, document_type, posting_id,"
			+ " total_open_amount, baseline_create_date, cust_payment_terms, invoice_id, sl_no) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_SQL = "SELECT * FROM winter_internship WHERE is_deleted = 0";
	private static final String UPDATE_SQL = "UPDATE winter_internship SET invoice_currency = ?, cust_payment_terms = ? WHERE sl_no = ?;";
	private static final String DELETE_SQL = "UPDATE winter_internship SET is_deleted = 1 WHERE sl_no = ?;";
	
	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public boolean insertInvoice(Invoice invoice) throws SQLException {
		try(Connection con = getConnection();
				PreparedStatement prepStmt = con.prepareStatement(INSERT_SQL)) {
			prepStmt.setString(1, invoice.getBusiness_code());
			prepStmt.setInt(2, invoice.getCust_number());
			prepStmt.setString(3, invoice.getClear_date());
			prepStmt.setInt(4, invoice.getBuisness_year());
			prepStmt.setString(5, invoice.getDoc_id());
			prepStmt.setString(6, invoice.getPosting_date());
			prepStmt.setString(7, invoice.getDocument_create_date());
			prepStmt.setString(8, invoice.getDue_in_date());
			prepStmt.setString(9, invoice.getInvoice_currency());
			prepStmt.setString(10, invoice.getDocument_type());
			prepStmt.setInt(11, invoice.getPosting_id());
			prepStmt.setDouble(12, invoice.getTotal_open_amount());
			prepStmt.setString(13, invoice.getBaseline_create_date());
			prepStmt.setString(14, invoice.getCust_payment_terms());
			prepStmt.setInt(15, invoice.getInvoice_id());
			
			prepStmt.setInt(16, 48592);
			
			if (prepStmt.executeUpdate() > 0) {
				return true;
			}
			return false;
		}
	}
	
	public ArrayList<Invoice> fetchInvoice() throws SQLException {
		ArrayList<Invoice> invoiceArr = new ArrayList<Invoice>();
		
		try(Connection con = getConnection();
				PreparedStatement prepStmt = con.prepareStatement(SELECT_SQL)) {
			
			ResultSet rs = prepStmt.executeQuery();
			
			while(rs.next()) {
				String business_code = rs.getString("business_code");
				int sl_no = rs.getInt("sl_no");
				int cust_number = rs.getInt("cust_number");
				int buisness_year = rs.getInt("buisness_year");
				String doc_id = rs.getString("doc_id");
				int posting_id = rs.getInt("posting_id");
				int invoice_id = rs.getInt("invoice_id");
				String clear_date = rs.getString("clear_date");
				String posting_date = rs.getString("posting_date");
				String document_create_date = rs.getString("document_create_date");
				String due_in_date = rs.getString("due_in_date");
				String baseline_create_date = rs.getString("baseline_create_date");
				Double total_open_amount = rs.getDouble("total_open_amount");
				String invoice_currency = rs.getString("invoice_currency");
				String document_type = rs.getString("document_type");
				String cust_payment_terms = rs.getString("cust_payment_terms");
				
				invoiceArr.add(new Invoice(sl_no, business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date,
						due_in_date, invoice_currency, document_type, posting_id, total_open_amount, baseline_create_date, cust_payment_terms,
						invoice_id));
	
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return invoiceArr;
	}
	
	public boolean editInvoice(int sl_no, String invoice_currency, String cust_payment_terms) throws SQLException {
		try(Connection con = getConnection();
				PreparedStatement prepStmt = con.prepareStatement(UPDATE_SQL)) {
			
			prepStmt.setString(1, invoice_currency);
			prepStmt.setString(2, cust_payment_terms);
			prepStmt.setInt(3, sl_no);
			
			boolean isUpdated = prepStmt.executeUpdate() > 0;
			
			return isUpdated;
		}
	}
	
	public boolean deleteInvoice(int[] sl_no) throws SQLException {
		boolean isRowDeleted = false;
		try(Connection con = getConnection();) {
			for(int x : sl_no) {
				PreparedStatement prepStmt = con.prepareStatement(DELETE_SQL);
				prepStmt.setInt(1, x);
				isRowDeleted  = prepStmt.executeUpdate() > 0;
			}
		}
		return isRowDeleted;
	}
}















