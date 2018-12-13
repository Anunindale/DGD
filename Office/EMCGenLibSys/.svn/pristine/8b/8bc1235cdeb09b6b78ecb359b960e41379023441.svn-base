/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.workflow.lines.EmployeeId;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.LineNo;
import emc.datatypes.workflow.lines.ExternalNotes;
import emc.datatypes.workflow.lines.InternalNotes;
import emc.datatypes.workflow.lines.OutputFileRequired;
import emc.datatypes.workflow.lines.HoursWorkEstimated;
import emc.datatypes.workflow.lines.Duration;
import emc.datatypes.workflow.lines.Skill;
import emc.datatypes.workflow.lines.ActivityType;
import emc.datatypes.workflow.lines.ActivityCategory;
import emc.datatypes.workflow.lines.ActivityGroup;
import emc.datatypes.workflow.lines.ManagerResponsible;
import emc.datatypes.workflow.lines.ReferenceSource;
import emc.datatypes.workflow.jobs.PrimaryIndicator;
import emc.datatypes.workflow.lines.Billable;
import emc.datatypes.workflow.lines.ClosedByManager;
import emc.datatypes.workflow.lines.CompletionRules;
import emc.datatypes.workflow.lines.WorkFlowId;
import emc.datatypes.workflow.workflow.EmailTemplate;
import emc.datatypes.workflow.workflow.NextNumber;
import emc.datatypes.workflow.workflow.QueryName;
import emc.datatypes.workflow.workflow.SMSTemplate;
import emc.entity.workflow.superclass.WorkFlowSuper;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Entity
@Table(name = "WorkFlowLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"workFlowId", "lineNo", "companyId"})})
public class WorkFlowLines extends WorkFlowSuper implements Serializable {

    private String workFlowId;
    private double LineNo;
    private double NextLineNo;
    private double HoursWorkEstimated;

    public WorkFlowLines() {
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public double getLineNo() {
        return LineNo;
    }

    public void setLineNo(double LineNo) {
        this.LineNo = LineNo;
    }

    public double getNextLineNo() {
        return NextLineNo;
    }

    public void setNextLineNo(double NextLineNo) {
        this.NextLineNo = NextLineNo;
    }

    public double getHoursWorkEstimated() {
        return HoursWorkEstimated;
    }

    public void setHoursWorkEstimated(double HoursWorkEstimated) {
        this.HoursWorkEstimated = HoursWorkEstimated;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("LineNo", new LineNo());
        toBuild.put("NextLineNo", new NextNumber());
        toBuild.put("primaryIndicator", new PrimaryIndicator());
        toBuild.put("descriptionOfActivity", new Description());
        toBuild.put("activityCategory", new ActivityCategory());
        toBuild.put("activityType", new ActivityType());
        toBuild.put("skill", new Skill());
        toBuild.put("employeeId", new EmployeeId());
        toBuild.put("managerResponsible", new ManagerResponsible());
        toBuild.put("activityGroup", new ActivityGroup());
        toBuild.put("referenceSource", new ReferenceSource());
        toBuild.put("duration", new Duration());
        toBuild.put("HoursWorkEstimated", new HoursWorkEstimated());
        toBuild.put("billable", new Billable());
        toBuild.put("completionRules", new CompletionRules());
        toBuild.put("outputFileRequired", new OutputFileRequired());
        toBuild.put("internalNotes", new InternalNotes());
        toBuild.put("externalNotes", new ExternalNotes());
        toBuild.put("closedByManager", new ClosedByManager());
        toBuild.put("workFlowId", new WorkFlowId());

        EmailTemplate emailTemplateDT = new EmailTemplate();
        toBuild.put("emailTemplate", emailTemplateDT);
        toBuild.put("activityGroupEmailTemplate", emailTemplateDT);

        SMSTemplate smsTemplateDT = new SMSTemplate();
        toBuild.put("smsTemplate", smsTemplateDT);
        toBuild.put("activityGroupSmsTemplate", smsTemplateDT);

        QueryName queryNameDT = new QueryName();
        toBuild.put("emailRecipientQueryName", queryNameDT);
        toBuild.put("smsRecipientQueryName", queryNameDT);
        return toBuild;
    }
}
