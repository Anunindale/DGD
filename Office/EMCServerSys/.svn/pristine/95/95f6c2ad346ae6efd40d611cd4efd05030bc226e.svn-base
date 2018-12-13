/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.batchconsolidation;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.InventoryJournalMasterLocal;
import emc.bus.inventory.register.removeregister.InventoryRemoveRegisterLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.batchconsolidation.BatchConsolidationStatus;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryBatchConsolidationLinesBean extends EMCEntityBean implements InventoryBatchConsolidationLinesLocal {

    @EJB
    private InventoryParametersLocal paramBean;
    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private InventoryJournalMasterLocal journalMasterBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryRemoveRegisterLocal registerBean;
    @EJB
    private InventoryBatchConsolidationMasterLocal consolidationMasterBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryBatchConsolidationLines record = (InventoryBatchConsolidationLines) theRecord;

        //Fetch Consolidation Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", record.getConsolidationNumber());
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the consolidation master.", userData);
            return false;
        }

        if (BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())
            || BatchConsolidationStatus.POSTED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to edit a batch consolidation in the " + consolidationMaster.getConsolidationStatus() + " state.", userData);
            return false;
        }

        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            if (fieldNameToValidate.equals("shortPicked")) {
                if (record.getQuantity().compareTo(record.getShortPicked()) < 0) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The short picked quantity may not be greater than the available quantity.", userData);
                    return false;
                }
            }
        }

        return valid;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationLines record = (InventoryBatchConsolidationLines) uobject;

        //Fetch Consolidation Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", record.getConsolidationNumber());
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())
            || BatchConsolidationStatus.POSTED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("You are not allowed to edit a batch consolidation in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        InventoryBatchConsolidationLines persistedRecord = (InventoryBatchConsolidationLines) util.findDetachedPersisted(record, userData);

        //Short Pick Value changed
        if (record.getShortPicked().compareTo(persistedRecord.getShortPicked()) != 0) {
            BigDecimal shortDiff = persistedRecord.getShortPicked().subtract(record.getShortPicked());

            handleShortPicked(record, shortDiff, userData);
        }

        return super.update(record, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationLines record = (InventoryBatchConsolidationLines) iobject;

        //Fetch Consolidation Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", record.getConsolidationNumber());
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())
            || BatchConsolidationStatus.POSTED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("You are not allowed to edit a batch consolidation in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        return super.insert(iobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryBatchConsolidationLines record = (InventoryBatchConsolidationLines) dobject;

        //Fetch Consolidation Master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
        query.addAnd("consolidationNumber", record.getConsolidationNumber());
        InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);
        if (consolidationMaster == null) {
            throw new EMCEntityBeanException("Failed to find the consolidation master.");
        }

        if (BatchConsolidationStatus.APPROVED.toString().equals(consolidationMaster.getConsolidationStatus())
            || BatchConsolidationStatus.POSTED.toString().equals(consolidationMaster.getConsolidationStatus())) {
            throw new EMCEntityBeanException("You are not allowed to edit a batch consolidation in the " + consolidationMaster.getConsolidationStatus() + " state.");
        }

        return super.delete(dobject, userData);
    }

    private void handleShortPicked(InventoryBatchConsolidationLines record, BigDecimal shortDiff, EMCUserData userData) throws EMCEntityBeanException {
        //Find Transfer Journal Line
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addAnd("recordID", record.getTransferJournalLineRecordId());
        InventoryJournalLines transferLine = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
        if (transferLine == null) {
            throw new EMCEntityBeanException("Transfer journal line not found.");
        }

        //Update before movement to free up stock for movement
        if (shortDiff.compareTo(BigDecimal.ZERO) < 0) {
            //Update Transfer Qty
            transferLine.setQuantity(transferLine.getQuantity() + shortDiff.doubleValue());
            journalLinesBean.update(transferLine, userData);
        }

        InventoryJournalLines movementJournal = null;

        //Check If Movement line exist
        if (record.getMovementJournalLineRecordId() == 0) {
            //Movement Line does not exist - Create It
            String journalNumber;
            String contraAccount;
            String contraType;
            double lineNo;

            //Fetch Movement journal reference for Consolidatiin Master
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryBatchConsolidationMaster.class);
            query.addAnd("consolidationNumber", record.getConsolidationNumber());
            InventoryBatchConsolidationMaster consolidationMaster = (InventoryBatchConsolidationMaster) util.executeSingleResultQuery(query, userData);

            //Check If Movment Master Exists
            if (consolidationMaster.getMovementJournalRecordID() == 0) {
                //Movement Master does not exist - Create It

                //Fetch Parameters
                InventoryParameters param = paramBean.getInventoryParameters(userData);

                //Fetch Movement Definition
                BaseJournalDefinitionTable journalDefinition = definitionBean.getJournalDefinition(param.getConsolidationMovementDefinition(), Modules.INVENTORY, userData);
                if (journalDefinition == null) {
                    throw new EMCEntityBeanException("Failed to find the inventory journal definition.");
                }

                //Create Master
                InventoryJournalMaster journalMaster = createJournalMaster(journalDefinition, userData);

                //Set Movement Journal record Id on Consolidation
                consolidationMaster.setMovementJournalRecordID(journalMaster.getRecordID());
                consolidationMasterBean.update(consolidationMaster, userData);

                //Get Values From Master
                journalNumber = journalMaster.getJournalNumber();
                contraAccount = journalMaster.getJournalContraAccount();
                contraType = journalMaster.getJournalContraType();
                lineNo = 0.0;
            } else {
                //Movement Master exist - Select It
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class);
                query.addTableAnd(InventoryJournalLines.class.getName(), "journalNumber", InventoryJournalMaster.class.getName(), "journalNumber");
                query.addAnd("recordID", consolidationMaster.getMovementJournalRecordID());
                query.addField("journalNumber", InventoryJournalMaster.class.getName());
                query.addField("journalContraAccount", InventoryJournalMaster.class.getName());
                query.addField("journalContraType", InventoryJournalMaster.class.getName());
                query.addFieldAggregateFunction("lineNo", InventoryJournalLines.class.getName(), "MAX");
                Object[] journalMaster = (Object[]) util.executeSingleResultQuery(query, userData);

                //Get Values From Master
                journalNumber = (String) journalMaster[0];
                contraAccount = (String) journalMaster[1];
                contraType = (String) journalMaster[2];
                lineNo = (Double) journalMaster[3];

                //Check for movement line with same item dim set
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
                query.addAnd("journalNumber", journalNumber);
                query.addAnd("itemId", transferLine.getItemId());
                query.addAnd("dimension1", transferLine.getDimension1());
                query.addAnd("dimension2", transferLine.getDimension2());
                query.addAnd("dimension3", transferLine.getDimension3());
                query.addAnd("warehouse", transferLine.getWarehouse());
                movementJournal = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);
            }

            if (movementJournal == null) {
                //Create Movement Line
                movementJournal = createJournalLine(journalNumber, contraAccount, contraType, lineNo, transferLine, shortDiff, userData);
            } else {
                //Found movement line with valid item dim set
                movementJournal.setQuantity(movementJournal.getQuantity() + shortDiff.doubleValue());

                //Do not delete registration
                userData.setUserData(5, true);
                journalLinesBean.update(movementJournal, userData);
                userData.setUserData(5, null);
            }

            //Set Movement Journal Line record Id on Consolidation
            record.setMovementJournalLineRecordId(movementJournal.getRecordID());
        } else {
            //Movement Line does exist - Select It and update quantity
            //Find Movement Line
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
            query.addAnd("recordID", record.getMovementJournalLineRecordId());
            movementJournal = (InventoryJournalLines) util.executeSingleResultQuery(query, userData);

            //Delete Register to free up reserved stock
            if (record.getMovementJournalLineRegisterRecordId() != 0) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class);
                query.addAnd("recordID", record.getMovementJournalLineRegisterRecordId());
                InventoryRemoveRegister register = (InventoryRemoveRegister) util.executeSingleResultQuery(query, userData);
                //Delete register
                registerBean.delete(register, userData);
                record.setMovementJournalLineRegisterRecordId(0);
            }

            //Update Movement Qty
            movementJournal.setQuantity(movementJournal.getQuantity() + shortDiff.doubleValue());
            //do not delete registration
            userData.setUserData(5, true);
            journalLinesBean.update(movementJournal, userData);
            userData.setUserData(5, null);
        }

        //Check if register line Exists
        if (record.getMovementJournalLineRegisterRecordId() == 0) {
            //Register Line Does Not Exist - Create it if short picked
            if (record.getShortPicked().compareTo(BigDecimal.ZERO) != 0) {
                //Create Register
                InventoryRemoveRegister register = createRegisterLine(movementJournal, transferLine, record.getShortPicked(), userData);

                //Set Movement Journal Line register record Id on Consolidation
                record.setMovementJournalLineRegisterRecordId(register.getRecordID());
            }
        } else {
            //Register Line Exists - Select It and update
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class);
            query.addAnd("recordID", record.getMovementJournalLineRegisterRecordId());
            InventoryRemoveRegister register = (InventoryRemoveRegister) util.executeSingleResultQuery(query, userData);

            //Delete register
            registerBean.delete(register, userData);
            record.setMovementJournalLineRegisterRecordId(0);

            if (record.getShortPicked().compareTo(BigDecimal.ZERO) != 0) {
                //Create Register
                register = createRegisterLine(movementJournal, transferLine, record.getShortPicked(), userData);

                //Set Movement Journal Line register record Id on Consolidation
                record.setMovementJournalLineRegisterRecordId(register.getRecordID());
            }
        }

        //Update After Movement as movement will free up stock needed
        if (shortDiff.compareTo(BigDecimal.ZERO) > 0) {
            //Update Transfer Qty
            transferLine.setQuantity(transferLine.getQuantity() + shortDiff.doubleValue());
            journalLinesBean.update(transferLine, userData);
        }
    }

    private InventoryJournalMaster createJournalMaster(BaseJournalDefinitionTable definition, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalMaster journal = new InventoryJournalMaster();
        journal.setJournalDefinitionId(definition.getJournalDefinitionId());
        journal.setJournalDescription("Crate Consolidation Movement - " + Functions.nowDateString("yyyy/MM/dd"));
        journal.setJournalDate(Functions.nowDate());
        journal.setJournalContraAccount(definition.getJournalContraAccount());
        journal.setJournalContraType(definition.getJournalContraType());

        journalMasterBean.insert(journal, userData);

        return journal;
    }

    private InventoryJournalLines createJournalLine(String journalNumber, String contraAccount, String contraType, double lineNo, InventoryJournalLines transferJournal, BigDecimal shortPicked, EMCUserData userData) throws EMCEntityBeanException {
        InventoryJournalLines line = new InventoryJournalLines();
        line.setJournalNumber(journalNumber);
        line.setContraAccount(contraAccount);
        line.setContraType(contraType);
        line.setLineNo(lineNo + 1);
        line.setLineDate(Functions.nowDate());
        line.setItemId(transferJournal.getItemId());
        line.setDimension1(transferJournal.getDimension1());
        line.setDimension2(transferJournal.getDimension2());
        line.setDimension3(transferJournal.getDimension3());
        line.setWarehouse(transferJournal.getWarehouse());
        line.setCost(transferJournal.getCost());
        line.setQuantity(shortPicked.abs().negate().doubleValue());

        journalLinesBean.insert(line, userData);

        return line;
    }

    private InventoryRemoveRegister createRegisterLine(InventoryJournalLines movementJournal, InventoryJournalLines transferJournal, BigDecimal shotPicked, EMCUserData userData) throws EMCEntityBeanException {
        InventoryRemoveRegister register = new InventoryRemoveRegister();
        register.setMasterId(movementJournal.getJournalNumber());
        register.setTransId(movementJournal.getTransId());
        register.setType(RegisterFromTypeEnum.JRN.toString());
        register.setQuantity(shotPicked.abs().negate().doubleValue());
        register.setWarehouse(transferJournal.getWarehouse());
        register.setLocation(transferJournal.getLocation());
        register.setBatch(transferJournal.getBatch());
        register.setSerial(transferJournal.getSerial());
        register.setPallet(transferJournal.getPallet());

        registerBean.insert(register, userData);

        return register;
    }
}
