/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools;

import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riaan
 */
@Stateless
public class DeveloperToolsBean implements DeveloperToolsLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private EMCServerUtilityLocal util;

    public List<Object> testQuery(String query, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();
        try {
            ret = em.createQuery(query).getResultList();
        } catch (Exception e) {
            ret.add("Exception occured: " + e.getMessage());
        } finally {
            return ret;
        }
    }

    public int executeNativeQuery(String query) {
        try {
            return em.createNativeQuery(query).executeUpdate();
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * This method executes a query. The util method used to execute the query
     * will attempt to add a company id.
     *
     * @param query Query to execute.
     * @param userData User data.
     * @return The data selected by the specified query.
     */
    public List<Object> executeQuery(EMCQuery query, EMCUserData userData) {
        return util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * This method executes a query. The util method used to execute the query
     * will attempt to add a company id. If result list contains bytes this will 
     * be converted to string
     *
     * @param query Query to execute.
     * @param userData User data.
     * @return The data selected by the specified query.
     */
    public List<Object> executeNativeQuery(EMCQuery query, EMCUserData userData) {

        List resultList = new ArrayList();
        resultList = util.executeNativeQuery(query, userData);
        byte[] byteArray;
        List retList = new ArrayList();
        for (Object o : resultList) {
            if (o instanceof byte[]) {
                byteArray = (byte[]) o;
                String st = new String(byteArray, StandardCharsets.UTF_8);
                retList.add(st);
            } else {
                retList.add(o);
            }

        }

        return retList;
    }

    /**
     * Returns an XML String declaring fields in the Jasper Reports format.
     *
     * @param reportDSClassName Class name of report datasource.
     * @param userData User data.
     * @return An XML String declaring fields in the Jasper Reports format.
     */
    public String generateReportFieldsXML(String reportDSClassName, EMCUserData userData) {
        try {
            StringBuilder sb = new StringBuilder();

            Class dsClass = Class.forName(reportDSClassName);
            Field[] fields = dsClass.getDeclaredFields();

            for (Field field : fields) {
                sb.append("\t\t<field name=\"" + field.getName() + "\" class=\"" + getReportClass(field) + "\">\n");
                sb.append("\t\t\t<fieldDescription><![CDATA[@" + field.getName() + "]]></fieldDescription>\n");
                sb.append("</field>\n");
            }

            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create report XML.", userData);
            return null;
        }
    }

    /**
     * Returns the class used to represent the given field on a Jasper Report.
     */
    private String getReportClass(Field field) {
        Class fieldClass = field.getType();



        if (fieldClass.equals(boolean.class)) {
            return Boolean.class
                    .getName();
        } else if (fieldClass.equals(int.class)) {
            return Integer.class
                    .getName();
        } else if (fieldClass.equals(long.class)) {
            return Long.class
                    .getName();
        } else if (fieldClass.equals(double.class)) {
            return Double.class
                    .getName();
        } else {
            return fieldClass.getName();
        }
    }
}
