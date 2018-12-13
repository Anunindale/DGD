/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.journalgenerator.resources;

import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.app.components.tables.EMCTablesMapComboBox;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventorySummary;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class InventoryTransferJournalTableMapComboBox extends EMCTablesMapComboBox {

    private EMCFieldsMapComboBox fieldComboBox;
    private EMCUserData userData;

    public InventoryTransferJournalTableMapComboBox(EMCFieldsMapComboBox fieldComboBox, EMCUserData userData) {
        super();
        this.fieldComboBox = fieldComboBox;
        this.userData = userData;
        this.addEmpty();
        this.addItem(InventoryItemMaster.class.getName());
        this.addItem(InventorySummary.class.getName());
        fieldComboBox.setPreferredSize(this.getPreferredSize());
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        if (fieldComboBox != null) {
            fieldComboBox.updateFields((String) anObject, userData);
        }
    }
}
