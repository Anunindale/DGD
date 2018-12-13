/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesrepcommission;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepCommissionWizzard;
import emc.entity.sop.SOPSalesRepGroups;
import emc.entity.sop.SOPSalesRepGroupsSetup;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.commission.SOPCommissionTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesRepCommissionBean extends EMCEntityBean implements SOPSalesRepCommissionLocal {

    @EJB
    private SOPParametersLocal parameterBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    @Override
    public List<String> getAvailableData(String type, EMCUserData userData) {
        EMCQuery formQuery = (EMCQuery) userData.getUserData(0);
        formQuery.addField("repId");
        formQuery.setSelectDistinctAll(true);
        EMCQuery query = null;
        enumPersistOptions dataType = enumPersistOptions.fromString(type);
        switch (dataType) {
            case INSERT:
                List notToInset = util.executeGeneralSelectQuery(formQuery, userData);
                query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepGroupsSetup.class);
                if (!notToInset.isEmpty()) {
                    query.addAndInList("repId", notToInset, true, true);
                }
                query.addField("repId");
                query.setSelectDistinctAll(true);
                break;
            case UPDATE:
            //Fall Through
            case DELETE:
                query = formQuery;
        }
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean populateSalesRepCommission(String type, List<Object[]> condittions, List<String> reps, String commissionPercString, boolean override, EMCUserData userData) throws EMCEntityBeanException {
        enumPersistOptions dataType = enumPersistOptions.fromString(type);
        switch (dataType) {
            case INSERT:
                return insertSalesRepCommission(condittions, reps, new BigDecimal(commissionPercString), override, userData);
            case UPDATE:
                return updateSalesRepCommission(reps, new BigDecimal(commissionPercString), userData);
            case DELETE:
                return deleteSalesRepCommission(reps, userData);
        }
        return false;
    }

    private boolean insertSalesRepCommission(List<Object[]> populateFor, List<String> reps, BigDecimal commissionPerc, boolean override, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query;
        List generationList;
        List<List> generationListHolder = new ArrayList<List>();
        for (Object[] toPop : populateFor) {
            query = new EMCQuery(enumQueryTypes.SELECT, toPop[0].toString());
            if (!isBlank(toPop[2])) {
                query.addAnd(toPop[1].toString(), toPop[2]);
            }
            query.addAnd(toPop[1].toString(), null, EMCQueryConditions.NOT);
            query.addField(toPop[1].toString());
            query.setSelectDistinctAll(true);
            generationList = util.executeGeneralSelectQuery(query, userData);
            if (generationList.isEmpty()) {
                throw new EMCEntityBeanException("No Data Found For Field " + toPop[1].toString());
            }
            generationListHolder.add(generationList);
        }
        if (generationListHolder.isEmpty()) {
            Logger.getLogger("emc").log(Level.WARNING, "No data found to generate commission for.", userData);
        } else {
            try {
                insertCommmission(new String[generationListHolder.size()], generationListHolder, 0, reps, commissionPerc, override, userData);
            } catch (Exception ex) {
                throw new EMCEntityBeanException(ex);
            }
        }
        return true;
    }

    private void insertCommmission(String[] insrtValues, List<List> generationListHolder, int pos, List<String> reps, BigDecimal commissionPerc, boolean override, EMCUserData userData) throws Exception {
        List valueList = generationListHolder.get(pos);
        for (Object o : valueList) {
            insrtValues[pos] = o.toString();
            if (generationListHolder.size() > pos + 1) {
                insertCommmission(insrtValues, generationListHolder, pos + 1, reps, commissionPerc, override, userData);
            } else {
                SOPSalesRepCommission commission;
                SOPSalesRepCommission sqlCommission;
                Field f;
                EMCQuery query;
                for (String rep : reps) {
                    commission = new SOPSalesRepCommission();
                    commission.setRepId(rep);
                    commission.setCommissionPerc(commissionPerc);
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                    query.addAnd("repId", rep);
                    for (int value = 0; value < insrtValues.length; value++) {
                        switch (value) {
                            case 0:
                                commission.setCustomerItemField1(insrtValues[value]);
                                query.addAnd("customerItemField1", insrtValues[value]);
                                break;
                            case 1:
                                commission.setCustomerItemField2(insrtValues[value]);
                                query.addAnd("customerItemField2", insrtValues[value]);
                                break;
                            case 2:
                                commission.setCustomerItemField3(insrtValues[value]);
                                query.addAnd("customerItemField3", insrtValues[value]);
                                break;
                            case 3:
                                commission.setCustomerItemField4(insrtValues[value]);
                                query.addAnd("customerItemField4", insrtValues[value]);
                                break;
                            case 4:
                                commission.setCustomerItemField5(insrtValues[value]);
                                query.addAnd("customerItemField5", insrtValues[value]);
                                break;
                            case 5:
                                commission.setCustomerItemField6(insrtValues[value]);
                                query.addAnd("customerItemField6", insrtValues[value]);
                                break;
                        }
                    }
                    sqlCommission = (SOPSalesRepCommission) util.executeSingleResultQuery(query, userData);
                    if (sqlCommission != null) {
                        if (override) {
                            sqlCommission.setCommissionPerc(commissionPerc);
                            this.update(sqlCommission, userData);
                        }
                    } else {
                        this.insert(commission, userData);
                    }
                }
            }
        }
    }

    private boolean updateSalesRepCommission(List<String> reps, BigDecimal commissionPerc, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addAndInList("repId", reps, true, false);
        List<SOPSalesRepCommission> toDelete = util.executeGeneralSelectQuery(query, userData);
        for (SOPSalesRepCommission com : toDelete) {
            com.setCommissionPerc(commissionPerc);
            this.update(com, userData);
        }
        return true;
    }

    private boolean deleteSalesRepCommission(List<String> reps, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addAndInList("repId", reps, true, false);
        List<SOPSalesRepCommission> toDelete = util.executeGeneralSelectQuery(query, userData);
        for (SOPSalesRepCommission com : toDelete) {
            this.delete(com, userData);
        }
        return true;
    }

    /**
     * Updates the commission percentage on all records matching the specified criteria.
     * @param repId Rep id to search for.
     * @param field1Value Field 1 value to search for.
     * @param field2Value Field 2 value to search for.
     * @param field3Value Field 3 value to search for.
     * @param field4Value Field 4 value to search for.
     * @param field5Value Field 5 value to search for.
     * @param field6Value Field 6 value to search for.
     * @param commissionPerc Commission percentage to set on records.
     * @param userData User data.
     * @return A boolean indicating whether the update was successful.
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public boolean doMassUpdate(String repId, String field1Value, String field2Value, String field3Value, String field4Value, String field5Value, String field6Value, BigDecimal commissionPerc, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, SOPSalesRepCommission.class);

        //Only include conditions with values
        if (!isBlank(repId)) {
            query.addAnd("repId", repId);
        }

        if (!isBlank(field1Value)) {
            query.addAnd("customerItemField1", field1Value);
        }

        if (!isBlank(field2Value)) {
            query.addAnd("customerItemField2", field2Value);
        }

        if (!isBlank(field3Value)) {
            query.addAnd("customerItemField3", field3Value);
        }

        if (!isBlank(field4Value)) {
            query.addAnd("customerItemField4", field4Value);
        }

        if (!isBlank(field5Value)) {
            query.addAnd("customerItemField5", field5Value);
        }

        if (!isBlank(field6Value)) {
            query.addAnd("customerItemField6", field6Value);
        }

        query.addSet("commissionPerc", commissionPerc);

        return util.executeUpdate(query, userData);
    }

    /**
     * Generates commission records for all possible combinations of the specified data.
     *
     * @param repId Rep id to generate records for.
     * @param field1Values Values for field 1.
     * @param field2Values Values for field 2.
     * @param field3Values Values for field 3.
     * @param field4Values Values for field 4.
     * @param field5Values Values for field 5.
     * @param field6Values Values for field 6.
     * @param overwrite Indicates whether existing data should be overwritten.
     * @param commissionPercentage Commission percentage to set on generated records.
     * @param userData User data.
     * @return A boolean indicating whether records where generated succesfully.
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public boolean generateSalesRepCommissionRecords(String repId, List<String> field1Values, List<String> field2Values, List<String> field3Values, List<String> field4Values, List<String> field5Values, List<String> field6Values, boolean overwrite, boolean delete, BigDecimal commissionPercentage, EMCUserData userData) throws EMCEntityBeanException {
        if (field1Values.isEmpty()) {
            field1Values.add(null);
        }

        if (field2Values.isEmpty()) {
            field2Values.add(null);
        }

        if (field3Values.isEmpty()) {
            field3Values.add(null);
        }

        if (field4Values.isEmpty()) {
            field4Values.add(null);
        }

        if (field5Values.isEmpty()) {
            field5Values.add(null);
        }

        if (field6Values.isEmpty()) {
            field6Values.add(null);
        }

        for (String field1Value : field1Values) {
            for (String field2Value : field2Values) {
                for (String field3Value : field3Values) {
                    for (String field4Value : field4Values) {
                        for (String field5Value : field5Values) {
                            for (String field6Value : field6Values) {
                                //Not very efficient, but we only want to update records that are an exact match.
                                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                                query.addAnd("repId", repId);
                                query.addAnd("customerItemField1", field1Value);
                                query.addAnd("customerItemField2", field2Value);
                                query.addAnd("customerItemField3", field3Value);
                                query.addAnd("customerItemField4", field4Value);
                                query.addAnd("customerItemField5", field5Value);
                                query.addAnd("customerItemField6", field6Value);

                                SOPSalesRepCommission existingCommissionRecord = (SOPSalesRepCommission) util.executeSingleResultQuery(query, userData);

                                if (delete) {
                                    if (existingCommissionRecord != null) {
                                        this.delete(existingCommissionRecord, userData);
                                    }
                                } else {
                                    if (existingCommissionRecord != null) {
                                        if (overwrite) {
                                            existingCommissionRecord.setCommissionPerc(commissionPercentage);
                                            this.update(existingCommissionRecord, userData);
                                        }
                                        continue;
                                    }
                                    SOPSalesRepCommission commissionRecord = new SOPSalesRepCommission();
                                    commissionRecord.setRepId(repId);
                                    commissionRecord.setCommissionPerc(commissionPercentage);
                                    commissionRecord.setCustomerItemField1(field1Value);
                                    commissionRecord.setCustomerItemField2(field2Value);
                                    commissionRecord.setCustomerItemField3(field3Value);
                                    commissionRecord.setCustomerItemField4(field4Value);
                                    commissionRecord.setCustomerItemField5(field5Value);
                                    commissionRecord.setCustomerItemField6(field6Value);
                                    commissionRecord.setCompanyId(userData.getCompanyId());
                                    commissionRecord.setVersion(0);
                                    super.doInsert(commissionRecord, userData);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns all distinct field values for the specified field on the specified table.
     * @param tableName Either InventoryItemMaster or SOPCustomers class name.
     * @param fieldName Field name to select on.
     * @param userData User data.
     * @return All distinct field values for the specified field on the specified table.
     */
    @Override
    public List<Object> getAllFieldValues(EMCQuery query, List<String> keys, EMCUserData userData) {
        for (String s : keys) {
            query.addField(s);
        }
        query.addGroupBy(keys.get(0));
        query.addOrderBy(keys.get(0));

        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean validateSalesRep(String repId, String customerId, SOPCustomers customer, String itemId, InventoryItemMaster item, EMCUserData userData) {
        if (isBlank(repId)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No Rep selected.", userData);
            return false;
        }
        SOPParameters param = parameterBean.getParameterRecord(userData);
        if (param == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "SOP Parameter not set up.", userData);
            return false;
        }
        if (customer == null) {
            customer = customerBean.findCustomer(customerId, userData);
            if (customer == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find customer.", userData);
                return false;
            }
        }
        if (SOPCommissionTypes.SINGLE.toString().equals(customer.getRepService())) {
            if (repId.equals(customer.getSalesRep())) {
                return true;
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Rep " + repId + " may not sell " + (customer == null ? (isBlank(customerId) ? "" : " to customer " + customerId) : " to customer " + customer.getCustomerId()), userData);
                return false;
            }
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
            query.addAnd("repId", repId);
            Field f;
            Object table;
            Object field;
            try {
                for (int i = 0; i < 6; i++) {
                    f = SOPParameters.class.getDeclaredField("customerItemTable" + (i + 1));
                    f.setAccessible(true);
                    table = f.get(param);
                    if (!isBlank(table)) {
                        f = SOPParameters.class.getDeclaredField("customerItemField" + (i + 1));
                        f.setAccessible(true);
                        field = f.get(param);
                        if (SOPCustomers.class.getName().equals(table.toString())) {
                            if (!isBlank(customerId) || customer != null) {
                                if (customer == null) {
                                    customer = customerBean.findCustomer(customerId, userData);
                                    if (customer == null) {
                                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to find customer.", userData);
                                        return false;
                                    }
                                }
                                f = SOPCustomers.class.getDeclaredField(field.toString());
                                f.setAccessible(true);
                                query.openAndConditionBracket();
                                query.addOr("customerItemField" + (i + 1), f.get(customer));
                                query.addOr("customerItemField" + (i + 1), null);
                                query.closeConditionBracket();
                            }
                        } else if (InventoryItemMaster.class.getName().equals(table.toString())) {
                            if (!isBlank(itemId) || item != null) {
                                if (item == null) {
                                    item = itemBean.findItem(itemId, userData);
                                    if (item == null) {
                                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to find item.", userData);
                                        return false;
                                    }
                                }
                                f = InventoryItemMaster.class.getDeclaredField(field.toString());
                                f.setAccessible(true);
                                query.openAndConditionBracket();
                                query.addOr("customerItemField" + (i + 1), f.get(item));
                                query.addOr("customerItemField" + (i + 1), null);
                                query.closeConditionBracket();
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to get value from parameter.", userData);
                return false;
            }
            if (util.exists(query, userData)) {
                return true;
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Rep " + repId + " may not sell " + (item == null ? (isBlank(itemId) ? "" : "item " + itemId) : "item " + item.getItemReference()) + (customer == null ? (isBlank(customerId) ? "" : " to customer " + customerId) : " to customer " + customer.getCustomerId()), userData);
                return false;
            }
        }
    }

    @Override
    public String findSalesRepFor(String customerId, SOPCustomers customer, EMCUserData userData) {
        SOPParameters param = parameterBean.getParameterRecord(userData);
        if (param == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "SOP Parameter not set up.", userData);
            return null;
        }
        if (customer == null) {
            customer = customerBean.findCustomer(customerId, userData);
            if (customer == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find customer.", userData);
                return null;
            }
        }
        if (SOPCommissionTypes.SINGLE.toString().equals(customer.getRepService())) {
            return isBlank(customer.getSalesRep()) ? null : customer.getSalesRep();
        }
        return null;
    }

    @Override
    public Object validateField(Object fieldValue, EMCQuery query, List<String> keys, EMCUserData userData) {
        for (String s : keys) {
            query.addField(s);
        }
        query.addAnd(keys.get(0), fieldValue);
        query.addGroupBy(keys.get(0));
        query.addOrderBy(keys.get(0));

        return util.executeSingleResultQuery(query, userData);
    }

    @Override
    public void saveCommissionWizzard(String setupId, String fieldName, List wizzardData, EMCUserData userData) throws EMCEntityBeanException {
        wizzardData.remove(0);

        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, SOPSalesRepCommissionWizzard.class);
        query.addAnd("wizzardId", setupId);
        query.addAnd("fieldId", fieldName);
        util.executeUpdate(query, userData);

        SOPSalesRepCommissionWizzard wizzard;
        for (Object o : wizzardData) {
            wizzard = (SOPSalesRepCommissionWizzard) o;
            super.insert(wizzard, userData);
        }
    }

    @Override
    public List loadCommissionWizzard(String setupId, String fieldName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommissionWizzard.class);
        query.addAnd("wizzardId", setupId);
        query.addAnd("fieldId", fieldName);
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public void deleteCommissionWizzard(String setupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, SOPSalesRepCommissionWizzard.class);
        query.addAnd("wizzardId", setupId);
        util.executeUpdate(query, userData);
    }

    @Override
    public List findCommissionWizzard(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommissionWizzard.class);
        query.addField("wizzardId");
        query.setSelectDistinctAll(true);
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid && vobject instanceof SOPSalesRepCommission) {
            SOPSalesRepCommission commission = (SOPSalesRepCommission) vobject;
            valid = doSaveValidation(commission, userData);
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            SOPSalesRepCommission commission = (SOPSalesRepCommission) vobject;
            valid = doSaveValidation(commission, userData);
        }
        return valid;
    }

    private boolean doSaveValidation(SOPSalesRepCommission commission, EMCUserData userData) {
        if (isBlank(commission.getCustomerItemField1())) {
            commission.setCustomerItemField1(null);
        }
        if (isBlank(commission.getCustomerItemField2())) {
            commission.setCustomerItemField2(null);
        }
        if (isBlank(commission.getCustomerItemField3())) {
            commission.setCustomerItemField3(null);
        }
        if (isBlank(commission.getCustomerItemField4())) {
            commission.setCustomerItemField4(null);
        }
        if (isBlank(commission.getCustomerItemField5())) {
            commission.setCustomerItemField5(null);
        }
        if (isBlank(commission.getCustomerItemField6())) {
            commission.setCustomerItemField6(null);
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
        query.addAnd("customerItemField1", commission.getCustomerItemField1());
        query.addAnd("customerItemField2", commission.getCustomerItemField2());
        query.addAnd("customerItemField3", commission.getCustomerItemField3());
        query.addAnd("customerItemField4", commission.getCustomerItemField4());
        query.addAnd("customerItemField5", commission.getCustomerItemField5());
        query.addAnd("customerItemField6", commission.getCustomerItemField6());
        query.addAnd("repId", commission.getRepId());
        query.addAnd("recordID", commission.getRecordID(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "A similar record already exists", userData);
            return false;
        }
        return true;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            SOPSalesRepCommission commission = (SOPSalesRepCommission) theRecord;

            List<String> customFields = new ArrayList<String>();
            customFields.add("customerItemField1");
            customFields.add("customerItemField2");
            customFields.add("customerItemField3");
            customFields.add("customerItemField4");
            customFields.add("customerItemField5");
            customFields.add("customerItemField6");
            if (customFields.contains(fieldNameToValidate)) {
                List<String[]> tableColumns = new ArrayList<String[]>();
                SOPParameters param = parameterBean.getParameterRecord(userData);
                if (param.getCustomerItemTable1() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable1(), param.getCustomerItemField1()});
                }
                if (param.getCustomerItemTable2() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable2(), param.getCustomerItemField2()});
                }
                if (param.getCustomerItemTable3() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable3(), param.getCustomerItemField3()});
                }
                if (param.getCustomerItemTable4() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable4(), param.getCustomerItemField4()});
                }
                if (param.getCustomerItemTable5() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable5(), param.getCustomerItemField5()});
                }
                if (param.getCustomerItemTable6() != null) {
                    tableColumns.add(new String[]{param.getCustomerItemTable6(), param.getCustomerItemField6()});
                }
                EMCQuery query;
                String[] customValues = null;
                String entityValue = null;
                switch (Integer.valueOf(fieldNameToValidate.substring(fieldNameToValidate.length() - 1))) {
                    case 1:
                        customValues = tableColumns.get(0);
                        entityValue = commission.getCustomerItemField1();
                        break;
                    case 2:
                        customValues = tableColumns.get(1);
                        entityValue = commission.getCustomerItemField2();
                        break;
                    case 3:
                        customValues = tableColumns.get(2);
                        entityValue = commission.getCustomerItemField3();
                        break;
                    case 4:
                        customValues = tableColumns.get(3);
                        entityValue = commission.getCustomerItemField4();
                        break;
                    case 5:
                        customValues = tableColumns.get(4);
                        entityValue = commission.getCustomerItemField5();
                        break;
                    case 6:
                        customValues = tableColumns.get(5);
                        entityValue = commission.getCustomerItemField6();
                        break;
                }
                if (!isBlank(entityValue)) {
                    query = new EMCQuery(enumQueryTypes.SELECT, customValues[0]);
                    query.addAnd(customValues[1], entityValue);
                    valid = util.exists(query, userData);
                    if (!valid) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Value entered does not exist.", userData);
                    }
                }
            } else if (fieldNameToValidate.equals("repId")) {
                if (!isBlank(commission.getRepId())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepGroups.class);
                    query.addAnd("groupManager", commission.getRepId());
                    valid = util.exists(query, userData);
                    if (!valid) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Value entered does not exist.", userData);
                    }
                }
            }
        }
        return valid;
    }

    @Override
    public boolean validateSalesRep(String repId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepGroups.class);
        query.addAnd("groupManager", repId);
        boolean valid = util.exists(query, userData);
        if (!valid) {
            Logger.getLogger("emc").log(Level.SEVERE, "Value entered for Rep Id does not exist.", userData);
        }
        return valid;
    }

    @Override
    public BigDecimal findsalesRepCommission(String salesRep, String customValue1, String customValue2, String customValue3, String customValue4,
            String customValue5, String customValue6, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
        query.addAnd("repId", salesRep);
        if (!isBlank(customValue1)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField1", customValue1);
            query.addStringIsBlank("customerItemField1", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField1", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        if (!isBlank(customValue2)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField2", customValue2);
            query.addStringIsBlank("customerItemField2", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField2", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        if (!isBlank(customValue3)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField3", customValue3);
            query.addStringIsBlank("customerItemField3", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField3", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        if (!isBlank(customValue4)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField4", customValue4);
            query.addStringIsBlank("customerItemField4", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField4", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        if (!isBlank(customValue5)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField5", customValue5);
            query.addStringIsBlank("customerItemField5", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField5", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        if (!isBlank(customValue6)) {
            query.openAndConditionBracket();
            query.addOr("customerItemField6", customValue6);
            query.addStringIsBlank("customerItemField6", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.OR);
            query.closeConditionBracket();
        } else {
            query.addStringIsBlank("customerItemField6", SOPSalesRepCommission.class.getName(), EMCQueryBracketConditions.AND);
        }
        List<SOPSalesRepCommission> commissionList = util.executeGeneralSelectQuery(query, userData);

        BigDecimal commission = BigDecimal.ZERO;
        boolean value1Blank = isBlank(customValue1);
        boolean value2Blank = isBlank(customValue2);
        boolean value3Blank = isBlank(customValue3);
        boolean value4Blank = isBlank(customValue4);
        boolean value5Blank = isBlank(customValue5);
        boolean value6Blank = isBlank(customValue6);
        boolean value1Match = value1Blank;
        boolean value2Match = value2Blank;
        boolean value3Match = value3Blank;
        boolean value4Match = value4Blank;
        boolean value5Match = value5Blank;
        boolean value6Match = value6Blank;

        for (SOPSalesRepCommission commissionRec : commissionList) {
            if (!value1Blank) {
                if (value1Match && isBlank(commissionRec.getCustomerItemField1())) {
                    continue;
                } else if (!value1Match && !isBlank(commissionRec.getCustomerItemField1())) {
                    commission = commissionRec.getCommissionPerc();
                    value1Match = true;
                    continue;
                }
            }
            if (!value2Blank) {
                if (value2Match && isBlank(commissionRec.getCustomerItemField2())) {
                    continue;
                } else if (!value2Match && !isBlank(commissionRec.getCustomerItemField2())) {
                    commission = commissionRec.getCommissionPerc();
                    value2Match = true;
                    continue;
                }
            }
            if (!value3Blank) {
                if (value3Match && isBlank(commissionRec.getCustomerItemField3())) {
                    continue;
                } else if (!value3Match && !isBlank(commissionRec.getCustomerItemField3())) {
                    commission = commissionRec.getCommissionPerc();
                    value3Match = true;
                    continue;
                }
            }
            if (!value4Blank) {
                if (value4Match && isBlank(commissionRec.getCustomerItemField4())) {
                    continue;
                } else if (!value4Match && !isBlank(commissionRec.getCustomerItemField4())) {
                    commission = commissionRec.getCommissionPerc();
                    value4Match = true;
                    continue;
                }
            }
            if (!value5Blank) {
                if (value5Match && isBlank(commissionRec.getCustomerItemField5())) {
                    continue;
                } else if (!value5Match && !isBlank(commissionRec.getCustomerItemField5())) {
                    commission = commissionRec.getCommissionPerc();
                    value5Match = true;
                    continue;
                }
            }
            if (!value6Blank) {
                if (value6Match && isBlank(commissionRec.getCustomerItemField6())) {
                    continue;
                } else if (!value6Match && !isBlank(commissionRec.getCustomerItemField6())) {
                    commission = commissionRec.getCommissionPerc();
                    value6Match = true;
                    continue;
                }
            }
            if (value1Match && value2Match && value3Match && value4Match && value5Match && value6Match) {
                break;
            }
        }
        return commission;
    }
}



