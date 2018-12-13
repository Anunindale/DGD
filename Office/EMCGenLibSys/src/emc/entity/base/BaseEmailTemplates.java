/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.query.foreignkeys.QueryNameFKNM;
import emc.datatypes.base.signatures.foreignkey.SignatureIdFKNM;
import emc.datatypes.base.templates.Subject;
import emc.datatypes.base.templates.Template;
import emc.datatypes.base.templates.EmailTemplateId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseEmailTemplates", uniqueConstraints = {@UniqueConstraint(columnNames = {"templateId", "companyId"})})
public class BaseEmailTemplates extends EMCEntityClass {

    private String templateId;
    private String description;
    private String subject;
    @Column(length = 10000)
    private String template;
    private String signature;
    private String queryName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("templateId", new EmailTemplateId());
        toBuild.put("description", new Description());
        toBuild.put("subject", new Subject());
        toBuild.put("template", new Template());
        toBuild.put("signature", new SignatureIdFKNM());
        toBuild.put("queryName", new QueryNameFKNM());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("templateId");
        toBuild.add("description");
        return toBuild;
    }
}
