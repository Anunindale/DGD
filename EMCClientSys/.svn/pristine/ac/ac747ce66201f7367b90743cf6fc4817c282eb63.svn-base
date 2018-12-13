/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.app.numbersequence;

import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.app.components.emcJComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.datatypes.EMCDataType;
import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author riaan
 */
public class NumberSequenceDRM extends emcDataRelationManagerUpdate {

    private emcJComboBox cbPerField;
    private EMCLookupFormComponent lkpPerValue;
    private boolean setModule = true;

    /** Creates a new instance of NumberSequenceDRM */
    public NumberSequenceDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void doRelation(int rowIndex) {
        if (getMainTableComponent() != null) {
            EMCFieldsMapComboBox fieldBox = (EMCFieldsMapComboBox) ((DefaultCellEditor) (this.getMainTableComponent()).getCellEditor(rowIndex, 4)).getComponent();
            String entityClass = (String) this.getFieldValueAt(rowIndex, "refTable");

            fieldBox.updateFields(entityClass, getUserData());
        }

        String sampleText = "";
        NumberSequenceForm theForm = null;

        if (this.getMainTableComponent() != null && getFieldValueAt(rowIndex, "refTable") != null) {

            String tableName = getFieldValueAt(rowIndex, "refTable").toString();
            //List<String> fields = new ArrayList<String>();
            List<String> relationFields = new ArrayList<String>();
            relationFields.add("");
            try {
                Class theTableClass = Class.forName(tableName);

                EMCTable table = (EMCTable) theTableClass.newInstance();

                List<Field> fields = table.getAllTableFields();

                HashMap<String, EMCDataType> fieldMapper = table.getFieldDataTypeMapper();

                EMCDataType curDataType = null;

                for (Field field : fields) {
                    relationFields.add(field.getName());
                }

            } catch (Exception ex) {
                if (EMCDebug.getDebug()) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to get list of fields", getUserData());
                }
            }
            if (getFieldValueAt(rowIndex, "refField") != null) {
                if (cbPerField != null) {
                    cbPerField.setModel(new DefaultComboBoxModel(relationFields.toArray()));

                }
            }
            if (!Functions.checkBlank(getFieldValueAt(rowIndex, "perField"))) {
                if (lkpPerValue != null) {
                    setPopUpForPerValueField();
                }
            }
            //emcJComboBox fieldBox = (emcJComboBox) ((DefaultCellEditor) (this.getMainTableComponent()).getCellEditor(rowIndex, 3)).getComponent();

            //fieldBox.setModel(new DefaultComboBoxModel(fields.toArray()));

            theForm = (NumberSequenceForm) this.getTheForm();

            Object prefix = super.getFieldValueAt(rowIndex, "prefixString");
            Object minNumber = super.getFieldValueAt(rowIndex, "minNumber");
            Object suffix = super.getFieldValueAt(rowIndex, "suffixString");
            Object maxNumber = super.getFieldValueAt(rowIndex, "maxNumber");

            String zeroes = "";

            if (minNumber != null && maxNumber != null) {
                int zeroesToAdd = maxNumber.toString().length() - minNumber.toString().length();

                for (int i = 0; i < zeroesToAdd; i++) {
                    zeroes += "0";
                }
            }
            sampleText = (prefix == null ? "" : prefix.toString()) + zeroes + (minNumber == null ? "" : minNumber.toString()) + (suffix == null ? "" : suffix.toString());
        }

