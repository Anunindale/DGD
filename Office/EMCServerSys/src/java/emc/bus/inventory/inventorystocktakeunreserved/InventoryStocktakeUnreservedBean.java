/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.inventorystocktakeunreserved;

import emc.entity.inventory.InventoryStocktakeUnreserved;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class InventoryStocktakeUnreservedBean extends EMCEntityBean implements InventoryStocktakeUnreservedLocal {

    public InventoryStocktakeUnreservedBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeUnreserved rec = (InventoryStocktakeUnreserved) iobject;
        rec.setUniqueIdentifier(rec.getReferenceJournal() + rec.getReferenceType() + rec.getReferenceId() + rec.getItemId() + rec.getDimensionId());
        return super.insert(rec, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryStocktakeUnreserved rec = (InventoryStocktakeUnreserved) uobject;
        rec.setUniqueIdentifier(rec.getReferenceJournal() + rec.getReferenceType() + rec.getReferenceId() + rec.getItemId() + rec.getDimensionId());

        InventoryStocktakeUnreserved persistedRec = (InventoryStocktakeUnreserved) findSQLVersionForEntity(rec, userData);
        if (rec.isResolved() && !persistedRec.isResolved()) {
            rec.setResolvedBy(userData.getUserName());
            rec.setResolvedDate(Functions.nowDate());
        }

        return super.update(rec, userData);
    }

    @Override
    public boolean deleteStockTakeUnreserved(boolean deleteCompleted, Date deletionDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, InventoryStocktakeUnreserved.class);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        if (deletionDate != null) {
            query.addOr("createdDate", deletionDate, EMCQueryConditions.LESS_THAN_EQ);
        }
        if (deleteCompleted) {
            query.addOr("resolved", true);
        }
        query.closeConditionBracket();
        util.executeUpdate(query, userData);

        return true;
    }
}
