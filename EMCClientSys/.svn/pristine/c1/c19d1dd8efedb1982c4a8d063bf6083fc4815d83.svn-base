/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.dimensions;

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
public class Dimension2Form extends BaseInternalFrame {
    
    private emcJPanel pnlDimension = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public Dimension2Form(EMCUserData userData){
        super("Sizes", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryCostingGroups.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.InventoryDimension2(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionId");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabDimension(){
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        keys.add("sortCode");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDimension.setLayout(new GridLayout(1,1));
        pnlDimension.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabDimension();
        tabbedPanetop.add("Sizes",this.pnlDimension); 
        this.add(tabbedPanetop);
    }
}
