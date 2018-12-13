package emc.entity.gl.rules;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.grouprules.AnalysisCode1;
import emc.datatypes.gl.grouprules.AnalysisCode2;
import emc.datatypes.gl.grouprules.AnalysisCode3;
import emc.datatypes.gl.grouprules.AnalysisCode4;
import emc.datatypes.gl.grouprules.AnalysisCode5;
import emc.datatypes.gl.grouprules.AnalysisCode6;
import emc.datatypes.gl.grouprules.Description;
import emc.datatypes.gl.grouprules.GroupRules;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import emc.datatypes.gl.grouprules.Granularity;
import java.util.List;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "GLGroupRules", uniqueConstraints = {@UniqueConstraint(columnNames = {"groupRules", "companyId"})})
public class GLGroupRules extends EMCEntityClass {

    private String groupRules;
    private String description;
    private boolean analysisCode1;
    private boolean analysisCode2;
    private boolean analysisCode3;
    private boolean analysisCode4;
    private boolean analysisCode5;
    private boolean analysisCode6;
    private String granularity;

    /** Creates a new instance of GLGroupRules. */
    public GLGroupRules() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("groupRules", new GroupRules());
        toBuild.put("description", new Description());
        toBuild.put("analysisCode1", new AnalysisCode1());
        toBuild.put("analysisCode2", new AnalysisCode2());
        toBuild.put("analysisCode3", new AnalysisCode3());
        toBuild.put("analysisCode4", new AnalysisCode4());
        toBuild.put("analysisCode5", new AnalysisCode5());
        toBuild.put("analysisCode6", new AnalysisCode6());
        toBuild.put("granularity", new Granularity());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("groupRules");
        toBuild.add("description");
        return toBuild;
    }

    public String getGroupRules() {
        return this.groupRules;
    }

    public void setGroupRules(String groupRules) {
        this.groupRules = groupRules;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getAnalysisCode1() {
        return this.analysisCode1;
    }

    public void setAnalysisCode1(boolean analysisCode1) {
        this.analysisCode1 = analysisCode1;
    }

    public boolean getAnalysisCode2() {
        return this.analysisCode2;
    }

    public void setAnalysisCode2(boolean analysisCode2) {
        this.analysisCode2 = analysisCode2;
    }

    public boolean getAnalysisCode3() {
        return this.analysisCode3;
    }

    public void setAnalysisCode3(boolean analysisCode3) {
        this.analysisCode3 = analysisCode3;
    }

    public boolean getAnalysisCode4() {
        return this.analysisCode4;
    }

    public void setAnalysisCode4(boolean analysisCode4) {
        this.analysisCode4 = analysisCode4;
    }

    public boolean getAnalysisCode5() {
        return this.analysisCode5;
    }

    public void setAnalysisCode5(boolean analysisCode5) {
        this.analysisCode5 = analysisCode5;
    }

    public boolean getAnalysisCode6() {
        return this.analysisCode6;
    }

    public void setAnalysisCode6(boolean analysisCode6) {
        this.analysisCode6 = analysisCode6;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
}