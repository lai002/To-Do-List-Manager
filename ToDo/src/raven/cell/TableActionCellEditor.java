/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raven.cell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author Admin
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        PanelAction action = new PanelAction();
        action.initEvent(event, row);
       action.setBackground(table.getSelectionBackground());

        return action;

    }
}
