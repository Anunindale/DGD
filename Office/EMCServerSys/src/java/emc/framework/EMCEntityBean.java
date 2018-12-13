/* 
 * EMCEntityBean.java
 *
 * Created on 09 October 2007, 04:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.framework;

import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.dblog.DBLogLogicLocal;
import emc.bus.base.numbersequences.BaseNumberSequenceLogicLocal;
import emc.bus.base.systemtables.SystemTableLogicLocal;
import emc.bus.base.tables.TablesLocal;
import emc.commands.EMCCommands;
import emc.constants.systemConstants;
import emc.datatypes.EMCDataType;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.dblog.BaseDBLog;
import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.functions.Functions;
import emc.messages.ServerBaseMessageEnum;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCTable;
import emc.tables.EMCTableInterface;
import emc.tables.EMCTableRelation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author rico
 */
public class EMCEntityBean extends EMCBean implements EMCEntityBeanProtectedInterface, EMCEntityBeanLocalInterface {

    private boolean companySuperTable = false;
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private EMCCommandManagerLocal commandManager;
    @EJB
    private SystemTableLogicLocal systemTableBean;
    @EJB
    private BaseNumberSequenceLogicLocal numberSqBean;
    @EJB
    private TablesLocal relations;
    @EJB
    private DBLogLogicLocal logBean;
    @EJB
    private BaseDocRefLocal documentBean;
    @Resource
    private EJBContext context;
    @Resource
    private TransactionSynchronizationRegistry registry;

    /**
     * Creates a new instance of EMCEntityBean
     */
    public EMCEntityBean() {
    }

    /**
     * Does field validation on entry of data into any field of any record of
     * EMC
     *
     * @param fieldNameToValidate this is the entityclass field name
     * @param theRecord - entityclass record
     * @param userData
     * @return null or false to fail, return true or object to pass
     */
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {

        return doFieldValidation(fieldNameToValidate, theRecord, userData);
    }

