package emc.forms.base.display.reporttext;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCComboBoxCellEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.texteditor.EMCHTMLEditorFormComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.reporttools.BaseReportText;
import emc.enums.base.reporttools.BaseReportSectionEnum;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/** 
 *
 * @author claudette
 */
public class BaseReportTextForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of BaseReportTextForm. */
    public BaseReportTextForm(EMCUserData userData) {
        super("Report Text", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 500);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseReportText(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("report");
        drm.setFormTextId2("part");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, tabs, createTextPanel());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(300);

        this.add(topBottomSplit, BorderLayout.CENTER);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("report");
        keys.add("part");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setComboBoxLookupToColumn("report", new emcJComboBox(getReports()));

        emcJComboBox cbPart = new emcJComboBox();
        EMCComboBoxCellEditor cbPartCellEditor = new EMCComboBoxCellEditor(cbPart) {

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                emcJComboBox cbPart = (emcJComboBox) getComponent();
                ;

                String report = (String) drm.getFieldValueAt(row, "report");

                if (!Functions.checkBlank(report)) {
                    Object[] reportParts = getReportParts(report);

                    if (reportParts != null) {
                        cbPart.removeAllItems();

                        for (Object o : reportParts) {
                            cbPart.addItem(o.toString());
                        }

                        return cbPart;
                    }
                }

                cbPart.removeAllItems();
                for (BaseReportSectionEnum p : BaseReportSectionEnum.values()) {
                    cbPart.addItem(p.toString());
                }

                return cbPart;
            }
        };
        cbPartCellEditor.setClickCountToStart(2);
        table.setColumnCellEditor("part", cbPartCellEditor);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    /** Creates the bug panel.  */
    private emcJPanel createTextPanel() {
        emcJPanel reportPanel = new emcJPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder(drm.getColumnName("part")));
        reportPanel.add(new EMCHTMLEditorFormComponent(drm, "text"));

        return reportPanel;
    }

    private Object[] getReports() {
        List<String> reports = new ArrayList<String>();
        //Debtors
        reports.add(EnumReports.DEBTORS_CUSTOMER_INVOICE.toString());
        reports.add(EnumReports.DEBTORS_CREDIT_NOTE.toString());
        reports.add(EnumReports.DEBTORS_DELIVERY_NOTE.toString());

        return reports.toArray();
    }

    private Object[] getReportParts(String report) {
        Object[] reportParts = null;
        switch (EnumReports.fromString(report)) {
            case DEBTORS_CUSTOMER_INVOICE:
                reportParts = new Object[]{BaseReportSectionEnum.FOOTER};
                break;
            case DEBTORS_CREDIT_NOTE:
                reportParts = new Object[]{BaseReportSectionEnum.FOOTER};
                break;
            case DEBTORS_DELIVERY_NOTE:
                reportParts = new Object[]{BaseReportSectionEnum.FOOTER};
                break;
        }
        return reportParts;
    }
}
