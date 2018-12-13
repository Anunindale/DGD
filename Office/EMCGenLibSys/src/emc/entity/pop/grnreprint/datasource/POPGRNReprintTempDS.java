/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.grnreprint.datasource;

import emc.entity.pop.grnreprint.POPGRNReprintTemp;
import emc.inventory.ItemReferenceInterface;

/**
 *
 * @author wikus
 */
public class POPGRNReprintTempDS extends POPGRNReprintTemp implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;
    private String suppName;

    public POPGRNReprintTempDS() {
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

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }
}
