/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryDimension1Bean extends EMCEntityBean implements InventoryDimension1Local {

    @EJB
    private InventoryDimension1Local thisBean;

    /** Creates a new instance of InventoryDimension1Bean */
    public InventoryDimension1Bean() {
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            InventoryDimension1 dimension1 = (InventoryDimension1) vobject;

            if (dimension1.isActive()) {
                ret = ret && checkDimension1Unique(dimension1.getDimensionId(), userData);
            }
        }

        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {

            InventoryDimension1 dimension1 = (InventoryDimension1) vobject;
            InventoryDimension1 persisted = (InventoryDimension1) util.findDetachedPersisted(dimension1, userData);

            if (dimension1.isActive() && !persisted.isActive()) {
                ret = ret && checkDimension1Unique(dimension1.getDimensionId(), userData);
                updateCombinationActive(dimension1, userData);
            } else {
                if (!dimension1.isActive() && persisted.isActive()) {
                    updateCombinationActive(dimension1, userData);
                }
            }
        }
        return ret;
    }

    private void updateCombinationActive(InventoryDimension1 dimension1, EMCUserData userData) throws EMCEntityBeanException {
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
//        query.addAnd("dimension1Id", dimension1.getDimensionId());
//        if (dimension1.isActive()) {
//            query.addAnd("active", false);
//            query.addAnd("deactivatedByDimension1", true);
//        } else {
//            query.addAnd("active", true);
//        }
//        List<InventoryItemDimensionCombinations> combinationsList = util.executeGeneralSelectQuery(query, userData);
//        for (InventoryItemDimensionCombinations combination : combinationsList) {
//            if (dimension1.isActive()) {
//                combination.setActive(true);
//                combination.setDeactivatedByDimension1(false);
//            } else {
//                combination.setActive(false);
//                combination.setDeactivatedByDimension1(true);
//            }
//            userData.setUserData(5, true);
//            combinationBean.update(combination, userData);
//        }
    }

    @Override
    public String findDimensionDescription(String dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
        query.addAnd("dimensionId", dimensionId);
        query.addField("description");
        Object ret = util.executeSingleResultQuery(query, userData);
        return isBlank(ret) ? "" : ret.toString();
    }

    /**
     * Approves the specified dimension 1.  This method checks that the dimension setup is unique.
     * @param dimensionId Dimension id of dimension to approve.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    //public void approveDimension1(String dimensionId, EMCUserData userData) throws EMCEntityBeanException {
    //    if (checkDimension1Unique(dimensionId, userData)) {
    //        TODO:  Complete this method.  For now, validate with tick on client.
    //    }
    //}
    /**
     * Returns a boolean indicating whether the specified dimension 1 sequence is unique.
     * For an explanation of the purpose of this, please see task #591.
     *
     * @param dimension1Id Dimension 1 id.
     * @param userData User data.
     * @return A boolean indicating whether the specified dimension 1 sequence is unique.
     */
    private boolean checkDimension1Unique(String dimension1Id, EMCUserData userData) {
        EMCQuery linesQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Lines.class);
        linesQuery.addAnd("dimension1Id", dimension1Id);

        List<InventoryDimension1Lines> lines = (List<InventoryDimension1Lines>) util.executeGeneralSelectQuery(linesQuery, userData);

        EMCQuery nestedQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Lines.class);
        nestedQuery.addField("dimension1Id");
        nestedQuery.addGroupBy("dimension1Id");
        nestedQuery.addOrHavingAggregateFunction("COUNT", InventoryDimension1Lines.class.getName(), "dimension1Id", EMCQueryConditions.EQUALS, lines.size());

        EMCQuery validateQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Lines.class);
        validateQuery.addField("dimension1Id");
        validateQuery.addAnd("dimension1Id", nestedQuery, EMCQueryConditions.IN);
        validateQuery.addGroupBy("dimension1Id");
        validateQuery.addOrHavingAggregateFunction("COUNT", InventoryDimension1Lines.class.getName(), "dimension1Id", EMCQueryConditions.EQUALS, lines.size());
        //Exclude current dimension, hence, if anything is returned, it is a duplicate.
        validateQuery.addAnd("dimension1Id", dimension1Id, EMCQueryConditions.NOT);

        boolean seqValid = true;

        if (!lines.isEmpty()) {
            validateQuery.openAndConditionBracket();

            boolean first = true;

            List<Integer> seqList = new ArrayList<Integer>();

            for (InventoryDimension1Lines line : lines) {
                //Check that both dimension3 id and sequence number match

                if (first) {
                    validateQuery.openConditionBracket(EMCQueryBracketConditions.NONE);
                    first = false;
                } else {
                    validateQuery.openConditionBracket(EMCQueryBracketConditions.OR);
                }

                validateQuery.addAnd("dimension3Id", line.getDimension3Id());
                validateQuery.addAnd("seq", line.getSeq());

                validateQuery.closeConditionBracket();

                //Check If Sequence of colours are unique.
                if (seqList.contains(line.getSeq())) {
                    if (seqValid) {
                        seqValid = false;
                        logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM1_SEQ_DUP, userData);
                    }
                } else {
                    seqList.add(line.getSeq());
                }
            }

            validateQuery.closeConditionBracket();
        }

        List<String> duplicates = (List<String>) util.executeGeneralSelectQuery(validateQuery, userData);

        for (String duplicate : duplicates) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.DIM1_DUP, userData, duplicate, dimension1Id);
        }

        if (duplicates.isEmpty()) {
            return seqValid;
        } else {
            return false;
        }
    }

    /**
     * Returns an InventoryDimension1 instance for the specified dimension id.
     * @param dimensionId Dimension id to search for.
     * @param userData User data.
     * @return An InventoryDimension1 instance for the specified dimension id, or null, if none is found.
     */
    @Override
    public InventoryDimension1 getDimension1(String dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
        query.addAnd("dimensionId", dimensionId);

        return (InventoryDimension1) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        thisBean.updateSortCodes(userData);
        return ret;
    }

    @Override
    public void updateSortCodes(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
        query.addOrderBy("dimensionId");
        List<InventoryDimension1> dimList = util.executeGeneralSelectQuery(query, userData);
        int count = 0;
        for (InventoryDimension1 dim : dimList) {
            if (dim.getSortCode() != count) {
                dim.setSortCode(count);
                this.doUpdate(dim, userData);
            }
            count++;
        }
    }
}
