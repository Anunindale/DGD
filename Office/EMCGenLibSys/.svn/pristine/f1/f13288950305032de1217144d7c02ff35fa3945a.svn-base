/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.journals.superclass.Description;
import emc.datatypes.base.query.foreignkeys.QueryNameFKNM;
import emc.datatypes.base.templates.SMSTemplateId;
import emc.datatypes.base.templates.Template;
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
@Table(name = "BaseSMSTemplate", uniqueConstraints = {@UniqueConstraint(columnNames = {"templateId", "companyId"})})
public class BaseSMSTemplate extends EMCEntityClass {

    private String templateId;
    private String description;
    @Column(length = 10000)
    private String template;
    private String queryName;

    public BaseSMSTemplate() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        toBuild.put("templateId", new SMSTemplateId());
        toBuild.put("description", new Description());
        toBuild.put("template", new Template());
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
