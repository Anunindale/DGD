/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimension1enquiry;

import emc.entity.inventory.dimension1enquiry.InventoryDimension1Enquiry;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class InventoryDimension1EnquiryBean extends EMCEntityBean implements InventoryDimension1EnquiryLocal {

    public InventoryDimension1EnquiryBean() {
    }

    /**
     *
     * @param values
     * @param currentSessionId
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    public long populate(List<String> values, long currentSessionId, EMCUserData userData) throws EMCEntityBeanException {
        clearOldData(currentSessionId, userData);
        EMCQuery dimensionQuery = null;

        while (values.contains("")) {
            values.remove("");
        }

        List<InventoryDimension1> queryResult = new ArrayList<InventoryDimension1>();

        if (values.size() == 0) {
            dimensionQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
            dimensionQuery.addAnd("dimensionId", null);

        } else if (values.size() == 1) {
            StringBuilder builder = new StringBuilder("SELECT dimension1 FROM InventoryDimension1 dimension1, InventoryDimension1Lines d1 WHERE d1.dimension1Id = dimension1.dimensionId AND d1.dimension3Id = '");
            builder.append(values.get(0));
            builder.append("' AND dimension1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' GROUP BY dimension1.dimensionId");

            dimensionQuery = new EMCQuery(builder.toString());

        } else if (values.size() == 2) {
            StringBuilder builder = new StringBuilder("SELECT dimension1 FROM InventoryDimension1 dimension1, InventoryDimension1Lines d1, InventoryDimension1Lines d2 WHERE d1.dimension1Id = dimension1.dimensionId AND d2.dimension1Id = d1.dimension1Id AND d1.dimension3Id = '");
            builder.append(values.get(0));
            builder.append("' AND d2.dimension3Id = '");
            builder.append(values.get(1));
            builder.append("' AND dimension1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d2.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' GROUP BY dimension1.dimensionId");

            dimensionQuery = new EMCQuery(builder.toString());

        } else if (values.size() == 3) {
            StringBuilder builder = new StringBuilder("SELECT dimension1 FROM InventoryDimension1 dimension1, InventoryDimension1Lines d1, InventoryDimension1Lines d2,InventoryDimension1Lines d3 WHERE d1.dimension1Id = dimension1.dimensionId AND d2.dimension1Id = d1.dimension1Id AND d3.dimension1Id = d2.dimension1Id AND d1.dimension3Id = '");
            builder.append(values.get(0));
            builder.append("' AND d2.dimension3Id = '");
            builder.append(values.get(1));
            builder.append("' AND d3.dimension3Id = '");
            builder.append(values.get(2));
            builder.append("' AND dimension1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d2.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d3.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' GROUP BY dimension1.dimensionId");

            dimensionQuery = new EMCQuery(builder.toString());

        } else if (values.size() == 4) {
            StringBuilder builder = new StringBuilder("SELECT dimension1 FROM InventoryDimension1 dimension1, InventoryDimension1Lines d1, InventoryDimension1Lines d2, InventoryDimension1Lines d3, InventoryDimension1Lines d4 WHERE d1.dimension1Id = dimension1.dimensionId AND d2.dimension1Id = d1.dimension1Id AND d3.dimension1Id = d2.dimension1Id AND d4.dimension1Id = d3.dimension1Id AND d1.dimension3Id = '");
            builder.append(values.get(0));
            builder.append("' AND d2.dimension3Id = '");
            builder.append(values.get(1));
            builder.append("' AND d3.dimension3Id = '");
            builder.append(values.get(2));
            builder.append("' AND d4.dimension3Id = '");
            builder.append(values.get(3));
            builder.append("' AND dimension1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d2.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d3.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d4.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' GROUP BY dimension1.dimensionId");

            dimensionQuery = new EMCQuery(builder.toString());

        } else if (values.size() == 5) {
            StringBuilder builder = new StringBuilder("SELECT dimension1 FROM InventoryDimension1 dimension1, InventoryDimension1Lines d1, InventoryDimension1Lines d2, InventoryDimension1Lines d3, InventoryDimension1Lines d4, InventoryDimension1Lines d5 WHERE d1.dimension1Id = dimension1.dimensionId AND d2.dimension1Id = d1.dimension1Id AND d3.dimension1Id = d2.dimension1Id AND d4.dimension1Id = d3.dimension1Id AND d5.dimension1Id = d4.dimension1Id AND d1.dimension3Id = '");
            builder.append(values.get(0));
            builder.append("' AND d2.dimension3Id = '");
            builder.append(values.get(1));
            builder.append("' AND d3.dimension3Id = '");
            builder.append(values.get(2));
            builder.append("' AND d4.dimension3Id = '");
            builder.append(values.get(3));
            builder.append("' AND d5.dimension3Id = '");
            builder.append(values.get(4));
            builder.append("' AND dimension1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d1.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d2.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d3.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d4.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' AND d5.companyId = '");
            builder.append(userData.getCompanyId());
            builder.append("' GROUP BY dimension1.dimensionId");

            dimensionQuery = new EMCQuery(builder.toString());
        }

        queryResult = util.executeGeneralSelectQuery(dimensionQuery.toString(), userData);
        long sessionID = System.currentTimeMillis();
        for (InventoryDimension1 current : queryResult) {
            InventoryDimension1Enquiry enquiry = new InventoryDimension1Enquiry();
            enquiry.setDimension1(current.getDimensionId());
            enquiry.setDescription(current.getDescription());
            enquiry.setSessionId(sessionID);
            this.insert(enquiry, userData);
        }

        return sessionID;
    }

    /**
     *
     * @param sessionId
     * @param userData
     */
    public boolean clearOldData(long sessionId, EMCUserData userData) {

        //setup delete query

        EMCQuery deleteQuery = new EMCQuery(enumQueryTypes.DELETE, InventoryDimension1Enquiry.class);
        deleteQuery.openAndConditionBracket();
        deleteQuery.openConditionBracket(EMCQueryBracketConditions.NONE);
        deleteQuery.addAnd("createdBy", userData.getUserName());
        deleteQuery.addAnd("createdDate", dateHandler.nowDate(), EMCQueryConditions.LESS_THAN);
        deleteQuery.closeConditionBracket();
        deleteQuery.addOr("sessionId", sessionId);
        deleteQuery.closeConditionBracket();
        //execute delete query
        util.executeUpdate(deleteQuery, userData);

        return true;
    }
}
