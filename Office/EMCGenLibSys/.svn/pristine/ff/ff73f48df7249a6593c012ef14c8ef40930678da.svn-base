/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.pricearangements;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class CustomerDisplayField extends EMCString {

    public CustomerDisplayField() {
        this.setEmcLabel("Customer");
        this.setColumnWidth(82);
        DSRelation dsr = new DSRelation();
        dsr.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, String queryCondition, EMCUserData userData) {
                query.openAndConditionBracket();
                query.addOr("priceGroup", theValue);
                query.addOr("customerId", theValue);
                query.closeConditionBracket();
                return query;
            }
        });
        this.setDsRelation(dsr);
    }
}
