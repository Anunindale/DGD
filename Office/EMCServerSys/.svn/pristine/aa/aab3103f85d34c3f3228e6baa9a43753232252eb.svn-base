/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import emc.bus.base.reporttools.BaseReportTextLocal;
import emc.entity.base.BaseCompanyTable;
import emc.enums.enumQueryTypes;
import emc.reporttools.CompanyReportInformation;
import emc.reporttools.charts.EMCReportChartEnumIF;
import emc.reporttools.EMCReportConfig;
import emc.reporttools.EMCReportDimensionSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;

/**
 *
 * @author rico 
 */
public class EMCReportBean extends EMCBean implements EMCReportLocalInterface {

    @EJB
    protected BaseReportTextLocal reportTextBean;

    public EMCReportBean() {
    }

    /**
     * This method executes the query
     * @param sqlQuery
     * @param userData
     * @return
     */
    public List<Object> executeQuery(String sqlQuery, EMCUserData userData) {
        return util.executeGeneralSelectQuery(sqlQuery, userData);
    }

    /**
     * This method executes the query
     * @param sqlQuery
     * @param userData
     * @return
     */
    public List<Object> executeQuery(EMCQuery query, EMCUserData userData) {
        return util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * This method executes the native query
     * @param sqlQuery
     * @param userData
     * @return
     */
    public List<Object> executeNativeQuery(EMCQuery sqlQuery, EMCReportDimensionSetup setup, EMCUserData userData) {
        try {
            Class dim1 = setup.getDim1Entity() == null ? null : Class.forName(setup.getDim1Entity());
            Class dim2 = setup.getDim2Entity() == null ? null : Class.forName(setup.getDim2Entity());
            Class dim3 = setup.getDim3Entity() == null ? null : Class.forName(setup.getDim3Entity());

            if (!isBlank(setup.getResultClass())) {
                Class resultSetClass = Class.forName(setup.getResultClass());

                return util.executeQuerySortedByDimensions(sqlQuery, resultSetClass, dim1, setup.getDim1Field(), dim2, setup.getDim2Field(), dim3, setup.getDim3Field(), true, setup.getSortOrder(), userData);
            } else {
                //Use result set name.  Old code.
                return util.executeQuerySortedByDimensions(sqlQuery, setup.getResultSetName(), dim1, setup.getDim1Field(), dim2, setup.getDim2Field(), dim3, setup.getDim3Field(), true, setup.getSortOrder(), userData);
            }
        } catch (ClassNotFoundException ex) {
            logMessage(Level.SEVERE, "Failed to execute native query to get report results.", userData);
            return new ArrayList<Object>();
        }
    }

    /**
     * Overwrite this method to manipulate results before dispatch to the client
     * @param queryResult
     * @param userData
     * @return
     */
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        return queryResult;
    }

    /**
     * Call this method from the client - overwrite as needed if not overwritten put query to execute in 
     * parameters position 1 as string.
     * @param parameters
     * @param userData
     * @return
     */
    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> result;
        if (parameters.size() > 2 && parameters.get(2) != null && parameters.get(2) instanceof EMCReportDimensionSetup) {
            EMCQuery query = (EMCQuery) parameters.get(1);
            EMCReportDimensionSetup setup = (EMCReportDimensionSetup) parameters.get(2);
            result = executeNativeQuery(manipulateQuery(query, userData), setup, userData);
        } else {
            if (parameters.get(1) instanceof EMCQuery) {
                result = executeQuery(manipulateQuery((EMCQuery) parameters.get(1), userData), userData);
            } else {
                result = executeQuery(parameters.get(1).toString(), userData);
            }
        }

        //Detach selected entities to save on memory.  The container will no longer try to manage these entities
        util.detachEntities(userData);

