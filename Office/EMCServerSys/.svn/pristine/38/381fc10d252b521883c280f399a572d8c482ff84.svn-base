/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.journals.superclass;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.base.journals.BaseJournalApprovalGroupEmployeesLocal;
import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.EMCMessageEnum;
import emc.messages.ServerBaseMessageEnum;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;

/**
 * @description : This bean contains validation logic common to all types of journals
 *                in EMC.
 *
 * @date        : 12 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
 public abstract class JournalMasterSuperBean extends EMCEntityBean {

    @EJB
    protected BaseJournalDefinitionLocal definitionBean;
    @EJB
    protected BaseEmployeeLocal employeeBean;
    @EJB
    protected BaseJournalApprovalGroupEmployeesLocal approvalGroupEmployeesBean;
    @Resource
    private EJBContext context;

    /** Creates a new instance of JournalMasterSuperBean */
    public JournalMasterSuperBean() {
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);
        JournalMasterSuperClass mast = (JournalMasterSuperClass) vobject;
        if (mast.getJournalStatus().equals(JournalStatus.POSTED.toString())) {
            ret = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Posted Journal may not be deleted.", userData);
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            JournalMasterSuperClass master = (JournalMasterSuperClass) vobject;
            JournalMasterSuperClass persisted = (JournalMasterSuperClass) util.findDetachedPersisted(master, userData);

            if (JournalStatus.POSTED == JournalStatus.fromString(persisted.getJournalStatus())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.JR_ALREADY_POSTED, userData);
                return false;
            }
        }

        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        Object ret = null;

        JournalMasterSuperClass master = (JournalMasterSuperClass) theRecord;

        if (master.getJournalStatus() != null) {
            switch (JournalStatus.fromString(master.getJournalStatus())) {
                case CAPTURED:
                    break;
                case APPROVED:
                    logMessage(Level.SEVERE, ServerBaseMessageEnum.APPROVED_MAY_NOT_CHANGE, userData);
                    valid = false;
                    break;
                case POSTED:
                    logMessage(Level.SEVERE, ServerBaseMessageEnum.POSTED_MAY_NOT_CHANGE, userData);
                    valid = false;
                    break;
            }
        }

        if (valid) {
            if (fieldNameToValidate.equals("journalContraType") || fieldNameToValidate.equals("journalContraAccount")) {
                valid = valid && checkContraFixed(master, userData);
            }

            if (fieldNameToValidate.equals("journalDefinitionId")) {
                valid = valid && doDefinition(master, userData);
            }
        }

        if (valid) {
            ret = master;
        } else {
            ret = false;
        }

        return ret;
    }

    /** This method is used to validate the journal definition on a journal and get fields from the journal definition table. */
    private boolean doDefinition(JournalMasterSuperClass master, EMCUserData userData) {
        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master, getModule(), userData);

        if (definition == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INVALID_DEF, userData, master.getJournalDefinitionId(), getModule().toString());
            return false;
        } else {
            master.setJournalContraType(definition.getJournalContraType());
            master.setJournalContraAccount(definition.getJournalContraAccount());

            return true;
        }
    }

    /** unapprove a Journal */
    public boolean unApproveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, getJournalClass());
        query.addAnd("journalNumber", journalNumber);

        JournalMasterSuperClass master = (JournalMasterSuperClass) util.executeSingleResultQuery(query, userData);
        switch (JournalStatus.fromString(master.getJournalStatus())) {
            case CAPTURED:
                Logger.getLogger("emc").log(Level.INFO, "Journal not approved, cannot unapprove.", userData);
                return false;
            case APPROVED:
                master.setJournalApprovedBy(null);
                master.setJournalApprovedDate(null);
                master.setJournalStatus(JournalStatus.CAPTURED.toString());
                this.update(master, userData);
                Logger.getLogger("emc").log(Level.INFO, "Journal unapproved", userData);
                return true;
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal posted.  You may not unapprove", userData);
                return false;
        }

        return true;
    }

    /** This method is used to approve a journal. */
    public boolean approveJournal(String journalNumber, EMCUserData userData) throws EMCEntityBeanException {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);

        if (isBlank(employee)) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not authorized to approve a journal.", userData);
            return false;
        } else {
            List<String> approvalGroup = approvalGroupEmployeesBean.findEmployeeApprovalGroup(employee, getModule(), userData);

            if (approvalGroup == null || approvalGroup.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No approval group found.  You are not authorized to approve a journal.", userData);
                return false;
            } else {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, getJournalClass());
                query.addAnd("journalNumber", journalNumber);

                JournalMasterSuperClass master = (JournalMasterSuperClass) util.executeSingleResultQuery(query, userData);
                boolean status = true;
                switch (JournalStatus.fromString(master.getJournalStatus())) {
                    case CAPTURED:

                        if (status) {
                            master.setJournalApprovedBy(employee);
                            master.setJournalApprovedDate(Functions.nowDate());
                            master.setJournalStatus(JournalStatus.APPROVED.toString());
                            this.update(master, userData);
                            Logger.getLogger("emc").log(Level.INFO, "Journal approved", userData);
                        }
                        return true;
                    case APPROVED:
                    //Fall through
                    case POSTED:
                        Logger.getLogger("emc").log(Level.SEVERE, "Journal already approved.", userData);
                        return false;
                }
            }

            return true;
        }
    }

    /** This method checks whether contra type and contra account may be changed. */
    private boolean checkContraFixed(JournalMasterSuperClass master, EMCUserData userData) {
        boolean ret = true;

        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master, getModule(), userData);

        if (definition != null) {
            if (definition.getJournalContraFixed()) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.CONTRA_FIXED, userData);
                ret = false;
            }
        }

        return ret;
    }

    /** This method is used to approve a journal. */
    public void approveJournal(JournalMasterSuperClass journal, EMCUserData userData) throws EMCEntityBeanException {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);

        if (isBlank(employee)) {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not authorized to approve a journal.", userData);
            return;
        } else {
            List<String> approvalGroup = approvalGroupEmployeesBean.findEmployeeApprovalGroup(employee, getModule(), userData);

            if (approvalGroup == null || approvalGroup.isEmpty()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No approval group found.  You are not authorized to approve a journal.", userData);
                return;
            } else {
                boolean status = true;
                switch (JournalStatus.fromString(journal.getJournalStatus())) {
                    case CAPTURED:
                        if (status) {
                            journal.setJournalApprovedBy(employee);
                            journal.setJournalApprovedDate(Functions.nowDate());
                            journal.setJournalStatus(emc.enums.base.journals.JournalStatus.APPROVED.toString());
                            this.update(journal, userData);
                            Logger.getLogger("emc").log(Level.INFO, "Journal approved", userData);
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
    }

    /**
     * Checks that the specified journal can be posted.  If so, it calls doPost(),
     * which is implementation-specific.
     *
     * @param journal Journal to be posted.
     * @param userData User data.
     * @return A boolean indicating whether the journal was successfully posted.
     * @throws EMCEntityBeanException
     */
    public boolean attemptPost(JournalMasterSuperClass journal, EMCUserData userData) throws EMCEntityBeanException {
        boolean status = true;
        switch (JournalStatus.fromString(journal.getJournalStatus())) {
            case CAPTURED:
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
                query.addAnd("journalDefinitionId", journal.getJournalDefinitionId());
                query.addAnd("companyId", userData.getCompanyId());
                query.addField("journalApprovalRequired");
                if ((Boolean) util.executeSingleResultQuery(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Approve the journal first.", userData);
                    status = false;
                    break;
                }//else fall through
            case APPROVED:
                return doPost(journal, userData);
            case POSTED:
                Logger.getLogger("emc").log(Level.SEVERE, "Journal already posted.", userData);
                status = false;
                break;
            default:
                break;
        }
        return status;
    }

    /**
     * Validates a journal.
     * @param record Journal master to validate.
     * @param userData User data.
     * @return A boolean indicating whether the journal is valid.
     */
    public final boolean validateJournal(JournalMasterSuperClass record, EMCUserData userData) {
        boolean status = true;
        switch (JournalStatus.fromString(record.getJournalStatus())) {
            case CAPTURED:
            case APPROVED:
                try {
                    if (doPost(record, userData)) {
                        //Rollback post
                        context.setRollbackOnly();
                        return true;
                    }
                    break;
                } catch (EMCEntityBeanException ex) {
                    logMessage(Level.SEVERE, ex.getMessage(), userData);
                    logMessage(Level.SEVERE, getValidateFailMessage(), userData);
                    return false;
                }
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
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        JournalMasterSuperClass journalMaster = (JournalMasterSuperClass) iobject;

        BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(journalMaster, getModule(), userData);

        if (isBlank(journalMaster.getJournalContraAccount())) {
            journalMaster.setJournalContraAccount(definition.getJournalContraAccount());
        }

        if (isBlank(journalMaster.getJournalContraType())) {
            journalMaster.setJournalContraType(definition.getJournalContraType());
        }

        return super.insert(iobject, userData);
    }

    /** Returns the module containing this the journal associated with this bean. */
    protected abstract Modules getModule();

    /** Returns journal entity class. */
    protected abstract Class getJournalClass();

    /** Posts a journal. */
    protected abstract boolean doPost(JournalMasterSuperClass journal, EMCUserData userData) throws EMCEntityBeanException;

    /** Returns a message to be logged when validation succeeds. */
    protected abstract EMCMessageEnum getValidateSuccessMessage();

    /** Returns a message to be logged when validation fails. */
    protected abstract EMCMessageEnum getValidateFailMessage();
}
