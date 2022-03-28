package dao;

public class InvoiceDao {
	private String url = "jdbc:mysql://localhost:3306/grey_goose";
	private String username = "admin";
	private String password = "asad";
	
	private static final String INSERT_SQL = "INSERT INTO winter_internship" + " (business_code, cust_number, clear_date,"
			+ " business_year, doc_id, posting_date, document_create_date, due_in_date, invoice_currency, document_type, posting_id,"
			+ " total_open_amount, baseline_create_date, cust_payment_terms, invoice_id) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_SQL = "SELECT * FROM winter_internship";
	private static final String UPDATE_SQL = "UPDATE winter_internship SET invoice_currency = ?, cust_payment_terms = ? WHERE sl_no = ?;";
	private static final String DELETE_SQL = "DELETE FROM winter_internship WHERE sl_no = ?;";
}
