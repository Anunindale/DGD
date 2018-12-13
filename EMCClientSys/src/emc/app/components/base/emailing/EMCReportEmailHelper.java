/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.emailing;

import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.parameters.ReportParameterObjectMap;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.reporttools.EMCReportConfig;

/**
 *
 * @author wikus
 */
public class EMCReportEmailHelper {

    private String lable;
    private EnumReports reportId;
    private JasperInformation jasperInfo;
    private EMCCommandClass commandClass;
    private EMCReportConfig reportConfig;
    private EMCQuery reportQuery;
    private String reportQueryField;
    private Class reportQueryClass;
    private String formField;
    private ReportParameterObjectMap reportParameterObjects;

    public EMCCommandClass getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(EMCCommandClass commandClass) {
        this.commandClass = commandClass;
    }

    public EMCReportConfig getReportConfig() {
        return reportConfig;
    }

    public void setReportConfig(EMCReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }

    public String getFormField() {
        return formField;
    }

    public void setFormField(String formField) {
        this.formField = formField;
    }

    public JasperInformation getJasperInfo() {
        return jasperInfo;
    }

    public void setJasperInfo(JasperInformation jasperInfo) {
        this.jasperInfo = jasperInfo;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public EnumReports getReportId() {
        return reportId;
    }

    public void setReportId(EnumReports reportId) {
        this.reportId = reportId;
    }

    public EMCQuery getReportQuery() {
        return reportQuery;
    }

    public void setReportQuery(EMCQuery reportQuery) {
        this.reportQuery = reportQuery;
    }

    public Class getReportQueryClass() {
        return reportQueryClass;
    }

    public void setReportQueryClass(Class reportQueryClass) {
        this.reportQueryClass = reportQueryClass;
    }

    public String getReportQueryField() {
        return reportQueryField;
    }

    public void setReportQueryField(String reportQueryField) {
        this.reportQueryField = reportQueryField;
    }

    public ReportParameterObjectMap getReportParameterObjects() {
        return reportParameterObjects;
    }

    public void setReportParameterObjects(ReportParameterObjectMap reportParameterObjects) {
        this.reportParameterObjects = reportParameterObjects;
    }

}
