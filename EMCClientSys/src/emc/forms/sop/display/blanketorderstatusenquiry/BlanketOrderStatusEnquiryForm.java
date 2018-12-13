/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.blanketorderstatusenquiry;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.sop.datasource.blanketorderstatusenquiry.SOPBlanketOrderStatusEnquiryDS;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class BlanketOrderStatusEnquiryForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of BlanketOrderStatusEnquiryForm. */
    public BlanketOrderStatusEnquiryForm(EMCUserData userData) {
        super("Blanket Order Status Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 300);

        this.drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new SOPBlanketOrderStatusEnquiryDS(), userData), userData);

        drm.setFormTextId1("salesOrderNo");
        drm.setFormTextId2("itemReference");

        this.setDataManager(this.drm);
        this.drm.setTheForm(this);
        
        initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel tablePanel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("wipQuantity");
        keys.add("packedQuantity");
        keys.add("quantityToPack");
        keys.add("calledOffQuantity");
        keys.add("balance");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //Editing not allowed.
                return false;
            }
        };
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        tablePanel.add(tableScroll);

        return tablePanel;
    }
}