    /**
     * Honour relationships for all dataType fields
     */
    public boolean doHonourDataTypesOnInsert(Object theRecord, EMCUserData userData) {
        boolean ret = true;
        EMCDataType dt;
        EMCTableInterface eTI = (EMCTableInterface) theRecord;
        HashMap<String, EMCDataType> dtMap = eTI.getFieldDataTypeMapper();
        Set<String> fieldKeys = dtMap.keySet();
        for (String key : fieldKeys) {
            dt = dtMap.get(key);
            if (dt != null) {
                //numberSeq
                if (dt.isNumberSeqAllowed()) {
                    Object keyValue = eTI.getValueForFieldInEntityObject(key, theRecord.getClass(), theRecord);
                    if (isBlank(keyValue)) {
                        populateNumberSequenceField(theRecord, key, dt, userData);
                    }
                }
                //Mandatory
                if (dt.isMandatory()) {
                    if (isBlank(eTI.getValueForFieldInEntityObject(key, theRecord.getClass(), theRecord))) {
                        Logger.getLogger("emc").log(Level.SEVERE, dt.getEmcLabel() + " is a required field. Please enter data.", userData);
                        return false;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public boolean populateNumberSequenceField(Object theRecord, String key, EMCDataType dt, EMCUserData userData) {
        Object transaction = registry.getTransactionKey();
        EMCTableInterface eTI = (EMCTableInterface) theRecord;
        List numSeqRes = numberSqBean.getNextAvailableSequenceNumber(theRecord.getClass().getName(), key, theRecord, userData);
        String number = null;
        if (numSeqRes != null && numSeqRes.size() > 1 && numSeqRes.get(0) != null) {
            number = numSeqRes.get(0).toString();
        }
        if (number != null) {
            if (!eTI.setValueForFieldInEntityObject(key, theRecord.getClass(), theRecord, number)) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to access field " + key + " for insert validation.", userData);
                }
                return false;
            } else {
                //load query to set to used on success
                EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                query.addAnd("recordID", numSeqRes.get(1));
                query.addSet("status", 2);//set it to used
                commandManager.setTransactionSucceedQuery(transaction, query.toString());
                //load query to use on fail
                EMCQuery fQuery = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                fQuery.addAnd("recordID", numSeqRes.get(1));
                fQuery.addSet("status", 0);//set it to available
                commandManager.setTransactionFailQuery(transaction, fQuery.toString());

                return true;
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not get a number from number sequence for " + dt.getEmcLabel(), userData);
            return false;
        }
    }

    /**
     * Honours datatypes on fieldValidation
     *
     * @param fieldNameToValidate
     * @param theRecord
     * @param userData
     * @return
     */
    public boolean doFieldValidation(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = true;
        EMCTableInterface eTI = (EMCTableInterface) theRecord;
        EMCDataType dt = eTI.getDataType(fieldNameToValidate, userData);
        if (dt != null) {
            Object fieldVal;
            String value = "";
            fieldVal = eTI.getValueForFieldInEntityObject(fieldNameToValidate, theRecord.getClass(), theRecord);
            if (fieldVal == null) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Could not validate Existence of " + fieldNameToValidate + " reflection failed", userData);
                }
            } else {
                value = fieldVal.toString();
            }
            //Relations
            if (dt.getRelatedTable() != null && dt.getRelatedField() != null) {
                boolean localret = util.exists(dt.getRelatedTable(), dt.getRelatedField(), value, userData);
                if (!localret && !isBlank(value)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The value entered for " + dt.getEmcLabel() + " does not exist. Please reselect.", userData);
                    return false;
                }
            }
            //Number Seq
            if (dt.isNumberSeqAllowed()) {
                if (!numberSqBean.isManualEntryAllowed(theRecord.getClass().getName(), fieldNameToValidate, theRecord, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, dt.getEmcLabel() + " is controlled by a number sequence, manual entry not allowed.", userData);
                    return false;

                }
            }
            //Mandatory
            if (dt.isMandatory()) {
                if (value.trim().equals("")) {
                    Logger.getLogger("emc").log(Level.SEVERE, dt.getEmcLabel() + " is mandatory. Please enter data.", userData);
                    return false;
                }
            }
        }

        return ret;
    }

    /**
     * does a direct update of the entity use with care as it bypasses all
     * validation logic
     *
     * @param dobject
     */
    public final void doUpdate(Object dobject, EMCUserData userData) {
        EMCEntityClass temp = (EMCEntityClass) dobject;
        updateStandardRegisters(temp, userData);
        entityManager.merge(dobject);
    }

    /**
     * Does a direct delete of the entity use with care as it bypasses all
     * validation logic
     *
     * @param dobject
     */
    public final void doDelete(Object dobject, EMCUserData userData) {
        entityManager.remove(dobject);
    }

    /**
     * Does a direct insert of the entity use with care as it bypasses all
     * validation logic
     *
     * @param dobject
     */
    public final void doInsert(Object dobject, EMCUserData userData) {
        EMCEntityClass temp = (EMCEntityClass) dobject;
        insertStandardRegisters(temp, userData);
        entityManager.persist(dobject);
    }

    /**
     * Tests the object and inserts if doInsertValidation succeed. Rolls the
     * transaction back if doInsertValidation fails.
     *
     * @param iobject
     * @param userData
     * @return
     */
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        try {
            if (!validate(enumPersistOptions.INSERT, (EMCTable) iobject, userData)) {
                //Calling this after calling setRollbackOnly() with cause the getMessage() method to throw an exception.
                String message = super.getMessage(ServerBaseMessageEnum.IVALFAIL, userData);
                context.setRollbackOnly();
                throw new EMCEntityBeanException(message);
            } else {
                doInsert(iobject, userData); // if this fails it will be caught only on em.flush wich we cannot do here
                if (!(iobject instanceof BaseDBLog)) {
                    logBean.doDBLogging((EMCEntityClass) iobject, enumPersistOptions.INSERT, userData);
                }
            }
            return iobject;
        } catch (Exception ex) {
            context.setRollbackOnly();
            throw new EMCEntityBeanException("Insert failed:" + ex.getMessage());
        }
    }

    /**
     * Tests the object and updates it if doUpdateValidation succeeds rolls back
     * the transaction if doUpdateValidation fails
     *
     * @param uobject
     * @param userData
     * @return
     */
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        try {

            if (!validate(enumPersistOptions.UPDATE, (EMCTable) uobject, userData)) {
                //Calling this after calling setRollbackOnly() with cause the getMessage() method to throw an exception.
                String message = super.getMessage(ServerBaseMessageEnum.UVALFAIL, userData);
                context.setRollbackOnly();
                throw new EMCEntityBeanException(message);
            } else {
                if (!(uobject instanceof BaseDBLog)) {
                    logBean.doDBLogging((EMCEntityClass) uobject, enumPersistOptions.UPDATE, userData);
                }
                doUpdate(uobject, userData);
            }
            return uobject;
        } catch (Exception ex) {
            context.setRollbackOnly();
            ex.printStackTrace();
            throw new EMCEntityBeanException("Update failed:" + ex.getMessage());
        }
    }

