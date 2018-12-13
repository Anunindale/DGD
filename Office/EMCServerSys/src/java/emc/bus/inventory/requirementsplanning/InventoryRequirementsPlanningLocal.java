/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryRequirementsPlanningLocal extends EMCEntityBeanLocalInterface {

   
    public void insertDemand(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, java.lang.String reference, java.util.Date demandDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal quantity, int managedBy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void updateDemand(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, java.lang.String reference, java.util.Date demandDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal quantity, int managedBy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void deleteDemand(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void closeDemand(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void closeSupply(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void populateReleaseOfPlanned(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long fromRecordID, long toRecordID, java.lang.String reference, java.util.Date supplyDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal quantity, java.math.BigDecimal quantityRemaining, int managedBy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean cleanupRequirementsPlanningAndPegging(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void insertSupply(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, java.lang.String reference, java.util.Date supplyDate, java.util.Date supplyCompletionDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal supplyQuantity, java.math.BigDecimal totalQuantity, boolean firm, int managedBy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void updateSupply(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, java.lang.String reference, java.util.Date supplyDate, java.util.Date supplyCompletionDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal supplyQuantity, java.math.BigDecimal totalQuantity, boolean firm, int managedBy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void deleteSupply(emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType type, long recordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning generateSafetyStockRecord(java.util.Date demandDate, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String serialNo, java.math.BigDecimal quantity, emc.enums.inventory.requirementsplanning.RequirementsPlanningManagedBy managedBy, int level, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning generateSafetyStockRecord(java.lang.String itemId, long itemDimId, java.util.Date demandDate, java.math.BigDecimal quantity, emc.enums.inventory.requirementsplanning.RequirementsPlanningManagedBy managedBy, emc.framework.EMCUserData userData);

    }
