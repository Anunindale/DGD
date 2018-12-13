/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.datatypes.trec.classes.foreignkey.ClassIdFKNM;
import emc.datatypes.trec.phrasecombinations.A;
import emc.datatypes.trec.phrasecombinations.Appearance;
import emc.datatypes.trec.phrasecombinations.Approved;
import emc.datatypes.trec.phrasecombinations.D;
import emc.datatypes.trec.phrasecombinations.E;
import emc.datatypes.trec.phrasecombinations.F;
import emc.datatypes.trec.phrasecombinations.H;
import emc.datatypes.trec.phrasecombinations.P;
import emc.datatypes.trec.phrasecombinations.PhraseCombRef;
import emc.datatypes.trec.phrasecombinations.Q;
import emc.datatypes.trec.phrasecombinations.RecId;
import emc.datatypes.trec.phrasecombinations.S;
import emc.datatypes.trec.trectype.foreignkey.TrecTypeFKNM;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECPhraseCombinations", uniqueConstraints = {@UniqueConstraint(columnNames = {"phraseRefNum", "companyId"})})
public class TRECPhraseCombinations extends EMCEntityClass {

    private String phraseRefNum;
    private String description;
    private String appearance;
    private String classId;
    private String trecType;
    private boolean approved;
    private String recId;
    private String h;
    private String p;
    private String q;
    private String d;
    private String s;
    private String f;
    private String a;
    private String e;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getPhraseRefNum() {
        return phraseRefNum;
    }

    public void setPhraseRefNum(String phraseRefNum) {
        this.phraseRefNum = phraseRefNum;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getTrecType() {
        return trecType;
    }

    public void setTrecType(String trecType) {
        this.trecType = trecType;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("phraseRefNum", new PhraseCombRef());
        toBuild.put("description", new Description());
        toBuild.put("appearance", new Appearance());
        toBuild.put("classId", new ClassIdFKNM());
        toBuild.put("trecType", new TrecTypeFKNM());
        toBuild.put("approved", new Approved());
        toBuild.put("recId", new RecId());
        toBuild.put("h", new H());
        toBuild.put("p", new P());
        toBuild.put("q", new Q());
        toBuild.put("d", new D());
        toBuild.put("s", new S());
        toBuild.put("f", new F());
        toBuild.put("a", new A());
        toBuild.put("e", new E());
        return toBuild;
    }
}
