/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemdimensionsetup;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcHelpFile;
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
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.scrollabledesktop.BaseInternalFrame;import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.enums.inventory.dimensions.EnumDimensions;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.itemdimensionsetup.resources.RemoveInActive;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class ItemDimension3SetupForm extends BaseInternalFrame {
    
    //Keeps track of whether changes were made, so that a message can be logged when the form is closed
    private boolean changesMade = false;
    
    private emcJPanel dimensions = new emcJPanel();
    private emcJPanel theMainPanel = new emcJPanel();
    private emcJPanel pnlItem = new emcJPanel();
    private emcJLabel lblItem = new emcJLabel("Item");
    private emcJPanel pnlButtons = new emcJPanel();
    private EMCControlLookupComponent lkpItem;
    private emcJLabel lblItemDesc = new emcJLabel("Description");
    private emcJTextField txtItemDesc = new emcJTextField();
    
    private EMCLookupJTableComponent lkpDimension;
    private EMCLookupTableCellEditor dimensionEditor;
            
    private EMCUserData copyUD;
    
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;
    
    public ItemDimension3SetupForm(EMCUserData userData){
        super("Colours", true, true,true, true,userData);
        copyUD = userData.copyUserDataAndDataList();
        this.setBounds(20, 20, 600, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventoryConfig.html"));
        try{

            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupFormDS(),userData),userData) {

                @Override
                public void updatePersist(int rowIndex) {
                    if (isRowUpdated()) {
                        ItemDimension3SetupForm.this.changesMade = true;
                    }
                    
                    super.updatePersist(rowIndex);
                }

                @Override
                public void deleteRowCache(int rowIndex) {
                    if ((Long)getLastFieldValueAt("recordID") != 0) {
                        //Check if dimension is in use
                        EMCUserData userData = getUserData();

                        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.CHECK_DIMENSION_IN_USE.toString());

                        List toSend = new ArrayList();
                        toSend.add(getLastFieldValueAt("dimensionId"));
                        toSend.add(getLastFieldValueAt("itemId"));
                        toSend.add(EnumDimensions.DIMENSION3.toString());

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
                            if (EMCDialogFactory.createQuestionDialog(getTheForm(), "Delete dimension?", "There are open orders making use of the selected dimension.  Are you sure you want to delete it?") != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                    }

                    super.deleteRowCache(rowIndex);
                    ItemDimension3SetupForm.this.changesMade = true;
                }
                
            };
            
            lkpItem = EMCItemLookupFactory.createItemControllLookup(dataRelation, txtItemDesc, InventoryItemDimension3Setup.class.getName(), copyUD);
            
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemId");
            dataRelation.setFormTextId2("dimensionId");
        
        }catch (Exception e){
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Dimension 1 Form", userData);
            }
        }
        
        setupComponents();
        initFrame();
        //dataRelation.setUserData(userData);
    }
    
    private void setupComponents(){
        txtItemDesc.setEditable(false);
       
        List dimensionKeys = new ArrayList<String>();
        dimensionKeys.add("dimensionId");
        dimensionKeys.add("description");
        
        lkpDimension = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension3());
        EMCLookupPopup dimensionPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3(),
                                                "dimensionId", dimensionKeys, copyUD);
        lkpDimension.setPopup(dimensionPopup);
        dimensionEditor = new EMCLookupTableCellEditor(lkpDimension);
    }
    
    private void createItemPanel(){
        pnlItem.setBorder(javax.swing.BorderFactory.createTitledBorder("Item"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0,y,0.1,GridBagConstraints.FIRST_LINE_START);
        pnlItem.add(this.lblItem,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,1,y,0.1);
        pnlItem.add(this.lkpItem,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        pnlItem.add(this.lblItemDesc,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        pnlItem.add(this.txtItemDesc,localg); 
    }
    
    private void tabDimension(){
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        keys.add("active");
  
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 1) {
                    return false;
                } else {
                    return true;
                }
            }
            
        };
        
        toptable.setLookupCellEditor(0, dimensionEditor);
        
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        dimensions.setLayout(new GridBagLayout());
        createItemPanel();
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        dimensions.add(pnlItem,localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        dimensions.add(topscroll,localg);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabDimension();
        tabButtons();
        theMainPanel.setLayout(new BorderLayout());
        theMainPanel.add(dimensions, BorderLayout.CENTER);
        theMainPanel.add(pnlButtons, BorderLayout.EAST);
        tabbedPanetop.add("Colours",theMainPanel); 
        this.add(tabbedPanetop);
    }
    private void tabButtons() {
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new RemoveInActive(this.dataRelation, InventoryItemDimension3Setup.class.getName()), gbc);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    @Override
    public void dispose() {
        if (changesMade) {
            changesMade = false;
            Logger.getLogger("emc").log(Level.INFO, "Remember to regenerate combinations.", getUserData());
        }
        
        super.dispose();
    }
}
