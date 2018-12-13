/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.sop;

import emc.framework.EMCEntityClass;

/**
 *
 * @author wikus
 */
public class SOPMassPostingErrorLog extends EMCEntityClass{

    private String salesOrderId;
    private String errorLog;

    public SOPMassPostingErrorLog() {
    }

    public SOPMassPostingErrorLog(String salesOrderId, String errorLog) {
        this.salesOrderId = salesOrderId;
        this.errorLog = errorLog;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

}
