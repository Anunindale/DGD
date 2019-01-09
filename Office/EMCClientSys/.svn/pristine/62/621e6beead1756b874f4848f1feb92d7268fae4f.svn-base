/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.inventoryreferencetype;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class ReferenceTypeForm extends BaseInternalFrame {
    
    private emcJPanel pnlReferenceType = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public ReferenceTypeForm(EMCUserData userData) {
        super("Item Reference Type", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryReferenceType(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("type");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabReferenceType();
        tabbedPane.add("Item Reference Type", this.pnlReferenceType);
        this.add(tabbedPane);
    }
    
    private void tabReferenceType() {
        List keys = new ArrayList();
        keys.add("type");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        pnlReferenceType.setLayout(new GridLayout(1, 1));
        pnlReferenceType.add(tableScroll);
        this.setTablePanel(tableScroll);
    }

}
