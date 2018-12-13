/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.crm.crm.DocumentId;
import emc.datatypes.crm.crm.DocumentType;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CRMDocuments", uniqueConstraints = {@UniqueConstraint(columnNames = {"documentId", "companyId"})})
public class CRMDocuments extends EMCEntityClass {

    private String documentId;
    private String type;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("documentId", new DocumentId());
        toBuild.put("type", new DocumentType());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("documentId");
        toBuild.add("type");
        return toBuild;
    }
}
