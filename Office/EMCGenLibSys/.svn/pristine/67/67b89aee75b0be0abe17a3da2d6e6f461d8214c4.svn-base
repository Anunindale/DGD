/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.servertransactionslog;

import emc.datatypes.EMCLong;
import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.QueryManipulator;
import emc.entity.base.servertransactions.BaseServerTransactionsLog;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

/**
 *
 * @author Owner
 */
public class ServerTransactionsLogTimeTakenDT extends EMCLong {

    public ServerTransactionsLogTimeTakenDT() {
        this.setEmcLabel("Duration (s)");
        DSRelation relation = new DSRelation();
        relation.setSearchCriteria(new QueryManipulator() {

            @Override
            public EMCQuery manipulateQuery(EMCQuery query, String PKField, String FKField, Object theValue, String queryCondition, EMCUserData userData) {
                String alias = query.getTableAlias(BaseServerTransactionsLog.class);
                String condition;
                switch (EMCQueryConditions.fromString(queryCondition)) {

                    case GREATER_THAN:
                        condition = ">";
                        break;
                    case GREATER_THAN_EQ:
                        condition = ">=";
                        break;
                    case LESS_THAN:
                        condition = "<";
                        break;
                    case LESS_THAN_EQ:
                        condition = "<=";

                    default:
                        condition = ">";
                }

                query.addCustomAnd("((" + alias + ".transEndTime - " + alias + ".transStartTime )) " + condition + " " + theValue);
                return query;
            }
        });
        this.setDsRelation(relation);
    }
}
