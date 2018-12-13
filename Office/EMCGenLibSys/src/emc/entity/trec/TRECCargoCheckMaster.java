/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.datatypes.trec.cargocheck.Cargo;
import emc.datatypes.trec.cargocheck.CargoCheckNumer;
import emc.datatypes.trec.cargocheck.CargoNotes;
import emc.datatypes.trec.cargocheck.PlaceCard;
import emc.datatypes.trec.cargocheck.TrecReq;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECCargoCheckMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"cargoCheckNumber", "companyId"})})
public class TRECCargoCheckMaster extends EMCEntityClass {

    private String cargoCheckNumber;
    private String description;
    private String placeCard;
    private boolean trecRequired;
    private boolean cargo = true;
    @Column(length = 10000)
    private String notes;

    public boolean isCargo() {
        return cargo;
    }

    public void setCargo(boolean cargo) {
        this.cargo = cargo;
    }

    public String getCargoCheckNumber() {
        return cargoCheckNumber;
    }

    public void setCargoCheckNumber(String cargoCheckNumber) {
        this.cargoCheckNumber = cargoCheckNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if(notes == null || notes.equals("null") || notes.equals("null"+"\n")){
            notes = "";
        }
        this.notes = notes;
    }

    public String getPlaceCard() {
        return placeCard;
    }

    public void setPlaceCard(String placeCard) {
        this.placeCard = placeCard;
    }

    public boolean isTrecRequired() {
        return trecRequired;
    }

    public void setTrecRequired(boolean trecRequired) {
        this.trecRequired = trecRequired;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("cargoCheckNumber", new CargoCheckNumer());
        toBuild.put("description", new Description());
        toBuild.put("placeCard", new PlaceCard());
        toBuild.put("trecRequired", new TrecReq());
        toBuild.put("cargo", new Cargo());
        toBuild.put("notes", new CargoNotes());
        return toBuild;
    }
}
