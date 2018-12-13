/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesrepcommission.datasource;

import emc.bus.sop.parameters.SOPParametersLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.datasource.SOPSalesRepCommissionDS;
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
 * @author wikus
 */
@Stateless
public class SOPSalesRepCommissionDSBean extends EMCDataSourceBean implements SOPSalesRepCommissionDSLocal {

    @EJB
    private SOPSalesRepCommissionLocal masterBean;
    @EJB
    private SOPParametersLocal paramBean;

    public SOPSalesRepCommissionDSBean() {
        this.setDataSourceClassName(SOPSalesRepCommissionDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPSalesRepCommissionDS ds = (SOPSalesRepCommissionDS) dataSourceInstance;

        SOPParameters param = paramBean.getParameterRecord(userData);
        if (param != null) {
            EMCQuery query;
            String description;
            if (!isBlank(param.getCustomerItemFieldDesc1())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable1());
                query.addAnd(param.getCustomerItemField1(), ds.getCustomerItemField1());
                query.addField(param.getCustomerItemFieldDesc1());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc1(description);
            }
            if (!isBlank(param.getCustomerItemFieldDesc2())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable2());
                query.addAnd(param.getCustomerItemField2(), ds.getCustomerItemField2());
                query.addField(param.getCustomerItemFieldDesc2());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc2(description);
            }
            if (!isBlank(param.getCustomerItemFieldDesc3())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable3());
                query.addAnd(param.getCustomerItemField3(), ds.getCustomerItemField3());
                query.addField(param.getCustomerItemFieldDesc3());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc3(description);
            }
            if (!isBlank(param.getCustomerItemFieldDesc4())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable4());
                query.addAnd(param.getCustomerItemField4(), ds.getCustomerItemField4());
                query.addField(param.getCustomerItemFieldDesc4());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc4(description);
            }
            if (!isBlank(param.getCustomerItemFieldDesc5())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable5());
                query.addAnd(param.getCustomerItemField5(), ds.getCustomerItemField5());
                query.addField(param.getCustomerItemFieldDesc5());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc5(description);
            }
            if (!isBlank(param.getCustomerItemFieldDesc6())) {
                query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable6());
                query.addAnd(param.getCustomerItemField6(), ds.getCustomerItemField6());
                query.addField(param.getCustomerItemFieldDesc6());
                description = (String) util.executeSingleResultQuery(query, userData);
                ds.setCustomerItemFieldDesc6(description);
            }
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object valid = masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
        if (valid instanceof SOPSalesRepCommission) {
            return convertSuperToDataSource(valid, userData);
        } else {
            return valid;
        }
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
