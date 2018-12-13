/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.grnlabels;

import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface LabelsLocal {

    public String getReportResult(java.util.List<java.lang.Object> parameters, emc.framework.EMCUserData userData);

    public java.lang.Boolean checkNumLabels(java.lang.String postMasterId, emc.framework.EMCUserData userData);

    /**
     * Reprints the lables
     * @param theData
     * @param userData
     * @return
     */
    public String reprintLables(String query, EMCUserData userData);

    public java.lang.String printGRNLables(java.util.List theList);
}
