/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting.postserialbatch;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wikus
 */
public class ReturnMultiValuePopup extends EMCMultiValuePopup {

    Map<String, String> keysMap;

    public ReturnMultiValuePopup(EMCEntityClass entityClassInstance, String lookupFieldKey, List<String> keys, emcDataRelationManagerUpdate formDRM, Map<String, String> formKeys, EMCUserData userData) {
        super(entityClassInstance, lookupFieldKey, keys, formDRM, new HashMap<String, String>(), userData);
        this.keysMap = formKeys;
    }

    @Override
    public void selectionComplete() {
        super.selectionComplete();
        int formLastRow = getFormDRM().getLastRowAccessed();
        Set<String> keys = keysMap.keySet();
        for (String key : keys) {
            if (key.equals("quantity")) {
                String fieldKey = "physicalAvailable";
                String location = (String) getDataRelation().getFieldValueAt(this.getLastSelectedRow(), keysMap.get("location"));
                if ("QC".equals(location)) {
                    fieldKey = "qcAvailable";
                } else if ("REC".equals(location)) {
                    fieldKey = "recAvailable";
                }
                double selectedQty = (Double) getDataRelation().getFieldValueAt(this.getLastSelectedRow(), fieldKey);
                double totalQty = (Double) getFormDRM().getUserData().getUserData(4);
                if (selectedQty > totalQty) {
                    selectedQty = totalQty;
                }
                getFormDRM().setFieldValueAt(formLastRow, key, selectedQty);
            } else {
                getFormDRM().setFieldValueAt(formLastRow, key, getDataRelation().getFieldValueAt(this.getLastSelectedRow(), keysMap.get(key)));
            }
        }

    }
}
