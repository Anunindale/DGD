/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.dimensiongroupsetup;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.BorderFactory;

/**
 *
 * @author riaan
 */
public class DimensionGroupSetupForm extends BaseInternalFrame {

    private emcJPanel pnlDimensionGroupSetup = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public DimensionGroupSetupForm(EMCUserData userData){
        super("Dimension Group Setup", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryDimensionGroups.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.InventoryItemDimensionGroup(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemDimensionGroupId");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create WarehouseForm", userData);
            }
        }
        initFrame();
    }
    
    private void tabWarehouses(){
        List keys = new ArrayList();
        keys.add("itemDimensionGroupId");
        keys.add("description");
        keys.add("dim1Active");
        keys.add("dim3Active");
        keys.add("dim2Active");   
        keys.add("batchNumberActive");
        keys.add("serialNumberActive");
        keys.add("warehouseActive");
        keys.add("locationActive");
        keys.add("palletIdActive");
        
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys) {
            @Override
            public Class getColumnClass(int columnIndex) {
                if(columnIndex != 0 && columnIndex != 1) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
            
        };
        
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        
        dataRelation.setMainTableComponent(toptable);
        
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDimensionGroupSetup.setLayout(new GridLayout(1,1));
        pnlDimensionGroupSetup.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
   /* private emcJPanel controlTab() {
        emcYesNoComponent ynDimensionCost = new emcYesNoComponent(dataRelation, "dimensionCost");
        Component[][] comp = {{new emcJLabel("Cost on dimension level"), ynDimensionCost}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        thePanel.setBorder(BorderFactory.createTitledBorder("Control"));
        return thePanel;
    }*/
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabWarehouses();
        tabbedPanetop.add("Dimension Group Setup",this.pnlDimensionGroupSetup); 
        //tabbedPanetop.add("Control", controlTab());
        this.add(tabbedPanetop);
    }
}

