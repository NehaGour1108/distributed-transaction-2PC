package org.example;

import java.sql.*;
import java.lang.Class;

public class DistributedTransaction {

    // Coordinator: Manages the transaction process
    public void startTransaction() {
        // Assume two databases (DB1 and DB2) involved in the distributed transaction
        Connection db1Conn = null;
        Connection db2Conn = null;

        try {
            // Step 1: Set up the connections to both databases
            db1Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC", "user1", "password1");
            db2Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2", "user2", "password2");

            // Start the transaction
            db1Conn.setAutoCommit(false);
            db2Conn.setAutoCommit(false);

            // Prepare Phase: Both databases prepare to commit or abort
            if (preparePhase(db1Conn) && preparePhase(db2Conn)) {
                // If both prepared successfully, commit Phase 2
                commitPhase(db1Conn);
                commitPhase(db2Conn);
                System.out.println("Transaction Committed Successfully.");
            } else {
                // If any participant failed to prepare, abort the transaction
                rollbackPhase(db1Conn);
                rollbackPhase(db2Conn);
                System.out.println("Transaction Aborted.");
            }
        } catch (SQLException  e) {
            try {
                // In case of failure, rollback both transactions
                if (db1Conn != null) db1Conn.rollback();
                if (db2Conn != null) db2Conn.rollback();
                System.out.println("Transaction Failed: " + e.getMessage());
            } catch (SQLException se) {
                System.out.println("Rollback Failed: " + se.getMessage());
            }
        } finally {
            try {
                if (db1Conn != null) db1Conn.close();
                if (db2Conn != null) db2Conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean preparePhase(Connection conn) {
        try {
            // Simulate the preparation for commit (checking constraints, locks, etc.)
            // For simplicity, we are just using a dummy query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM some_table");
            return rs.next();
        } catch (SQLException e) {
            // If an error occurs, abort the transaction
            return false;
        }
    }

    private void commitPhase(Connection conn) throws SQLException {
        // Commit the transaction if all participants agree
        conn.commit();
    }

    private void rollbackPhase(Connection conn) throws SQLException {
        // Rollback the transaction if any participant aborts
        conn.rollback();
    }

    public static void main(String[] args) {
        DistributedTransaction dt = new DistributedTransaction();
        dt.startTransaction();
    }
}
