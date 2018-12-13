/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.dimension2groupsetup;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class Dimension2GroupSetupForm  extends BaseInternalFrame {
    
    private emcJPanel sizes = new emcJPanel();
    private emcJPanel pnlSizeGroup = new emcJPanel();
    private emcJLabel lblItem = new emcJLabel("Size Group");
    private EMCControlLookupComponent lkpSizeGroup;
    private emcJLabel lblSizeGroupDesc = new emcJLabel("Description");
    private emcJTextField txtSizeGroupDesc = new emcJTextField();
    
    private EMCLookupJTableComponent lkpDimension;
    private EMCLookupTableCellEditor dimensionEditor;
    
    private EMCUserData copyUD;
    
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;
    
    public Dimension2GroupSetupForm(EMCUserData userData){
        super("Size Group Setup", true, true,true, true,userData);
        copyUD = userData.copyUserData();
        this.setBounds(20, 20, 550, 290);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryConfig.html"));
        try{
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.datasource.InventoryDimension2GroupSetupDS(),userData),userData);
            
            lkpSizeGroup = new EMCControlLookupComponent(new emc.menus.inventory.menuitems.display.Dimension2Groups(), dataRelation, "dimensionGroupId", txtSizeGroupDesc, "description", InventoryDimension2GroupSetup.class.getName());
            dataRelation.setLookup(lkpSizeGroup);
            
            List sizeGroupKeys = new ArrayList();
            sizeGroupKeys.add("dimensionGroupId");
            sizeGroupKeys.add("description");
            
            EMCLookupPopup sizeGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.InventoryDimension2Group(),
                "dimensionGroupId",sizeGroupKeys, copyUD);
            lkpSizeGroup.setPopup(sizeGroupPopup);
            
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2GroupSetup.class.getName());
            lkpSizeGroup.setFormQuery(query);
            
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionId");
            dataRelation.setFormTextId2("dimensionGroupId");
        
        }catch (Exception e){
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Size Group Setup Form", userData);
            }
        }
        
        setupComponents();
        initFrame();
        //dataRelation.setUserData(userData);
    }
    
    private void setupComponents(){
        txtSizeGroupDesc.setEditable(false);
        
        List dimensionKeys = new ArrayList<String>();
        dimensionKeys.add("dimensionId");
        dimensionKeys.add("description");
        
        lkpDimension = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension2());
        EMCLookupPopup dimensionPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension2(),
                                                "dimensionId", dimensionKeys, copyUD);
        lkpDimension.setPopup(dimensionPopup);
        dimensionEditor = new EMCLookupTableCellEditor(lkpDimension);
    }
    
    private void createSizeGroupPanel(){
        pnlSizeGroup.setBorder(javax.swing.BorderFactory.createTitledBorder("Size Group"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0,y,0.1,GridBagConstraints.FIRST_LINE_START);
        pnlSizeGroup.add(this.lblItem,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,1,y,0.1);
        pnlSizeGroup.add(this.lkpSizeGroup,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        pnlSizeGroup.add(this.lblSizeGroupDesc,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        pnlSizeGroup.add(this.txtSizeGroupDesc,localg); 
    }
    
    private void tabSizes(){
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        keys.add("sortCode");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 1) {
                    return false;
                }
                return true;
            }

        };
        
        dataRelation.setMainTableComponent(toptable);
        toptable.setLookupCellEditor(0, dimensionEditor);
        
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        sizes.setLayout(new GridBagLayout());
        createSizeGroupPanel();
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        sizes.add(pnlSizeGroup,localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        sizes.add(topscroll,localg);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabSizes();
        tabbedPanetop.add("Size Group Setup",this.sizes); 
        this.add(tabbedPanetop);
    }
}
