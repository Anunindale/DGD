/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemranges;

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
public class ItemRangesForm extends BaseInternalFrame {
    
    private emcJPanel pnlItemRanges = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public ItemRangesForm(EMCUserData userData){
        super("Item Ranges", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryItemRanges.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.InventoryItemRange(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemRangeId");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabItemRanges(){
        List keys = new ArrayList();
        keys.add("itemRangeId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlItemRanges.setLayout(new GridLayout(1,1));
        pnlItemRanges.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabItemRanges();
        tabbedPanetop.add("Item Ranges",this.pnlItemRanges); 
        this.add(tabbedPanetop);
    }
}
