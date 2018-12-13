/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.creditnoteapprovalgroupemployees;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.debtors.creditnoteapprovalgroupemployeess.DebtorsCreditNoteApprovalGroupEmployeesLocal;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import emc.entity.debtors.datasource.DebtorsCreditNoteApprovalGroupEmployeesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Data source bean for DebtorsCreditNoteApprovalGroupEmployeesDS.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditNoteApprovalGroupEmployeesDSBean extends EMCDataSourceBean implements DebtorsCreditNoteApprovalGroupEmployeesDSLocal {

    @EJB
    private DebtorsCreditNoteApprovalGroupEmployeesLocal approvalGroupEmployeesBean;
    @EJB
    private BaseEmployeeLocal employeeBean;

    /** Creates a new instance of DebtorsCreditNoteApprovalGroupEmployeesDSBean */
    public DebtorsCreditNoteApprovalGroupEmployeesDSBean() {
        this.setDataSourceClassName(DebtorsCreditNoteApprovalGroupEmployeesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return approvalGroupEmployeesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return approvalGroupEmployeesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return approvalGroupEmployeesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsCreditNoteApprovalGroupEmployeesDS ds = (DebtorsCreditNoteApprovalGroupEmployeesDS) dataSourceInstance;

        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = approvalGroupEmployeesBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        DebtorsCreditNoteApprovalGroupEmployeesDS ds = null;

        if (ret instanceof Boolean && (Boolean)ret) {
            ds = (DebtorsCreditNoteApprovalGroupEmployeesDS)theRecord;
        } else if (ret instanceof DebtorsCreditNoteApprovalGroupEmployees) {
            ds = (DebtorsCreditNoteApprovalGroupEmployeesDS)convertSuperToDataSource(ret, userData);
        } else {
            return ret;
        }

        if (fieldNameToValidate.equals("employeeId")) {
            return populateDataSourceFields(ds, userData);
        }

        return ret;
    }
}
