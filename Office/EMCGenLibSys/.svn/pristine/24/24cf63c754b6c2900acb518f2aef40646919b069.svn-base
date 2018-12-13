/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.inventorystocktakeunreserved.DimensionId;
import emc.datatypes.inventory.inventorystocktakeunreserved.ItemId;
import emc.datatypes.inventory.inventorystocktakeunreserved.ReferenceId;
import emc.datatypes.inventory.inventorystocktakeunreserved.ReferenceJournal;
import emc.datatypes.inventory.inventorystocktakeunreserved.ReferenceType;
import emc.datatypes.inventory.inventorystocktakeunreserved.Resolved;
import emc.datatypes.inventory.inventorystocktakeunreserved.ResolvedBy;
import emc.datatypes.inventory.inventorystocktakeunreserved.ResolvedDate;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "InventoryStocktakeUnreserved", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueIdentifier", "companyId"})})
public class InventoryStocktakeUnreserved extends EMCEntityClass {

    private String uniqueIdentifier;
    private Long dimensionId;
    private String itemId;
    private String referenceType;
    private String referenceId;
    private String referenceJournal;
    private boolean resolved;
    private String resolvedBy;
    @Temporal(TemporalType.DATE)
    private Date resolvedDate;

    public InventoryStocktakeUnreserved() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("dimensionId", new DimensionId());
        toBuild.put("itemId", new ItemId());
        toBuild.put("referenceType", new ReferenceType());
        toBuild.put("referenceId", new ReferenceId());
        toBuild.put("referenceJournal", new ReferenceJournal());
        toBuild.put("resolved", new Resolved());
        toBuild.put("resolvedBy", new ResolvedBy());
        toBuild.put("resolvedDate", new ResolvedDate());


        return toBuild;
    }

    @Override
    public List<String> getDefaultLookupFields() {
        List<String> fields = super.getDefaultLookupFields();
        fields.add("dimensionId");
        fields.add("itemId");
        fields.add("referenceTable");
        fields.add("referenceId");
        fields.add("referenceJournal");
        fields.add("resolved");
        fields.add("resolvedBy");
        fields.add("resolvedDate");
        return fields;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceJournal() {
        return referenceJournal;
    }

    public void setReferenceJournal(String referenceJournal) {
        this.referenceJournal = referenceJournal;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
