/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.consolidatedpickinglist;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.consolidatedpickinglistmaster.ConsolidatedPickingListId;
import emc.datatypes.inventory.consolidatedpickinglistmaster.PickingDate;
import emc.datatypes.inventory.consolidatedpickinglistmaster.PickingListStatus;
import emc.datatypes.systemwide.Comments;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.pickinglist.PickingListStatusses;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryConsolidatedPLMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"consolidatedPickingListId", "companyId"})})
public class InventoryConsolidatedPLMaster extends EMCEntityClass {

    private String consolidatedPickingListId;
    @Temporal(TemporalType.DATE)
    private Date pickingDate;
    @Column(length = 3000)
    private String comments;
    private String pickingListStatus = PickingListStatusses.OPEN.toString();

    /** Creates a new instance of InventoryConsolidatedPLMaster. */
    public InventoryConsolidatedPLMaster() {
        this.setEmcLabel("Consolidated Picking List Master");
    }

    public String getConsolidatedPickingListId() {
        return consolidatedPickingListId;
    }

    public void setConsolidatedPickingListId(String consolidatedPickingListId) {
        this.consolidatedPickingListId = consolidatedPickingListId;
    }

    public Date getPickingDate() {
        return pickingDate;
    }

    public void setPickingDate(Date pickingDate) {
        this.pickingDate = pickingDate;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryConsolidatedPLMaster.class);
        query.addAnd("pickingListStatus", PickingListStatusses.OPEN);

        return query;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("consolidatedPickingListId", new ConsolidatedPickingListId());
        toBuild.put("pickingDate", new PickingDate());
        toBuild.put("pickingListStatus", new PickingListStatus());
        toBuild.put("comments", new Comments());

        return toBuild;
    }

    public String getPickingListStatus() {
        return pickingListStatus;
    }

    public void setPickingListStatus(String pickingListStatus) {
        this.pickingListStatus = pickingListStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
