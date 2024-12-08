import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import javax.swing.JTable;

public class MyTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, 
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Example: Highlight incomplete tasks
        if (!isSelected && table.getValueAt(row, column).toString().equalsIgnoreCase("Incomplete")) {
            c.setBackground(Color.YELLOW);
        } else {
            c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        }
        return c;
    }
}