/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.reporttools.ReportQueryEntityToEMCQueryConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseReportUserQueryTableBean extends EMCEntityBean implements BaseReportUserQueryTableLocal {

    @EJB
    private BaseReportOrderTableLocal orderTableBean;
    @EJB
    private BaseReportWhereTableLocal whereTableBean;
    @EJB
    private BaseReportPrintOptionsLocal printOptionsBean;

    /**
     * Creates a new instance of BaseReportUserQueryTableBean.
     */
    public BaseReportUserQueryTableBean() {
    }

    /**
     * This method is used to save the default query, for a specific report, for
     * a specific user.
     */
    @Override
    public BaseReportUserQueryTable saveDefaultQueryForUser(List queryInformation, EMCUserData userData) {
        //Indicates when existing default/custom query should be deleted.
        boolean reset = (Boolean) queryInformation.remove(0);
        boolean continueInsert;

        BaseReportUserQueryTable baseReportUserQueryTable = (BaseReportUserQueryTable) queryInformation.get(0);

        if (reset) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
            query.addAnd("reportId", baseReportUserQueryTable.getReportId());
            query.addAnd("createdBy", userData.getUserName());

            query.openConditionBracket(EMCQueryBracketConditions.AND);
            //Should be "default"
            query.addOr("userRecordName", baseReportUserQueryTable.getUserRecordName());
            query.addOr("userRecordName", "Custom");
            query.closeConditionBracket();

            List<BaseReportUserQueryTable> queries = (List<BaseReportUserQueryTable>) util.executeGeneralSelectQuery(query, userData);

            for (BaseReportUserQueryTable queryRecord : queries) {
                deleteInNewTrans(queryRecord, userData);
            }

            continueInsert = true;
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
            query.addAnd("userRecordName", baseReportUserQueryTable.getUserRecordName());
            query.addAnd("reportId", baseReportUserQueryTable.getReportId());
            query.addAnd("createdBy", userData.getUserName());

            continueInsert = !util.exists(query, userData);
        }

        if (continueInsert) {
            baseReportUserQueryTable.setCreatedBy(userData.getUserName());
            baseReportUserQueryTable.setLastExecTimestamp(System.currentTimeMillis());

            try {
                this.insert(baseReportUserQueryTable, userData);
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert default query.", userData);
                }
            }

            int curIndex = 1;
            int size = queryInformation.size();

            BaseReportOrderTable orderTable;

            while (curIndex != size && queryInformation.get(curIndex) instanceof BaseReportOrderTable) {
                orderTable = (BaseReportOrderTable) queryInformation.get(curIndex);
                orderTable.setReportQueryId(baseReportUserQueryTable.getRecordID());
                try {
                    orderTableBean.superInsert(orderTable, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert order by line.", userData);
                    }
                }

                curIndex++;
            }

            BaseReportWhereTable whereTable;

            while (curIndex != size && queryInformation.get(curIndex) instanceof BaseReportWhereTable) {
                whereTable = (BaseReportWhereTable) queryInformation.get(curIndex);
                whereTable.setReportQueryId(baseReportUserQueryTable.getRecordID());
                try {
                    whereTableBean.superInsert(whereTable, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert where line.", userData);
                    }
                }

                curIndex++;
            }
        }

        return baseReportUserQueryTable;
    }

    /**
     * Returns the names of all the queries saved by the given user for the
     * given report.
     */
    @Override
    public List getUserSavedQueries(String reportId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addField("userRecordName");
        query.addAnd("reportId", reportId);
        query.addAnd("createdBy", userData.getUserName());
        query.addAnd("userRecordName", "Custom", EMCQueryConditions.NOT);
        query.addOrderBy("userRecordName");

        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public boolean checkQueryExistance(String queryName, String reportId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addAnd("reportId", reportId);
        query.addAnd("userRecordName", queryName);
        query.addAnd("createdBy", userData.getUserName());
        return util.exists(query, userData);
    }

    /**
     * Saves a query by copying everything in the previous query and assigning a
     * new name. Returns a boolean indicating success.
     */
    @Override
    public boolean saveUserQuery(String currentQueryName, String newQueryName, String reportId, EMCUserData userData) {
        boolean ret = true;

        if (!currentQueryName.equalsIgnoreCase(newQueryName)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
            query.addAnd("reportId", reportId);
            query.addAnd("userRecordName", newQueryName);
            query.addAnd("createdBy", userData.getUserName());
            Object exi = util.executeSingleResultQuery(query, userData);
            if (exi != null) {
                deleteInNewTrans((BaseReportUserQueryTable) exi, userData);
            }

            //Master
            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
            query.addAnd("reportId", reportId);
            query.addAnd("userRecordName", currentQueryName);
            query.addAnd("createdBy", userData.getUserName());

            BaseReportUserQueryTable oldQuery = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);

            BaseReportUserQueryTable newQuery;

            newQuery = (BaseReportUserQueryTable) doClone(oldQuery, userData);
            newQuery.setUserRecordName(newQueryName);

            try {
                this.insert(newQuery, userData);
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to save user query", userData);
                    ret = false;
                }
            }

            //Where fields
            BaseReportWhereTable newWhere;

            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class.getName());
            query.addAnd("reportQueryId", oldQuery.getRecordID());

            List<BaseReportWhereTable> whereList = (List<BaseReportWhereTable>) util.executeGeneralSelectQuery(query, userData);

            for (BaseReportWhereTable whereTable : whereList) {
                newWhere = (BaseReportWhereTable) doClone(whereTable, userData);
                newWhere.setReportQueryId(newQuery.getRecordID());

                try {
                    whereTableBean.superInsert(newWhere, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert where part of query", userData);
                        ret = false;
                    }
                }
            }

            //Order fields
            BaseReportOrderTable newOrder;

            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class.getName());
            query.addAnd("reportQueryId", oldQuery.getRecordID());

            List<BaseReportOrderTable> orderList = (List<BaseReportOrderTable>) util.executeGeneralSelectQuery(query, userData);

            for (BaseReportOrderTable orderTable : orderList) {
                newOrder = (BaseReportOrderTable) doClone(orderTable, userData);
                newOrder.setReportQueryId(newQuery.getRecordID());

                try {
                    orderTableBean.superInsert(newOrder, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert order part of query", userData);
                    }
                }
            }

            //Print Options
            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class);
            query.addAnd("reportId", reportId);
            query.addAnd("queryName", currentQueryName);
            query.addAnd("createdBy", userData.getUserName());
            BaseReportPrintOptions currentOptions = (BaseReportPrintOptions) util.executeSingleResultQuery(query, userData);

            if (currentOptions != null) {
                BaseReportPrintOptions newOptions = (BaseReportPrintOptions) doClone(currentOptions, userData);
                newOptions.setQueryName(newQueryName);
                try {
                    printOptionsBean.insert(newOptions, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to insert print options.", userData);
                    }
                }
            }

            if (!ret) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to save query", userData);
            }
        }
        return ret;
    }

    /**
     * Gets the last query executed for a specific report by a specific user.
     * Returns an array containing an EMCQuery, the query name, an XML String
     * containing parameters and an XML representation of the tables in the
     * saved report query.
     */
    @Override
    public List getLastQueryForUser(String reportId, EMCUserData userData) {
        List ret = null;

        String className = BaseReportUserQueryTable.class.getName();

        EMCQuery nestedQuery = new EMCQuery(enumQueryTypes.SELECT, className);
        nestedQuery.addFieldAggregateFunction("lastExecTimestamp", className, "MAX");
        nestedQuery.addAnd("reportId", reportId);
        nestedQuery.addAnd("createdBy", userData.getUserName());

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addAnd("lastExecTimestamp", nestedQuery);
        query.addAnd("reportId", reportId);
        query.addAnd("createdBy", userData.getUserName());

        BaseReportUserQueryTable userQuery = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);

        if (userQuery != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class.getName());
            query.addAnd("reportQueryId", userQuery.getRecordID());

            List<BaseReportWhereTable> whereInformation = (List<BaseReportWhereTable>) util.executeGeneralSelectQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class.getName());
            query.addAnd("reportQueryId", userQuery.getRecordID());

            List<BaseReportOrderTable> orderInformation = (List<BaseReportOrderTable>) util.executeGeneralSelectQuery(query, userData);

            ret = new ArrayList();
            ret.add(new ReportQueryEntityToEMCQueryConverter().convertEntitiesToQuery(userQuery, whereInformation, orderInformation));
            ret.add(userQuery.getUserRecordName());
            ret.add(userQuery.getReportParameters() != null ? userQuery.getReportParameters() : "");
            ret.add(userQuery.getTablesTree());
        }

        return ret;
    }

    @Override
    public EMCQuery constructAndReturnQuery(String reportId, String queryName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class);
        query.addAnd("createdBy", userData.getUserName());
        query.addAnd("reportId", reportId);
        query.addAnd("userRecordName", queryName);
        BaseReportUserQueryTable userQuery = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);

        if (userQuery != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class);
            query.addAnd("reportQueryId", userQuery.getRecordID());
            List<BaseReportWhereTable> whereInformation = (List<BaseReportWhereTable>) util.executeGeneralSelectQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class);
            query.addAnd("reportQueryId", userQuery.getRecordID());
            List<BaseReportOrderTable> orderInformation = (List<BaseReportOrderTable>) util.executeGeneralSelectQuery(query, userData);

            return new ReportQueryEntityToEMCQueryConverter().convertEntitiesToQuery(userQuery, whereInformation, orderInformation);
        }

        return null;
    }

    /**
     * Delete a query
     *
     * @param reportId
     * @param queryName
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public boolean deleteQuery(String reportId, String queryName, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addAnd("reportId", reportId);
        query.addAnd("userRecordName", queryName);
        query.addAnd("companyId", userData.getCompanyId());
        BaseReportUserQueryTable record = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);
        super.delete(record, userData);
        return true;
    }

    @Override
    public long createCustom(BaseReportUserQueryTable fromRecord, long recId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addAnd("reportId", fromRecord.getReportId());
        query.addAnd("userRecordName", "Custom");
        query.addAnd("createdBy", fromRecord.getCreatedBy());
        Object crec = util.executeSingleResultQuery(query, userData);
        //WHERE
        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class.getName());
        query.addAnd("reportQueryId", fromRecord.getRecordID());
        query.addAnd("recordID", recId, EMCQueryConditions.NOT);
        List<BaseReportWhereTable> whereList = util.executeGeneralSelectQuery(query, userData);
        //ORDER
        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class.getName());
        query.addAnd("reportQueryId", fromRecord.getRecordID());
        query.addAnd("recordID", recId, EMCQueryConditions.NOT);
        List<BaseReportOrderTable> orderList = util.executeGeneralSelectQuery(query, userData);
        if (crec != null) {
            this.deleteInNewTrans((BaseReportUserQueryTable) crec, userData);
        }
        BaseReportUserQueryTable custom = (BaseReportUserQueryTable) doClone(fromRecord, userData);
        custom.setUserRecordName("Custom");
        custom.setLastExecTimestamp(System.currentTimeMillis());
        super.insert(custom, userData);

        for (BaseReportWhereTable rec : whereList) {
            BaseReportWhereTable whereRec = (BaseReportWhereTable) doClone(rec, userData);
            whereRec.setReportQueryId(custom.getRecordID());
            whereTableBean.superInsert(whereRec, userData);
        }

        for (BaseReportOrderTable rec : orderList) {
            BaseReportOrderTable orderRec = (BaseReportOrderTable) doClone(rec, userData);
            orderRec.setReportQueryId(custom.getRecordID());
            orderTableBean.superInsert(orderRec, userData);
        }
        return custom.getRecordID();
    }

    /**
     * Used to delete the existing custom record in its own transaction.
     *
     * @param rec
     * @param userData
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void deleteInNewTrans(BaseReportUserQueryTable rec, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, BaseReportWhereTable.class.getName());
        query.addAnd("reportQueryId", rec.getRecordID());
        util.executeUpdate(query, userData);

        query = new EMCQuery(enumQueryTypes.DELETE, BaseReportOrderTable.class.getName());
        query.addAnd("reportQueryId", rec.getRecordID());
        util.executeUpdate(query, userData);

        query = new EMCQuery(enumQueryTypes.DELETE, BaseReportUserQueryTable.class.getName());
        query.addAnd("recordID", rec.getRecordID());
        util.executeUpdate(query, userData);
    }
}
