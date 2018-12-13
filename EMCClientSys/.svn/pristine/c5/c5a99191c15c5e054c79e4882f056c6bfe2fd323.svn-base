/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.returnreason;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.POPGoodsReturnReason;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class POPGoodsReturnReasonForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public POPGoodsReturnReasonForm(EMCUserData userData) {
        super("Return Reason", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new POPGoodsReturnReason(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("reasonCode");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {

        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Return Reason", tablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        this.add(thePanel);
    }

    private emcJPanel tablePane() {
        List keys = new ArrayList();
        keys.add("reasonCode");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }
}
