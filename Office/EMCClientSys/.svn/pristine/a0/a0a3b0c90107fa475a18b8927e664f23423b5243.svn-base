/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemserial;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class ItemSerialForm extends BaseInternalFrame {

    private emcJPanel pnlMain = new emcJPanel();
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of ItemSerialForm */
    public ItemSerialForm(EMCUserData userData) {
        super("Item Serial", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                            enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemSerial(), userData),userData);

            this.setDataManager(dataRelation);

            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("serialId");
            dataRelation.setFormTextId2("description");

            tabMain();
            initFrame();
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create form", userData);
            }
        }
    }
    
     private void tabMain(){
        List keys = new ArrayList();
        keys.add("serialId");
        keys.add("description");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlMain.setLayout(new GridLayout(1,1));
        pnlMain.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Item Serial", this.pnlMain); 
        this.add(tabbedPanetop);
    }
}
