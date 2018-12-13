/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.journals;

import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.debtors.journals.DebtorsJournalDirection;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.enums.inventory.journals.MovementDirections;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseJournalDefinitionBean extends EMCEntityBean implements BaseJournalDefinitionLocal {

    /** Creates a new instance of BaseJournalDefinitionBean. */
    public BaseJournalDefinitionBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        BaseJournalDefinitionTable definition = (BaseJournalDefinitionTable) theRecord;

        if (fieldNameToValidate.equals("approvalBy")) {
            ret = ret && validateApprovalBy(definition, userData);
        } else if (fieldNameToValidate.equals("journalAccessGroup")) {
            ret = ret && validateAccessGroup(definition, userData);
        } else {
            if (fieldNameToValidate.equals("journalModule") && ret) {
                Modules journalModule = Modules.fromString(definition.getJournalModule());
                if (Modules.INVENTORY.equals(journalModule)) {
                    definition.setMovementDirection(MovementDirections.IN.toString());
                } else {
                    if (Modules.DEBTORS.equals(journalModule)) {
                        definition.setMovementDirection(DebtorsJournalDirection.CREDIT.toString());
                    }
                }
            } else {
                if (fieldNameToValidate.equals("movementDirection")) {
                    if (Modules.INVENTORY.equals(Modules.fromString(definition.getJournalModule())) && !InventoryJournalTypes.MOVEMENT.equals(InventoryJournalTypes.fromString(definition.getJournalType()))) {
                        Logger.getLogger("emc").log(Level.SEVERE, definition.getDisplayLabelForField(fieldNameToValidate, userData) + " may only be specified for Movement journals.", userData);
                        ret = false;
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        BaseJournalDefinitionTable definition = (BaseJournalDefinitionTable) vobject;

        ret = ret && validateApprovalBy(definition, userData);

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        BaseJournalDefinitionTable definition = (BaseJournalDefinitionTable) vobject;

        ret = ret && validateApprovalBy(definition, userData);

        if (ret) {
            BaseJournalDefinitionTable persisted = (BaseJournalDefinitionTable) util.findDetachedPersisted(definition, userData);

            if (!definition.getJournalType().equals(persisted.getJournalType())) {
                ret = validateTypeChange(definition, userData);
            }
        }

        return ret;
    }

    /** This method validates the "approvalBy" field */
    private boolean validateApprovalBy(BaseJournalDefinitionTable definition, EMCUserData userData) {
        if (isBlank(definition.getApprovalBy())) {
            if (definition.getJournalApprovalRequired()) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.APPROVAL_REQ, userData);
                return false;
            }
        } else {
            //Check that Approval Group is in the correct module.
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalApprovalGroups.class);
            query.addField("groupModule");
            query.addAnd("journalApprovalGroupId", definition.getApprovalBy());

            String approvalGroupModule = (String) util.executeSingleResultQuery(query, userData);
            if (approvalGroupModule == null || !util.checkObjectsEqual(approvalGroupModule, definition.getJournalModule())) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.WRONG_GRP_MODULE, userData, definition.getApprovalBy(), definition.getJournalModule());
                return false;
            }
        }

        return true;
    }

    /** This method validates the Access Group on a Journal Definition. */
    private boolean validateAccessGroup(BaseJournalDefinitionTable definition, EMCUserData userData) {
        if (!isBlank(definition.getJournalAccessGroup())) {         
            //Check that Access Group is in the correct module.
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalAccessGroups.class);
            query.addField("groupModule");
            query.addAnd("accessGroupId", definition.getJournalAccessGroup());

            String approvalGroupModule = (String) util.executeSingleResultQuery(query, userData);
            if (approvalGroupModule == null || !util.checkObjectsEqual(approvalGroupModule, definition.getJournalModule())) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.WRONG_GRP_MODULE, userData, definition.getJournalAccessGroup(), definition.getJournalModule());
                return false;
            }
        }

        return true;
    }

    /** Returns the journal definition associated with a journal.  Use when journal definition is already specified on journal master.  Only works for Inventory journals*/
    public BaseJournalDefinitionTable getJournalDefinition(InventoryJournalMaster master, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class.getName());
        query.addAnd("journalDefinitionId", master.getJournalDefinitionId());

        return (BaseJournalDefinitionTable) util.executeSingleResultQuery(query, userData);
    }

    /** Returns the journal definition associated with a journal.  Use when journal definition is already specified on journal master.  This method only works for Inventory journals.  */
    @Deprecated()
    public BaseJournalDefinitionTable getJournalDefinition(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class.getName());
        query.addAnd("journalNumber", journalNumber);

        InventoryJournalMaster master = (InventoryJournalMaster) util.executeSingleResultQuery(query, userData);
        return getJournalDefinition(master, userData);
    }

    public BaseJournalDefinitionTable getJournalDefinition(JournalMasterSuperClass journal, Modules journalModule, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
        query.addAnd("journalModule", journalModule.toString());
        query.addAnd("journalDefinitionId", journal.getJournalDefinitionId());

        return (BaseJournalDefinitionTable) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns a JournalDefinitionTable instance.
     * @param journalDefinitionId Id of journal definition to search for.
     * @param module Journal definition module.
     * @param userData User data.
     * @return A JournalDefinitionTable instance, or null, if nothing is found.
     */
    public BaseJournalDefinitionTable getJournalDefinition(String journalDefinitionId, Modules module, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
        query.addAnd("journalModule", module.toString());
        query.addAnd("journalDefinitionId", journalDefinitionId);

        return (BaseJournalDefinitionTable) util.executeSingleResultQuery(query, userData);
    }

    private boolean validateTypeChange(BaseJournalDefinitionTable definition, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalMaster.class);
        query.addAnd("journalDefinitionId", definition.getJournalDefinitionId());
        query.addAnd("journalStatus", JournalStatus.POSTED.toString(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "An open Inventory Journal exists that use the selected journal definition.", userData);
            return false;
        }
        query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
        query.addAnd("journalDefinitionId", definition.getJournalDefinitionId());
        query.addAnd("journalStatus", JournalStatus.POSTED.toString(), EMCQueryConditions.NOT);
        if (util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "An open Debtors Journal exists that use the selected journal definition.", userData);
            return false;
        }

        return true;
    }
}
