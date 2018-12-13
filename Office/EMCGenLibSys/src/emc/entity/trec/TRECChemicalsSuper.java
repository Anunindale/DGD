/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.trec.chemicals.APhrases;
import emc.datatypes.trec.chemicals.AdditionalShippingName;
import emc.datatypes.trec.chemicals.ChemicalId;
import emc.datatypes.trec.chemicals.DPhrases;
import emc.datatypes.trec.chemicals.EPhrases;
import emc.datatypes.trec.chemicals.ERG;
import emc.datatypes.trec.chemicals.FPhrases;
import emc.datatypes.trec.chemicals.HPhrases;
import emc.datatypes.trec.chemicals.PPhrases;
import emc.datatypes.trec.chemicals.PackingGroup;
import emc.datatypes.trec.chemicals.QPhrases;
import emc.datatypes.trec.chemicals.Ready;
import emc.datatypes.trec.chemicals.SPhrases;
import emc.datatypes.trec.chemicals.ShippingName;
import emc.datatypes.trec.chemicals.SubsidairyRisk;
import emc.datatypes.trec.chemicals.TrecVersion;
import emc.datatypes.trec.chemicals.UNNumber;
import emc.datatypes.trec.chemicals.risk1;
import emc.datatypes.trec.chemicals.risk2;
import emc.datatypes.trec.chemicals.risk3;
import emc.datatypes.trec.classes.foreignkey.ClassIdFKNM;
import emc.datatypes.trec.erg.ErgNumberFKNM;
import emc.datatypes.trec.phrasecombinations.foreignkeys.PhraseCombRefFKNM;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author asd_admin
 */
@Entity
public class TRECChemicalsSuper extends EMCEntityClass {

    private int chemicalId;
    private String unNumber;
    private String erg;
    private String classId;
    private String shippingName;
    private String addShippingName;
    private String phrasesRefNum;
    private String subsidairyRisk;
    private String packingGroup;
    private boolean ready;
    //Version Number;
    private int trecVersion;
    private double risk1;
    private double risk2;
    private double risk3;
    @Column(length = 1000)
    private String hPhrases;
    @Column(length = 1000)
    private String pPhrases;
    @Column(length = 1000)
    private String qPhrases;
    @Column(length = 1000)
    private String dPhrases;
    @Column(length = 1000)
    private String sPhrases;
    @Column(length = 1000)
    private String fPhrases;
    @Column(length = 1000)
    private String aPhrases;
    @Column(length = 1000)
    private String ePhrases;

    public double getRisk1() {
        return risk1;
    }

    public void setRisk1(double risk1) {
        this.risk1 = risk1;
    }

    public double getRisk2() {
        return risk2;
    }

    public void setRisk2(double risk2) {
        this.risk2 = risk2;
    }

    public double getRisk3() {
        return risk3;
    }

    public void setRisk3(double risk3) {
        this.risk3 = risk3;
    }

    public String getAddShippingName() {
        return addShippingName;
    }

    public void setAddShippingName(String addShippingName) {
        this.addShippingName = addShippingName;
    }

    public int getChemicalId() {
        return chemicalId;
    }

    public void setChemicalId(int chemicalId) {
        this.chemicalId = chemicalId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getErg() {
        return erg;
    }

    public void setErg(String erg) {
        this.erg = erg;
    }

    public String getPackingGroup() {
        return packingGroup;
    }

    public void setPackingGroup(String packingGroup) {
        this.packingGroup = packingGroup;
    }

    public String getPhrasesRefNum() {
        return phrasesRefNum;
    }

    public void setPhrasesRefNum(String phrasesRefNum) {
        this.phrasesRefNum = phrasesRefNum;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getSubsidairyRisk() {
        return subsidairyRisk;
    }

    public void setSubsidairyRisk(String subsidairyRisk) {
        this.subsidairyRisk = subsidairyRisk;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    public int getTrecVersion() {
        return trecVersion;
    }

    public void setTrecVersion(int trecVersion) {
        this.trecVersion = trecVersion;
    }

    public String gethPhrases() {
        return hPhrases;
    }

    public void sethPhrases(String hPhrases) {
        this.hPhrases = hPhrases;
    }

    public String getpPhrases() {
        return pPhrases;
    }

    public void setpPhrases(String pPhrases) {
        this.pPhrases = pPhrases;
    }

    public String getqPhrases() {
        return qPhrases;
    }

    public void setqPhrases(String qPhrases) {
        this.qPhrases = qPhrases;
    }

    public String getdPhrases() {
        return dPhrases;
    }

    public void setdPhrases(String dPhrases) {
        this.dPhrases = dPhrases;
    }

    public String getsPhrases() {
        return sPhrases;
    }

    public void setsPhrases(String sPhrases) {
        this.sPhrases = sPhrases;
    }

    public String getfPhrases() {
        return fPhrases;
    }

    public void setfPhrases(String fPhrases) {
        this.fPhrases = fPhrases;
    }

    public String getaPhrases() {
        return aPhrases;
    }

    public void setaPhrases(String aPhrases) {
        this.aPhrases = aPhrases;
    }

    public String getePhrases() {
        return ePhrases;
    }

    public void setePhrases(String ePhrases) {
        this.ePhrases = ePhrases;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("chemicalId", new ChemicalId());
        toBuild.put("unNumber", new UNNumber());
        toBuild.put("erg", new ErgNumberFKNM());
        toBuild.put("classId", new ClassIdFKNM());
        toBuild.put("shippingName", new ShippingName());
        toBuild.put("addShippingName", new AdditionalShippingName());
        toBuild.put("phrasesRefNum", new PhraseCombRefFKNM());
        toBuild.put("subsidairyRisk", new SubsidairyRisk());
        toBuild.put("packingGroup", new PackingGroup());
        toBuild.put("ready", new Ready());
        toBuild.put("trecVersion", new TrecVersion());
        toBuild.put("risk1", new risk1());
        toBuild.put("risk2", new risk2());
        toBuild.put("risk3", new risk3());
        toBuild.put("hPhrases", new HPhrases());
        toBuild.put("pPhrases", new PPhrases());
        toBuild.put("qPhrases", new QPhrases());
        toBuild.put("dPhrases", new DPhrases());
        toBuild.put("sPhrases", new SPhrases());
        toBuild.put("fPhrases", new FPhrases());
        toBuild.put("aPhrases", new APhrases());
        toBuild.put("ePhrases", new EPhrases());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("unNumber");      
        toBuild.add("classId");
        toBuild.add("shippingName");
        toBuild.add("addShippingName");
        toBuild.add("erg");
        return toBuild;
    }
}
