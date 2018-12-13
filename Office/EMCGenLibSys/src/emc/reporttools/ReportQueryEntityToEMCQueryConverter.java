/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools;

import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.calendar.WeekDays;
import emc.enums.base.reporttools.DaysOfMonth;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.base.reporttools.ReportSelectionLockTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class is used to convert instances of BaseReportUserQueryTable,
 * BaseReportWhereTable and BaseReportOrderTable to instances of EMCQuery.
 *
 * @author riaan
 */
public class ReportQueryEntityToEMCQueryConverter {

    /**
     * Creates a new instance of ReportQueryEntityToEMCQueryConverter.
     */
    public ReportQueryEntityToEMCQueryConverter() {
    }

    /**
     * This method takes instances of the various entities used to store user
     * report queries and returns an EMCQuery containing the given parameters.
     */
    public EMCQuery convertEntitiesToQuery(BaseReportUserQueryTable userQuery, List<BaseReportWhereTable> whereInformation, List<BaseReportOrderTable> orderInformation) {
        EnumReports reportId = EnumReports.fromString(userQuery.getReportId());

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, reportId.getMainEntityClass());

        populateQueryWithTables(query, reportId, new EMCXMLHandler().xmltoTree(userQuery.getTablesTree(), "Tables"));

        for (BaseReportOrderTable orderTable : orderInformation) {
            query.addOrderBy(orderTable.getField(), orderTable.getTableName());
        }

        for (BaseReportWhereTable whereTable : whereInformation) {
            if (whereTable.isLockValue()) {
                if (!Functions.checkBlank(whereTable.getLockType())) {
                    Calendar nowCal = Calendar.getInstance();

                    switch (ReportSelectionLockTypes.fromString(whereTable.getLockType())) {
                        case DAY:
                            //Check Lock Values
                            if (!Functions.checkBlank(whereTable.getLockFrom())) {
                                //Create Calendar
                                Calendar cal = Calendar.getInstance();

                                //Fetch From Value
                                DaysOfMonth fromEnum = DaysOfMonth.fromString(whereTable.getLockFrom());
                                int from = fromEnum.getId();

                                //Check From against Month End
                                if (fromEnum == DaysOfMonth.MONTH_END || from > cal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                    from = cal.getMaximum(Calendar.DAY_OF_MONTH);
                                }

                                //Check if current or previous month
                                if (nowCal.get(Calendar.DAY_OF_MONTH) < from) {
                                    cal.add(Calendar.MONTH, -1);

                                    //Re-Check From against Month End
                                    if (fromEnum == DaysOfMonth.MONTH_END || from > cal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                        from = cal.getMaximum(Calendar.DAY_OF_MONTH);
                                    }
                                }

                                //Set From on Calendar
                                cal.set(Calendar.DAY_OF_MONTH, from);

                                //Add Where to Query
                                query.addAnd(whereTable.getField(), cal.getTime(), whereTable.getTableName(), EMCQueryConditions.fromString(whereTable.getWhereCondition()));
                            }
                            break;
                        case WEEK:
                            //Check Lock Values
                            if (!Functions.checkBlank(whereTable.getLockFrom()) && !Functions.checkBlank(whereTable.getLockTo())) {
                                //Create Calendar
                                Calendar fromCal = Calendar.getInstance();
                                Calendar toCal = Calendar.getInstance();

                                //Set From on Calendar
                                fromCal.set(Calendar.DAY_OF_WEEK, WeekDays.fromString(whereTable.getLockFrom()).getId());

                                //Set To on Calendar
                                toCal.set(Calendar.DAY_OF_WEEK, WeekDays.fromString(whereTable.getLockTo()).getId());

                                //Check if current or previous Week
                                if (nowCal.get(Calendar.DAY_OF_WEEK) < toCal.get(Calendar.DAY_OF_WEEK)) {
                                    fromCal.add(Calendar.WEEK_OF_YEAR, -1);
                                    toCal.add(Calendar.WEEK_OF_YEAR, -1);
                                }

                                //Add Where to Query
                                query.addAnd(whereTable.getField(), fromCal.getTime(), whereTable.getTableName(), EMCQueryConditions.GREATER_THAN_EQ);
                                query.addAnd(whereTable.getField(), toCal.getTime(), whereTable.getTableName(), EMCQueryConditions.LESS_THAN_EQ);
                            }
                            break;
                        case MONTH:
                            //Check Lock Values
                            if (!Functions.checkBlank(whereTable.getLockFrom()) && !Functions.checkBlank(whereTable.getLockTo())) {
                                //Create Calendars
                                Calendar fromCal = Calendar.getInstance();
                                Calendar toCal = Calendar.getInstance();

                                //Fetch From Value
                                DaysOfMonth fromEnum = DaysOfMonth.fromString(whereTable.getLockFrom());
                                int from = fromEnum.getId();

                                //Fetch To Value
                                DaysOfMonth toEnum = DaysOfMonth.fromString(whereTable.getLockTo());
                                int to = toEnum.getId();

                                //Check From against Month End
                                if (fromEnum == DaysOfMonth.MONTH_END || from > fromCal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                    from = fromCal.getMaximum(Calendar.DAY_OF_MONTH);
                                }

                                //Check To against Month End
                                if (toEnum == DaysOfMonth.MONTH_END || to > toCal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                    to = toCal.getMaximum(Calendar.DAY_OF_MONTH);
                                }

                                //Check if current or previous month
                                if (nowCal.get(Calendar.DAY_OF_MONTH) < to) {
                                    fromCal.add(Calendar.MONTH, -1);
                                    toCal.add(Calendar.MONTH, -1);

                                    //Re-Check From against Month End
                                    if (fromEnum == DaysOfMonth.MONTH_END || from > fromCal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                        from = fromCal.getMaximum(Calendar.DAY_OF_MONTH);
                                    }

                                    //Re-Check To against Month End
                                    if (toEnum == DaysOfMonth.MONTH_END || to > toCal.getMaximum(Calendar.DAY_OF_MONTH)) {
                                        to = toCal.getMaximum(Calendar.DAY_OF_MONTH);
                                    }
                                }

                                //Set From on Calendar
                                fromCal.set(Calendar.DAY_OF_MONTH, from);

                                //Set To on Calendar
                                toCal.set(Calendar.DAY_OF_MONTH, to);

                                //Add Where to Query
                                query.addAnd(whereTable.getField(), fromCal.getTime(), whereTable.getTableName(), EMCQueryConditions.GREATER_THAN_EQ);
                                query.addAnd(whereTable.getField(), toCal.getTime(), whereTable.getTableName(), EMCQueryConditions.LESS_THAN_EQ);
                            }
                            break;
                    }
                }
            } else {
                if (!Functions.checkBlank(whereTable.getFieldValue()) || EMCQueryConditions.IS_BLANK.toString().equals(whereTable.getWhereCondition()) || EMCQueryConditions.IS_NOT_BLANK.toString().equals(whereTable.getWhereCondition())) {
                    query.addAndCommaSeperated(whereTable.getField(), whereTable.getFieldValue(), whereTable.getTableName(), EMCQueryConditions.fromString(whereTable.getWhereCondition()));
                }
            }
        }

