/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.entity.base.dblog.BaseDBLogSetup;
import emc.enums.enumPersistOptions;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DSLogSetupBean extends EMCEntityBean implements DBLogSetupLocal {

    @EJB
    private DBLogLogicLocal logBean;

    public boolean refress(EMCUserData userData) {
        return logBean.refresh(userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            BaseDBLogSetup record = (BaseDBLogSetup) theRecord;
            if (fieldNameToValidate.equals("actionValue")) {
                if (!enumPersistOptions.UPDATE.toString().equals(record.getType())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "You may only set the action value on a Update type record.", userData);
                    return false;
                }
            } else if (fieldNameToValidate.equals("type")) {
                if (!enumPersistOptions.UPDATE.toString().equals(record.getType())) {
                    record.setActionValue(null);
                    return record;
                }
            }
        }
        return valid;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseDBLogSetup record = (BaseDBLogSetup) iobject;
        setUniqueConstraint(record, userData);
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseDBLogSetup record = (BaseDBLogSetup) uobject;
        setUniqueConstraint(record, userData);
        return super.update(uobject, userData);
    }

    private void setUniqueConstraint(BaseDBLogSetup record, EMCUserData userData) {
        if (isBlank(record.getType())) {
            record.setType(null);
        }
        if (isBlank(record.getTableName())) {
            record.setTableName(null);
        }
        if (isBlank(record.getFieldName())) {
            record.setFieldName(null);
        }
        if (isBlank(record.getActionValue())) {
            record.setActionValue(null);
        }
        if (isBlank(record.getActionWorkFlow())) {
            record.setActionWorkFlow(null);
        }
        record.setUniqueConstraintValue(record.getType() + record.getTableName() + record.getFieldName() + record.getActionValue() + record.getActionWorkFlow());
    }
}