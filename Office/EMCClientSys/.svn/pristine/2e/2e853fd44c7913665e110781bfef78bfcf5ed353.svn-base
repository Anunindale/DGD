/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.location;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Warehouse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryLocationForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public InventoryLocationForm(EMCUserData userData) {
        super("Location", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryLocation(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("locationId");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        List keys = new ArrayList();
        keys.add("locationId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        emcJTextField txtDesc = new emcJTextField();
        txtDesc.setEditable(false);
        EMCControlLookupComponent lkpWarehouse = new EMCControlLookupComponent(new Warehouse(), dataRelation, "warehouseId", txtDesc, "description", InventoryLocation.class.getName());
        dataRelation.setLookup(lkpWarehouse);

        EMCLookupPopup warehousePop = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        lkpWarehouse.setPopup(warehousePop);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
        query.addOrderBy("locationId");

        lkpWarehouse.setFormQuery(query);
        this.add(new EMCControlLookupPanel("Warehouse", "Warehouse", lkpWarehouse, "description", txtDesc, tableScroll, "Location"));
    }
}
