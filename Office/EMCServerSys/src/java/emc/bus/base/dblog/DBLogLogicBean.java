/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.bus.base.systemtables.SystemTableLogicLocal;
import emc.bus.workflow.WFJobMasterLocal;
import emc.bus.workflow.WFMasterLocal;
import emc.entity.base.dblog.BaseDBLog;
import emc.entity.base.dblog.BaseDBLogSetup;
import emc.entity.workflow.WorkFlowJobMaster;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.external.EMCExternalWSXMLException;
import emc.functions.xml.external.EMCExternalWSXMLHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DBLogLogicBean extends EMCBusinessBean implements DBLogLogicLocal {

    @EJB
    private DBLogLocal DBLogBean;
    @EJB
    private WFJobMasterLocal jobMasterBean;
    @EJB
    private SystemTableLogicLocal systemTableBean;
    private static DBLogMap logMap;
    private static EMCExternalWSXMLHandler xmlHandler;
    @EJB
    private WFMasterLocal workFlowBean;

    @Override
    public void doDBLogging(EMCEntityClass theRecord, enumPersistOptions type, EMCUserData userData) throws EMCEntityBeanException {
        if (logMap == null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDBLogSetup.class);
            query.addAnd("companyId", userData.getCompanyId());
            logMap = new DBLogMap(util.executeGeneralSelectQuery(query, userData));
            xmlHandler = new EMCExternalWSXMLHandler();
        }
        String className = theRecord.getClass().getName();
        ArrayList<String> changeValues;
        BaseDBLogSetup setup = null;
        switch (type) {
            case INSERT:
                setup = logMap.shouldCreateLog(type, className, null);
                if (setup != null) {
                    try {
                        changeValues = getChangeValues(theRecord, type, userData);

                    } catch (Exception cnfe) {
                        throw new EMCEntityBeanException(cnfe);
                    }
                    logChanges(theRecord, type, changeValues.get(0), changeValues.get(1), userData);
                    createWF(theRecord, setup, userData);
                }
                break;
            case DELETE:
                setup = logMap.shouldCreateLog(type, className, null);
                if (setup != null) {
                    try {
                        changeValues = getChangeValues(theRecord, type, userData);
                    } catch (Exception cnfe) {
                        throw new EMCEntityBeanException(cnfe);
                    }
                    logChanges(theRecord, type, changeValues.get(0), changeValues.get(1), userData);
                    createWF(theRecord, setup, userData);
                }
                break;
            case UPDATE:
                //Get fields to log
                HashMap<String, BaseDBLogSetup> fieldsToLog = logMap.getFieldsToLog(theRecord.getClass());

                boolean shouldLog = false;
                if (fieldsToLog != null) {
                    if (fieldsToLog.containsKey("*")) { //Log all changes
                        setup = fieldsToLog.get("*");
                        shouldLog = true;
                        //action value is meaningless - create the flow
                        createWF(theRecord, setup, userData);
                    } else {
                        try {
                            EMCEntityClass persisted = findPersisted(theRecord, userData);
                            shouldLog = theRecord.checkFieldsDiffer(persisted, (String[]) fieldsToLog.keySet().toArray(new String[]{}));
                            //check all fields may be different
                            for (String field : fieldsToLog.keySet()) {
                                if (theRecord.checkFieldsDiffer(persisted, new String[]{field})) {
                                    setup = fieldsToLog.get(field);
                                    //action value specified
                                    if (!isBlank(setup.getActionValue())) {
                                        //only launch if value is the same as that of action value
                                        if (theRecord.getValueForField(field).toString().equals(setup.getActionValue())) {
                                            createWF(theRecord, setup, userData);
                                        }
                                    } else {
                                        createWF(theRecord, setup, userData);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            //Assume that record is being inserted and updated in same container transaction.
                            //Do not log update.
                            shouldLog = false;
                        }
                    }

                    if (shouldLog) {
                        try {
                            changeValues = getChangeValues(theRecord, type, userData);
                        } catch (Exception cnfe) {
                            throw new EMCEntityBeanException(cnfe);
                        }
                        logChanges(theRecord, type, changeValues.get(0), changeValues.get(1), userData);
                    }
                }
        }

    }

    /**
     * Creates the workflow
     *
     * @param theRecord
     * @param setup
     * @param userData
     * @throws EMCEntityBeanException
     */
    private void createWF(EMCEntityClass theRecord, BaseDBLogSetup setup, EMCUserData userData) throws EMCEntityBeanException {
        if (!isBlank(setup.getActionWorkFlow())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowMaster.class);
            query.addAnd("workFlowId", setup.getActionWorkFlow());
            WorkFlowMaster wfMaster = (WorkFlowMaster) util.executeSingleResultQuery(query, userData);
            if (wfMaster == null) {
                throw new EMCEntityBeanException("Failed to find WorkFlow actioned:" + setup.getActionWorkFlow());
            }
            String primary[] = theRecord.getUniqueConstraintFields();
            String reference = "";
            if (primary != null) {
                for (int j = 0; j < primary.length; j++) {
                    if (!primary[j].equals("companyId")) {
                        Object res = theRecord.getValueForFieldInEntityObject(primary[j], theRecord.getClass(), theRecord);
                        if (res != null) {
                            if (isBlank(reference)) {
                                reference = res.toString();
                            } else {
                                reference += "-" + res.toString();
                            }
                        }
                    }
                }
            }
            if (isBlank(reference)) {
                reference = Long.toString(theRecord.getRecordID());
            }
            String unique = Long.toString(dateHandler.nowDate().getTime());
            int from = unique.length() - 6;
            if (from < 0) {
                from = 0;
            }
            reference += "-" + unique.substring(from, unique.length());
            WorkFlowJobMaster jobMaster = workFlowBean.createJob(wfMaster, reference, userData);
            if (jobMaster != null) {
                jobMaster.setSourceTable(theRecord.getClass().getName());
                jobMaster.setSourceRecordId(theRecord.getRecordID());
                jobMaster.setManagerResponsible(userData.getUserName());
                jobMaster.setStartedDate(new Date());
                jobMaster.setStartedBy(userData.getUserName());
                jobMasterBean.update(jobMaster, userData);
            } else {
                throw new EMCEntityBeanException("Failed to create Task Master for WorkFlow actioned.");
            }
        }

    }

    private ArrayList<String> getChangeValues(EMCEntityClass theRecord, enumPersistOptions type, EMCUserData userData) throws ClassNotFoundException, EMCExternalWSXMLException {
        ArrayList<String> retList = new ArrayList<String>();
        switch (type) {
            case INSERT:
                //Copied from EMCEntityBean.
                theRecord.setCreatedDate(Functions.nowDate());
                theRecord.setCreatedTime(Functions.nowDate());
                if (isSystemTable(theRecord.getClass().getName())) {
                    theRecord.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
                }
                if (theRecord.getCreatedBy() == null) {
                    theRecord.setCreatedBy(userData.getUserName());
                }
                retList.add(0, null);
                retList.add(1, xmlHandler.parseObject(theRecord));
                break;
            case DELETE:
                retList.add(0, xmlHandler.parseObject(theRecord));
                retList.add(1, null);
                break;
            case UPDATE:
                //Old value
                EMCEntityClass oldEntity = findPersisted(theRecord, userData);
                retList.add(0, xmlHandler.parseObject(oldEntity));
                //Copied from EMCEntityBean
                theRecord.setModifiedBy(userData.getUserName());
                theRecord.setModifiedDate(Functions.nowDate());
                theRecord.setModifiedTime(Functions.nowDate());
                if (isSystemTable(theRecord.getClass().getName())) {
                    theRecord.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
                }
                //New value
                retList.add(1, xmlHandler.parseObject(theRecord));
                break;
        }
        return retList;
    }

    private void logChanges(EMCEntityClass theRecord, enumPersistOptions type, String oldValue, String newValue, EMCUserData userData) throws EMCEntityBeanException {
        BaseDBLog log = new BaseDBLog();
        log.setTableName(theRecord.getClass().getName());
        log.setTableLabel(theRecord.getEmcLabel());

        if (theRecord.getUniqueConstraintFields() != null) {
            log.setUniqueIdentifier(String.valueOf(theRecord.getValueForFieldInEntityObject(theRecord.getUniqueConstraintFields()[0], theRecord.getClass(), theRecord)));
        }

        log.setType(type.toString());
        log.setUserName(userData.getUserName());
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        DBLogBean.insert(log, userData);
    }

    @Override
    public boolean refresh(EMCUserData userData) {
        DBLogMap tempMap;
        try {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDBLogSetup.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            tempMap = new DBLogMap(util.executeGeneralSelectQuery(query, userData));
        } catch (Exception ex) {
            return false;
        }
        logMap = tempMap;
        return true;
    }

    private boolean isSystemTable(String tableClassName) {
        return systemTableBean.isTableSystemTable(tableClassName);
    }

    private EMCEntityClass findPersisted(EMCEntityClass theRecord, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, theRecord.getClass().getName());
        query.addAnd("recordID", theRecord.getRecordID());
        List<Object> recordList = util.executeGeneralSelectQueryInNewTx(query, userData);
        if (recordList == null || recordList.isEmpty()) {
            return null;
        } else {
            return (EMCEntityClass) recordList.get(0);
        }
    }
}