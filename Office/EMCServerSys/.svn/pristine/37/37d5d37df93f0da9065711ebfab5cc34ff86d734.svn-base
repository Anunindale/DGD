/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.genericreport;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseGenericReportLocal {

    public java.util.List fetchReportData(java.lang.String tableName, emc.framework.EMCCommandClass cmd, int from, int max, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List fetchReportData(java.util.List<java.lang.String> fieldName, java.lang.String tableName, emc.framework.EMCCommandClass cmd, int from, int max, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
