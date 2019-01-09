/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.brandgroups;

import emc.app.components.emcHelpFile;
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
public class BrandGroupsForm extends BaseInternalFrame {
    
     private emcJPanel pnlBrandGroups = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public BrandGroupsForm(EMCUserData userData){
        super("Brand Groups", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryBrandGroups.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.InventoryBrandGroup(),userData),userData);
            this.setDataManager(dataRelation);
            
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("brandGroupId");
            dataRelation.setFormTextId2("brandGroupName");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabBrandGroups(){
        List keys = new ArrayList();
        keys.add("brandGroupId");
        keys.add("brandGroupName");
        keys.add("brandGroupDescription");
        //keys.add("royaltyPercentage");
        
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlBrandGroups.setLayout(new GridLayout(1,1));
        pnlBrandGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabBrandGroups();
        tabbedPanetop.add("Brand Groups",this.pnlBrandGroups); 
        this.add(tabbedPanetop);
    }
}
