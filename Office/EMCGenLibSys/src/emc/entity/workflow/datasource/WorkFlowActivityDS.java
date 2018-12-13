/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow.datasource;

import emc.datatypes.base.query.QueryName;
import emc.datatypes.workflow.activities.SourceFields;
import emc.datatypes.workflow.activityds.Complete;
import emc.datatypes.workflow.activityds.DocumentAttached;
import emc.datatypes.workflow.activityds.EmployeeName;
import emc.datatypes.workflow.workflow.EmailTemplate;
import emc.datatypes.workflow.workflow.SMSTemplate;
import emc.entity.workflow.WorkFlowActivity;
import java.util.HashMap;

/**
 *
 * @author rico
 */
public class WorkFlowActivityDS extends WorkFlowActivity {

    private boolean documentAttached;
    private String managerName;
    private String employeeName;
    private boolean overdue;
    private boolean complete;
    private String sourceEmailField;
    private String sourceCellPhoneField;
    private String sourceReferenceField;
    private String sourceUserField1;
    private String sourceUserField2;
    private String sourceUserField3;
    private String sourceUserField4;
    private String sourceUserField5;
    private String sourceUserTextArea;
    //For Selections
    private String sourceEmailFieldName;
    private String sourceCellPhoneFieldName;
    private String sourceReferenceFieldName;
    private String sourcePostalCodeField;

    public WorkFlowActivityDS() {
        this.setDataSource(true);
    }

    public boolean isDocumentAttached() {
        return documentAttached;
    }

    public void setDocumentAttached(boolean documentAttached) {
        this.documentAttached = documentAttached;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getSourceCellPhoneField() {
        return sourceCellPhoneField;
    }

    public void setSourceCellPhoneField(String sourceCellPhoneField) {
        this.sourceCellPhoneField = sourceCellPhoneField;
    }

    public String getSourcePostalCodeField() {
        return sourcePostalCodeField;
    }

    public void setSourcePostalCodeField(String sourcePostalCodeField) {
        this.sourcePostalCodeField = sourcePostalCodeField;
    }

    public String getSourceEmailField() {
        return sourceEmailField;
    }

    public void setSourceEmailField(String sourceEmailField) {
        this.sourceEmailField = sourceEmailField;
    }

    public String getSourceUserField1() {
        return sourceUserField1;
    }

    public void setSourceUserField1(String sourceUserField1) {
        this.sourceUserField1 = sourceUserField1;
    }

    public String getSourceUserField2() {
        return sourceUserField2;
    }

    public void setSourceUserField2(String sourceUserField2) {
        this.sourceUserField2 = sourceUserField2;
    }

    public String getSourceUserField3() {
        return sourceUserField3;
    }

    public void setSourceUserField3(String sourceUserField3) {
        this.sourceUserField3 = sourceUserField3;
    }

    public String getSourceUserField4() {
        return sourceUserField4;
    }

    public void setSourceUserField4(String sourceUserField4) {
        this.sourceUserField4 = sourceUserField4;
    }

    public String getSourceUserField5() {
        return sourceUserField5;
    }

    public void setSourceUserField5(String sourceUserField5) {
        this.sourceUserField5 = sourceUserField5;
    }

    public String getSourceReferenceField() {
        return sourceReferenceField;
    }

    public void setSourceReferenceField(String sourceReferenceField) {
        this.sourceReferenceField = sourceReferenceField;
    }

    public String getSourceCellPhoneFieldName() {
        return sourceCellPhoneFieldName;
    }

    public void setSourceCellPhoneFieldName(String sourceCellPhoneFieldName) {
        this.sourceCellPhoneFieldName = sourceCellPhoneFieldName;
    }

    public String getSourceEmailFieldName() {
        return sourceEmailFieldName;
    }

    public void setSourceEmailFieldName(String sourceEmailFieldName) {
        this.sourceEmailFieldName = sourceEmailFieldName;
    }

    public String getSourceReferenceFieldName() {
        return sourceReferenceFieldName;
    }

    public void setSourceReferenceFieldName(String sourceReferenceFieldName) {
        this.sourceReferenceFieldName = sourceReferenceFieldName;
    }

    public String getSourceUserTextArea() {
        return sourceUserTextArea;
    }

    public void setSourceUserTextArea(String sourceUserTextArea) {
        this.sourceUserTextArea = sourceUserTextArea;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("documentAttached", new DocumentAttached());
        toBuild.put("managerName", new EmployeeName());
        toBuild.put("employeeName", new EmployeeName());
        toBuild.put("complete", new Complete());

        SourceFields activitySource = new SourceFields();
        activitySource.setEmcLabel("Source");

        toBuild.put("sourceReferenceField", activitySource);

        SourceFields sourceDT = new SourceFields();

        toBuild.put("sourceEmailField", sourceDT);
        toBuild.put("sourceCellPhoneField", sourceDT);
        toBuild.put("sourcePostalCodeField", sourceDT);
        toBuild.put("sourceUserField1", sourceDT);
        toBuild.put("sourceUserField2", sourceDT);
        toBuild.put("sourceUserField3", sourceDT);
        toBuild.put("sourceUserField4", sourceDT);
        toBuild.put("sourceUserField5", sourceDT);
        toBuild.put("sourceUserTextArea", sourceDT);

        EmailTemplate emailTemplateDT = new EmailTemplate();
        toBuild.put("emailTemplate", emailTemplateDT);

        SMSTemplate smsTemplateDT = new SMSTemplate();
        toBuild.put("smsTemplate", smsTemplateDT);

        QueryName queryNameDT = new QueryName();
        toBuild.put("emailRecipientQueryName", queryNameDT);
        toBuild.put("smsRecipientQueryName", queryNameDT);

        return toBuild;
    }
}
