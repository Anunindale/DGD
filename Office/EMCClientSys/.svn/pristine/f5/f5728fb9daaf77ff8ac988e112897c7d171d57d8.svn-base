package emc.forms.developertools.display.columnpreview;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 * 
 * @author wikus
 */
public class ColumnPreviewer extends BaseInternalFrame implements ChangeListener, MouseListener, ListSelectionListener {

    private JSpinner numberOfColumns;
    private emcJTextField columnName;
    private emcJTextField columnWidth;
    private emcJButton button;
    private emcJPanel compPane;
    private emcJPanel tablePane;
    private emcJPanel mainPane;
    private emcJTabbedPane tabpane;
    private emcJLabel nocLabel;
    private emcJLabel cnLabel;
    private emcJLabel cwLabel;
    private JList columnList;
    private JScrollPane tableScrollPane;
    private JScrollPane listScrollPane;
    private JTable table;

    public ColumnPreviewer(EMCUserData userData) {
        super("Column Previewer", true, true, true, true, userData);
        this.setBounds(50, 50, 700, 465);

        init();
    }

    private void init() {
        nocLabel = new emcJLabel("Number of columns");
        cnLabel = new emcJLabel("Column Header");
        cwLabel = new emcJLabel("Column Width");

        compPane = new emcJPanel();
        tablePane = new emcJPanel();
        mainPane = new emcJPanel();
        tabpane = new emcJTabbedPane();

        SpinnerModel spinModel = new SpinnerNumberModel(6, 1, 50, 1);
        numberOfColumns = new JSpinner(spinModel);

        columnName = new emcJTextField();
        columnName.setText("Column 1");
        columnWidth = new emcJTextField();
        columnWidth.setText("75");

        button = new emcJButton("Set");

        String[] columnNames = {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5", "Column 6"};
        Object[][] rows = new Object[23][6];
        table = new JTable(rows, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new java.awt.Dimension(453, 386));

        Object[] listData = {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5", "Column 6"};
        columnList = new JList(listData);
        columnList.setSelectedIndex(0);
        listScrollPane = new JScrollPane(columnList);
        listScrollPane.setPreferredSize(new java.awt.Dimension(150, 150));

        setLayout();
    }

    private void setLayout() {
        GridBagLayout gbl = new GridBagLayout();
        compPane.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        compPane.add(nocLabel, gbc);


        gbc.gridy = 1;
        numberOfColumns.setPreferredSize(new java.awt.Dimension(50, 25));
        compPane.add(numberOfColumns, gbc);


        gbc.gridy = 2;
        compPane.add(new JLabel(" "), gbc);

        gbc.gridy = 3;
        compPane.add(cnLabel, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        compPane.add(columnName, gbc);



        gbc.gridy = 5;
        compPane.add(new JLabel(" "), gbc);

        gbc.gridy = 6;
        compPane.add(cwLabel, gbc);

        gbc.gridy = 7;
        compPane.add(columnWidth, gbc);

        gbc.gridy = 8;
        compPane.add(new JLabel(" "), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        compPane.add(button, gbc);

        gbc.gridy = 10;
        compPane.add(new JLabel(" "), gbc);

        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.BOTH;
        columnList.setPreferredSize(new java.awt.Dimension(150, 150));
        compPane.add(listScrollPane, gbc);

        tablePane.add(tableScrollPane);

        mainPane.add(compPane, BorderLayout.WEST);
        mainPane.add(tablePane, BorderLayout.EAST);

        this.tabpane.addTab("Preview", mainPane);
        this.add(tabpane, BorderLayout.CENTER);

        numberOfColumns.addChangeListener(this);
        button.addMouseListener(this);
        columnList.addListSelectionListener(this);
    }

    public void stateChanged(ChangeEvent arg0) {
        Columns(numberOfColumns.getValue());
    }

    public void mouseClicked(MouseEvent arg0) {
        setColumns(columnName.getText().trim(), columnWidth.getText().trim());
    }

    public void valueChanged(ListSelectionEvent e) {
        setValues();
    }

    public void mouseEntered(MouseEvent arg0) {

    }

    public void mouseExited(MouseEvent arg0) {

    }

    public void mousePressed(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {

    }
    //Logic
    private static int columnsNow = 6;

    private void Columns(Object value) {
        int setColumns = (Integer) value;
        if (columnsNow > setColumns) {
            table.removeColumn(table.getColumnModel().getColumn(table.getColumnCount() - 1));
        } else if (columnsNow < setColumns) {
            table.addColumn(new TableColumn());
            table.getColumnModel().getColumn(setColumns - 1).setHeaderValue("Column " + setColumns);
        }
        columnsNow = setColumns;
        setList();
    }

    private void setColumns(String name, String width) {
        int col = columnList.getSelectedIndex();
        if (width.length() != 0) {
            try {
                int columnWidth = Integer.parseInt(width);
                table.getColumnModel().getColumn(col).setPreferredWidth(columnWidth);
                table.doLayout();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Only an Integer value allowed for Column Width",
                        "NumberFormatException", JOptionPane.ERROR_MESSAGE);
                columnWidth.setText("");
            }
        }

        if (name.length() != 0) {
            table.getColumnModel().getColumn(col).setHeaderValue(name);
            table.getTableHeader().repaint();
        }
        setList();
    }

    private void setList() {
        Object[] listData = new Object[columnsNow];
        for (int i = 0; i < columnsNow; i++) {
            listData[i] = table.getColumnModel().getColumn(i).getHeaderValue();
        }
        columnList.setListData(listData);
        columnList.setSelectedIndex(0);
    }

    private void setValues() {
        try {
            columnName.setText(String.valueOf(table.getColumnModel().getColumn(columnList.getSelectedIndex()).getHeaderValue()));
            columnWidth.setText(String.valueOf(table.getColumnModel().getColumn(columnList.getSelectedIndex()).getWidth()));

        } catch (ArrayIndexOutOfBoundsException e) {
            columnName.setText(String.valueOf(table.getColumnModel().getColumn(0).getHeaderValue()));
            columnWidth.setText(String.valueOf(table.getColumnModel().getColumn(0).getWidth()));
        }
    }
}
