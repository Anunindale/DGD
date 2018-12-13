/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesrepcommission;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPSalesRepCommissionLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<java.lang.String> getAvailableData(java.lang.String type, emc.framework.EMCUserData userData);

    public boolean populateSalesRepCommission(java.lang.String type, java.util.List<java.lang.Object[]> condittions, java.util.List<java.lang.String> reps, java.lang.String commissionPercString, boolean override, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean doMassUpdate(String repId, String field1Value, String field2Value, String field3Value, String field4Value, String field5Value, String field6Value, BigDecimal commissionPerc, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns all distinct field values for the specified field on the specified table.
     * @param tableName Either InventoryItemMaster or SOPCustomers class name.
     * @param fieldName Field name to select on.
     * @param userData User data.
     * @return All distinct field values for the specified field on the specified table.
     */
    public java.util.List<java.lang.Object> getAllFieldValues(EMCQuery query, List<String> keys, emc.framework.EMCUserData userData);

    /**
     * Generates commission records for all possible combinations of the specified data.
     *
     * @param repId Rep id to generate records for.
     * @param field1Values Values for field 1.  May not be null.
     * @param field2Values Values for field 2.  May not be null.
     * @param field3Values Values for field 3.  May not be null.
     * @param field4Values Values for field 4.  May not be null.
     * @param field5Values Values for field 5.  May not be null.
     * @param field6Values Values for field 6.  May not be null.
     * @param overwrite Indicates whether existing data should be overwritten.
     * @param commissionPercentage Commission percentage to set on generated records.
     * @param userData User data.
     * @return A boolean indicating whether records where generated succesfully.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean generateSalesRepCommissionRecords(java.lang.String repId, java.util.List<java.lang.String> field1Values, java.util.List<java.lang.String> field2Values, java.util.List<java.lang.String> field3Values, java.util.List<java.lang.String> field4Values, java.util.List<java.lang.String> field5Values, java.util.List<java.lang.String> field6Values, boolean overwrite, boolean delete, java.math.BigDecimal commissionPercentage, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean validateSalesRep(java.lang.String repId, java.lang.String customerId, emc.entity.sop.SOPCustomers customer, java.lang.String itemId, emc.entity.inventory.InventoryItemMaster item, emc.framework.EMCUserData userData);

    public java.lang.String findSalesRepFor(java.lang.String customerId, emc.entity.sop.SOPCustomers customer, emc.framework.EMCUserData userData);

    public java.lang.Object validateField(java.lang.Object fieldValue, emc.framework.EMCQuery query, java.util.List<java.lang.String> keys, emc.framework.EMCUserData userData);

    public void saveCommissionWizzard(java.lang.String setupId, java.lang.String fieldName, java.util.List wizzardData, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List loadCommissionWizzard(java.lang.String setupId, java.lang.String fieldName, emc.framework.EMCUserData userData);

    public void deleteCommissionWizzard(java.lang.String setupId, emc.framework.EMCUserData userData);

    public java.util.List findCommissionWizzard(emc.framework.EMCUserData userData);

    public boolean validateSalesRep(String repId, EMCUserData userData);

    public BigDecimal findsalesRepCommission(String salesRep, String customValue1, String customValue2, String customValue3, String customValue4, String customValue5, String customValue6, EMCUserData userData);
}
