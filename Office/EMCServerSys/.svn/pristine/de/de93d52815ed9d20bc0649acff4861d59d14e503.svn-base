/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.inventory.transactions.InventorySummaryUpdateOptions;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Local
public interface InventorySummaryLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the number of items with the given itemId and dimensions
     * available in stock.
     *
     * Deprecated - use getOnHandAvailable() method instead.
     *
     * @param itemId Id of item to check.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param warehouse Warehouse.
     * @param batch Batch.
     * @param serialNo Serial number.
     * @param location Location.
     * @param pallet Pallet.
     * @param userData Userdata.
     * @return A double indicating the number of available items.
     */
    @Deprecated
    public double getQuantityAvailable(java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String warehouse, java.lang.String batch, java.lang.String serialNo, java.lang.String location, java.lang.String pallet, emc.framework.EMCUserData userData);

    /**
     * Returns the number of items with the given itemId and dimensionId
     * available in stock.
     *
     * @param itemId Id of item to search for.
     * @param dimId Dimension id to search for.
     * @param userData User data.
     * @return A double indicating the number of available items.
     */
    public double getQuantityAvailable(java.lang.String itemId, long dimId, emc.framework.EMCUserData userData);

    /**
     * This method should be used by RESERVE only. It is called from
     * UpdateOnHand superclass
     *
     * @param toEx
     * @param suspence summary that will be inserted into SQL to show that this
     * stock is taken
     * @param userData
     * @return List with 0 - the availableQty 1 - the recordId of inserted
     * suspense summary
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getQuantityAvailableLoadSuspenceTrans(EMCQuery checkQty, InventorySummary suspence, InventorySummaryUpdateOptions theOption, String transRef, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the total number of items available in stock for the specified
     * item/dimensions. Any dimension may be specified as null or a blank
     * String. Dimensions which are specified as null or blank Strings will not
     * be included in the group by clause.
     *
     * @param itemId Item id.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param conn Connection used to execute JDBC queries directly. May be
     * null.
     * @param userData User data.
     * @return A BigDecimal containing the total number of items available in
     * stock for the specified item/dimensions.
     */
    public BigDecimal getOnHandAvailable(String itemId, String dimension1, String dimension2, String dimension3, Connection conn, EMCUserData userData);

    /**
     * Returns the total number of items available in stock for the specified
     * item/dimensions. Any dimension may be specified as null or a blank
     * String. Dimensions which are specified as null or blank Strings will not
     * be included in the group by clause.
     *
     * @param itemId Item id.
     * @param dimension1 Dimension 1.
     * @param dimension2 Dimension 2.
     * @param dimension3 Dimension 3.
     * @param warehouse Warehouse.
     * @param location Location.
     * @param batch Batch.
     * @param serialNo Serial number.
     * @param pallet Pallet.
     * @param conn Connection used to execute JDBC queries directly. May be
     * null.
     * @param userData User data.
     * @return A BigDecimal containing the total number of items available in
     * stock for the specified item/dimensions.
     */
    public BigDecimal getOnHandAvailable(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData);

    public BigDecimal getOnHandTotal(String itemId, String dimension1, String dimension2, String dimension3, Connection conn, EMCUserData userData);

    public BigDecimal getOnHandTotal(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData);

    public BigDecimal getOnHandReserved(String itemId, String dimension1, String dimension2, String dimension3, String warehouse, String location, String batch, String serialNo, String pallet, Connection conn, EMCUserData userData);

    public boolean fixBlanketOrderSummaries(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
