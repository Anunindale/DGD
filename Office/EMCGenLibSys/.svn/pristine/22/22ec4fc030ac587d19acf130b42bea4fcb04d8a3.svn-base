/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.signatures.SignatureId;
import emc.datatypes.base.signatures.SignaturePath;
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
@Table(name = "BaseEmailSignatures", uniqueConstraints = {@UniqueConstraint(columnNames = {"signatureId", "companyId"})})
public class BaseEmailSignatures extends EMCEntityClass {

    private String signatureId;
    private String signaturePath;

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("signatureId", new SignatureId());
        toBuild.put("signaturePath", new SignaturePath());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("signatureId");
        return toBuild;
    }
}
