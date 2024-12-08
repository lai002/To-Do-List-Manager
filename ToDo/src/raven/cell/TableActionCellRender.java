package raven.cell;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Custom cell renderer to handle table cell actions.
 *
 * @author Admin
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

     public TableActionCellRender() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      
        PanelAction action = new PanelAction();

      
        if (!isSelected && row % 2 == 0) {
            action.setBackground(Color.WHITE); 
        } else {
            action.setBackground(super.getBackground()); 
        }


        return action;
    }
}