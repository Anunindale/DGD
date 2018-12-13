/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.docuref.Note;
import emc.datatypes.trec.chemicals.ShippingName;
import emc.datatypes.trec.chemicals.UNNumber;
import emc.datatypes.trec.classes.ClassDescription;
import emc.datatypes.trec.classes.ClassId;
import emc.datatypes.trec.classes.HasChildren;
import emc.datatypes.trec.classes.IsParent;
import emc.datatypes.trec.classes.PackGrpThreshold1;
import emc.datatypes.trec.classes.PackGrpThreshold2;
import emc.datatypes.trec.classes.PackGrpThreshold3;
import emc.datatypes.trec.classes.ShortDescription;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold1;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold2;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold3;
import emc.datatypes.trec.classes.foreignkey.ClassIdFKNM;
import emc.datatypes.trec.preferredshipname.ChemicalLink;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author chris
 */
@Entity
@Table(name = "TRECPreferredShipName" )
public class TRECPreferredShipName extends EMCEntityClass {

    private Long chemicalLink;
    private String properShipName;
    private String unNumber;

    public Long getChemicalLink() {
        return chemicalLink;
    }

    public void setChemicalLink(Long chemicalLink) {
        this.chemicalLink = chemicalLink;
    }

    public String getProperShipName() {
        return properShipName;
    }

    public void setProperShipName(String properShipName) {
        this.properShipName = properShipName;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }
  
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("chemicalLink", new ChemicalLink());
        toBuild.put("properShipName", new ShippingName());
        toBuild.put("unNumber", new UNNumber());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("unNumber");
        toBuild.add("properShipName");
        return toBuild;
    }
}
