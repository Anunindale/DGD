/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog.resources;

import emc.app.components.emctable.renderers.EMCCellRenderer;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;

/**
 *
 * @author wikus
 */
public class RecordInfoRenderer extends EMCCellRenderer {

    @Override
    public void setValue(Object val) {
        if (!Functions.checkBlank(val)) {
            try {
                EMCEntityClass record = (EMCEntityClass) new EMCXMLHandler().XMLToObject((String) val, new EMCUserData());
                super.setValue(record.getRecordID());
            } catch (Exception e) {
                super.setValue(val);
            }
        } else {
            super.setValue(val);
        }

    }
}
