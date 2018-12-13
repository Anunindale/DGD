/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.consolidatedpickinglist;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.consolidatedpickinglistlines.Dimension1;
import emc.datatypes.inventory.consolidatedpickinglistlines.Dimension2;
import emc.datatypes.inventory.consolidatedpickinglistlines.Dimension3;
import emc.datatypes.inventory.consolidatedpickinglistlines.Issue;
import emc.datatypes.inventory.consolidatedpickinglistlines.IssueQuantity;
import emc.datatypes.inventory.consolidatedpickinglistlines.OrderQuantity;
import emc.datatypes.inventory.consolidatedpickinglistsetup.foreignkeys.ConsolidatedPickingListIdFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryConsolidatedPLLines")
public class InventoryConsolidatedPLLines extends EMCEntityClass {

    private String consolidatedPickingListId;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private int dimension2SortCode;
    private String dimension3;
    private BigDecimal orderQuantity = BigDecimal.ZERO;
    private BigDecimal issueQuantity = BigDecimal.ZERO;
    private boolean issue;

    /** Creates a new instance of InventoryConsolidatedPLLines. */
    public InventoryConsolidatedPLLines() {
    }

    public String getConsolidatedPickingListId() {
        return consolidatedPickingListId;
    }

    public void setConsolidatedPickingListId(String consolidatedPickingListId) {
        this.consolidatedPickingListId = consolidatedPickingListId;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public boolean isIssue() {
        return issue;
    }

    public void setIssue(boolean issue) {
        this.issue = issue;
    }

    public BigDecimal getIssueQuantity() {
        return issueQuantity;
    }

    public void setIssueQuantity(BigDecimal issueQuantity) {
        this.issueQuantity = issueQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getDimension2SortCode() {
        return dimension2SortCode;
    }

    public void setDimension2SortCode(int dimension2SortCode) {
        this.dimension2SortCode = dimension2SortCode;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("consolidatedPickingListId", new ConsolidatedPickingListIdFK());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("orderQuantity", new OrderQuantity());
        toBuild.put("issueQuantity", new IssueQuantity());
        toBuild.put("issue", new Issue());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("itemId");
        query.addOrderBy("dimension1");
        query.addOrderBy("dimension3");
        query.addOrderBy("dimension2SortCode");
        return query;
    }
    
    
}
