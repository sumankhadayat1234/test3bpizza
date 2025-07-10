package demomavinfx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaOrderDAO {

    private final String url = "jdbc:mysql://localhost:3306/your_database_name";
    private final String user = "your_mysql_username";
    private final String password = "your_mysql_password";

    private Connection conn;

    public PizzaOrderDAO() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    // Create (Insert)
    public PizzaOrder insert(PizzaOrder order) throws SQLException {
        String sql = "INSERT INTO pizza_orders (name, mobile, size, toppings, total_bill) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getName());
            pstmt.setString(2, order.getMobile());
            pstmt.setString(3, order.getSize());
            pstmt.setInt(4, order.getToppings());
            pstmt.setDouble(5, order.getTotalBill());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
        return order;
    }

    // Read (Select All)
    public List<PizzaOrder> getAllOrders() throws SQLException {
        List<PizzaOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM pizza_orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PizzaOrder order = new PizzaOrder(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("size"),
                        rs.getInt("toppings"),
                        rs.getDouble("total_bill")
                );
                orders.add(order);
            }
        }
        return orders;
    }

    // Read by ID
    public PizzaOrder getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM pizza_orders WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new PizzaOrder(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("size"),
                        rs.getInt("toppings"),
                        rs.getDouble("total_bill")
                    );
                }
            }
        }
        return null; // not found
    }

    // Update
    public boolean update(PizzaOrder order) throws SQLException {
        String sql = "UPDATE pizza_orders SET name = ?, mobile = ?, size = ?, toppings = ?, total_bill = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getName());
            pstmt.setString(2, order.getMobile());
            pstmt.setString(3, order.getSize());
            pstmt.setInt(4, order.getToppings());
            pstmt.setDouble(5, order.getTotalBill());
            pstmt.setInt(6, order.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Delete
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pizza_orders WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Close connection when done
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

