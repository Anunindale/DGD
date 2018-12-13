/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.dblog.resources;

import emc.app.components.emctable.editors.EMCTableCellEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.framework.EMCUserData;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author wikus
 */
public class RecordInfoCellEditor extends EMCTableCellEditor{
    
    private RecordInfoLookup lookup ;

    public RecordInfoCellEditor(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        lookup = new RecordInfoLookup(dataRelation, fieldKey, userData);
    }

    public Object getCellEditorValue() {
        return null;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        lookup.setValue(value);
        return lookup;
    }

}
