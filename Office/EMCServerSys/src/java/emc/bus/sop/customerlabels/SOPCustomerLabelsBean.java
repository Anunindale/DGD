/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.customerlabels;

import emc.entity.sop.SOPCustomerLabels;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class SOPCustomerLabelsBean extends EMCEntityBean implements SOPCustomerLabelsLocal {

    @Override
    public List getCustomerLablesForSO(String salesOrderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomerLabels.class);
        query.addTableAnd(SOPSalesOrderMaster.class.getName(), "customerId", SOPCustomerLabels.class.getName(), "customerNo");
        query.addAnd("salesOrderNo", salesOrderId, SOPSalesOrderMaster.class.getName());
        query.addField("custLabelDocument");
        return util.executeGeneralSelectQuery(query, userData);
    }
}
