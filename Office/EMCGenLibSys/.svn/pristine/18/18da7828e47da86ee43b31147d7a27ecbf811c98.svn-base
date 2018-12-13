/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.templatedocuments;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.templates.foreignkeys.EmailTemplateIdFK;
import emc.datatypes.crm.crm.foreignkey.DocumentIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author kapeshi
 */
@Entity
@Table(name = "BaseTemplateDocuments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"templateId", "documentId", "companyId"})})
public class BaseTemplateDocuments extends EMCEntityClass {

    private String templateId;
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("templateId", new EmailTemplateIdFK());
        toBuild.put("documentId", new DocumentIdFK());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("templateId");
        toBuild.add("documentId");
        return toBuild;
    }

}
