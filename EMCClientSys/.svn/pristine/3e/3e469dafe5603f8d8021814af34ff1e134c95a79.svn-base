/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package emc.forms.pop.display.deliveryterms;

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
public class DeliveryTermsForm extends BaseInternalFrame {

    private emcJPanel pnlDeliveryTerms = new emcJPanel();
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of DeliveryTermsForm */
    public DeliveryTermsForm(EMCUserData userData) {
        super("Delivery Terms", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                            enumEMCModules.POP.getId(), new emc.entity.pop.POPDeliveryTerms(), userData),userData);

            this.setDataManager(dataRelation);

            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("deliveryTermsId");
            dataRelation.setFormTextId2("description");

            tabDeliveryTerms();
            initFrame();
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create form", userData);
            }
        }
    }
    
     private void tabDeliveryTerms(){
        List keys = new ArrayList();
        keys.add("deliveryTermsId");
        keys.add("description");
       // keys.add("deliveryCharge");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDeliveryTerms.setLayout(new GridLayout(1,1));
        pnlDeliveryTerms.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Delivery Groups", this.pnlDeliveryTerms); 
        this.add(tabbedPanetop);
    }
}
