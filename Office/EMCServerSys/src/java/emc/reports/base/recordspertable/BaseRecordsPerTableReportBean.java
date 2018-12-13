/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.base.recordspertable;

import emc.bus.base.tables.TablesLocal;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.StopWatchFactory;
import emc.reporttools.EMCReportConfig;
import emc.tables.EMCTable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ivan
 */
@Stateless
public class BaseRecordsPerTableReportBean extends EMCReportBean implements BaseRecordsPerTableReportLocal {

    @EJB
    private TablesLocal tablesBean;
    private List<Object> reportData;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        Map<String, Object> paramMap = reportConfig.getParameters();
        List<Object> result = new ArrayList<Object>();
        List<Object> tables = new ArrayList<Object>();

        String tableNames = (String) paramMap.get("tables");
        if (isBlank(tableNames)) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please select the 'Table Name' on the parameters tab of the selection form.", userData);
            return reportData;
        }

        if (tableNames.equals("All")) {
            tables = tablesBean.getEMCTables(userData);
            StopWatchFactory fact = new StopWatchFactory(true);

            for (Object o : tables) {
                String classNames = o.toString();


                try {
                    Class tmpCls = Class.forName(classNames); //construct the class from name as string
                    Constructor c = tmpCls.getConstructor(new Class[]{}); //get constructor for class
                    Object ob = c.newInstance(new Object[]{}); //create instance with the constructor
                    EMCTable tmpTable = (EMCTable) ob;
                    BaseRecordsPerTableReportDS ds = new BaseRecordsPerTableReportDS();
                    if (!tmpTable.isDataSource()) {
                        classNames = classNames.substring(classNames.lastIndexOf(".") + 1, classNames.length());
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, classNames);
                        query.addFieldAggregateFunction("recordID", classNames, "COUNT");//0
                        Long rows = (Long) util.executeSingleResultQuery(query, userData);

                        ds.setTableName(classNames);
                        ds.setNumberOfRows(rows == null ? "0" : ((Long) rows).toString());
                        ds.setPrintAccount(classNames);
                        result.add(ds);
                    }
                } catch (Exception ex) {
                }

            }
        } //If the user wants to see a specific table in EMC:
        else {
            BaseRecordsPerTableReportDS ds = new BaseRecordsPerTableReportDS();
            try {
                Class tmpCls = Class.forName(tableNames); //construct the class from name as string
                Constructor c = tmpCls.getConstructor(new Class[]{}); //get constructor for class
                Object ob = c.newInstance(new Object[]{}); //create instance with the constructor
                EMCTable tmpTable = (EMCTable) ob;
                if (!tmpTable.isDataSource()) {
                    tableNames = tableNames.substring(tableNames.lastIndexOf(".") + 1, tableNames.length());
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, tableNames);
                    query.addFieldAggregateFunction("recordID", tableNames, "COUNT");//0
                    Long values = (Long) util.executeSingleResultQuery(query, userData);

                    ds.setTableName(tableNames);
                    ds.setNumberOfRows(values == null ? "0" : ((Long) values).toString());
                    ds.setPrintAccount(tableNames);
                    result.add(ds);
                }
            } catch (Exception ex) {
            }

        }
        return result;

    }
}
