/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.parameters.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.tables.EMCFormFieldsMapComboBox;
import emc.app.components.tables.EMCFormTablesMapComboBox;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class SOPParametersTableMapComboBox extends EMCFormTablesMapComboBox {

    private EMCFormFieldsMapComboBox fieldComboBox;
    private EMCFormFieldsMapComboBox descriptionComboBox;

    public SOPParametersTableMapComboBox(emcDataRelationManagerUpdate dataRelation, String columnIndex, EMCFormFieldsMapComboBox fieldComboBox, EMCFormFieldsMapComboBox descriptionComboBox) {
        super(dataRelation, columnIndex);
        this.fieldComboBox = fieldComboBox;
        this.descriptionComboBox = descriptionComboBox;
        this.addEmpty();
        this.addItem(SOPCustomers.class.getName());
        this.addItem(InventoryItemMaster.class.getName());
        updateDocumentContents();
        fieldComboBox.setPreferredSize(this.getPreferredSize());
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        if (fieldComboBox != null) {
            fieldComboBox.updateFields((String) anObject, super.dataRelation.getUserData());
            if (!Functions.checkBlank(anObject)) fieldComboBox.updateDocumentContents();
        }
        if (descriptionComboBox != null) {
            descriptionComboBox.updateFields((String) anObject, super.dataRelation.getUserData());
            if (!Functions.checkBlank(anObject)) descriptionComboBox.updateDocumentContents();
        }
    }
}
