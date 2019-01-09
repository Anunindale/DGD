/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.transactionperiodsummary.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author riaan
 */
public class GLTransactionPeriodSummaryColumnDialog extends emcJDialog {

    private boolean ok = false;
    private JCheckBox chkAnalysisCode1;
    private JCheckBox chkAnalysisCode2;
    private JCheckBox chkAnalysisCode3;
    private JCheckBox chkAnalysisCode4;
    private JCheckBox chkAnalysisCode5;
    private JCheckBox chkAnalysisCode6;

    /**
     * Creates a new instance of GLTransactionPeriodSummaryColumnDialog.

     * @param sourceDRM DRM of the form on which active columns should be changed.
     * This will be used to determine column labels and current active columns.
     */
    public GLTransactionPeriodSummaryColumnDialog(GLTransactionPeriodSummaryDRM sourceDRM) {
        super(null, "Columns", true);

        initCheckBoxes(sourceDRM);

        this.setLayout(new GridLayout(1, 1));
        
        emcJPanel pnlComponents = new emcJPanel();
        pnlComponents.setPreferredSize(new Dimension(170, 190));
        pnlComponents.setLayout(new BoxLayout(pnlComponents, BoxLayout.Y_AXIS));

        pnlComponents.add(Box.createRigidArea(new Dimension(1, 10)));
        pnlComponents.add(chkAnalysisCode1);
        pnlComponents.add(chkAnalysisCode2);
        pnlComponents.add(chkAnalysisCode3);
        pnlComponents.add(chkAnalysisCode4);
        pnlComponents.add(chkAnalysisCode5);
        pnlComponents.add(chkAnalysisCode6);
        pnlComponents.add(Box.createRigidArea(new Dimension(1, 10)));

        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                GLTransactionPeriodSummaryColumnDialog.this.ok = true;
                GLTransactionPeriodSummaryColumnDialog.this.dispose();
            }
        };

        btnOK.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlComponents.add(btnOK);
        pnlComponents.add(Box.createRigidArea(new Dimension(1, 10)));

        this.add(pnlComponents);
        
        this.pack();
        this.setResizable(false);

        this.setVisible(true);
    }

    /**
     * Initializes checkboxes.
     *
     * @param sourceDRM DRM of the form on which active columns should be changed.
     * This will be used to determine column labels and current active columns.
     */
    private void initCheckBoxes(GLTransactionPeriodSummaryDRM sourceDRM) {
        this.chkAnalysisCode1 = new JCheckBox(sourceDRM.getColumnName("analysisCode1"));
        this.chkAnalysisCode2 = new JCheckBox(sourceDRM.getColumnName("analysisCode2"));
        this.chkAnalysisCode3 = new JCheckBox(sourceDRM.getColumnName("analysisCode3"));
        this.chkAnalysisCode4 = new JCheckBox(sourceDRM.getColumnName("analysisCode4"));
        this.chkAnalysisCode5 = new JCheckBox(sourceDRM.getColumnName("analysisCode5"));
        this.chkAnalysisCode6 = new JCheckBox(sourceDRM.getColumnName("analysisCode6"));

        this.chkAnalysisCode1.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chkAnalysisCode2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chkAnalysisCode3.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chkAnalysisCode4.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chkAnalysisCode5.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.chkAnalysisCode6.setAlignmentX(Component.CENTER_ALIGNMENT);

        List<String> columns = sourceDRM.getTableModel().getKeys();

        if (columns.contains("analysisCode1")) {
            chkAnalysisCode1.setSelected(true);
        }
        if (columns.contains("analysisCode2")) {
            chkAnalysisCode2.setSelected(true);
        }
        if (columns.contains("analysisCode3")) {
            chkAnalysisCode3.setSelected(true);
        }
        if (columns.contains("analysisCode4")) {
            chkAnalysisCode4.setSelected(true);
        }
        if (columns.contains("analysisCode5")) {
            chkAnalysisCode5.setSelected(true);
        }
        if (columns.contains("analysisCode6")) {
            chkAnalysisCode6.setSelected(true);
        }
    }

    /**
     * Returns a List containing the column names of all columns to be displayed.
     * @return A List containing the column names of all columns to be displayed.
     */
    public List<String> getActiveColumns() {
        List<String> columns = new ArrayList<String>();
        if (chkAnalysisCode1.isSelected()) {
            columns.add("analysisCode1");
        }
        if (chkAnalysisCode2.isSelected()) {
            columns.add("analysisCode2");
        }
        if (chkAnalysisCode3.isSelected()) {
            columns.add("analysisCode3");
        }
        if (chkAnalysisCode4.isSelected()) {
            columns.add("analysisCode4");
        }
        if (chkAnalysisCode5.isSelected()) {
            columns.add("analysisCode5");
        }
        if (chkAnalysisCode6.isSelected()) {
            columns.add("analysisCode6");
        }
        return columns;
    }

    /**
     * @return the ok
     */
    public boolean isOK() {
        return ok;
    }
}
