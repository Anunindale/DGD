/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.budgetlines.datasource;

import emc.bus.gl.budgetlines.GLBudgetLinesLocal;
import emc.entity.gl.GLBudgetLines;
import emc.entity.gl.GLBudgetLinesDS;
import emc.entity.gl.GLChartOfAccounts;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class GLBudgetLinesDSBean extends EMCDataSourceBean implements GLBudgetLinesDSLocal {

    @EJB
    private GLBudgetLinesLocal linesBean;

    public GLBudgetLinesDSBean() {
        super.setDataSourceClassName(GLBudgetLinesDS.class.getName());
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.entityUpdate(uobject, userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        GLBudgetLinesDS linesDS = (GLBudgetLinesDS) dataSourceInstance;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
        query.addAnd("accountNumber", linesDS.getAccount());
        query.addField("description");
        String description = (String) util.executeSingleResultQuery(query, userData);

        linesDS.setDescription(description);
        return linesDS;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object result = linesBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (result == Boolean.TRUE || result instanceof GLBudgetLines) {
            return populateDataSourceFields((GLBudgetLines) theRecord, userData);
        }
        return result;
    }
}
