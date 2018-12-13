/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.output.bdloggenericreport.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.reporttools.ReportFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author wikus
 */
public class BaseDBLogDataFetchThread extends SwingWorker<Void, Void> {

    private ReportFrame reportFrame;

    public BaseDBLogDataFetchThread(ReportFrame reportFrame) {
        this.reportFrame = reportFrame;

        StringBuilder msg = new StringBuilder();
        msg.append("The database log will fetch the log data for each table in your selection and prompt you for the column to print.");
        msg.append("\n");
        msg.append("You can either print a report with a maximum of 10 columns,");
        msg.append("\n");
        msg.append("or you can export the data to excel by ticking the \'Export To Excel\' option on the column dialog.");
        msg.append("\n");
        msg.append("\n");
        msg.append("We recommend that you always print the \'Version\' field as this is used to show the order of the updates.");

        EMCDialogFactory.createMessageDialog(utilFunctions.findEMCDesktop(reportFrame), "Database Log", msg.toString());
    }

    @Override
    protected Void doInBackground() throws Exception {
        EMCQuery query = reportFrame.getActiveQuery();
        EMCUserData userData = reportFrame.getUserData();

        int rowCount = 0;

        Map<String, List<EMCEntityClass>> tempMap;
        Map<String, List<EMCEntityClass>> reportDataMap = new TreeMap<String, List<EMCEntityClass>>();

        Iterator<Map.Entry<String, List<EMCEntityClass>>> mapIterator;
        Map.Entry<String, List<EMCEntityClass>> mapEntry;

        List<EMCEntityClass> reportDataList;

        EMCCommandClass cmd = reportFrame.getReportCommand();
        List toSend;

        for (;;) {
            toSend = new ArrayList<Object>();
            toSend.add(query);
            toSend.add(rowCount);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
            if (toSend.size() > 1 && toSend.get(1) instanceof Map) {
                tempMap = (Map<String, List<EMCEntityClass>>) toSend.get(1);

                if (tempMap.isEmpty()) {
                    break;
                }

                mapIterator = tempMap.entrySet().iterator();

                while (mapIterator.hasNext()) {
                    mapEntry = mapIterator.next();

                    reportDataList = reportDataMap.get(mapEntry.getKey());
                    if (reportDataList == null) {
                        reportDataList = new ArrayList<EMCEntityClass>();
                    }

                    reportDataList.addAll(mapEntry.getValue());

                    reportDataMap.put(mapEntry.getKey(), reportDataList);
                }

                rowCount += 500;
            } else {
                break;
            }
        }

        if (reportDataMap.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No data selected.", userData);
        }

        BaseDBLogGenericReportDataManager dataManager;

        for (Map.Entry entry : reportDataMap.entrySet()) {
            try {
                dataManager = new BaseDBLogGenericReportDataManager(Class.forName((String) entry.getKey()), (List<EMCEntityClass>) entry.getValue(), userData);
                dataManager.printTableData();
            } catch (Exception ex) {
                //
            }
        }

        reportFrame.setGeneratingReport(false);


        return null;
    }
}