        //Add group by.  This is added when using the selection form, so for consistency, it is added here as well.
        if (reportId.getMainEntityClassMainField() != null) {
            query.addGroupBy(reportId.getMainEntityClass(), reportId.getMainEntityClassMainField());
        }

        return query;
    }

    /**
     * This method is used to populate the given EMCQuery with the tables in the
     * given tree node. This should be called from the selection form as well.
     * (super class)
     */
    public EMCQuery populateQueryWithTables(EMCQuery query, EnumReports reportId, DefaultMutableTreeNode node) {
        Enumeration children = node.children();

        //Don't do for root or main entity class
        if (node.getLevel() != 0) {
            ReportTableObject table = (ReportTableObject) node.getUserObject();
            if (!table.getEntityClass().equals(reportId.getMainEntityClass())) {
                if (table.getJoinField() == null && table.getEntityClassField() == null) {
                    query.addTable(table.getEntityClass());
                } else {
                    if (table.isLeftOuterJoin()) {
                        try {
                            query.addLeftOuterJoin(Class.forName(table.getJoinTable()), table.getJoinField(), Class.forName(table.getEntityClass()), table.getEntityClassField());
                        } catch (ClassNotFoundException ex) {
                            if (EMCDebug.getDebug()) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Failed to add loef outer join table: " + ex.getMessage());
                            }
                        }
                    } else {
                        query.addTableAnd(table.getEntityClass(), table.getJoinField(), table.getJoinTable(), table.getEntityClassField());
                    }
                }
            }
        }

        while (children.hasMoreElements()) {
            query = populateQueryWithTables(query, reportId, (DefaultMutableTreeNode) children.nextElement());
        }

        return query;
    }
}
