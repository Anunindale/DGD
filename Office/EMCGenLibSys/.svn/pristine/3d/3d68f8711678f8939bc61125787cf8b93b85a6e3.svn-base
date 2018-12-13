package emc.entity.gl.analysiscodes;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.analysiscodesuper.AnalysisCode;
import emc.datatypes.gl.analysiscodesuper.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

/** 
 *
 * @author claudette
 */
@Entity
public class GLAnalysisCodeSuper extends EMCEntityClass {

    private String analysisCode;
    private String description;

    /** Creates a new instance of GLAnalysisCodeSuper. */
    public GLAnalysisCodeSuper() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("analysisCode", new AnalysisCode());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("analysisCode");
        toBuild.add("description");
        return toBuild;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the analysisCode
     */
    public String getAnalysisCode() {
        return analysisCode;
    }

    /**
     * @param analysisCode the analysisCode to set
     */
    public void setAnalysisCode(String analysisCode) {
        this.analysisCode = analysisCode;
    }
}