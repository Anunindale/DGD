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
@Table(name = "DGCargoCheckMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"cargoMasterId", "companyId"})})
public class DGCargoCheckMaster extends EMCEntityClass {
    private String cargoMasterId;
    private String description;

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
