/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.priceaudittrail.ItemReference;
import emc.datatypes.sop.priceaudittrail.Source;
import emc.datatypes.sop.priceaudittrail.UserName;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailDS extends SOPPriceAuditTrail implements ItemReferenceInterface {

    private String userName;
    private String itemReference;
    private String itemDescription;
    private String cutomerName;
    private String sourceReference;

    public SOPPriceAuditTrailDS() {
        this.setDataSource(true);
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCutomerName() {
        return cutomerName;
    }

    public void setCutomerName(String cutomerName) {
        this.cutomerName = cutomerName;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("userName", new UserName());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("sourceReference", new Source());
        return toBuild;
    }
}
