/*  
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.constants.systemConstants;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryCostingGroupBean extends EMCEntityBean implements InventoryCostingGroupLocal {

    @EJB
    private InventoryItemDimensionCombinationsLocal combinationsBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryTransactionsLocal transBean;

    /** Creates a new instance of InventoryCostingGroupBean */
    public InventoryCostingGroupBean() {
    }

    /** Updates the cost of items on either the Item Master or Item Dimension Combination using the cost group specified on the item.
     *  The cost is pulled through from the oldest journal line.
     *
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean updateCostRoutine(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        query.addAnd("costingGroup", null,EMCQueryConditions.IS_NOT_NULL);
        query.addAnd("itemType", emc.enums.inventory.inventorytables.InventoryItemTypes.ITEM.toString());
        //query.addAnd("itemId", "002055");
        List<InventoryItemMaster> items = util.executeGeneralSelectQuery(query, userData);
        boolean itemUpdated = false;
        boolean itemHasNoCost = false;
        boolean comJournalUpdated = false;
        boolean comAverageUpdated = false;
        boolean journalLinesFound = false;
        for (InventoryItemMaster item : items) {
            itemUpdated = false;
            itemHasNoCost = false;
            comJournalUpdated = false;
            comAverageUpdated = false;
            journalLinesFound = false;
            if (!isBlank(item.getCostingGroup())) {
                if (item.getCostingGroup().equals("COM")) {
                    //COM
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                    query.addAnd("itemId", item.getItemId());

                    List<InventoryItemDimensionCombinations> combinations = (List<InventoryItemDimensionCombinations>) util.executeGeneralSelectQuery(query, userData);

                    double totalCost = 0d;
                    int totalCombinations = 0;  //We could use list size, but that could include zero costs.

                    for (InventoryItemDimensionCombinations combination : combinations) {
                        if (util.compareDouble(combination.getCostPrice(), 0) < 1 || util.compareDouble(combination.getPurchasePrice(), 0) < 1) {
                            itemHasNoCost = true;
                            query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
                            query.addAnd("itemId", item.getItemId());
                            if(combination.getDimension1Id() == null || combination.getDimension1Id().equals(systemConstants.emcNA())){
                                query.addAnd("dimension1", null, EMCQueryConditions.IS_NULL);
                            }else{
                                query.addAnd("dimension1",combination.getDimension1Id());
                            }
                            if(combination.getDimension2Id() == null || combination.getDimension2Id().equals(systemConstants.emcNA())){
                                query.addAnd("dimension2", null, EMCQueryConditions.IS_NULL);
                            }else{
                                query.addAnd("dimension2",combination.getDimension2Id());
                            }
                            if(combination.getDimension3Id() == null || combination.getDimension3Id().equals(systemConstants.emcNA())){
                                query.addAnd("dimension3", null, EMCQueryConditions.IS_NULL);
                            }else{
                                query.addAnd("dimension3",combination.getDimension3Id());
                            }
                            query.addAnd("quantity", 0, EMCQueryConditions.GREATER_THAN);
                            query.addOrderBy("lineDate");

                            List<InventoryJournalLines> journalLines = (List<InventoryJournalLines>) util.executeGeneralSelectQuery(query, userData);

                            if (!journalLines.isEmpty()) {
                                journalLinesFound = true;
                                if (util.compareDouble(combination.getCostPrice(), 0) < 1) {
                                    //Cost Price
                                    combination.setCostPrice(journalLines.get(0).getCost());
                                }

                                if (util.compareDouble(combination.getPurchasePrice(), 0) < 1) {
                                    //Purchase Price
                                    combination.setPurchasePrice(journalLines.get(0).getCost());
                                }

                                totalCombinations++;
                                totalCost += journalLines.get(0).getCost();
                                comJournalUpdated = true;
                                combinationsBean.update(combination, userData);
                            } else {
                               // Logger.getLogger("emc").log(Level.WARNING, "No journal lines found for item - " + item.getItemReference() + ".  (dim1 - " + combination.getDimension1Id() + ", (dim2 - " + combination.getDimension2Id() + ", (dim3 - " + combination.getDimension3Id(), userData);
                            }
                        }else{
                            totalCombinations++;
                            totalCost += combination.getCostPrice();
                        }
                    }
                    if(totalCombinations == 0) totalCombinations = 1;
                    double averageCost = totalCost / Double.parseDouble(Integer.toString(totalCombinations));

                    if (util.compareDouble(averageCost, 0) > 0) {

                        //Loop through combinations again to update any zero costs.
                        for (InventoryItemDimensionCombinations combination : combinations) {
                            if (util.compareDouble(combination.getCostPrice(), 0) < 1 || util.compareDouble(combination.getPurchasePrice(), 0) < 1) {
                                itemHasNoCost = true;
                                if (util.compareDouble(combination.getCostPrice(), 0) < 1) {
                                    //Cost Price
                                    combination.setCostPrice(averageCost);
                                }

                                if (util.compareDouble(combination.getPurchasePrice(), 0) < 1) {
                                    //Purchase Price
                                    combination.setPurchasePrice(averageCost);
                                }
                                comAverageUpdated = true;
                                combinationsBean.update(combination, userData);
                            }
                        }
                        boolean needToPersistItemMaster = false;
                        if (util.compareDouble(item.getCostingCurrentCost(), 0) < 1) {
                            //Cost Price
                            item.setCostingCurrentCost(averageCost);
                            needToPersistItemMaster = true;
                        }

                        if (util.compareDouble(item.getPurchasePrice(), 0) < 1) {
                            //Purchase Price
                            item.setPurchasePrice(averageCost);
                            needToPersistItemMaster = true;
                        }

                        if(needToPersistItemMaster){
                            itemUpdated = true;
                            itemMasterBean.update(item, userData);
                        }
                    }
                } else {
                    //ITEM
                    if (util.compareDouble(item.getCostingCurrentCost(), 0) < 1 || util.compareDouble(item.getPurchasePrice(), 0) < 1) {
                        itemHasNoCost = true;
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
                        query.addAnd("itemId", item.getItemId());
                        query.addOrderBy("lineDate");

                        List<InventoryJournalLines> journalLines = (List<InventoryJournalLines>) util.executeGeneralSelectQuery(query, userData);

                        if (!journalLines.isEmpty()) {
                            journalLinesFound = true;
                            if (util.compareDouble(item.getCostingCurrentCost(), 0) < 1) {
                                //Cost Price
                                item.setCostingCurrentCost(journalLines.get(0).getCost());
                                
                            }

                            if (util.compareDouble(item.getPurchasePrice(), 0) < 1) {
                                //Purchase Price
                                item.setPurchasePrice(journalLines.get(0).getCost());
                            }
                            itemMasterBean.update(item, userData);
                            itemUpdated = true;
                            
                        } else {
                            itemUpdated = false;
                        }
                    }
                }
            if(itemHasNoCost && !itemUpdated){
                String toWrite = "\n Cost Group = "+item.getCostingGroup() +" Journal lines found? -" +journalLinesFound +" Comb lines updated from Journal? -" +comJournalUpdated + " Comb Ave Cost update? -" +comAverageUpdated;
                Logger.getLogger("emc").log(Level.WARNING, "Item - " + item.getItemReference() + " ("+item.getItemId()+") was not updated. Current Cost on Master is:"+item.getCostingCurrentCost() + toWrite, userData);
            }
            } else {
                Logger.getLogger("emc").log(Level.WARNING, "No costing group found for item - " + item.getItemReference(), userData);
            }
        }
        return true;
    }

    /** Updates transaction costs.
     *
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean fixTransactions(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
        query.addAnd("direction", InventoryTransactionDirection.OUT.toString());
        query.addAnd("physicalDate", null, EMCQueryConditions.IS_NOT_NULL);
        query.addAnd("cost",0);
        
        List<InventoryTransactions> transactions = (List<InventoryTransactions>)util.executeGeneralSelectQuery(query, userData);

        for (InventoryTransactions trans : transactions) {
            double cost = itemMasterBean.getCostPrice( trans.getItemId(), trans.getItemDimId(), userData);

            trans.setCost(cost * trans.getQuantity());

            transBean.update(trans, userData);
        }
        
        return true;
    }
}