    /**
     * Tests the obejct and deletes is if doDeleteValidation succeeds rolls back
     * the transaction if it fails and throws exception
     *
     * @param dobject
     * @param userData
     * @return
     */
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        try {
            if (!validate(enumPersistOptions.DELETE, (EMCTable) dobject, userData)) {
                //Calling this after calling setRollbackOnly() with cause the getMessage() method to throw an exception.
                String message = super.getMessage(ServerBaseMessageEnum.DVALFAIL, userData);
                context.setRollbackOnly();
                throw new EMCEntityBeanException(message);
            } else {
                EMCEntityClass a = (EMCEntityClass) dobject;
                if (a.getRecordID() != 0) {
                    deleteAttachments(a, userData);
                    Object b = entityManager.find(dobject.getClass(), a.getRecordID());
                    doDelete(b, userData);
                    if (!(dobject instanceof BaseDBLog)) {
                        logBean.doDBLogging((EMCEntityClass) b, enumPersistOptions.DELETE, userData);
                    }
                }
            }

            return dobject;

        } catch (Exception ex) {
            context.setRollbackOnly();
            throw new EMCEntityBeanException("Delete Failed:" + ex.getMessage());
        }
    }

    private void deleteAttachments(EMCEntityClass entity, EMCUserData userData) throws EMCEntityBeanException {
        if (entity.getHasAttachment()) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class.getName());
            query.addAnd("refTableName", entity.getClass().getSimpleName());
            query.addAnd("refRecId", entity.getRecordID());
            List<BaseDocuRefTable> docList = util.executeGeneralSelectQuery(query, userData);
            for (BaseDocuRefTable doc : docList) {
                documentBean.delete(doc, userData);
            }
        }
    }

    /**
     * This method maps the different validation code to the
     * insert/delete/update do not overwrite this method, rather overwrite the
     * do(Insert)(Update)(Delete)Validation mehods
     *
     * @param theOption
     * @param vobject
     * @param userData
     * @return
     */
    private boolean validate(enumPersistOptions theOption, EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean result = true;
        try {
            switch (theOption) {
                case INSERT:
                    result = doInsertValidation(vobject, userData);
                    break;
                case DELETE:
                    result = doDeleteValidation(vobject, userData);
                    break;
                case UPDATE:
                    result = doUpdateValidation(vobject, userData);
                    break;
            }
        } catch (Exception ex) {
            throw new EMCEntityBeanException("Do Validation failed:" + ex.getMessage());
        }
        return result;
    }

    /**
     * Overwrite this method to do validation before insert
     *
     * @param vobject
     * @param userData
     * @return
     */
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean allowPersist = true;
        EMCEntityClass toBeTested = (EMCEntityClass) vobject;
        if (this.isBlank(toBeTested.getCompanyId())) {
            if (isSystemTable(vobject.getClass().getName())) {
                toBeTested.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
            } else {
                if (isBlank(userData.getCompanyId())) {
                    allowPersist = false;
                    Logger.getLogger("emc").log(Level.SEVERE, "Company Id blank,both UserData and on record", userData);
                } else {
                    toBeTested.setCompanyId(userData.getCompanyId());
                }

            }

        }
        //honour Data Types
        if (allowPersist) {
            allowPersist = doHonourDataTypesOnInsert(vobject, userData);
        }
        return allowPersist;
    }

    /**
     * Overwrite to do own deleteValidation should always call super as it
     * honours table relations
     *
     * @param vobject
     * @param userData
     * @return
     */
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean allowAction = true;
        //test relations
        if (allowAction) {
            DefaultMutableTreeNode root = testRelations(enumPersistOptions.DELETE, (EMCTable) vobject, userData);
            if (((DefaultMutableTreeNode) root.getChildAt(2)).getChildCount() > 0) {
                allowAction = false;
            }
        }
        if (allowAction) {
            allowAction = honourTableRelationsDelete(vobject, userData);
        }
        return allowAction;
    }

    /**
     * Should always call super it handles relations
     *
     * @param vobject
     * @param userData
     * @return
     */
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean allowPersist = true;
        EMCEntityClass toBeTested = (EMCEntityClass) vobject;
        if (isBlank(toBeTested.getCompanyId())) {
            if (isSystemTable(vobject.getClass().getName())) {
                toBeTested.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
            } else {
                if (isBlank(userData.getCompanyId())) {
                    allowPersist = false;
                    Logger.getLogger("emc").log(Level.SEVERE, "Company Id blank,both UserData and on record", userData);
                } else {
                    toBeTested.setCompanyId(userData.getCompanyId());
                }
            }
        }
        EMCEntityClass persisted = (EMCEntityClass) entityManager.find(vobject.getClass(), toBeTested.getRecordID());
        if (toBeTested.getVersion() < persisted.getVersion()) {
            allowPersist = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Newer version in database, please refresh data. (" + toBeTested.getClass().getName() + ")", userData);
        }
        //test relations
        if (allowPersist) {
            DefaultMutableTreeNode root = testRelations(enumPersistOptions.UPDATE, (EMCTable) vobject, userData);
            if (((DefaultMutableTreeNode) root.getChildAt(2)).getChildCount() > 0) {
                allowPersist = false;
            }
        }
        //honour the relations
        if (allowPersist) {
            allowPersist = honourTableRelationsUpdate(vobject, userData);
        }
        return allowPersist;
    }

    public DefaultMutableTreeNode testRelations(enumPersistOptions theOption, EMCTable theTable, EMCUserData userData) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(theOption.toString());
        DefaultMutableTreeNode cascade = new DefaultMutableTreeNode("ALSO " + theOption.toString());
        DefaultMutableTreeNode ignore = new DefaultMutableTreeNode("IGNORED");
        DefaultMutableTreeNode restrict = new DefaultMutableTreeNode("DISALLOW " + theOption.toString());
        DefaultMutableTreeNode clearField = new DefaultMutableTreeNode("CLEAR FIELD ON");
        root.add(cascade);//0 these postions are used in this class if change take care 
        root.add(ignore); //1
        root.add(restrict); //2
        root.add(clearField); //3
        return testRelationDetail(root, theOption, theTable, userData);
    }

    private DefaultMutableTreeNode testRelationDetail(DefaultMutableTreeNode node, enumPersistOptions theOption, EMCTable theTable, EMCUserData userData) {
        Class realTableClass = theTable.getClass();
        if (theTable.isDataSource()) {
            realTableClass = realTableClass.getSuperclass();
        }
        HashMap<String, List> fieldsWithRelations = relations.getRelations(realTableClass.getName(), userData);
        EMCQuery q;
        List res;
        String numAffected;
        if (fieldsWithRelations != null) {
            //get the keys for the hashmap
            Set<String> fieldList = fieldsWithRelations.keySet();
            //loop through fields that have relations
            for (String key : fieldList) {
                List<EMCTableRelation> children = fieldsWithRelations.get(key);
                EMCTable persisted = (EMCTable) entityManager.find(realTableClass, theTable.getValueForFieldInEntityObject("recordID", theTable.getClass(), theTable));
                Object persistedObjVal = persisted.getValueForFieldInEntityObject(key, persisted.getClass(), persisted);
                String persistedVal = "";
                if (persistedObjVal != null) {
                    persistedVal = persistedObjVal.toString();
                }
                Object oldObjVal = theTable.getValueForFieldInEntityObject(key, theTable.getClass(), theTable);
                String oldVal = "";
                if (oldObjVal != null) {
                    oldVal = oldObjVal.toString();
                }
                if (children != null && (!(persistedVal.trim().equals(oldVal.trim())) || theOption.toString().equals(enumPersistOptions.DELETE.toString()))) {
                    //loop through tables on this field

                    for (EMCTableRelation relation : children) {
                        //test deleteAction
                        //build query
                        q = new EMCQuery(enumQueryTypes.SELECT, relation.getChildClassPath());
                        q.addAnd(relation.getChildFieldName(), persistedVal);
                        if (!(key.equals("companyId")) && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
                            q.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
                        }
                        res = entityManager.createQuery(q.getCountQuery().toString()).getResultList();
                        numAffected = "0";
                        if (res.size() > 0) {
                            try {
                                numAffected = res.get(0).toString();
                            } catch (Exception e) {
                            }
                        }
                        if (Integer.parseInt(numAffected) > 0) {
                            switch (theOption) {
                                case UPDATE:
                                    switch (relation.getUpdateAction()) {
                                        case CASCADE:
                                            ((DefaultMutableTreeNode) node.getChildAt(0)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        case IGNORE:
                                            ((DefaultMutableTreeNode) node.getChildAt(1)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        case CLEARFIELD:
                                            ((DefaultMutableTreeNode) node.getChildAt(3)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        case RESTRICT:
                                            ((DefaultMutableTreeNode) node.getChildAt(2)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case DELETE:
                                    switch (relation.getDeleteAction()) {
                                        case CASCADE:
                                            ((DefaultMutableTreeNode) node.getChildAt(0)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            //TODO honour relations further on
                                            break;
                                        case IGNORE:
                                            ((DefaultMutableTreeNode) node.getChildAt(1)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        case CLEARFIELD:
                                            ((DefaultMutableTreeNode) node.getChildAt(3)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        case RESTRICT:
                                            ((DefaultMutableTreeNode) node.getChildAt(2)).add(new DefaultMutableTreeNode(relation.getChildTableEmcLabel() + ": related rows (" + numAffected + ")"));
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                }

            }
        }
        return node;
    }

    /**
     * Honours relations on update
     *
     * @param theTable
     * @param userData
     * @return
     */
    private boolean honourTableRelationsUpdate(EMCTable theTable, EMCUserData userData) throws EMCEntityBeanException {
        //get the hashmap for this table, null if no relations
        Class realTableClass = theTable.getClass();
        if (theTable.isDataSource()) {
            realTableClass = realTableClass.getSuperclass();
        }
        boolean toContinue = true;
        Object oldValue;
        HashMap<String, List> fieldsWithRelations = relations.getRelations(realTableClass.getName(), userData);
        if (fieldsWithRelations != null) {
            EMCTable persisted = (EMCTable) entityManager.find(realTableClass, ((EMCEntityClass) theTable).getRecordID());
            //get the keys for the hashmap
            Set<String> fieldList = fieldsWithRelations.keySet();
            //loop through fields that have relations
            if (persisted != null) {
                for (String key : fieldList) {
                    oldValue = persisted.getValueForFieldInEntityObject(key, persisted.getClass(), persisted);
                    List<EMCTableRelation> children = fieldsWithRelations.get(key);
                    if (children != null && oldValue != null && !oldValue.equals(theTable.getValueForFieldInEntityObject(key, theTable.getClass(), theTable))) {
                        //loop through tables on this field
                        for (EMCTableRelation relation : children) {
                            //test deleteAction
                            switch (relation.getUpdateAction()) {
                                case CASCADE:
                                    toContinue = doCascadeUpdate(relation, key, theTable, oldValue, userData);
                                    break;
                                case IGNORE:
                                    toContinue = doIgnoreDeleteUpdate(relation, key, theTable, userData);
                                    break;
                                case CLEARFIELD:
                                    toContinue = doClearFieldDeleteUpdate(relation, key, theTable, userData);
                                    break;
                                case RESTRICT:
                                    toContinue = doRestrictDeleteUpdate(relation, key, theTable, userData);
                                    break;
                                default:
                                    break;
                            }
                            if (!toContinue) {
                                return false;
                            }
                        }
                    }
                    if (!toContinue) {
                        return false;
                    }
                }
            }
        }
        return toContinue;
    }

    /**
     * Does a check on related tables to ensure data integrity
     *
     * @param theTable
     * @param userData
     * @return
     */
    private boolean honourTableRelationsDelete(EMCTable theTable, EMCUserData userData) throws EMCEntityBeanException {
        //get the hashmap for this table, null if no relations
        boolean toContinue = true;
        Class realTableClass = theTable.getClass();
        if (theTable.isDataSource()) {
            realTableClass = realTableClass.getSuperclass();
        }
        HashMap<String, List> fieldsWithRelations = relations.getRelations(realTableClass.getName(), userData);
        if (fieldsWithRelations != null) {
            //get the keys for the hashmap
            Set<String> fieldList = fieldsWithRelations.keySet();
            //loop through fields that have relations
            for (String key : fieldList) {
                List<EMCTableRelation> children = fieldsWithRelations.get(key);
                if (children != null) {
                    //loop through tables on this field
                    for (EMCTableRelation relation : children) {
                        //test deleteAction
                        switch (relation.getDeleteAction()) {
                            case CASCADE:
                                toContinue = doCascadeDelete(relation, key, theTable, userData);
                                break;
                            case IGNORE:
                                toContinue = doIgnoreDeleteUpdate(relation, key, theTable, userData);
                                break;
                            case CLEARFIELD:
                                toContinue = doClearFieldDeleteUpdate(relation, key, theTable, userData);
                                break;
                            case RESTRICT:
                                toContinue = doRestrictDeleteUpdate(relation, key, theTable, userData);
                                break;
                            default:
                                break;
                        }
                        if (!toContinue) {
                            return false;
                        }
                    }
                }
                if (!toContinue) {
                    return false;
                }
            }
        }
        return toContinue;
    }

    /**
     *
     * @param relation specifies a child table
     * @param field on this entity class that is related to child table
     * @param theTable this entity class
     * @param userData
     * @return false if the child has data in else true - false will cause
     * delete validation to fail
     */
    private boolean doRestrictDeleteUpdate(EMCTableRelation relation, String field, EMCTable theTable, EMCUserData userData) {
        //see if data exists
        if (util.exists(relation.getChildClassPath(), relation.getChildFieldName(), theTable.getValueForFieldInEntityObject(field, theTable.getClass(), theTable).toString(), userData)) {
            Logger.getLogger("emc").log(Level.INFO, "Table " + theTable.getClass().getName() + " restricts deletion of this record.", userData);
            return false;
        } else {
            return true;
        }

    }

    /**
     *
     * @param relation specifies a child table
     * @param field on this entity class that is related to child table
     * @param theTable this entity class
     * @param userData
     * @return true by default - false will cause delete validation to fail
     */
    private boolean doIgnoreDeleteUpdate(EMCTableRelation relation, String field, EMCTable theTable, EMCUserData userData) {
        return true;
    }

    /**
     *
     * @param relation specifies a child table
     * @param field on this entity class that is related to child table
     * @param theTable this entity class
     * @param userData
     * @return
     */
    private boolean doClearFieldDeleteUpdate(EMCTableRelation relation, String field, EMCTable theTable, EMCUserData userData) {
        Object transaction = registry.getTransactionKey();
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, relation.getChildClassPath());
        query.addSet(relation.getChildFieldName(), "");
        query.addAnd(relation.getChildFieldName(), theTable.getValueForFieldInEntityObject(field, theTable.getClass(), theTable).toString());
        if (!(field.equals("companyId")) && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
            query.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
        }
        commandManager.setTransactionSucceedQuery(transaction, query.toString());
        return true;
    }

    /**
     *
     * @param relation specifies a child table
     * @param field on this entity class that is related to child table
     * @param theTable this entity class
     * @param userData
     * @return
     */
    private boolean doCascadeDelete(EMCTableRelation relation, String field, EMCTable theTable, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = true;
        Object transaction = registry.getTransactionKey();
        EMCQuery query;
        Object valueToLookFor;
        //get related stuff
        //don't want to do this on company Table
        if (!(field.equals("companyId"))) {
            try {
                //ret = honourTableRelationsDelete(created, userData);
            } catch (Exception e) {
                ret = false;
                Logger.getLogger("emc").log(Level.WARNING, "Could not create child class for Cascade Delete: " + relation.getChildClassPath(), userData);
            }
        }
        //ok now delete this child
        if (ret) {
            switch (relation.getCallBeanOption()) {
                case SQL:
                    query = new EMCQuery(enumQueryTypes.DELETE, relation.getChildClassPath());
                    valueToLookFor = theTable.getValueForFieldInEntityObject(field, theTable.getClass(), theTable);
                    if (valueToLookFor != null) {
                        query.addAnd(relation.getChildFieldName(), valueToLookFor.toString());
                        if (!field.equals("companyId") && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
                            query.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
                        }
                        commandManager.setTransactionSucceedQuery(transaction, query.toString());
                    }
                    break;
                case UPDATE_ONLY:
                    break;
                case DELETE_ONLY:
                case UPDATE_AND_DELETE:
                    EMCQuery sQuery = new EMCQuery(enumQueryTypes.SELECT, relation.getChildClassPath());
                    Object valueToLookForS = theTable.getValueForFieldInEntityObject(field, theTable.getClass(), theTable);
                    if (valueToLookForS != null) {
                        sQuery.addAnd(relation.getChildFieldName(), valueToLookForS.toString());
                        if (!field.equals("companyId") && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
                            sQuery.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
                        }
                        List qRes = entityManager.createQuery(sQuery.toString()).getResultList();

                        if (qRes != null) {
                            EMCCommandClass delCmd = relation.getDeleteBeanCommand();
                            if (delCmd == null) {
                                enumEMCModules childModule = Functions.getEMCModule(relation.getChildClassPath());
                                if (childModule == null) {
                                    throw new EMCEntityBeanException("Could not resolve EMCmodule for:" + relation.getChildClassPath() + " in cascade delete");

                                }
                                String shortName = relation.getChildClassPath();
                                int index = shortName.lastIndexOf(".");
                                shortName = shortName.substring(index + 1, shortName.length());
                                delCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), childModule.getId(), "DELETE_" + shortName);
                            }
                            List toSend = new ArrayList();
                            for (Object entity : qRes) {
                                toSend.removeAll(toSend);
                                toSend.add(delCmd);
                                toSend.add(entity);
                                commandManager.mapCommand(delCmd, toSend, userData);
                            }
                        }
                    }
                    break;
                default:
                    throw new EMCEntityBeanException("No logic found for cascade delete");
            }

        }
        return ret;
    }

    /**
     *
     * @param relation
     * @param key
     * @param theTable
     * @param oldValue - the persisted value
     * @param userData
     * @return
     */
    private boolean doCascadeUpdate(EMCTableRelation relation, String key, EMCTable theTable, Object oldValue, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = true;
        Object transaction = registry.getTransactionKey();

        switch (relation.getCallBeanOption()) {
            case SQL:
                EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, relation.getChildClassPath());
                query.addSet(relation.getChildFieldName(), theTable.getValueForFieldInEntityObject(key, theTable.getClass(), theTable).toString());
                //get Old value

                query.addAnd(relation.getChildFieldName(), oldValue.toString());
                if (!key.equals("companyId") && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
                    query.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
                }
                commandManager.setTransactionSucceedQuery(transaction, query.toString());
                break;
            case DELETE_ONLY:
                break;
            case UPDATE_ONLY:
            case UPDATE_AND_DELETE:
                EMCQuery sQuery = new EMCQuery(enumQueryTypes.SELECT, relation.getChildClassPath());

                sQuery.addAnd(relation.getChildFieldName(), oldValue.toString());
                if (!key.equals("companyId") && !relation.getChildClassPath().equals(BaseCompanyTable.class.getName())) {
                    sQuery.addAnd("companyId", util.getCompanyId(relation.getChildClassPath(), userData));
                }
                List<EMCTable> qRes = entityManager.createQuery(sQuery.toString()).getResultList();

                if (qRes != null) {
                    EMCCommandClass updCmd = relation.getUpdateBeanCommand();
                    if (updCmd == null) {
                        enumEMCModules childModule = Functions.getEMCModule(relation.getChildClassPath());
                        if (childModule == null) {
                            throw new EMCEntityBeanException("Could not resolve EMCmodule for:" + relation.getChildClassPath() + " in cascade update");
                        }
                        String shortName = relation.getChildClassPath();
                        int index = shortName.lastIndexOf(".");
                        shortName = shortName.substring(index + 1, shortName.length());
                        updCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), childModule.getId(), "UPDATE_" + shortName);
                    }
                    List toSend = new ArrayList();
                    for (EMCTable entity : qRes) {
                        toSend.removeAll(toSend);
                        boolean upRes = entity.setValueForFieldInEntityObject(relation.getChildFieldName(), entity.getClass(), entity, theTable.getValueForFieldInEntityObject(key, theTable.getClass(), theTable).toString());
                        if (!upRes) {
                            throw new EMCEntityBeanException("Failed to set value on: " + relation.getChildFieldName() + " for " + entity.getClass().getName() + " in cascade update");
                        }
                        toSend.add(updCmd);
                        toSend.add(entity);
                        commandManager.mapCommand(updCmd, toSend, userData);

                    }
                }
                break;
            default:
                throw new EMCEntityBeanException("No logic found for cascade update");
        }
        return ret;
    }

    /**
     * Use this method to initialize a record before insert TODO not integrated
     * with insert
     *
     * @return
     */
    public boolean init(EMCTable theTable, EMCUserData userData) {
        return true;
    }

    private void updateStandardRegisters(EMCEntityClass object, EMCUserData userData) {
        object.setModifiedBy(userData.getUserName());
        object.setModifiedDate(Functions.nowDate());
        object.setModifiedTime(Functions.nowDate());
        if (isSystemTable(object.getClass().getName())) {
            object.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
        }
    }

    private void insertStandardRegisters(EMCEntityClass object, EMCUserData userData) {
        object.setCreatedDate(Functions.nowDate());
        object.setCreatedTime(Functions.nowDate());
        if (isSystemTable(object.getClass().getName())) {
            object.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
        }
        if (object.getCreatedBy() == null) {
            object.setCreatedBy(userData.getUserName());
        }

    }

    public boolean isSystemTable(String tableClassName) {
        return systemTableBean.isTableSystemTable(tableClassName);
    }

    /**
     * Returns a comma seperated String containing the number of rows in the
     * specified table, and the max recordId.
     *
     * @param theTable Table to get row count from.
     * @param userData User data.
     * @return A comma seperated String containing the number of rows in the
     * specified table, and the max recordId.
     */
    public String getNumRows(Class theTable, EMCUserData userData) {
        Query qr = entityManager.createQuery(getQuery(theTable, 1, userData));
        List qrResult = qr.getResultList();
        long numRows = 0;
        for (Object o : qrResult) {
            //Backwards compatibility.
            if (o instanceof Object[]) {
                Object[] res = (Object[]) o;

                numRows += res[0] == null ? 0 : (Long) res[0];
            } else {
                numRows = (Long) o;
            }
        }
        return String.valueOf(String.valueOf(numRows));
    }

    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Query qr = entityManager.createQuery(getQuery(theTable, 0, userData));
        qr.setFirstResult(start);
        qr.setMaxResults(end - start);
        return qr.getResultList();
    }

    protected String getQuery(Class theTable, int pos, EMCUserData userData) {
        EMCQuery query = null;
        if (userData.getUserData() == null || userData.getUserData().size() == 0) {
            query = new EMCQuery(enumQueryTypes.SELECT, theTable.getName());
            if (isSystemTable(theTable.getName())) {
                if (!companySuperTable) {
                    query.addAnd("companyId", systemConstants.defaultCompanyId());
                }
            } else {
                query.addAnd("companyId", userData.getCompanyId());
            }
        } else if (userData.getUserData(0) instanceof EMCQuery) {
            query = (EMCQuery) userData.getUserData(0);
            super.checkCompanyId(query, userData);
        } else {
            String stringQuery = (String) userData.getUserData(pos);
            if (isSystemTable(theTable.getName()) && !companySuperTable) {
                stringQuery = replaceCompIdSystemTable(stringQuery, userData);
            }
            return stringQuery;
        }
        if (pos == 0) {
            return query.toString();
        } else {
            return query.getTableInfoQuery();
        }
    }

    private String replaceCompIdSystemTable(String sqlQuery, EMCUserData userData) {
        String ret = "";
        //find comp
        int pos = sqlQuery.indexOf("companyId");
        if (pos != -1) {
            ret = sqlQuery.substring(pos);
            sqlQuery = sqlQuery.substring(0, pos);
            //find '
            pos = ret.indexOf("'");
            sqlQuery += ret.substring(0, pos);
            ret = ret.substring(pos + 1);
            //find '
            pos = ret.indexOf("'");
            ret = ret.substring(pos + 1);
            sqlQuery += " '" + systemConstants.defaultCompanyId() + "' " + ret;
        } else {
            String andWhere = null;
            if (sqlQuery.indexOf("WHERE") != -1) {
                andWhere = " AND ";
            } else {
                andWhere = " WHERE ";
            }
            String[] words = sqlQuery.substring(sqlQuery.indexOf("FROM")).split(" ");
            //Alias will be in position 1
            sqlQuery += andWhere + words[2] + ".companyId = '" + systemConstants.defaultCompanyId() + "'";
        }
        return sqlQuery;
    }

    /**
     * Only applies to BaseCompanyTable
     *
     * @return
     */
    public boolean isCompanySuperTable() {
        return companySuperTable;
    }

    /**
     * Only applies to BaseCompanyTable
     *
     */
    public void setCompanySuperTable(boolean companySuperTable) {
        this.companySuperTable = companySuperTable;
    }
}