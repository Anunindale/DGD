/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.pegging;

import emc.entity.inventory.pegging.InventoryPeggingTable;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.pegging.InventoryPeggingTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Owner
 */
@Stateless
public class InventoryPeggingBean extends EMCEntityBean implements InventoryPeggingLocal {

    public InventoryPeggingBean() {
    }

    /**
     * Create A Pegging Record Between the Demand And Supply
     * The method will go and fetch the Requirements Planning for the records passed in and peg with their record IDs
     * @param demandRecord The demand record to Peg
     * @param supplyRecord The supply record to Peg
     * @param pegQty The quantity to peg
     * @param userData Plain old user data
     * @return The newly created pegging record
     * @throws EMCEntityBeanException If Demand or Supply Requiremenst Planning Records were not found or if inserting of the Pegging record failed
     */
    public InventoryPeggingTable doPegging(EMCEntityClass demandRecord, EMCEntityClass supplyRecord, BigDecimal pegQty, EMCUserData userData) throws EMCEntityBeanException {
        //Find Demand Requirements Planning
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", demandRecord.getRecordID());
        query.addField("recordID");
        Long demandReqPlanId = (Long) util.executeSingleResultQuery(query, userData);
        if (isBlank(demandReqPlanId)) {
            Logger.getLogger("emc").log(Level.WARNING, "Failed to find requirements planning records for demand.", userData);
            return null;
        }
        //Find Supply Requirements Planning
        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRequirementsPlanning.class);
        query.addAnd("referenceRecordID", supplyRecord.getRecordID());
        query.addField("recordID");
        Long supplyReqPlanId = (Long) util.executeSingleResultQuery(query, userData);
        if (isBlank(supplyReqPlanId)) {
            Logger.getLogger("emc").log(Level.WARNING, "Failed to find requirements planning records for supply.", userData);
            return null;
        }
        //Create Pegging
        InventoryPeggingTable pegging = new InventoryPeggingTable();
        pegging.setDemandRef(demandReqPlanId);
        pegging.setSupplyRef(supplyReqPlanId);
        pegging.setPeggingQuantity(pegQty);
        pegging.setPeggingType(InventoryPeggingTypes.STANDARD.getId());
        pegging.setRatioToParent(BigDecimal.ONE);
        this.insert(pegging, userData);
        return pegging;
    }
}
