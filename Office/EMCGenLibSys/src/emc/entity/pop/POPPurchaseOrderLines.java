/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.pop.purchaseorderlines.DesignCode;
import emc.datatypes.pop.purchaseorderlines.PrintColour;
import emc.entity.pop.purchaseordersuperclass.POPPurchaseOrderLinesSuper;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPPurchaseOrderLines", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lineNo", "purchaseOrderId", "companyId"})})
public class POPPurchaseOrderLines extends POPPurchaseOrderLinesSuper implements Serializable {

    //Ninian Spesific Fields
    private String designCode;
    private String printColour;
    private double fabricPrice;
    private double printPrice;
    //Ninian Spesific Fields

    //Ninian Spesific Fields
    public String getDesignCode() {
        return designCode;
    }

    public void setDesignCode(String designCode) {
        this.designCode = designCode;
    }

    public double getFabricPrice() {
        return fabricPrice;
    }

    public void setFabricPrice(double fabricPrice) {
        this.fabricPrice = fabricPrice;
    }

    public String getPrintColour() {
        return printColour;
    }

    public void setPrintColour(String printColour) {
        this.printColour = printColour;
    }

    public double getPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(double printPrice) {
        this.printPrice = printPrice;
    }
    //Ninian Spesific Fields

    /**
     * Creates a new instance of POPPurchaseOrderLines
     */
    public POPPurchaseOrderLines() {
        this.setEmcLabel("Purchase Order Lines");
        this.setDataSource(false);
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("printColour", new PrintColour());
        toBuild.put("designCode", new DesignCode());
        return toBuild;
    }
}
