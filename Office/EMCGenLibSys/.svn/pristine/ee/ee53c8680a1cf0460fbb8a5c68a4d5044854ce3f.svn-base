/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.BigDecimalQuantity;
import emc.datatypes.trec.cargocheck.Allowed;
import emc.datatypes.trec.cargocheck.foreignkeys.CargoCheckNumberFK;
import emc.datatypes.trec.chemicals.foreignkey.UNNumberFK;
import emc.datatypes.trec.treccards.ShippingNameFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECCargoCheckLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"cargoCheckNumber", "unNumber", "companyId"})})
public class TRECCargoCheckLines extends EMCEntityClass {

    private String cargoCheckNumber;
    private String unNumber;
    private String properShipping;
    private String packingGroup;
    private BigDecimal quantity = BigDecimal.ZERO;
    private boolean allowed;
    @Column(length = 2000)
    private String lineNote;
    @Column(length = 2000)
    private String classNote;

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getCargoCheckNumber() {
        return cargoCheckNumber;
    }

    public void setCargoCheckNumber(String cargoCheckNumber) {
        this.cargoCheckNumber = cargoCheckNumber;
    }

    public String getPackingGroup() {
        return packingGroup;
    }

    public void setPackingGroup(String packingGroup) {
        this.packingGroup = packingGroup;
    }

    public String getProperShipping() {
        return properShipping;
    }

    public void setProperShipping(String properShipping) {
        this.properShipping = properShipping;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("cargoCheckNumber", new CargoCheckNumberFK());
        toBuild.put("unNumber", new UNNumberFK());
        toBuild.put("properShipping", new ShippingNameFK());
        toBuild.put("packingGroup", new emc.datatypes.trec.cargocheck.PackingGroup());
        toBuild.put("quantity", new BigDecimalQuantity());
        toBuild.put("allowed", new Allowed());
        return toBuild;
    }


   /* @Override
    public EMCQuery buildQuery() {
        EMCQuery ret = super.buildQuery();
        ret.addOrderBy("recordID");
        return ret;
    }*/
    /**
     * @return the lineNote
     */
    public String getLineNote() {
        return lineNote;
    }

    /**
     * @param lineNote the lineNote to set
     */
    public void setLineNote(String lineNote) {
        this.lineNote = lineNote;
    }

    /**
     * @return the classNote
     */
    public String getClassNote() {
        return classNote;
    }

    /**
     * @param classNote the classNote to set
     */
    public void setClassNote(String classNote) {
        this.classNote = classNote;
    }
}
