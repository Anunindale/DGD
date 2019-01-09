/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.journalgenerator.resources;

import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class InventoryTransferJournalFieldMapComboBox extends EMCFieldsMapComboBox {

    private EMCLookup fieldValue;
    private EMCUserData userData;

    public InventoryTransferJournalFieldMapComboBox(EMCLookup fieldValue, EMCUserData userData) {
        this.fieldValue = fieldValue;
        this.userData = userData;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        if (fieldValue != null) {
            if (!Functions.checkBlank(anObject)) {
                try {
                    List<String> popupFiels = new ArrayList<String>();
                    popupFiels.add((String) anObject);
                    EMCLookupPopup popup = new EMCLookupPopup((EMCEntityClass) Class.forName(lastEntityClassName).newInstance(), (String) anObject, popupFiels, userData);
                    fieldValue.setPopup(popup);
                } catch (Exception ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct popup.", userData);
                }
            } else {
                fieldValue.setPopup(null);
            }
        }
    }
}
