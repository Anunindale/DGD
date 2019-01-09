/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.colourdisignmaster;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryColourDesignForm extends BaseInternalFrame {
    private emcDataRelationManagerUpdate dataRelation;
    private EMCLookupJTableComponent uomLookup;
    private EMCUserData copyUd;
    
    public InventoryColourDesignForm(EMCUserData userData) {
        super("Colour Design Master", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            copyUd = userData.copyUserData();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("designNo");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        createLookups();
        initFrame();
    }
    private void createLookups(){
         uomLookup = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure());
         List keys = new ArrayList();
         keys.add("unit");
         keys.add("description");
         EMCLookupPopup uomPopup = new EMCLookupPopup(emc.enums.modules.enumEMCModules.BASE.getId(),new emc.entity.base.BaseUnitsOfMeasure(), "unit", keys, copyUd);
         uomLookup.setPopup(uomPopup);
    }
    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Colour Design", mainPanel());
        this.add(tabbed);
    }
    
    private emcJPanel mainPanel() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridLayout(1,1));
        List keys = new ArrayList();
        keys.add("designNo");
        keys.add("description");
        keys.add("thresholdQty");
        keys.add("uom");
        keys.add("receivedQty");
        keys.add("claimed");
        keys.add("designPerc");
                
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        table.setLookupCellEditor(3, new EMCLookupTableCellEditor(uomLookup));
        emcTablePanelUpdate tablescroll = new emcTablePanelUpdate(table);
        dataRelation.setMainTableComponent(table);
        thePanel.add(tablescroll);
        this.setTablePanel(tablescroll);
        return thePanel;
    }
}

