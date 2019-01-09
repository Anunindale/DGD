/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog.resources;

import emc.app.components.emcpicker.EMCPicker;
import emc.app.components.emcpicker.EMCPickerButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import java.awt.event.ActionEvent;

/**
 *
 * @author wikus
 */
public class RecordInfoLookup extends EMCPicker {

    private RecordInfoPopup popup;
    private EMCUserData userData;

    public RecordInfoLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        this.userData = userData;
        popup = new RecordInfoPopup(this, dataRelation, fieldKey, userData);
        this.setPopup(popup);
    }

    @Override
    public void setValue(Object value) {
        if (!Functions.checkBlank(value)) {
            popup.setPopupRecord(new EMCXMLHandler().XMLToObject((String) value, userData));
        }
    }

    @Override
    protected EMCPickerButton makeButton() {
        EMCPickerButton retButton = new EMCPickerButton(this) {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                RecordInfoLookup.this.popup.show();
            }
        };
        return retButton;
    }
}
