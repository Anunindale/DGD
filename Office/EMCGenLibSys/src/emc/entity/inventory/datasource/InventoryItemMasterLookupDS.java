/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.datasource;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.List;

/**
 *
 * @author riaan
 */
public class InventoryItemMasterLookupDS extends EMCEntityClass {

    private String itemId;
    private String description;
    private String dimensionGroup;
    private String itemGroup;
    private String classificationClassGroup4;
    private String reference;
    private String refType;

    /** Creates a new instance of InventoryItemMasterLookupDS. */
    public InventoryItemMasterLookupDS() {

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensionGroup() {
        return dimensionGroup;
    }

    public void setDimensionGroup(String dimensionGroup) {
        this.dimensionGroup = dimensionGroup;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getClassificationClassGroup4() {
        return classificationClassGroup4;
    }

    public void setClassificationClassGroup4(String classificationClassGroup4) {
        this.classificationClassGroup4 = classificationClassGroup4;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    @Override
    public EMCQuery buildQuery() {
        String itemMasterClassName = InventoryItemMaster.class.getName();
        String referenceClassName = InventoryReference.class.getName();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, itemMasterClassName);

        query.addTableAnd(referenceClassName, "itemId", itemMasterClassName, "itemId");

        query.addField("itemId");
        query.addField("description");
        query.addField("dimensionGroup");
        query.addField("itemGroup");
        query.addField("classificationClassGroup4");
        query.addField("reference", referenceClassName);
        query.addField("refType", referenceClassName);

        query.openConditionBracket(EMCQueryBracketConditions.AND);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("itemReference", null, itemMasterClassName, EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addAnd("refType", "D", referenceClassName, EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addOr("itemReference", null, itemMasterClassName);
        query.closeConditionBracket();

        query.addOrderBy("itemId", itemMasterClassName);

        return query;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> lookupFields = super.buildDefaultLookupFieldList();

        lookupFields.add("itemId");
        lookupFields.add("reference");
        lookupFields.add("description");
        lookupFields.add("dimensionGroup");
        lookupFields.add("itemGroup");
        lookupFields.add("refType");

        return lookupFields;
    }

    public static EMCQuery getItemLookupQuery() {
        String itemMasterClassName = InventoryItemMaster.class.getName();
        String referenceClassName = InventoryReference.class.getName();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, itemMasterClassName);

        query.addTableAnd(referenceClassName, "itemId", itemMasterClassName, "itemId");

        query.addField("itemId");
        query.addField("description");
        query.addField("dimensionGroup");
        query.addField("itemGroup");
        query.addField("classificationClassGroup4");
        query.addField("reference", referenceClassName);
        query.addField("refType", referenceClassName);

        query.openConditionBracket(EMCQueryBracketConditions.AND);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("itemReference", null, itemMasterClassName, EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addAnd("refType", "D", referenceClassName, EMCQueryConditions.NOT);
        query.closeConditionBracket();
        query.addOr("itemReference", null, itemMasterClassName);
        query.closeConditionBracket();

        query.addOrderBy("itemId", itemMasterClassName);

        return query;
    }
}
