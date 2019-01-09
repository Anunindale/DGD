/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.costchange.reason;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.costchange.POPCostChangeReason;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CostChangeReasonForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public CostChangeReasonForm(EMCUserData userData) {
        super("Cost Change Reasons", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(
                    new emcGenericDataSourceUpdate(enumEMCModules.POP.getId(), new POPCostChangeReason(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("reasonId");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
        }
        iniFrame();
    }

    private void iniFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Reasons", contentPane());
        this.add(tabbed);
    }
    
    private emcJPanel contentPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout(1,1));
        List keys = new ArrayList();
        keys.add("reasonId");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tablescroll =new emcTablePanelUpdate(table);
        thePanel.add(tablescroll);
        return thePanel;
    }
}
