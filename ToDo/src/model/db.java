/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class db {

    Connection con;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;

    public db() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager", "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean insertTask(Tasks tasks) {
        String sql = "INSERT INTO tasks (Title, Des, Status, Date, Time, Prio) VALUES (?, ?, ?, ?, ?,?)";

        try {
            pst = con.prepareStatement(sql);

            // Set string parameters
            pst.setString(1, tasks.getTitle());
            pst.setString(2, tasks.getDes());
            pst.setString(3, tasks.getStatus());

            // Parse and format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM d, yy");
            java.util.Date parsedDate = dateFormat.parse(tasks.getDate());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            // Set date parameter
            pst.setDate(4, sqlDate);
            pst.setString(5, tasks.getTime());

            // Set priority
            pst.setString(6, tasks.getPrio());

            // Execute the insert operation
            return pst.executeUpdate() > 0;

        } catch (java.text.ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

  public void getallTasks(JTable table) {
    String sql = "SELECT * FROM `tasks` ORDER BY `TaskID` ASC"; // You can adjust sorting as needed

    try {
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear any existing data in the table

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM d, yy");

        ArrayList<Object[]> rows = new ArrayList<>();
        
        while (rs.next()) {
            Object[] row = new Object[7]; // Adjust size as needed
            row[0] = rs.getInt("TaskID");
            row[1] = rs.getString("Title");   // Title
            row[2] = rs.getString("Des");     // Description
            row[3] = rs.getString("Status");  // Status

            String dbDate = rs.getString("Date"); // Get the date as a string
            if (dbDate != null && !dbDate.isEmpty()) {
                row[4] = dbDate; // Directly use the database date string
            } else {
                row[4] = ""; // If the date is null or empty
            }
            row[5] = rs.getString("Time");

            row[6] = rs.getString("Prio");  // Priority

            rows.add(row);
        }

        // Sort rows: Incomplete tasks first, then Completed tasks
        rows.sort((row1, row2) -> {
            String status1 = row1[3].toString(); // Assuming the Status column is at index 3
            String status2 = row2[3].toString();

            // "Incomplete" tasks should come before "Completed" tasks
            if (status1.equalsIgnoreCase("Incomplete") && status2.equalsIgnoreCase("Completed")) {
                return -1;
            } else if (status1.equalsIgnoreCase("Completed") && status2.equalsIgnoreCase("Incomplete")) {
                return 1;
            }
            return 0; // Keep the order if both statuses are the same
        });

        // Re-add sorted rows into the model
        for (Object[] row : rows) {
            model.addRow(row);
        }

        // Optionally hide the TaskID column if needed
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}


    public boolean updateTasks(Tasks p) {
        String sql = "UPDATE tasks SET Title=?, Des=?, Status=?, Date=?, Time=?, Prio=? WHERE TaskID=?";

        try {
            pst = con.prepareStatement(sql);

            // Set all the parameters
            pst.setString(1, p.getTitle());
            pst.setString(2, p.getDes());
            pst.setString(3, p.getStatus());
            pst.setString(4, p.getDate());
            pst.setString(5, p.getTime());// Store the date as a string
            pst.setString(6, p.getPrio());
            pst.setInt(7, p.getTaskID());

            // Execute the update and return true if successful
            return pst.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean delete(Tasks t) {
        try {
            pst = con.prepareStatement("DELETE FROM tasks WHERE TaskID=?");
            pst.setInt(1, t.getTaskID());
            return pst.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }
   public boolean ifDateExists(String due) {
    boolean exists = false;
    String sql = "SELECT COUNT(*) AS count FROM tasks WHERE Date = ?";
    
    // Use try-with-resources to ensure that resources are closed automatically
    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, due);
        
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("count");
                exists = (count > 0);  // If count > 0, the date already exists
            }
        }
    } catch (SQLException ex) {
        // Log the exception with an error message
        System.err.println("Error while checking if date exists: " + ex.getMessage());
        ex.printStackTrace();
    }

    return exists;
}

}
