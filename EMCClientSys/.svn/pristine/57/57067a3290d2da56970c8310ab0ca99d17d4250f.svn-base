/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard;

import emc.forms.base.action.emcimportwizard.resources.ColumnMap;
import emc.forms.base.action.emcimportwizard.resources.ColumnInfo;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.config.emcicons;
import emc.forms.base.action.emcimportwizard.resources.TableSuper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 * This form is usede to manage the visible columns on the import wizard grid.
 * 
 * @author wikus
 */
public class ColumnForm extends emcJDialog {

    private ColumnMap columnMap;
    private TableSuper table;
    private DefaultTableModel model;
    private JDialog dialog;

    public ColumnForm(ColumnMap columnMap) {
        this.columnMap = columnMap;
        initFrame();
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(toppane(), BorderLayout.CENTER);
        thePanel.add(buttons(), BorderLayout.SOUTH);
        dialog = new JDialog(this, "Columns", true);
        dialog.setContentPane(thePanel);
        dialog.setSize(new Dimension(380, 330));
        dialog.setVisible(true);
    }

    private emcJPanel toppane() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout(1, 1));
        thePanel.add(tabelPane(), BorderLayout.CENTER);
        thePanel.add(tableButtons(), BorderLayout.EAST);
        return thePanel;
    }

    private emcJPanel tabelPane() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridLayout(1, 1));
        Object[] columnNames = {"Table Column", "Active", "Default Value", "Override"};
        model = new DefaultTableModel(setData(), columnNames);
        table = new TableSuper(model, columnMap) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                if (arg1 == 0) {
                    return false;
                }
                if (arg1 == 1) {
                    return !columnMap.get(model.getValueAt(arg0, 0)).isMandatory();
                }
                if (arg1 == 3) {
                    return emc.functions.Functions.checkBlank(model.getValueAt(arg0, 2)) ? false : true;
                }
                if (emc.functions.Functions.checkBlank(model.getValueAt(arg0, 2))) {
                    model.setValueAt(false, arg0, 3);
                }
                return super.isCellEditable(arg0, arg1);
            }

            @Override
            public Class<?> getColumnClass(int arg0) {
                if (arg0 == 1 || arg0 == 3) {
                    return Boolean.class;
                }
                return super.getColumnClass(arg0);
            }
        };
        JScrollPane topscroll = new JScrollPane(table);
        thePanel.add(topscroll);
        return thePanel;
    }

    private emcJPanel tableButtons() {
        emcJButton btnUp = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                moveRow(0);
            }
        };
        btnUp.setIcon(new ImageIcon(getClass().getResource(emcicons.getUpArrow())));
        emcJButton btnDown = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                moveRow(1);
            }
        };
        btnDown.setIcon(new ImageIcon(getClass().getResource(emcicons.getDownArrow())));
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(btnUp, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 0, 1, 0.1);
        thePanel.add(btnDown, gbc);
        return thePanel;
    }

    private emcJPanel buttons() {
        emcJButton btnOk = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                setColumnInfo(0);
                dialog.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                //  setColumnInfo(1);
                dialog.dispose();
            }
        };
        btnOk.setSize(new java.awt.Dimension(80, 25));
        btnCancel.setSize(new java.awt.Dimension(80, 25));
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.insets = new Insets(10, 40, 10, 10);
        thePanel.add(btnOk, gbc);

        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, 0, 0.1);
        thePanel.add(btnCancel, gbc);

        return thePanel;
    }

    /**
     * Populated the table with data.
     * @return Object[][] withe the table data
     */
    private Object[][] setData() {
        Object[][] oArray = new Object[columnMap.size()][4];
        Collection<ColumnInfo> infoCollec = columnMap.values();
        int row = 0;
        for (ColumnInfo info : infoCollec) {
            try {
                oArray[row][0] = info.getColumnName();
                oArray[row][1] = info.isActive();
                oArray[row][2] = info.getDefaultValue();
                oArray[row][3] = info.isOverride();
                row++;
            } catch (Exception e) {
                continue;
            }
        }
        return oArray;
    }

    /**
     * Sets the columnMap with the new settings for the import wizzard to use.
     * @param result the result of this dialog
     */
    private void setColumnInfo(int result) {
        if (result == 0) {
            ColumnInfo info;
            String columnName;
            for (int i = 0; i < model.getRowCount(); i++) {
                columnName = (String) model.getValueAt(i, 0);
                info = columnMap.get(columnName);
                info.setActive((Boolean) model.getValueAt(i, 1));
                info.setOverride((Boolean) model.getValueAt(i, 3));
                if (model.getValueAt(i, 2) == null) {
                    info.setDefaultValue("");
                } else {
                    info.setDefaultValue(model.getValueAt(i, 2).toString());
                }
                columnMap.put(columnName, info);
            }
        }
    }

    /**
     * moves the selected row up or down in the table.
     * @param dir the direction to move. 0 = up; 1 = down;
     */
    private void moveRow(int dir) {
        int row = table.getSelectedRow();
        String s;
        List<String> l = columnMap.getOrder();
        if (row != -1) {
            if (l.size() != model.getRowCount()) {
                int current = l.indexOf(model.getValueAt(row, 0));
                if (dir == 0) {
                    if (row != 0) {
                        s = l.get(current);
                        l.remove(current);
                        l.add(l.indexOf(model.getValueAt(row - 1, 0)), s);
                        columnMap.setOrder(l);
                        model.moveRow(row, row, row - 1);
                        table.setRowSelectionInterval(row - 1, row - 1);
                    }
                } else if (dir == 1) {
                    if (row != model.getRowCount() - 1) {
                        s = l.get(current);
                        l.remove(current);
                        l.add(l.indexOf(model.getValueAt(row + 1, 0)), s);
                        columnMap.setOrder(l);
                        model.moveRow(row, row, row + 1);
                        table.setRowSelectionInterval(row + 1, row + 1);
                    }
                }
            } else {
                if (dir == 0) {
                    if (row != 0) {
                        model.moveRow(row, row, row - 1);
                        table.setRowSelectionInterval(row - 1, row - 1);
                        s = l.get(row);
                        l.remove(row);
                        l.add(row - 1, s);
                        columnMap.setOrder(l);
                    }
                } else if (dir == 1) {
                    if (row != model.getRowCount() - 1) {
                        model.moveRow(row, row, row + 1);
                        table.setRowSelectionInterval(row + 1, row + 1);
                        s = l.get(row);
                        l.remove(row);
                        l.add(row + 1, s);
                        columnMap.setOrder(l);
                    }
                }
            }
        }
    }

    public ColumnMap getDialogResult() {
        return columnMap;
    }
}
