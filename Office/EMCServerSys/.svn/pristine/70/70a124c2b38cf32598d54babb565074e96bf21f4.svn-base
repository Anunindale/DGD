/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.base.dbloggenericreport;

import emc.entity.base.dblog.BaseDBLog;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseDBLogGenericReportBean extends EMCBusinessBean implements BaseDBLogGenericReportLocal {

    @Override
    public Map<String, List<EMCEntityClass>> getReportData(EMCQuery query, int rowNumber, EMCUserData userData) {
        List<BaseDBLog> reportData = util.executeGeneralSelectQueryLimit(query.toString(), userData, rowNumber, 500);
        util.detachEntities(userData);

        Map<String, List<EMCEntityClass>> reportDataMap = new TreeMap<String, List<EMCEntityClass>>();
        List<EMCEntityClass> reportDataList;

        EMCXMLHandler xml = new EMCXMLHandler();

        EMCEntityClass entityClass = null;
        long previousLogRecordID = 0;
        int version = 0;


        for (BaseDBLog log : reportData) {
            reportDataList = reportDataMap.get(log.getTableName());
            if (reportDataList == null) {
                reportDataList = new ArrayList<EMCEntityClass>();
            }

            if (!isBlank(log.getOldValue())) {
                entityClass = (EMCEntityClass) xml.XMLToObject(log.getOldValue(), userData);

                if (entityClass.getRecordID() != previousLogRecordID) {
                    version = 0;
                    previousLogRecordID = entityClass.getRecordID();
                } else {
                    version++;
                }

                entityClass.setVersion(version);

                reportDataList.add(entityClass);
            }

            if (!isBlank(log.getNewValue())) {
                entityClass = (EMCEntityClass) xml.XMLToObject(log.getNewValue(), userData);
                if (entityClass.getRecordID() != previousLogRecordID) {
                    version = 0;
                    previousLogRecordID = entityClass.getRecordID();
                } else {
                    version++;
                }

                entityClass.setVersion(version);

                reportDataList.add(entityClass);
            }

            reportDataMap.put(log.getTableName(), reportDataList);
        }

        return reportDataMap;
    }
}
