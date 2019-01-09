/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journal.resources;

import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author riaan
 */
public class TotalsDialog extends emcJDialog {

    /**
     * Creates a new instance of TotalsDialog.
     */
    public TotalsDialog(String journalNumber, EMCUserData userData) {
        super(null, "Totals");

        this.setLayout(new BorderLayout(1, 1));

        String[] columns = new String[]{"Period", "Debit", "Credit"};
        Object[][] values = fetchTotals(journalNumber, userData);

        emcJTableUpdate tblTotals = new emcJTableUpdate(values, columns);

        emcJTextField txtJournalNumber = new emcJTextField();
        txtJournalNumber.setText(journalNumber);
        txtJournalNumber.setEditable(false);
        Component[][] components = new Component[][]{
            {new emcJLabel("Journal Number"), txtJournalNumber}
        };

        TableColumn col = tblTotals.getColumnModel().getColumn(1);
        TableColumn col2 = tblTotals.getColumnModel().getColumn(2);
        DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
        rend.setHorizontalAlignment(SwingConstants.RIGHT);
        col.setCellRenderer(rend);
        col2.setCellRenderer(rend);

        this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.NORTH);
        this.add(new JScrollPane(tblTotals), BorderLayout.CENTER);

        this.setSize(300, 300);
    }

    /**
     * Fetches totals
     * @param journalNumber Journal number.
     * @return Totals for the specified journal.
     */
    private Object[][] fetchTotals(String journalNumber, EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.GET_JOURNAL_TOTALS);

        List toSend = new ArrayList();
        toSend.add(journalNumber);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof List) {
            List<Object[]> result = (List<Object[]>) toSend.get(1);
            Object[][] totals = new Object[result.size() + 1][3];
            BigDecimal grandTotalDebit = BigDecimal.ZERO;
            BigDecimal grandTotalCredit = BigDecimal.ZERO;
            for (int i = 0; i < result.size(); i++) {
                totals[i] = result.get(i);
                grandTotalDebit = grandTotalDebit.add((BigDecimal) result.get(i)[1]);
                grandTotalCredit = grandTotalCredit.add((BigDecimal) result.get(i)[2]);
            }
            totals[result.size()][0] = "Total";
            totals[result.size()][1] = grandTotalDebit;
            totals[result.size()][2] = grandTotalCredit;

            return totals;
        }

        return null;
    }
}
