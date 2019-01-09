/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.posting.SBType;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class PurchasePostLinesDRM extends emcDataRelationManagerUpdate {

    private String formType;

    /** Creates a new instance of PurchasePostLinesDRM */
    public PurchasePostLinesDRM(emcGenericDataSourceUpdate tableDataSource, String formType, EMCUserData userData) {
        super(tableDataSource, userData);
        this.formType = formType;
    }

    /** Rows cannot be inserted */
    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {

    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        List x;
        switch (Index) {
            case 0:
                String transId = (String) super.getFieldValueAt(this.getLastRowAccessed(), "transactionNumber");
                String postMasterId = (String) super.getFieldValueAt(this.getLastRowAccessed(), "postMasterId");
                if (transId != null) {
                    x = new ArrayList();
                    EMCQuery query;
                    if (formType.equals(DocumentTypes.RETURN_GOODS.toString())) {
                        query = getQuery(InventoryRemoveSerialBatch.class.getName(), transId, postMasterId, formUserData);
                    } else {
                        query = getQuery(InventorySerialBatch.class.getName(), transId, postMasterId, formUserData);
                    }
                    x.add(0, query.toString());
                    x.add(1, query.getCountQuery());
                    x.add(2, super.getFieldValueAt(this.getLastRowAccessed(), "itemPrimaryReference"));
                    x.add(3, super.getFieldValueAt(this.getLastRowAccessed(), "itemDesc"));
                    x.add(4, super.getFieldValueAt(this.getLastRowAccessed(), "quantity"));
                    x.add(5, transId);
                    x.add(6, postMasterId);
                    x.add(7, SBType.PURCHASEORDER.toString());
                    x.add(8, formType);
                    x.add(9, super.getHeaderTable().getFieldValueAt(super.getHeaderTable().getLastRowAccessed(), "documentNumber"));
                    x.add(10, super.getFieldValueAt(this.getLastRowAccessed(), "dimension1Id"));
                    x.add(11, super.getFieldValueAt(this.getLastRowAccessed(), "dimension2Id"));
                    x.add(12, super.getFieldValueAt(this.getLastRowAccessed(), "dimension3Id"));
                    x.add(13, super.getFieldValueAt(this.getLastRowAccessed(), "warehouse"));
                    x.add(14, super.getFieldValueAt(this.getLastRowAccessed(), "itemId"));
                    formUserData.setUserData(x);
                }
                break;
            default:
                break;
        }
        return formUserData;
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (columnIndex.equals("numberLabels") && aValue == null) {
            super.setFieldValueAt(rowIndex, columnIndex, Math.ceil((Double) super.getFieldValueAt(rowIndex, "quantity")));
        } else {
            super.setFieldValueAt(rowIndex, columnIndex, aValue);
        }
    }

    private EMCQuery getQuery(String name, String transId, String postMasterId, EMCUserData formUserData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, name);
        query.addAnd("transId", transId);
        if (formType.equals(DocumentTypes.RETURN_GOODS.toString())) {
            emcDataRelationManagerUpdate headerDRM = super.getHeaderTable();
            String docNum = (String) headerDRM.getFieldValueAt(headerDRM.getLastRowAccessed(), "documentNumber");
            query.addTableAnd(POPPurchasePostMaster.class.getName(), "masterId", name, "postMasterId");
            query.addAnd("documentNumber", docNum, POPPurchasePostMaster.class.getName());
        } else {
            query.addAnd("masterId", postMasterId);
        }
        query.addAnd("type", SBType.PURCHASEORDER.toString());
        query.addAnd("companyId", formUserData.getCompanyId());
        query.setSelectDistinctAll(true);
        return query;
    }
}
