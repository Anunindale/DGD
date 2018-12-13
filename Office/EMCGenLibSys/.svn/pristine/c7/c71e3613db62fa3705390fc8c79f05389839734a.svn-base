/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.sop.datasource.blanketorderstatusenquiry;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.blanketorderstatusenquiry.Balance;
import emc.datatypes.sop.blanketorderstatusenquiry.CalledOffQuantity;
import emc.datatypes.sop.blanketorderstatusenquiry.Dimension1;
import emc.datatypes.sop.blanketorderstatusenquiry.Dimension2;
import emc.datatypes.sop.blanketorderstatusenquiry.Dimension3;
import emc.datatypes.sop.blanketorderstatusenquiry.ItemReference;
import emc.datatypes.sop.blanketorderstatusenquiry.PackedQuantity;
import emc.datatypes.sop.blanketorderstatusenquiry.Quantity;
import emc.datatypes.sop.blanketorderstatusenquiry.QuantityToPack;
import emc.datatypes.sop.blanketorderstatusenquiry.WIPQuantity;
import emc.entity.sop.SOPSalesOrderLines;
import emc.inventory.ItemReferenceInterface;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class SOPBlanketOrderStatusEnquiryDS extends SOPSalesOrderLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;
    private BigDecimal wipQuantity = BigDecimal.ZERO;          //Open STK Assembly Orders.
    private BigDecimal packedQuantity = BigDecimal.ZERO;       //Completed STK Assembly Orders.
    private BigDecimal quantityToPack = BigDecimal.ZERO;       //Line quantity - packed quantity - called off quantity.
    private BigDecimal calledOffQuantity = BigDecimal.ZERO;    //Quantity released from Blanket Order.
    private BigDecimal balance = BigDecimal.ZERO;              //Called off - line quantity.

    /** Creates a new instance of SOPBlanketOrderStatusEnquiryDS. */
    public SOPBlanketOrderStatusEnquiryDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("quantity", new Quantity());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("wipQuantity", new WIPQuantity());
        toBuild.put("packedQuantity", new PackedQuantity());
        toBuild.put("quantityToPack", new QuantityToPack());
        toBuild.put("calledOffQuantity", new CalledOffQuantity());
        toBuild.put("balance", new Balance());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());

        return toBuild;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getWipQuantity() {
        return wipQuantity;
    }

    public void setWipQuantity(BigDecimal wipQuantity) {
        this.wipQuantity = wipQuantity;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCalledOffQuantity() {
        return calledOffQuantity;
    }

    public void setCalledOffQuantity(BigDecimal calledOffQuantity) {
        this.calledOffQuantity = calledOffQuantity;
    }

    public BigDecimal getPackedQuantity() {
        return packedQuantity;
    }

    public void setPackedQuantity(BigDecimal packedQuantity) {
        this.packedQuantity = packedQuantity;
    }

    public BigDecimal getQuantityToPack() {
        return quantityToPack;
    }

    public void setQuantityToPack(BigDecimal quantityToPack) {
        this.quantityToPack = quantityToPack;
    }
}
