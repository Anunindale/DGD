/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.sop;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.sop.SOPCustomers;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class CustomerNameDS extends EMCString {

    /** Creates a new instance of CustomerNameDS.
     *
     * @param customerIdField Customer id field on the entity class on which this datatype will be set.  This varies between entity classes (customerId/customerNo).
     */
    public CustomerNameDS(String customerIdField) {
        this.setEmcLabel("Customer Name");

        DSRelation dsr = new DSRelation();
        dsr.setFKField(customerIdField);
        dsr.setPKField("customerId");
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, java.lang.String queryCondition, EMCUserData userData) {
                query.addTable(SOPCustomers.class.getName());
                query.addTableAnd(query.getEntityClassName(), PKField, SOPCustomers.class.getName(), FKField);
                query.openConditionBracket(EMCQueryBracketConditions.AND);
                query.addOr("customerName", theValue, SOPCustomers.class.getName(), EMCQueryConditions.fromString(queryCondition));
                query.closeConditionBracket();
                return query;
            }
        });
        this.setDsRelation(dsr);
    }
}
