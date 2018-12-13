/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.hr.employeesummary;

import emc.entity.base.BaseEmployeeTable;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HREmployeeSummaryBean extends EMCReportBean implements HREmployeeSummaryLocal {

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        //Base query selects from BaseEmployeeTable only
        query.addField("dateOfEmployment", BaseEmployeeTable.class.getName());
        query.addField("terminationDate", BaseEmployeeTable.class.getName());
        query.addField("typeOfEmployment", BaseEmployeeTable.class.getName());
        query.addField("remunerationType", BaseEmployeeTable.class.getName());
        query.addField("gender", BaseEmployeeTable.class.getName());
        query.addField("race", BaseEmployeeTable.class.getName());
        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List<Object> reportData = new ArrayList<Object>();

        Object[] selectedData;


        Date fromDate = (Date) paramMap.get("fromDate");
        Date toDate = (Date) paramMap.get("toDate");

        if (fromDate == null || toDate == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please select the from dand to dates on the parameters tab.", userData);
            return reportData;
        }


        HREmployeeSummaryDS appointed = populateDS("Appointed");
        HREmployeeSummaryDS terminated = populateDS("Terminated");
        HREmployeeSummaryDS weekly = populateDS("Weekly Payed");
        HREmployeeSummaryDS monthly = populateDS("Monthly Payed");
        HREmployeeSummaryDS concract = populateDS("Contract Worker");
        HREmployeeSummaryDS total = populateDS("All Employed");

        for (Object o : queryResult) {
            selectedData = (Object[]) o;
            
            System.out.println("3 - " + selectedData[3]);
            System.out.println("4 - " + selectedData[4]);
            System.out.println("");

            //Appointed
            if (selectedData[0] != null) {
                if (dateHandler.compareDatesIgnoreTime(fromDate, (Date) selectedData[0], userData) <= 0
                    && dateHandler.compareDatesIgnoreTime(toDate, (Date) selectedData[0], userData) >= 0) {
                    addEmployee(appointed, (String) selectedData[4], (String) selectedData[5]);
                }
            }

            //Terminated
            if (selectedData[1] != null) {
                if (dateHandler.compareDatesIgnoreTime(fromDate, (Date) selectedData[1], userData) <= 0
                    && dateHandler.compareDatesIgnoreTime(toDate, (Date) selectedData[1], userData) >= 0) {
                    addEmployee(terminated, (String) selectedData[4], (String) selectedData[5]);
                }
            }

            //Weekly
            if ("PERMANENT".equals((String) selectedData[2]) && "WK".equals((String) selectedData[3])) {
                addEmployee(weekly, (String) selectedData[4], (String) selectedData[5]);
            }

            //Monthly
            if ("PERMANENT".equals((String) selectedData[2]) && ("SA".equals((String) selectedData[3]) || "SB".equals((String) selectedData[3]))) {
                addEmployee(monthly, (String) selectedData[4], (String) selectedData[5]);
            }

            //Contract
            if ("CONTRACT".equals((String) selectedData[2])) {
                addEmployee(concract, (String) selectedData[4], (String) selectedData[5]);
            }

            //Total
            addEmployee(total, (String) selectedData[4], (String) selectedData[5]);
        }

        reportData.add(appointed);
        reportData.add(terminated);
        reportData.add(weekly);
        reportData.add(monthly);
        reportData.add(concract);
        reportData.add(total);

        return reportData;
    }

    private HREmployeeSummaryDS populateDS(String type) {
        HREmployeeSummaryDS ds = new HREmployeeSummaryDS();
        ds.setSummaryType(type);
        ds.setRaceHeading1("A");
        ds.setRaceHeading2("C");
        ds.setRaceHeading3("CH");
        ds.setRaceHeading4("I");
        ds.setRaceHeading5("W");
        ds.setFemaleRace1(0);
        ds.setFemaleRace2(0);
        ds.setFemaleRace3(0);
        ds.setFemaleRace4(0);
        ds.setFemaleRace5(0);
        ds.setFemaleRaceTotal(0);
        ds.setMaleRace1(0);
        ds.setMaleRace2(0);
        ds.setMaleRace3(0);
        ds.setMaleRace4(0);
        ds.setMaleRace5(0);
        ds.setMaleRaceTotal(0);
        ds.setRaceTotal(0);
        return ds;
    }

    private void addEmployee(HREmployeeSummaryDS ds, String gender, String race) {
        /*if (!isBlank(gender) && !isBlank(race)) {
            switch (gender) {
                case "Female":
                    switch (race) {
                        case "A":
                            ds.setFemaleRace1(ds.getFemaleRace1() + 1);
                            ds.setFemaleRaceTotal(ds.getFemaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "C":
                            ds.setFemaleRace2(ds.getFemaleRace2() + 1);
                            ds.setFemaleRaceTotal(ds.getFemaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "CH":
                            ds.setFemaleRace3(ds.getFemaleRace3() + 1);
                            ds.setFemaleRaceTotal(ds.getFemaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "I":
                            ds.setFemaleRace4(ds.getFemaleRace4() + 1);
                            ds.setFemaleRaceTotal(ds.getFemaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "W":
                            ds.setFemaleRace5(ds.getFemaleRace5() + 1);
                            ds.setFemaleRaceTotal(ds.getFemaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                    }
                    break;
                case "Male":
                    switch (race) {
                        case "A":
                            ds.setMaleRace1(ds.getMaleRace1() + 1);
                            ds.setMaleRaceTotal(ds.getMaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "C":
                            ds.setMaleRace2(ds.getMaleRace2() + 1);
                            ds.setMaleRaceTotal(ds.getMaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "CH":
                            ds.setMaleRace3(ds.getMaleRace3() + 1);
                            ds.setMaleRaceTotal(ds.getMaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "I":
                            ds.setMaleRace4(ds.getMaleRace4() + 1);
                            ds.setMaleRaceTotal(ds.getMaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                        case "W":
                            ds.setMaleRace5(ds.getMaleRace5() + 1);
                            ds.setMaleRaceTotal(ds.getMaleRaceTotal() + 1);
                            ds.setRaceTotal(ds.getRaceTotal() + 1);
                            break;
                    }
                    break;
            }
        }*/
    }
}
