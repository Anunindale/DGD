/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.deliverymodes;

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
public class DeliveryModesForm extends BaseInternalFrame {

    private emcJPanel pnlDeliveryModes = new emcJPanel();
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of DeliveryModesForm */
    public DeliveryModesForm(EMCUserData userData) {
        super("Delivery Modes", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                            enumEMCModules.POP.getId(), new emc.entity.pop.POPDeliveryModes(), userData),userData);

            this.setDataManager(dataRelation);


            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("deliveryModeId");
            dataRelation.setFormTextId2("description");

            tabDeliveryModes();
            initFrame();
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create form", userData);
            }
        }
    }
    
     private void tabDeliveryModes(){
        List keys = new ArrayList();
        keys.add("deliveryModeId");
        keys.add("description");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDeliveryModes.setLayout(new GridLayout(1,1));
        pnlDeliveryModes.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Delivery Modes", this.pnlDeliveryModes); 
        this.add(tabbedPanetop);
    }
}
