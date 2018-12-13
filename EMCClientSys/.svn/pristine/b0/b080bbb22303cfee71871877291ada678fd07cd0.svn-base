/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.discountgroups;

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
 * @author riaan
 */
public class DiscountGroupsForm extends BaseInternalFrame {

    //Panel used on the form
    private emcJPanel pnlDiscountGroups = new emcJPanel();
    
    //Data relation manager used by the form
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of VATCodeForm */
    public DiscountGroupsForm(EMCUserData userData) {
        //Sets up the form
        super("Discount Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        //Sets up the data relation manager
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.POP.getId(), new emc.entity.pop.POPDiscountGroup(), userData),userData);
        
        this.setDataManager(dataRelation);
        
        //Add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("discountGroupId");
        dataRelation.setFormTextId2("description");
        
        tabDiscountGroups();
        initFrame();
    }
    
     private void tabDiscountGroups(){
        List keys = new ArrayList();
        keys.add("discountGroupId");
        keys.add("description");
//        keys.add("discountPercentage");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDiscountGroups.setLayout(new GridLayout(1,1));
        pnlDiscountGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Discount Groups", this.pnlDiscountGroups); 
        this.add(tabbedPanetop);
    }
}
