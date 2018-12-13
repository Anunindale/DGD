/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.transactionperiodsummary.resources;

import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;
import emc.enums.enumQueryTypes;
import emc.forms.gl.display.transactionperiodsummary.GLTransactionPeriodSummaryForm;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLTransactionsDetailMI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class GLTransactionViewButton extends emcMenuButtonList {

    private GLTransactionPeriodSummaryForm form;
    private GLTransactionPeriodSummaryDRM drm;

    public GLTransactionViewButton(GLTransactionPeriodSummaryForm form) {
        super("GL View", form);

        this.form = form;
        this.drm = (GLTransactionPeriodSummaryDRM) form.getDataManager();

        this.addMenuItem("Columns", null, -1, false);
        this.addMenuItem("Transactions", new GLTransactionsDetailMI(), 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if ("Columns".equals(theCmd)) {
            GLTransactionPeriodSummaryColumnDialog dialog = new GLTransactionPeriodSummaryColumnDialog(drm);
            if (dialog.isOK()) {
                List<String> activeColumns = dialog.getActiveColumns();

                List<String> tableKeys = new ArrayList<String>();
                tableKeys.add("accountNumber");
                tableKeys.add("financialPeriod");
                //Only include the column selected by the user.
                tableKeys.addAll(activeColumns);
                tableKeys.add("debit");
                tableKeys.add("credit");

                drm.getTableModel().setKeys(tableKeys);
                drm.getTableModel().fireTableStructureChanged();

                //Include conditions in current query.
                EMCQuery query = ((EMCQuery)drm.getOriginalQuery());

                if (query == null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionPeriodSummary.class);
                } else {
                    query = query.copyQuery();
                }

                //Only group and select new entities if not all columns have been 
                //specified. Otherwise, select individual records.  For unspecified
                //columns, use empty String values rather than null.  When null is
                //used, the container can't find the appropriate constructor.
                if (activeColumns.size() < 6) {
                    String tableAlias = query.getTableAlias(GLTransactionPeriodSummary.class);

                    StringBuilder constructor = new StringBuilder("new GLTransactionPeriodSummary(");
                    constructor.append(tableAlias);
                    constructor.append(".accountNumber, ");
                    constructor.append(tableAlias);
                    constructor.append(".financialPeriod, ");
                    query.addGroupBy("accountNumber");
                    query.addGroupBy("financialPeriod");
                    query.addOrderBy("financialPeriod");
                    query.addOrderBy("accountNumber");
                    if (activeColumns.contains("analysisCode1")) {
                        query.addGroupBy("analysisCode1");
                        query.addOrderBy("analysisCode1");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode1, ");
                    } else {
                        constructor.append("'', ");
                    }
                    if (activeColumns.contains("analysisCode2")) {
                        query.addGroupBy("analysisCode2");
                        query.addOrderBy("analysisCode2");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode2, ");
                    } else {
                        constructor.append("'', ");
                    }
                    if (activeColumns.contains("analysisCode3")) {
                        query.addGroupBy("analysisCode3");
                        query.addOrderBy("analysisCode3");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode3, ");
                    } else {
                        constructor.append("'', ");
                    }
                    if (activeColumns.contains("analysisCode4")) {
                        query.addGroupBy("analysisCode4");
                        query.addOrderBy("analysisCode4");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode4, ");
                    } else {
                        constructor.append("'', ");
                    }
                    if (activeColumns.contains("analysisCode5")) {
                        query.addGroupBy("analysisCode5");
                        query.addOrderBy("analysisCode5");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode5, ");
                    } else {
                        constructor.append("'', ");
                    }
                    if (activeColumns.contains("analysisCode6")) {
                        query.addGroupBy("analysisCode6");
                        query.addOrderBy("analysisCode6");
                        constructor.append(tableAlias);
                        constructor.append(".analysisCode6, ");
                    } else {
                        constructor.append("'', ");
                    }

                    //Calculate totals
                    constructor.append("SUM(");
                    constructor.append(tableAlias);
                    constructor.append(".debit), ");
                    constructor.append("SUM(");
                    constructor.append(tableAlias);
                    constructor.append(".credit))");

                    query.addCustomField(constructor.toString(), false);
                }

                EMCUserData userData = drm.getUserData();
                userData.setUserData(0, query);

                drm.setUserData(userData);
            }
        } else {
            super.executeCmd(theCmd);
        }
    }
}