        return manipulateQueryResult(result, null, userData);
    }

    /** Get the default company information for the current company. */
    @Override
    public CompanyReportInformation getCompanyReportInformation(EMCUserData userData) {
        String className = BaseCompanyTable.class.getName();
        EMCQuery companyQuery = new EMCQuery(enumQueryTypes.SELECT, className);
        companyQuery.addAnd("companyId", util.getCompanyId(className, userData));

        BaseCompanyTable company = (BaseCompanyTable) util.executeSingleResultQuery(companyQuery, userData);

        CompanyReportInformation companyReportInfo = new CompanyReportInformation();
        companyReportInfo.setCompanyName(company.getCompanyName());
        companyReportInfo.setTradingAs(company.getCoTradingAs());
        companyReportInfo.setWebsite(company.getCoWebsite());

        return companyReportInfo;
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCReportConfig reportConfig, EMCUserData userData) {
        List<Object> result;
        if (parameters.size() > 2 && parameters.get(2) != null && parameters.get(2) instanceof EMCReportDimensionSetup) {
            EMCQuery query = (EMCQuery) parameters.get(1);
            EMCReportDimensionSetup setup = (EMCReportDimensionSetup) parameters.get(2);
            result = executeNativeQuery(manipulateQuery(query, userData), setup, userData);
        } else {
            if (parameters.get(1) instanceof EMCQuery) {
                result = executeQuery(manipulateQuery((EMCQuery) parameters.get(1), userData), userData);
            } else {
                result = executeQuery(parameters.get(1).toString(), userData);
            }
        }

        //Detach selected entities to save on memory.  The container will no longer try to manage these entities
        util.detachEntities(userData);

        return manipulateQueryResult(result, reportConfig.getParameters(), userData);
    }

    /**
     * Removes empty address lines.
     * @param addressLine1
     * @param addressLine2
     * @param addressLine3
     * @param addressLine4
     * @param addressLine5
     * @param postalCode
     * @return A List with a size of 6 with the compressed address lines
     */
    protected List<String> concertinaAddress(String addressLine1, String addressLine2, String addressLine3, String addressLine4, String addressLine5, String postalCode) {
        List<String> addressList = new ArrayList<String>();
        if (!isBlank(addressLine1)) {
            addressList.add(addressLine1);
        }
        if (!isBlank(addressLine2)) {
            addressList.add(addressLine2);
        }
        if (!isBlank(addressLine3)) {
            addressList.add(addressLine3);
        }
        if (!isBlank(addressLine4)) {
            addressList.add(addressLine4);
        }
        if (!isBlank(addressLine5)) {
            addressList.add(addressLine5);
        }
        if (!isBlank(postalCode)) {
            addressList.add(postalCode);
        }
        while (addressList.size() < 6) {
            addressList.add("");
        }
        return addressList;
    }

    protected <T extends EMCReportChartEnumIF> Object getReportChartData(List<Object> reportData, T chart, EMCUserData userData) {
        //Do nothing
        return null;
    }

    /**
     * Returns data for the specified chart.
     * @param reportQuery Report query.
     * @param chartId Chart enum id.
     * @param chartEnum Chart enum values.
     * @param reportConfig Report config.
     * @param userData User data.
     * @return Data for the specified chart.
     */
    @Override
    public final <T extends EMCReportChartEnumIF> Object getReportChartData(EMCQuery reportQuery, int chartId, EMCReportChartEnumIF[] chartEnum, EMCReportConfig reportConfig, EMCUserData userData) {
        T chart = (T) chartEnum[0].fromId(chartId);

        List<Object> reportData = executeQuery(reportQuery, userData);
        util.detachEntities(userData);

        return getReportChartData(reportData, chart, userData);
    }

    /**
     * This method is called by getReportResult() immediately before selecting data.
     * If you need to make any changes to a report query (i.e., selecting specific
     * fields only), override this method.  By default, this method returns the
     * specified query as is.
     * @param query Query to be manipulated.
     * @param userData User data.
     * @returns The query to be used when selecting data for a report.
     */
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        return query;
    }

    public List<String[]> getReportImages(EMCQuery query, EMCReportConfig reportConfig, EMCUserData userData) {
        return new ArrayList<String[]>();
    }
}
