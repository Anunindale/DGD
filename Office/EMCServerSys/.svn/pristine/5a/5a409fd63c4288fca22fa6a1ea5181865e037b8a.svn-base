/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.framework;

import emc.reporttools.CompanyReportInformation;
import emc.reporttools.charts.EMCReportChartEnumIF;
import emc.reporttools.EMCReportConfig;
import java.util.List;

/**
 *
 * @author riaan
 */
public interface EMCReportLocalInterface {
    
    /**
     * Call this method from the client - overwrite as needed if not overwritten put query to execute in 
     * parameters position 1 as string.
     * @param parameters
     * @param userData
     * @return
     */
    public List<Object> getReportResult(List<Object> parameters,EMCUserData userData);

    /**
     * Call this method from the client - overwrite as needed if not overwritten put query to execute in
     * parameters position 1 as string.
     * @param queryList List containing EMCQuery.
     * @param reportConfig EMC Report Configuration class containing parameters.
     * @param userData User data.
     * @return Report result.
     */
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData);

    /**
     * Returns Company information used on reports.
     */
    public CompanyReportInformation getCompanyReportInformation(EMCUserData userData);

    /**
     * Returns data for the specified chart.
     * @param reportQuery Report query.
     * @param chartId Chart enum id.
     * @param chartEnum Chart enum values.
     * @param reportConfig Report config.
     * @param userData User data.
     * @return Data for the specified chart.
     */
    public <T extends EMCReportChartEnumIF> Object getReportChartData(EMCQuery reportQuery, int chartId, EMCReportChartEnumIF[] chartEnum, EMCReportConfig reportConfig, EMCUserData userData);


    public List<String[]> getReportImages(EMCQuery query, EMCReportConfig reportConfig, EMCUserData userData);
}