        if (theForm != null) {
            theForm.setSampleText(sampleText);
        }
        super.doRelation(rowIndex);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (setModule) {
            setModule = false;
            if (Functions.checkBlank(getFieldValueAt(rowIndex, "moduleId"))) {
                String module = ((NumberSequenceForm) this.getTheForm()).getModuleId().toString();
                if (!enumEMCModules.BASE.toString().equals(module)) {
                    setFieldValueAt(rowIndex, "moduleId", module);
                }
            }
            setModule = true;
        }
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        if (columnIndex.equals("refTable")) {
            super.setFieldValueAt(rowIndex, "refField", null);
            super.setFieldValueAt(rowIndex, "perField", null);
            super.setFieldValueAt(rowIndex, "perValue", null);
        }
        if (columnIndex.equals("refField")) {
            super.setFieldValueAt(rowIndex, "perField", null);
            super.setFieldValueAt(rowIndex, "perValue", null);
        }
        if (columnIndex.equals("perField")) {
            super.setFieldValueAt(rowIndex, "perValue", null);
        }

    }

    private void setPopUpForPerValueField() {
        if (this.lkpPerValue != null) {
            //create instance of entityClass
            try {
                Class theTableClass = Class.forName(getFieldValueAt(this.getLastRowAccessed(), "refTable").toString());
                EMCEntityClass table = (EMCEntityClass) theTableClass.newInstance();
                HashMap<String, EMCDataType> fieldList = table.getFieldDataTypeMapper();
                EMCDataType curDataType = fieldList.get((String) this.getFieldValueAt(this.getLastRowAccessed(), "perField"));

                if (curDataType != null && curDataType.getRelatedField() != null && curDataType.getRelatedTable() != null) {
                    List keys = new ArrayList();
                    Class relatedClass = Class.forName(curDataType.getRelatedTable());
                    Object relatedEntity = relatedClass.newInstance();
                    keys.add(curDataType.getRelatedField());
                    EMCLookupPopup unknownPopUp = new EMCLookupPopup(((EMCEntityClass) relatedEntity).getEmcModule().getId(), (EMCEntityClass) relatedEntity,
                            curDataType.getRelatedField(), keys, this.getUserData().copyUserData());
                    lkpPerValue.setPopup(unknownPopUp);

                } else {
                    List keys = new ArrayList();
                    keys.add(this.getFieldValueAt(this.getLastRowAccessed(), "perField"));
                    EMCLookupPopup unknownPopUp = new EMCLookupPopup(table, (String) this.getFieldValueAt(this.getLastRowAccessed(), "perField"), keys, this.getUserData().copyUserData());
                    lkpPerValue.setPopup(unknownPopUp);
                }
            } catch (Exception e) {
                if (EMCDebug.getDebug()) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create perValue popup for Lookup", getUserData());
                }
            }
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {

        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object numberId;
        Object description;
        List x;
        switch (Index) {
            case 0:
                numberId = super.getFieldValueAt(this.getLastRowAccessed(), "numberSequenceId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                if (numberId != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class);
                    query.addAnd("sequenceNumberId", numberId);
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, (String) description);
                    x.add(3, numberId.toString());
                    formUserData.setUserData(x);
                }
                break;
            default:
                break;
        }
        return formUserData;
    }

    @Override
    public void updatePersist(int rowIndex) {
        boolean insert = (Long) this.getFieldValueAt(this.getLastRowAccessed(), "recordID") == 0;

        super.updatePersist(rowIndex);

        if (!insert) {
            this.refreshRecord(this.getLastRowAccessed());
        }
    }

    public emcJComboBox getCbPerField() {
        return cbPerField;
    }

    public void setCbPerField(emcJComboBox cbPerField) {
        this.cbPerField = cbPerField;
    }

    public EMCLookupFormComponent getLkpPerValue() {
        return lkpPerValue;
    }

    public void setLkpPerValue(EMCLookupFormComponent lkpPerValue) {
        this.lkpPerValue = lkpPerValue;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }
        super.insertRowCache(rowIndex, addRowAfter);
        if (addRowAfter) {
            rowIndex++;
        }
        String module = ((NumberSequenceForm) this.getTheForm()).getModuleId().toString();
        if (!enumEMCModules.BASE.toString().equals(module)) {
            setFieldValueAt(rowIndex, "moduleId", module);
        }
    }
}
