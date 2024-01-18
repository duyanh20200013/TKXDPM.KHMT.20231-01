package entity.payment;

import entity.db.AIMSDB;

import java.sql.*;
import java.util.ArrayList;

public class PaymentTransaction {
    private int id;
    private String errorCode;
    private String transactionId;
    private String transactionContent;

    public PaymentTransaction() {
    }

    private int amount;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionContent(String transactionContent) {
        this.transactionContent = transactionContent;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;

    public PaymentTransaction(String errorCode, String transactionId, String transactionContent,
                              int amount, String createdAt) {
        super();
        this.errorCode = errorCode;

        this.transactionId = transactionId;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public void saveTransaction() throws SQLException {
        System.out.println(this.transactionId);
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO `Transaction` (transactionId, transactionContent, amount, createdAt) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, this.transactionId);
            preparedStatement.setString(2, this.transactionContent);
            preparedStatement.setInt(3,this.amount);
            preparedStatement.setString(4,this.createdAt);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng PreparedStatement để tránh rò rỉ nguồn và giải phóng tài nguyên
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @return String
     */
    public String getErrorCode() {
        return errorCode;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public ArrayList<PaymentTransaction> getListPaymentTransaction() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("SELECT * from `Transaction`");
        ArrayList<PaymentTransaction> invoices = new ArrayList<>();
        while (res.next()) {
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setId(res.getInt("id"));
            paymentTransaction.setAmount(res.getInt("amount"));
            paymentTransaction.setTransactionId(res.getString("transactionId"));
            paymentTransaction.setTransactionContent("Chờ Admin Xác nhận");
            invoices.add(paymentTransaction);
        }
        return invoices;
    }

//    public void deleteTransaction(int id){
//        Connection connection = AIMSDB.getConnection();
//        PreparedStatement preparedStatement = null;
//        try{
//            String sql = "UPDATE invoice SET status = ? WHERE paypalId = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1,status);
//            preparedStatement.setString(2, this.getPaypalId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
