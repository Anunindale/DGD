/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wikus
 */
public class TableSuper extends JTable {

    private final DefaultTableModel tableModel;
    private java.awt.Color rowColors[] = new java.awt.Color[2];
    private boolean drawStripes = false;

    public TableSuper(DefaultTableModel model) {
        super(model);
        initComp();
        this.tableModel = model;
        this.setColumnSelectionAllowed(false);
        this.setModel(this.tableModel);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().setReorderingAllowed(false);
    }

    public TableSuper(DefaultTableModel model, ColumnMap map) {
        super(model);
        initComp();
        this.tableModel = model;
        this.setColumnSelectionAllowed(false);
        this.setModel(this.tableModel);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().setReorderingAllowed(false);
        JPopupMenu popup = new JPopupMenu("Show");
        ActionListener listener = new PopupListener(tableModel, map,this);
        JMenuItem all = new JMenuItem("Show All");
        all.addActionListener(listener);
        popup.add(all);
        JMenuItem active = new JMenuItem("Show Active");
        active.addActionListener(listener);
        popup.add(active);
        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(listener);
        popup.add(print);
        this.setComponentPopupMenu(popup);
    }

    /** Add stripes between cells and behind non-opaque cells. */
    @Override
    public void paintComponent(java.awt.Graphics g) {
        if (!(drawStripes = isOpaque())) {
            super.paintComponent(g);
            return;
        }

        // Paint zebra background stripes
        updateZebraColors();
        final java.awt.Insets insets = getInsets();
        final int w = getWidth() - insets.left - insets.right;
        final int h = getHeight() - insets.top - insets.bottom;
        final int x = insets.left;
        int y = insets.top;
        int theRowHight = 16; // A default for empty tables
        final int nItems = getRowCount();
        for (int i = 0; i < nItems; i++, y += theRowHight) {
            theRowHight = getRowHeight(i);
            g.setColor(rowColors[i & 1]);
            g.fillRect(x, y, w, theRowHight);
        }
        // Use last row height for remainder of table area
        final int nRows = nItems + (insets.top + h - y) / theRowHight;
        for (int i = nItems; i < nRows; i++, y += theRowHight) {
            g.setColor(rowColors[i & 1]);
            g.fillRect(x, y, w, theRowHight);
        }
        final int remainder = insets.top + h - y;
        if (remainder > 0) {
            g.setColor(rowColors[nRows & 1]);
            g.fillRect(x, y, w, remainder);
        }

        // Paint component
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }

    /** Add background stripes behind rendered cells. */
    @Override
    public java.awt.Component prepareRenderer(
            javax.swing.table.TableCellRenderer renderer, int row, int col) {
        final java.awt.Component c = super.prepareRenderer(renderer, row, col);
        if (drawStripes && !isCellSelected(row, col)) {
            c.setBackground(rowColors[row & 1]);
        }
        return c;
    }

    /** Add background stripes behind edited cells. */
    @Override
    public java.awt.Component prepareEditor(
            javax.swing.table.TableCellEditor editor, int row, int col) {
        final java.awt.Component c = super.prepareEditor(editor, row, col);
        if (drawStripes && !isCellSelected(row, col)) {
            c.setBackground(rowColors[row & 1]);
        }
        return c;
    }

    /** Force the table to fill the viewport's height. */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        final java.awt.Component p = getParent();
        if (!(p instanceof javax.swing.JViewport)) {
            return false;
        }
        return ((javax.swing.JViewport) p).getHeight() > getPreferredSize().height;
    }

    /** Compute zebra background stripe colors. */
    private void updateZebraColors() {
        if ((rowColors[0] = getBackground()) == null) {
            rowColors[0] = rowColors[1] = java.awt.Color.white;
            return;
        }
        final java.awt.Color sel = getSelectionBackground();
        if (sel == null) {
            rowColors[1] = rowColors[0];
            return;
        }
        final float[] bgHSB = java.awt.Color.RGBtoHSB(
                rowColors[0].getRed(), rowColors[0].getGreen(),
                rowColors[0].getBlue(), null);
        final float[] selHSB = java.awt.Color.RGBtoHSB(
                sel.getRed(), sel.getGreen(), sel.getBlue(), null);
        rowColors[1] = java.awt.Color.getHSBColor(
                (selHSB[1] == 0.0 || selHSB[2] == 0.0) ? bgHSB[0] : selHSB[0],
                0.1f * selHSB[1] + 0.9f * bgHSB[1],
                bgHSB[2] + ((bgHSB[2] < 0.5f) ? 0.05f : -0.05f));
    }

    private void initComp() {
        this.setRowHeight(17);
        this.setRowMargin(1);
        this.setFont(emc.app.config.emcAppConstants.getDataFont());
    }
}
