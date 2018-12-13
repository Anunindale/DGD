/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.sop;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "SOPCustomerLabels", uniqueConstraints = {@UniqueConstraint(columnNames = {"customerId", "companyId"})})
public class SOPCustomerLabels extends EMCEntityClass{
    private String customerId;
    private String custLabelDocument;
    private String labelTitle;

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the custLabelDocument
     */
    public String getCustLabelDocument() {
        return custLabelDocument;
    }

    /**
     * @param custLabelDocument the custLabelDocument to set
     */
    public void setCustLabelDocument(String custLabelDocument) {
        this.custLabelDocument = custLabelDocument;
    }

    /**
     * @return the labelTitle
     */
    public String getLabelTitle() {
        return labelTitle;
    }

    /**
     * @param labelTitle the labelTitle to set
     */
    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }
}
