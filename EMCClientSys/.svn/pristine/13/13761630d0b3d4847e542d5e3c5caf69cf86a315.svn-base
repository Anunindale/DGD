/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard;

import emc.app.components.tables.EMCMapComboBoxFactory;
import emc.forms.base.action.emcimportwizard.resources.ImportPreview;
import emc.forms.base.action.emcimportwizard.resources.ColumnMap;
import emc.app.components.tables.EMCTablesMapComboBox;
import emc.app.components.emcDialogue;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJCheckBox;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.messagehandler.emcHandler;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.forms.base.action.emcimportwizard.resources.GenClass;
import emc.forms.base.action.emcimportwizard.resources.ImportThread;
import emc.forms.base.action.emcimportwizard.resources.TableSuper;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class EMCImportWizard extends BaseInternalFrame implements ItemListener {

    private EMCFilePicker filePicker;
    private EMCFilePicker loadSave;
    private emcJComboBox seperatorCombo;
    private emcJComboBox delimiterCombo;
    private EMCTablesMapComboBox tableCombo;
    private emcJButton btnOk;
    private emcJButton btnCancel;
    private emcJButton btnColumns;
    private emcJButton btnPreview;
    private emcJButton btnLoadColumns;
    private emcJButton btnStoreColumns;
    private EMCUserData userData;
    private DefaultTableModel tableModel;
    private TableSuper table;
    private emcJComboBox edtDateFormat;
    private JProgressBar progressBar;
    private ImportThread thread;
    private ImportPreview preview;
    private ColumnMap theMap;
    private emcYesNoComponent multipros;
    private emcYesNoComponent directInsert;
    private emcYesNoComponent validateField;

    public EMCImportWizard(EMCUserData userData) {
        super("EMC Import Wizard", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 560);
        this.setHelpFile(new emcHelpFile("Base/EMCImportWizard.html"));
        this.userData = userData;
        preview = new ImportPreview();
        initFrame();
        GenClass.setColumns(tableCombo.getSelectedItem().toString(), tableModel);
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        emcJPanel mainPane = new emcJPanel();
        mainPane.setLayout(new BorderLayout(1, 1));
        setComp();
        mainPane.add(topPanel(), BorderLayout.NORTH);
        mainPane.add(bottomPanel(), BorderLayout.CENTER);
        mainPane.add(addProgressBar(), BorderLayout.SOUTH);
        tabbed.add("Import Wizard", mainPane);
        tabbed.add("Settings", logPane());
        this.add(tabbed);
    }

    private void setComp() {
        filePicker = new EMCFilePicker();
        //filePicker.getFc().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //filePicker.setPreferredSize(new java.awt.Dimension(300, 25));
        String[] seprators = {".(Dot)", ",(Comma)", " (Space)", "  (Tab)", "\\(Back slash)", "\'(Single Quote)", "\"(Double Quote)"};
        String[] delimiters = {"\"(Double Quote)", "|(Pipe)"};
        seperatorCombo = new emcJComboBox(seprators);
        seperatorCombo.setEditable(true);
        seperatorCombo.setPreferredSize(new java.awt.Dimension(300, 25));
        delimiterCombo = new emcJComboBox(delimiters);
        delimiterCombo.setEditable(true);
        delimiterCombo.setPreferredSize(new java.awt.Dimension(300, 25));
        tableCombo = EMCMapComboBoxFactory.getTablesMapComboBox(userData);
        tableCombo.addItemListener(this);
        tableCombo.setPreferredSize(new java.awt.Dimension(300, 25));
        btnOk = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    for (int c = 0; c < tableModel.getColumnCount(); c++) {
                        tableModel.setValueAt("", r, c);
                    }
                }
                if (filePicker.getFilePath() != null) {
                    if (preview.preview(tableCombo.getSelectedItem().toString(), table, filePicker.getFilePath().toString(),
                            seperatorCombo.getSelectedItem().toString(), delimiterCombo.getSelectedItem().toString(), edtDateFormat.getSelectedItem().toString(), theMap, userData)) {
                        emcDialogue d = new emcDialogue("Continue?", "Are you sure you want to continue with the import?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                        if (d.getDialogueResult() == 0) {
                            if (thread != null && thread.getState() != SwingWorker.StateValue.DONE) {
                                Logger.getLogger("emc").log(Level.WARNING, "A import is currently running, please cansel it first.", userData);
                            } else {
                                thread = new ImportThread(filePicker.getFilePath().toString(), tableCombo.getSelectedItem().toString(),
                                        seperatorCombo.getSelectedItem().toString(), delimiterCombo.getSelectedItem().toString(),
                                        edtDateFormat.getSelectedItem().toString(), getImportMethod(), getTableColumnNames(), doFieldValidation(),
                                        progressBar.getModel(), theMap, userData);
                                thread.execute();
                            }
                        }
                    }
                } else {
                    new emcDialogue("No File Selected!", "Please select a file to import?", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
                }
            }
        };
        btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                emcDialogue d = new emcDialogue("Continue?", "Are you sure you want to cancel the import?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (d.getDialogueResult() == 0) {
                    if (thread != null) {
                        thread.cancel(true);
                    }
                }
            }
        };
        btnColumns = new emcJButton("Columns") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (theMap == null || !theMap.getTableName().equals(tableCombo.getSelectedItem().toString())) {
                    theMap = GenClass.setColumnInfo(tableCombo.getSelectedItem().toString());
                }
                ColumnForm column = new ColumnForm(theMap);
                theMap = column.getDialogResult();
                if (theMap != null) {
                    preview.setColumnsFromSpec(theMap, tableModel);
                }
            }
        };
        btnLoadColumns = new emcJButton("Load Columns") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (theMap == null || !theMap.getTableName().equals(tableCombo.getSelectedItem().toString())) {
                    theMap = GenClass.setColumnInfo(tableCombo.getSelectedItem().toString());
                }
                if (loadSave != null && loadSave.getFile() != null) {
                    theMap = theMap.load(loadSave.getFile().getAbsolutePath());
                }
                if (theMap != null) {
                    preview.setColumnsFromSpec(theMap, tableModel);
                }
            }
        };
        btnStoreColumns = new emcJButton("Store Columns") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (theMap == null || !theMap.getTableName().equals(tableCombo.getSelectedItem().toString())) {
                    theMap = GenClass.setColumnInfo(tableCombo.getSelectedItem().toString());
                }
                if (loadSave != null && loadSave.getFile() != null) {
                    theMap.store(loadSave.getFile().getAbsolutePath(), theMap);
                }
            }
        };
        btnPreview = new emcJButton("Preview") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    for (int c = 0; c < tableModel.getColumnCount(); c++) {
                        tableModel.setValueAt("", r, c);
                    }
                }
                if (filePicker.getFilePath() != null) {
                    preview.preview(tableCombo.getSelectedItem().toString(), table, filePicker.getFilePath().toString(),
                            seperatorCombo.getSelectedItem().toString(), delimiterCombo.getSelectedItem().toString(), edtDateFormat.getSelectedItem().toString(), theMap, userData);
                } else {
                    new emcDialogue("No File Selected!", "Please select a file to import?", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
                }
            }
        };
    }

    private emcJPanel topPanel() {
        emcJPanel theMainPanel = new emcJPanel();
        theMainPanel.setLayout(new BorderLayout(1, 1));
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Choose File"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(filePicker, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Separated By"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(seperatorCombo, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Text Delimited By"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(delimiterCombo, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Import Into EMCTable"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(tableCombo, gbc);

        y++;
        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        theMainPanel.add(thePanel, BorderLayout.CENTER);
        theMainPanel.add(buttons(), BorderLayout.EAST);
        return theMainPanel;
    }

    private emcJPanel buttons() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnOk, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnCancel, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnPreview, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnLoadColumns, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnStoreColumns, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        thePanel.add(btnColumns, gbc);

        y++;
        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        thePanel.setPreferredSize(new Dimension(120, 25));
        return thePanel;
    }

    private emcJPanel bottomPanel() {
        Object[] columns = {"Column 1", "Column 2", "Column 3"};
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        tableModel = new DefaultTableModel(columns, 10);
        table = new TableSuper(tableModel) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        };
        table.setMinimumSize(new java.awt.Dimension(550, 195));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new java.awt.Dimension(550, 195));
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.BOTH;
        thePanel.add(tableScroll, gbc);
        return thePanel;
    }

    private emcJPanel logPane() {
        final emcJCheckBox importLog = new emcJCheckBox("Write new import log file");
        final emcJCheckBox systemLog = new emcJCheckBox("Use system logger settings", true);
        final emcJTextField logFileName = new emcJTextField();
        this.loadSave = new EMCFilePicker();
        multipros = new emcYesNoComponent();
        multipros.setSelected(false);
        directInsert = new emcYesNoComponent();
        directInsert.setSelected(false);
        validateField = new emcYesNoComponent();
        validateField.setSelected(true);
        logFileName.setEnabled(false);
        String[] dates = {"dd/MM/yyyy", "MM/dd/yyyy", "dd/MM/yy", "MM/dd/yy", "dd-MM-yyyy", "MM-dd-yyyy", "dd-MM-yy", "MM-dd-yy", "dd.MM.yyyy", "MM.dd.yyyy", "dd.MM.yy", "MM.dd.yy"};
        edtDateFormat = new emcJComboBox(dates);
        edtDateFormat.setEditable(true);


        importLog.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent arg0) {
                if (importLog.isSelected()) {
                    systemLog.setSelected(false);
                    logFileName.setEnabled(true);
                    emcHandler.setWriteLog(true);
                } else {
                    systemLog.setSelected(true);
                    logFileName.setEnabled(false);
                    emcHandler.setWriteLog(false);
                }
            }
        });

        systemLog.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent arg0) {
                if (systemLog.isSelected()) {
                    importLog.setSelected(false);
                } else {
                    importLog.setSelected(true);
                }
            }
        });

        logFileName.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent arg0) {
            }

            public void focusLost(FocusEvent arg0) {
                emcHandler.setPath(logFileName.getText());
            }
        });

        Component[][] componentsLog = {{systemLog},
            {importLog},
            {new emcJLabel("Log file name"), logFileName}};

        Component[][] componentsDate = {{new emcJLabel("Date Format"), edtDateFormat},
            {new emcJLabel("Validate Fields"), validateField},
            {new emcJLabel("Use Multiple Threads"), multipros},
            {new emcJLabel("Use Direct Insert"), directInsert},
            {new emcJLabel("Load/Save Column to"), loadSave}};


        emcJPanel logPanel = emcSetGridBagConstraints.createSimplePanel(componentsLog, GridBagConstraints.NONE, false);
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Settings"));

        emcJPanel datePanel = emcSetGridBagConstraints.createSimplePanel(componentsDate, GridBagConstraints.NONE, false);
        datePanel.setBorder(BorderFactory.createTitledBorder("Date Settings"));

        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(logPanel, BorderLayout.NORTH);
        thePanel.add(datePanel, BorderLayout.CENTER);
        return thePanel;
    }

    private JPanel addProgressBar() {
        emcJPanel thePanel = new emcJPanel();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        thePanel.add(progressBar);
        return thePanel;
    }

    public void itemStateChanged(ItemEvent evt) {
        if (evt.getSource() == tableCombo) {
            GenClass.setColumns(tableCombo.getSelectedItem().toString(), tableModel);
        }
    }

    public int getImportMethod() {
        if (directInsert.getSelectedItem().equals("Yes")) {
            return 2;
        }
        if (multipros.getSelectedItem().equals("Yes")) {
            return 1;
        } else {
            return 0;
        }
    }

    public String[] getTableColumnNames() {
        int colNum = table.getColumnCount();
        String[] columns = new String[colNum];

        for (int col = 0; col < colNum; col++) {
            columns[col] = table.getColumnName(col);
        }

        return columns;
    }
    
    public boolean doFieldValidation() {
        return validateField.getSelectedItem().equals("Yes");
    }
}
