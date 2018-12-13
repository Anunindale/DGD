package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.CostAdjustment;
import emc.datatypes.debtors.customerinvoice.CostAtAverage;
import emc.datatypes.debtors.customerinvoice.StdUnitPrice;
import emc.datatypes.debtors.customerinvoice.VATAmount;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.sop.pricechangereason.foreignkey.PriceChangeReasonFKNM;
import emc.datatypes.sop.salesorderlines.Dimension1;
import emc.datatypes.sop.salesorderlines.Dimension2;
import emc.datatypes.sop.salesorderlines.Dimension3;
import emc.datatypes.sop.salesorderlines.DiscountPerc;
import emc.datatypes.sop.salesorderlines.ExternalReference;
import emc.datatypes.sop.salesorderlines.Price;
import emc.datatypes.sop.salesorderlines.Quantity;
import emc.datatypes.sop.salesorderlines.UOM;
import emc.datatypes.sop.salesordermaster.foreignkeys.CanceledSalesOrderNoFK;
import emc.datatypes.systemwide.LineNo;
import emc.entity.sop.superclass.SOPSalesOrderLinesSuper;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @Author wikus
 */
@Entity
@Table(name = "SOPSalesOrderCancelLines")
public class SOPSalesOrderCancelLines extends SOPSalesOrderLinesSuper {

    private String cancelReason;

    public SOPSalesOrderCancelLines() {
        this.setDataSource(false);
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("salesOrderNo", new CanceledSalesOrderNoFK());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new WarehouseFK());
        toBuild.put("quantity", new Quantity());
        toBuild.put("uom", new UOM());
        toBuild.put("price", new Price());
        toBuild.put("discountPerc", new DiscountPerc());
        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("vatAmount", new VATAmount());
        toBuild.put("stdUnitPrice", new StdUnitPrice());
        toBuild.put("costAtAverage", new CostAtAverage());
        toBuild.put("costAdjustment", new CostAdjustment());
        toBuild.put("priceChangeReason", new PriceChangeReasonFKNM());
        toBuild.put("externalReference", new ExternalReference());

        return toBuild;
    }
}
