/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import emc.framework.EMCDebug;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wikus
 */
public class PopupListener implements ActionListener {

    private DefaultTableModel model;
    private ColumnMap map;
    private TableSuper theTable;

    public PopupListener(DefaultTableModel model, ColumnMap map,TableSuper theTable) {
        this.model = model;
        this.map = map;
        this.theTable = theTable;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareTo("Show All") == 0) {
            setColumnInfo();
            setModel(true);
        } else if (e.getActionCommand().compareTo("Show Active") == 0) {
            setColumnInfo();
            setModel(false);
        } else if((e.getActionCommand().compareTo("Print") == 0)){
              MessageFormat header = new MessageFormat("Import Columns");
              MessageFormat footer = new MessageFormat("Printed with EMC by ASD" +
                    "                                                                 Page {0,number,integer}");
            try {

                    theTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                
            } catch (java.awt.print.PrinterException ex) {
                ex.printStackTrace();
                if (EMCDebug.getDebug()) System.out.println("Cannot print" + ex.getMessage());
            }
        }
    }

    private void setModel(boolean allFlag) {
        String[] names = new String[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            names[i] = model.getColumnName(i);
        }
        Object[][] oArray = new Object[map.size()][4];
        Collection<ColumnInfo> infoCollec = map.values();
        int row = 0;
        for (ColumnInfo info : infoCollec) {
            try {
                if (allFlag || info.isActive()) {
                    oArray[row][0] = info.getColumnName();
                    oArray[row][1] = info.isActive();
                    oArray[row][2] = info.getDefaultValue();
                    oArray[row][3] = info.isOverride();
                    row++;
                }
            } catch (Exception e) {
                continue;
            }
        }
        model.setDataVector(oArray, names);
        model.setRowCount(row);
    }

    private void setColumnInfo() {
        ColumnInfo info;
        String columnName;
        for (int i = 0; i < model.getRowCount(); i++) {
            columnName = (String) model.getValueAt(i, 0);
            info = map.get(columnName);
            info.setActive((Boolean) model.getValueAt(i, 1));
            info.setOverride((Boolean) model.getValueAt(i, 3));
            if (model.getValueAt(i, 2) == null) {
                info.setDefaultValue("");
            } else {
                info.setDefaultValue(model.getValueAt(i, 2).toString());
            }
            map.put(columnName, info);
        }
    }
}
