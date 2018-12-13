/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.base;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author riaan
 */
class EMCMapComboBoxTableCellRenderer extends DefaultTableCellRenderer {
    
    /** Creates a new instance of EMCMapComboBoxTableCellRenderer. */
    public EMCMapComboBoxTableCellRenderer() {
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Object val = ((JLabel)(table.getCellEditor(row, column).getTableCellEditorComponent(table, value, hasFocus, row, column))).getText();

        return super.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, column);
    }
}
