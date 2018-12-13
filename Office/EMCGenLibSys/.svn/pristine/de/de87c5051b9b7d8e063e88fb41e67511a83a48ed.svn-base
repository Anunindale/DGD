/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.dangerousgoods;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "DGCargoCheckLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"cargoMasterId","lineNumber", "companyId"})})
public class DGCargoCheckLines extends EMCEntityClass {
    private String cargoMasterId;
    private String lineNumber;
    private String unNumber;
    private String packingGroup;
    private double quantity;
    private boolean placcardReq;
    private boolean trecReq;
    private boolean allowedInLoad;

    /**
     * @return the cargoMasterId
     */
    public String getCargoMasterId() {
        return cargoMasterId;
    }

    /**
     * @param cargoMasterId the cargoMasterId to set
     */
    public void setCargoMasterId(String cargoMasterId) {
        this.cargoMasterId = cargoMasterId;
    }

    /**
     * @return the lineNumber
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the unNumber
     */
    public String getUnNumber() {
        return unNumber;
    }

    /**
     * @param unNumber the unNumber to set
     */
    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    /**
     * @return the packingGroup
     */
    public String getPackingGroup() {
        return packingGroup;
    }

    /**
     * @param packingGroup the packingGroup to set
     */
    public void setPackingGroup(String packingGroup) {
        this.packingGroup = packingGroup;
    }

    /**
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the placcardReq
     */
    public boolean isPlaccardReq() {
        return placcardReq;
    }

    /**
     * @param placcardReq the placcardReq to set
     */
    public void setPlaccardReq(boolean placcardReq) {
        this.placcardReq = placcardReq;
    }

    /**
     * @return the trecReq
     */
    public boolean isTrecReq() {
        return trecReq;
    }

    /**
     * @param trecReq the trecReq to set
     */
    public void setTrecReq(boolean trecReq) {
        this.trecReq = trecReq;
    }

    /**
     * @return the allowedInLoad
     */
    public boolean isAllowedInLoad() {
        return allowedInLoad;
    }

    /**
     * @param allowedInLoad the allowedInLoad to set
     */
    public void setAllowedInLoad(boolean allowedInLoad) {
        this.allowedInLoad = allowedInLoad;
    }
}
