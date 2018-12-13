/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.developertools;

import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DeveloperToolsLocal {
    List<Object> testQuery(String query, EMCUserData userData);

    /**
     * Returns an XML String declaring fields in the Jasper Reports format.
     * @param reportDSClassName Class name of report datasource.
     * @param userData User data.
     * @return An XML String declaring fields in the Jasper Reports format.
     */
    public java.lang.String generateReportFieldsXML(java.lang.String reportDSClassName, emc.framework.EMCUserData userData);

    /**
     * This method executes a query. The util method used to execute the query will attempt to add a company id.
     * @param query Query to execute.
     * @param userData User data.
     * @return The data selected by the specified query.
     */
    public java.util.List<java.lang.Object> executeQuery(emc.framework.EMCQuery query, emc.framework.EMCUserData userData);

    public int executeNativeQuery(java.lang.String query);

    public java.util.List<java.lang.Object> executeNativeQuery(emc.framework.EMCQuery query, emc.framework.EMCUserData userData);
}
