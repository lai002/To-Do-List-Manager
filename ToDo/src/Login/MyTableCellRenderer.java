/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Admin
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, 
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Highlight "Incomplete" tasks in Magenta and "Completed" tasks in Green
        if (!isSelected) {
            String status = table.getValueAt(row, column).toString();
            if (status.equalsIgnoreCase("Incomplete")) {
                c.setBackground(Color.MAGENTA);
                c.setForeground(Color.WHITE); // Adjust text color for better readability
            } else if (status.equalsIgnoreCase("Completed")) {
                c.setBackground(Color.GREEN);
                c.setForeground(Color.BLACK); // Adjust text color for better readability
            } else {
                c.setBackground(Color.WHITE); // Default background
                c.setForeground(Color.BLACK); // Default text color
            }
        } else {
            // Use selection colors when row is selected
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }
        return c;
    }
}