/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import emc.messages.EMCMessageEnum;
import emc.messages.EMCMessageHandler;
import emc.messages.EMCServerMessageHandlerLocal;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.server.utility.EMCServerUtilityLocal;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author rico
 */
public class EMCBean {

    @EJB
    protected EMCServerUtilityLocal util;
    @EJB
    protected EMCDateHandlerLocal dateHandler;
    @EJB
    private EMCServerMessageHandlerLocal defaultMessageHandler;
    //Allow any number of additional message handlers.
    private Map<String, EMCMessageHandler> messageHandlers;

    public EMCBean() {
    }

    /** This method checks whether a value is blank. (null or "") */
    public boolean isBlank(Object toCheck) {
        if (toCheck == null) {
            return true;
        } else if (toCheck.toString().trim().equals("")) {
            return true;
        }

        return false;
    }

    /**
     * Returns an exact copy of the EMCTable also handle super fields to be
     * as if new record
     * 
     * @param toClone
     * @param userData
     * @return
     */
    public EMCEntityClass doClone(EMCEntityClass toClone, EMCUserData userData) {
        EMCEntityClass ret = doCloneWithSuperFields(toClone, userData);
        //remove super fields
        if (ret != null) {
            ret.setRecordID(0);
            ret.setCreatedBy(userData.getUserName());
            ret.setClosed(null);
            ret.setCreatedDate(null);
            ret.setCreatedTime(null);
            ret.setModifiedBy(null);
            ret.setModifiedDate(null);
            ret.setModifiedTime(null);
            ret.setVersion(0);
        }
        return ret;
    }

    /**
     * Returns an exact copy of the EMCTable superfields are not cleared
     * 
     * @param toClone
     * @param userData
     * @return
     */
    public EMCEntityClass doCloneWithSuperFields(EMCEntityClass toClone, EMCUserData userData) {
        EMCEntityClass ret = null;
        Class cls = toClone.getClass();
        //create a new instance of the Table
        try {
            Constructor c = cls.getConstructor(new Class[]{});
            ret = (EMCEntityClass) c.newInstance(new Object[]{});
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to create record to copy", userData);
            }
            return ret;
        }
        //now copy the data
        List<Field> fieldList = toClone.getAllTableFields();
        for (Field curF : fieldList) {
            curF.setAccessible(true);
            try {

                curF.set(ret, curF.get(toClone));
            } catch (Exception ex) {
                if (EMCDebug.getDebug())
                    Logger.getLogger("emc").log(Level.SEVERE, "Could not clone field" + curF.getName(), userData);
            }

        }

        return ret;
    }

    /**
     * This method attempts to find the SQL value of an entity. 
     * Only use on persisted entities.
     * If the entity is detached it will return an attached entity. 
     * If entity is attached it will return a clone of what is in SQL.
     * To do this the method does two clones and 1 value exchange. 
     * E.g. 3 field for field loops are made through the entity.
     * Do not use this method on a new record
     * @param toFind
     * @param userData
     * @return
     */
    public EMCEntityClass findSQLVersionForEntity(EMCEntityClass toFind, EMCUserData userData) {
        //attatched and persisted before
        if (util.containsEntity(toFind, userData) && toFind.getRecordID() != 0) {
            //clone the record
            EMCEntityClass clone = doCloneWithSuperFields(toFind, userData);
            //refresh the attached entity reverting all changes on this class
            util.refreshEntity(toFind, userData);
            //now clone the sql version of the record
            EMCEntityClass toReturn = doClone(toFind, userData);
            //now reinstate changes on toFind
            toFind = exchangeValues(toFind, clone, userData);
            return toReturn;
        } else { //not attatched
            return (EMCEntityClass) util.findPersisted(toFind.getClass(), toFind.getRecordID(), userData);
        }
    }

    /**
     * Replaces toUpdate with the fields of source. Reflection field for field update.
     * used by find SQL value, but could be useful elsewhere therfore public
     * @param toUpdate
     * @param source
     * @param userData
     * @return
     */
    public EMCEntityClass exchangeValues(EMCEntityClass toUpdate, EMCEntityClass source, EMCUserData userData) {
        if (!toUpdate.getClass().getName().equals(source.getClass().getName())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Can only exchangeValues on objects of the same class", userData);
            return toUpdate;
        }
        List<Field> fieldList = source.getAllTableFields();
        for (Field curF : fieldList) {
            curF.setAccessible(true);
            try {
                curF.set(toUpdate, curF.get(source));
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "ExchangeValues could not update field:" + curF.getName() + " " + ex.getMessage(), userData);
            }
        }
        return toUpdate;

    }

    @Deprecated
    protected void logMessage(Level level, String message, EMCUserData userData) {
        Logger.getLogger("emc").log(level, message, userData);
    }

    /**
     * Logs a message to the EMC logger.  Uses the default message handler to retrieve the message.
     * @param level Level of log message.
     * @param messageId Message id to be passed to message handler.
     * @param userData User data.
     * @param parameters Message parameters.
     */
    protected void logMessage(Level level, EMCMessageEnum messageId, EMCUserData userData, Object... parameters) {
        Logger.getLogger("emc").log(level, defaultMessageHandler.getMessage(messageId, userData, parameters), userData);
    }

    /**
     * Logs a message to the EMC logger.  Uses the default message handler to retrieve the message.
     * @param level Level of log message.
     * @param messageId Message id to be passed to message handler.
     * @param tree Indicates the tree in which the message should be logged.  If this is the last message, prefix tree identifier with '/'
     * @param userData User data.
     * @param parameters Message parameters.
     */
    protected void logMessage(Level level, EMCMessageEnum messageId, String tree, EMCUserData userData, Object... parameters) {
        Logger.getLogger("emc").log(level, defaultMessageHandler.getMessage(messageId, userData, parameters), new Object[]{userData, tree, "x"});
    }

    /** Forwards the method call to the default message handler.  (EMCServerMessageHandler)*/
    protected String getMessage(EMCMessageEnum messageId, EMCUserData userData, Object... parameters) {
        return defaultMessageHandler.getMessage(messageId, userData, parameters);
    }

    /** Forwards the method call to the specified message handler. */
    protected String getMessage(String handlerId, EMCMessageEnum messageId, EMCUserData userData, Object... parameters) {
        return messageHandlers.get(handlerId).getMessage(messageId, userData, parameters);
    }

    public void checkCompanyId(EMCQuery query, EMCUserData userData) {
        String[] tables = query.getEntityClassNames();
        for (String table : tables) {
            query.removeAnd("companyId", table);
//            if (table.equals(BaseCompanyTable.class.getName())) continue;
            query.addAnd("companyId", util.getCompanyId(table, userData), table);
        }
    }
}
