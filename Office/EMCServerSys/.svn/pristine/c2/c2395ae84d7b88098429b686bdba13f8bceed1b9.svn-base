package emc.bus.inventory.transactions.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.transactions.datasource.InventoryTransactionsDS;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.TransEnum;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryTransactionDSBean extends EMCDataSourceBean implements InventoryTransactionDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;

    /** Creates a new instance of InventoryTransactionDSBean */
    public InventoryTransactionDSBean() {
        this.setDataSourceClassName(InventoryTransactionsDS.class.getName());
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Collection theData = util.executeGeneralSelectQueryLimit(getQuery(theTable, 0, userData), userData, start, end);
        InventoryTransactions t;
        InventoryTransactionsDS trans;
        InventoryDimensionTable inventDim;
        List transData = new ArrayList();
        Object[] oArray;
        for (Object transObject : theData) {
            if (transObject instanceof Object[]) {
                oArray = (Object[]) transObject;
                t = (InventoryTransactions) oArray[0];
                inventDim = (InventoryDimensionTable) oArray[1];
                trans = (InventoryTransactionsDS) convertSuperToDataSource(t, userData);
                if (inventDim != null) {
                    trans.setColour(inventDim.getDimension3Id());
                    trans.setConfig(inventDim.getDimension1Id());
                    trans.setWarehouse(inventDim.getWarehouseId());
                    trans.setSize(inventDim.getDimension2Id());
                    trans.setBatch(inventDim.getBatchId());
                    trans.setSerialNo(inventDim.getItemSerialId());
                    trans.setLocation(inventDim.getLocationId());
                    trans.setPallet(inventDim.getPalletId());
                    String[] itemRef = referenceBean.checkItemReference(trans.getItemId(), userData);
                    trans.setItemRef(itemRef == null ? trans.getItemId() : (isBlank(itemRef[1]) ? itemRef[0] : itemRef[1]));
                }
                if (userData.getUserData().size() > 2 && userData.getUserData(2).equals(TransEnum.ADD_EXTRA.toString())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
                    query.addAnd("itemId", trans.getItemId(), InventoryItemMaster.class.getName());
                    InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

                    String toAdd = "";
                    if (dimGroup.getDim1Active()) toAdd += "config~";
                    if (dimGroup.getDim2Active()) toAdd += "size~";
                    if (dimGroup.getDim3Active()) toAdd += "colour~";
                    if (dimGroup.getWarehouseActive()) toAdd += "warehouse~";
                    if (dimGroup.getBatchNumberActive()) toAdd += "batch~";
                    if (dimGroup.getSerialNumberActive()) toAdd += "serialNo~";
                    if (dimGroup.getLocationActive()) toAdd += "location~";
                    if (dimGroup.getPalletIdActive()) toAdd += "pallet~";

                    if (toAdd.substring(toAdd.length() - 1, toAdd.length()).equals("~")) {
                        toAdd = toAdd.substring(0, toAdd.length() - 1);
                    }
                    trans.setDisplayColumns(toAdd);
                }
            } else {
                trans = (InventoryTransactionsDS) populateDataSourceFields((EMCEntityClass) convertSuperToDataSource(transObject, userData), userData);
            }
            transData.add(trans);
        }
        return transData;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryTransactionsDS trans = (InventoryTransactionsDS) dataSourceInstance;
        InventoryDimensionTable inventDim = (InventoryDimensionTable) util.findPersisted(InventoryDimensionTable.class, trans.getItemDimId(), userData);
        if (inventDim != null) {
            trans.setColour(inventDim.getDimension3Id());
            trans.setConfig(inventDim.getDimension1Id());
            trans.setWarehouse(inventDim.getWarehouseId());
            trans.setSize(inventDim.getDimension2Id());
            trans.setBatch(inventDim.getBatchId());
            trans.setSerialNo(inventDim.getItemSerialId());
            trans.setLocation(inventDim.getLocationId());
            trans.setPallet(inventDim.getPalletId());
            String[] itemRef = referenceBean.checkItemReference(trans.getItemId(), userData);
            trans.setItemRef(itemRef == null ? trans.getItemId() : (isBlank(itemRef[1]) ? itemRef[0] : itemRef[1]));
        }
        if (userData.getUserData().size() > 2 && userData.getUserData(2).equals(TransEnum.ADD_EXTRA.toString())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
            query.addAnd("itemId", trans.getItemId(), InventoryItemMaster.class.getName());
            InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);

            String toAdd = "";
            if (dimGroup.getDim1Active()) toAdd += "config~";
            if (dimGroup.getDim2Active()) toAdd += "size~";
            if (dimGroup.getDim3Active()) toAdd += "colour~";
            if (dimGroup.getWarehouseActive()) toAdd += "warehouse~";
            if (dimGroup.getBatchNumberActive()) toAdd += "batch~";
            if (dimGroup.getSerialNumberActive()) toAdd += "serialNo~";
            if (dimGroup.getLocationActive()) toAdd += "location~";
            if (dimGroup.getPalletIdActive()) toAdd += "pallet~";

            if (toAdd.substring(toAdd.length() - 1, toAdd.length()).equals("~")) {
                toAdd = toAdd.substring(0, toAdd.length() - 1);
            }
            trans.setDisplayColumns(toAdd);
        }
        return trans;
    }
}
