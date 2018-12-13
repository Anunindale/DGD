/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author asd_admin
 */
@Entity
@Table(name = "TRECCustomerChemicals", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"chemicalId", "customerId", "companyId"})})
public class TRECCustomerChemicals extends TRECChemicalsSuper {

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
