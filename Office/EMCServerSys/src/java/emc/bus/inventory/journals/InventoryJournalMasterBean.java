/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals;

import emc.bus.base.journals.BaseJournalApprovalGroupEmployeesLocal;
import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupsLocal;
import emc.bus.inventory.transactions.EMCStockException;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import emc.bus.inventory.transactions.ProcessStockTransactionLocal;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.bus.inventory.transactions.TransactionType;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.helpers.inventory.JournalGeneratorHelper;
import emc.messages.ServerBaseMessageEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryJournalMasterBean extends EMCEntityBean implements InventoryJournalMasterLocal {

    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private BaseJournalApprovalGroupEmployeesLocal approvalGroupEmployeesBean;
    @EJB
    private ProcessStockTransactionLocal trnsBean;
    @EJB
    private BaseJournalAccessGroupsLocal accessBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @Resource
    private EJBContext context;

    /**
     * Creates a new instance of InventoryJournalMasterBean
     */
    public InventoryJournalMasterBean() {
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalMaster mast = (InventoryJournalMaster) vobject;
        if (mast.getJournalStatus().equals(JournalStatus.POSTED.toString())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Posted Journal may not be deleted.", userData);
            return false;
        }
        InventoryJournalTypes type = InventoryJournalTypes.MOVEMENT;
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(mast.getJournalNumber(), userData);
        if (definition != null) {
            type = InventoryJournalTypes.fromString(definition.getJournalType());
        }
        return super.doDeleteValidation(vobject, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalMaster master = (InventoryJournalMaster) iobject;
        master.setJournalStatus(JournalStatus.CAPTURED.toString());
        return super.insert(iobject, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        Object ret = null;

        InventoryJournalMaster master = (InventoryJournalMaster) theRecord;

        if (fieldNameToValidate.equals("journalContraType") || fieldNameToValidate.equals("journalContraAccount")) {
            valid = valid && checkContraFixed(master, userData);
        }
        if (master.getJournalStatus() != null) {
            switch (JournalStatus.fromString(master.getJournalStatus())) {
                case CAPTURED:
                    break;
                case APPROVED:
                    Logger.getLogger("emc").log(Level.SEVERE, "Journal approved may not change, unapprove first.", userData);
                    valid = false;
                    break;
                case POSTED:
                    Logger.getLogger("emc").log(Level.SEVERE, "Journal posted may not change.", userData);
                    valid = false;
                    break;
            }
        }

        if (fieldNameToValidate.equals("journalDefinitionId")) {
            boolean validDef = false;
            List validDefs = accessBean.getDefinitionList(userData);
            for (int i = 0; i < validDefs.size(); i++) {
                if (validDefs.get(i).toString().equalsIgnoreCase(master.getJournalDefinitionId())) {
                    validDef = true;
                    break;
                }
            }
            if (!validDef) {
                this.logMessage(Level.SEVERE, ServerBaseMessageEnum.NOACCESS, userData);
            }
            valid = valid && validDef;
            if (valid && master.getRecordID() != 0) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
                query.addAnd("journalNumber", master.getJournalNumber());
                if (util.exists(query, userData)) {
                    this.logMessage(Level.SEVERE, ServerBaseMessageEnum.HAS_LINES, userData);
                    valid = false;
                }
            }
        }
        if (valid) {
            if (fieldNameToValidate.equals("journalDefinitionId")) {
                doDefinition(master, userData);
            }

            ret = master;
        } else {
            ret = valid;
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition((InventoryJournalMaster) vobject, userData);
            if (InventoryJournalTypes.fromString(definition.getJournalType()).equals(InventoryJournalTypes.STOCKTAKE)) {
                ret = false;
                this.logMessage(Level.SEVERE, "Stock take journals may only be generated not captured.", userData);
            }
        }
        return ret;
    }

    /**
     * This method is used to get fields from the journal definition table.
     */
    private void doDefinition(InventoryJournalMaster master, EMCUserData userData) {
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master, userData);

        master.setJournalContraType(definition.getJournalContraType());
        master.setJournalContraAccount(definition.getJournalContraAccount());
    }

    /**
     * unapprove a Journal
     */
    @Override
    public boolean unApproveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalNumber", journalNumber);

        InventoryJournalMaster master = (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);
        switch (JournalStatus.fromString(master.getJournalStatus())) {
            case CAPTURED:

                Logger.getLogger("emc").log(Level.INFO, "Journal not approved, cannot unapprove.", userData);
                break;
            case APPROVED:
                master.setJournalApprovedBy(null);
                master.setJournalApprovedDate(null);
                master.setJournalStatus(emc.enums.base.journals.JournalStatus.CAPTURED.toString());
                this.update(master, userData);
                Logger.getLogger("emc").log(Level.INFO, "Journal unapproved", userData);
                return true;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal posted may not unapprove", userData);
                break;
        }
        return false;
    }

    /**
     * This method is used to approve a journal.
     */
    @Override
    public boolean approveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);

        if (isBlank(employee)) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not authorized to approve a journal.", userData);
            return false;
        } else {
            List<String> approvalGroup = approvalGroupEmployeesBean.findEmployeeApprovalGroup(employee, Modules.INVENTORY, userData);

            if (approvalGroup == null || approvalGroup.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No approval group found.  You are not authorized to approve a journal.", userData);
                return false;
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
                query.addAnd("journalNumber", journalNumber);

                InventoryJournalMaster master = (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);

                BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master.getJournalDefinitionId(), Modules.INVENTORY, userData);

                if (!approvalGroup.contains(definition.getApprovalBy())) {
                    throw new EMCEntityBeanException("You do not belong to the journal approval group for the selected journal.");
                }
                if (master.getCreatedBy().equalsIgnoreCase(userData.getUserName())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You may not approve a journal that you captured.", userData);
                    return false;
                }
                boolean status = true;
                switch (JournalStatus.fromString(master.getJournalStatus())) {
                    case CAPTURED:
                        if (status) {
                            master.setJournalApprovedBy(employee);
                            master.setJournalApprovedDate(Functions.nowDate());
                            master.setJournalStatus(emc.enums.base.journals.JournalStatus.APPROVED.toString());
                            this.update(master, userData);
                            Logger.getLogger("emc").log(Level.INFO, "Journal approved", userData);

                            return true;
                        }
                        break;
                    case APPROVED:
                        Logger.getLogger("emc").log(Level.SEVERE, "Journal already approved", userData);
                        break;
                    case POSTED:
                        Logger.getLogger("emc").log(Level.SEVERE, "Journal already approved", userData);
                        break;
                }

            }
        }
        return false;
    }

    private boolean doValidateJournal(InventoryJournalMaster record, EMCUserData userData) {
        boolean status = true;
        if (checkRegistration(record.getJournalNumber(), userData)) {
            try {
                trnsBean.validatePost(record, null, userData);
            } catch (EMCStockException ex) {
                context.setRollbackOnly();
                if (ex.getMessage().contains("VALIDATION_PASSED")) {
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to Validate Journal. " + ex.getMessage(), userData);
                    status = false;
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to Validate Journal. Items not registered.", userData);
            status = false;
        }
        return status;
    }

    @Override
    public boolean validateJournal(InventoryJournalMaster record, EMCUserData userData) {
        boolean status = true;
        switch (JournalStatus.fromString(record.getJournalStatus())) {
            case CAPTURED:
            case APPROVED:
                status = doValidateJournal(record, userData);
                break;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal already posted", userData);
                status = false;
                break;
            default:
                break;
        }

        return status;
    }

    @Override
    public boolean postJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class);
        query.addAnd("journalNumber", journalNumber);
        InventoryJournalMaster record = (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);
        if (record == null) {
            throw new EMCEntityBeanException("Failed to find the journal record for journal number " + journalNumber);
        }
        return postJournal(record, userData);
    }

    @Override
    public boolean postJournal(InventoryJournalMaster record, EMCUserData userData) throws EMCEntityBeanException {
        boolean status = true;
        switch (JournalStatus.fromString(record.getJournalStatus())) {
            case CAPTURED:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class.getName());
                query.addAnd("journalDefinitionId", record.getJournalDefinitionId());
                query.addAnd("companyId", userData.getCompanyId());
                query.addField("journalApprovalRequired");
                if ((Boolean) util.executeSingleResultQuery(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Approve the Journal first", userData);
                    status = false;
                    break;
                }
            //else fall through
            case APPROVED:
                if (checkRegistration(record.getJournalNumber(), userData)) {
                    try {
                        TransactionHelper h = new TransactionHelper(TransactionType.IVENT_POST_JRMASTER);
                        h.setPostJournal(true);
                        trnsBean.post(record, h, userData);
                        InventoryJournalMaster persisted = (InventoryJournalMaster) util.findPersisted(InventoryJournalMaster.class, record.getRecordID(), userData);
                        persisted.setJournalStatus(JournalStatus.POSTED.toString());
                        persisted.setJournalPostedDate(Functions.nowDate());
                        persisted.setJournalPostedBy(userData.getUserName());
                        this.update(persisted, userData);
                    } catch (EMCStockException ex) {
                        throw new EMCEntityBeanException(ex.getMessage());
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to Validate Journal. Items not registered.", userData);
                    status = false;
                }
                break;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal already posted", userData);
                status = false;
                break;
            default:
                break;
        }
        return status;
    }

    /**
     * This method checks whether contra type and contra account may be changed.
     */
    private boolean checkContraFixed(InventoryJournalMaster master, EMCUserData userData) {
        boolean ret = true;

        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master, userData);

        if (definition != null) {
            if (definition.getJournalContraFixed()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Field may not be changed.  Contra fixed on Journal Definition Table.", userData);
                ret = false;
            }

        }
        return ret;
    }

    private boolean checkRegistration(String journalNumber, EMCUserData userData) {
        Boolean ret = true;
        InventoryJournalTypes type = InventoryJournalTypes.MOVEMENT;
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(journalNumber, userData);
        if (definition != null) {
            type = InventoryJournalTypes.fromString(definition.getJournalType());
        }
        switch (type) {
            case MOVEMENT:
                break;
            case STOCKTAKE:
                return true;
            case TRANSFER:
                return true;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addOr("batchNumberActive", true);
        query.addOr("serialNumberActive", true);
        query.closeConditionBracket();
        query.addField("itemDimensionGroupId");
        List<String> dimGroups = util.executeGeneralSelectQuery(query, userData);

        List firstList = new ArrayList();

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryJournalLines.class.getName(), "itemId");
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("journalNumber", journalNumber, InventoryJournalLines.class.getName());
        query.addField("transId", InventoryJournalLines.class.getName());
        query.addField("quantity", InventoryJournalLines.class.getName());
        query.addField("itemReference", InventoryItemMaster.class.getName());
        query.addAnd("dimensionGroup", "", InventoryItemMaster.class.getName());

        for (String dimGroup : dimGroups) {
            query.removeAnd("dimensionGroup", InventoryItemMaster.class.getName());
            query.addAnd("dimensionGroup", dimGroup, InventoryItemMaster.class.getName());
            firstList.addAll(util.executeGeneralSelectQuery(query, userData));
        }

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
        query.addFieldAggregateFunction("quantity", InventoryRegister.class.getName(), "SUM");
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("masterId", journalNumber);
        query.addAnd("type", RegisterFromTypeEnum.JRN.toString());
        query.addAnd("transId", "");
        EMCQuery removeQ = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.register.InventoryRemoveRegister.class.getName());
        removeQ.addFieldAggregateFunction("quantity", InventoryRemoveRegister.class.getName(), "SUM");
        removeQ.addAnd("companyId", userData.getCompanyId());
        removeQ.addAnd("masterId", journalNumber);
        removeQ.addAnd("type", RegisterFromTypeEnum.JRN.toString());
        removeQ.addAnd("transId", "");

        Object[] oArray;
        double totalQty;
        double regQty;
        for (Object o : firstList) {
            oArray = (Object[]) o;
            Object queryO;
            if ((Double) oArray[1] >= 0) {
                query.removeAnd("transId");
                query.addAnd("transId", (String) oArray[0]);
                queryO = util.executeSingleResultQuery(query, userData);
            } else {
                removeQ.removeAnd("transId");
                removeQ.addAnd("transId", (String) oArray[0]);
                queryO = util.executeSingleResultQuery(removeQ, userData);
            }

            if (queryO != null) {
                totalQty = (Double) oArray[1];
                regQty = (Double) queryO;
                if (util.compareDouble(totalQty, regQty) != 0) {
                    ret = false;
                    Logger.getLogger("emc").log(Level.WARNING, "There are " + Math.abs(totalQty) + " items received but only " + Math.abs(regQty) + " are registered for the item " + (String) oArray[2] + ".", userData);
                }
            } else {
                ret = false;
                Logger.getLogger("emc").log(Level.WARNING, "You did not register any of the items you received. Please register them first.", userData);
            }
        }
        return ret;
    }

    public boolean generateTransferJournal(JournalGeneratorHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        boolean addedItemMaster = false;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        if (!isBlank(helper.getFromItem())) {
            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
            query.openAndConditionBracket();
            query.addOr("itemId", helper.getFromItem(), InventoryItemMaster.class.getName());
            query.addOr("itemReference", helper.getFromItem(), InventoryItemMaster.class.getName());
            query.closeConditionBracket();

            addedItemMaster = true;
        }
        if (!isBlank(helper.getFromDimension1())) {
            query.addAnd("dimension1", helper.getFromDimension1());
        }
        if (!isBlank(helper.getFromDimension2())) {
            query.addAnd("dimension2", helper.getFromDimension2());
        }
        if (!isBlank(helper.getFromDimension3())) {
            query.addAnd("dimension3", helper.getFromDimension3());
        }
        if (!isBlank(helper.getFromWarehouse())) {
            query.addAnd("warehouse", helper.getFromWarehouse());
        }
        if (!isBlank(helper.getFromLocation())) {
            query.addAnd("location", helper.getFromLocation());
        }
        if (!isBlank(helper.getFromBatch())) {
            query.addAnd("batch", helper.getFromBatch());
        }
        if (!isBlank(helper.getFromSerial())) {
            query.addAnd("serialNo", helper.getFromSerial());
        }
        if (!isBlank(helper.getFromPallet())) {
            query.addAnd("pallet", helper.getFromPallet());
        }

        if (!isBlank(helper.getSelectionTable1())) {
            if (helper.getSelectionTable1().equals(InventoryItemMaster.class.getName())) {
                if (!query.containsTable(InventoryItemMaster.class.getName())) {
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                }
                addedItemMaster = true;
            }

            query.addAnd(helper.getSelectionField1(), helper.getSelectionValue1(), helper.getSelectionTable1());
        }
        if (!isBlank(helper.getSelectionTable2())) {
            if (helper.getSelectionTable2().equals(InventoryItemMaster.class.getName())) {
                if (!query.containsTable(InventoryItemMaster.class.getName())) {
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                }
                addedItemMaster = true;
            }

            query.addAnd(helper.getSelectionField2(), helper.getSelectionValue2(), helper.getSelectionTable2());
        }
        if (!isBlank(helper.getSelectionTable3())) {
            if (helper.getSelectionTable3().equals(InventoryItemMaster.class.getName())) {
                if (!query.containsTable(InventoryItemMaster.class.getName())) {
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                }
                addedItemMaster = true;
            }

            query.addAnd(helper.getSelectionField3(), helper.getSelectionValue3(), helper.getSelectionTable3());
        }
        if (!isBlank(helper.getSelectionTable4())) {
            if (helper.getSelectionTable4().equals(InventoryItemMaster.class.getName())) {
                if (!query.containsTable(InventoryItemMaster.class.getName())) {
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                }
                addedItemMaster = true;
            }

            query.addAnd(helper.getSelectionField4(), helper.getSelectionValue4(), helper.getSelectionTable4());
        }
        if (!isBlank(helper.getSelectionTable5())) {
            if (helper.getSelectionTable5().equals(InventoryItemMaster.class.getName())) {
                if (!query.containsTable(InventoryItemMaster.class.getName())) {
                    query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventorySummary.class.getName(), "itemId");
                }
                addedItemMaster = true;
            }

            query.addAnd(helper.getSelectionField5(), helper.getSelectionValue5(), helper.getSelectionTable5());
        }

        query.addField("itemId", InventorySummary.class.getName());//0
        query.addField("dimension1", InventorySummary.class.getName());//1
        query.addField("dimension2", InventorySummary.class.getName());//2
        query.addField("dimension3", InventorySummary.class.getName());//3
        query.addField("warehouse", InventorySummary.class.getName());//4
        query.addField("location", InventorySummary.class.getName());//5
        query.addField("batch", InventorySummary.class.getName());//6
        query.addField("serialNo", InventorySummary.class.getName());//7
        query.addField("pallet", InventorySummary.class.getName());//8
        query.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");//9

        query.addGroupBy(InventorySummary.class.getName(), "itemId");
        query.addGroupBy(InventorySummary.class.getName(), "dimension1");
        query.addGroupBy(InventorySummary.class.getName(), "dimension2");
        query.addGroupBy(InventorySummary.class.getName(), "dimension3");
        query.addGroupBy(InventorySummary.class.getName(), "warehouse");
        query.addGroupBy(InventorySummary.class.getName(), "location");
        query.addGroupBy(InventorySummary.class.getName(), "batch");
        query.addGroupBy(InventorySummary.class.getName(), "serialNo");
        query.addGroupBy(InventorySummary.class.getName(), "pallet");

        query.addOrderBy("itemId", InventorySummary.class.getName());
        query.addOrderBy("dimension1", InventorySummary.class.getName());
        query.addOrderBy("dimension2", InventorySummary.class.getName());
        query.addOrderBy("dimension3", InventorySummary.class.getName());
        query.addOrderBy("warehouse", InventorySummary.class.getName());
        query.addOrderBy("location", InventorySummary.class.getName());
        query.addOrderBy("batch", InventorySummary.class.getName());
        query.addOrderBy("serialNo", InventorySummary.class.getName());
        query.addOrderBy("pallet", InventorySummary.class.getName());

        query.addAndHavingAggregateFunction("SUM", InventorySummary.class.getName(), "physicalAvailable", EMCQueryConditions.GREATER_THAN, 0);

        List<Object[]> summaryDataList = util.executeGeneralSelectQuery(query, userData);
        if (summaryDataList.isEmpty()) {
            throw new EMCEntityBeanException("No data was found to generate the journal with.");
        }

        BaseJournalDefinitionTable journalDefinition = null;
        InventoryJournalMaster journalMaster = null;
        InventoryJournalLines journalLine = null;
        String toItemId = null;
        int journalLineCount = 0;
        int journalMasterCount = 0;
        List<String> journalNumbers = new ArrayList<String>() {
            @Override
            public String toString() {
                if (this.size() == 0) {
                    return "";
                }
                if (this.size() == 1) {
                    return get(0);
                } else {
                    String toString = get(0);
                    for (String s : this) {
                        toString += ", " + s;
                    }
                    return toString;
                }
            }
        };

        for (Object[] summaryData : summaryDataList) {
            if (util.compareDouble((Double) summaryData[9], 0) <= 0) {
                continue;
            }
            if (journalMaster == null || journalLineCount == 1000) {
                if (journalDefinition == null) {
                    journalDefinition = definitionBean.getJournalDefinition(helper.getJournalDefinition(), Modules.INVENTORY, userData);
                    if (journalDefinition == null) {
                        throw new EMCEntityBeanException("Failed to find the inventory journal definition.");
                    }
                }

                journalMaster = new InventoryJournalMaster();
                journalMaster.setJournalDefinitionId(helper.getJournalDefinition());
                journalMaster.setJournalContraAccount(journalDefinition.getJournalContraAccount());
                journalMaster.setJournalContraType(journalDefinition.getJournalContraType());
                journalMaster.setJournalDescription(helper.getJournalDescription() + " - " + ++journalMasterCount);
                journalMaster.setJournalDate(Functions.nowDate());

                this.insert(journalMaster, userData);

                journalNumbers.add(journalMaster.getJournalNumber());
                journalLineCount = 0;
            }
            journalLineCount++;
            journalLine = new InventoryJournalLines();
            journalLine.setJournalNumber(journalMaster.getJournalNumber());
            journalLine.setLineDate(journalMaster.getJournalDate());
            journalLine.setLineNo(journalLineCount * 10);
            journalLine.setContraAccount(journalDefinition.getJournalContraAccount());
            journalLine.setContraType(journalDefinition.getJournalContraType());

            journalLine.setItemId((String) summaryData[0]);
            journalLine.setDimension1((String) summaryData[1]);
            journalLine.setDimension2((String) summaryData[2]);
            journalLine.setDimension3((String) summaryData[3]);
            journalLine.setWarehouse((String) summaryData[4]);
            journalLine.setLocation((String) summaryData[5]);
            journalLine.setBatch((String) summaryData[6]);
            journalLine.setSerial((String) summaryData[7]);
            journalLine.setPallet((String) summaryData[8]);
            journalLine.setQuantity((Double) summaryData[9]);
            journalLine.setCost(0.01d);

            //if (isBlank(toItemId)) { -may be many items reset each time
            if (isBlank(helper.getToItem())) {
                toItemId = (String) summaryData[0];
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                query.openConditionBracket(EMCQueryBracketConditions.NONE);
                query.addOr("itemId", helper.getToItem());
                query.addOr("itemReference", helper.getToItem());
                query.closeConditionBracket();
                query.addField("itemId");
                toItemId = (String) util.executeSingleResultQuery(query, userData);
            }
            //}
            journalLine.setToItemId(toItemId);
            journalLine.setToDimension1(isBlank(helper.getToDimension1()) ? (String) summaryData[1] : helper.getToDimension1());
            journalLine.setToDimension2(isBlank(helper.getToDimension2()) ? (String) summaryData[2] : helper.getToDimension2());
            journalLine.setToDimension3(isBlank(helper.getToDimension3()) ? (String) summaryData[3] : helper.getToDimension3());
            journalLine.setToWarehouse(isBlank(helper.getToWarehouse()) ? (String) summaryData[4] : helper.getToWarehouse());
            journalLine.setToLocation(isBlank(helper.getToLocation()) ? (String) summaryData[5] : helper.getToLocation());
            journalLine.setToBatch(isBlank(helper.getToBatch()) ? (String) summaryData[6] : helper.getToBatch());
            journalLine.setToSerial(isBlank(helper.getToSerial()) ? (String) summaryData[7] : helper.getToSerial());
            journalLine.setToPallet(isBlank(helper.getToPallet()) ? (String) summaryData[8] : helper.getToPallet());

            journalLinesBean.insert(journalLine, userData);
        }

        Logger.getLogger("emc").log(Level.INFO, "The following journals were created: " + journalNumbers.toString(), userData);

        return true;
    }
}
