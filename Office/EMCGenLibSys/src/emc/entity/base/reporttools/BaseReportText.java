package emc.entity.base.reporttools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.reporttools.reporttext.Language;
import emc.datatypes.base.reporttools.reporttext.Part;
import emc.datatypes.base.reporttools.reporttext.Report;
import emc.datatypes.base.reporttools.reporttext.Text;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "BaseReportText", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "uniqueField"})})
public class BaseReportText extends EMCEntityClass {

    private String report;
    private String part;
    @Column(name = "text", length = 10000)
    private String text;
    private String languageField;
    private String uniqueField;

    /** Creates a new instance of BaseReportText. */
    public BaseReportText() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("report", new Report());
        toBuild.put("part", new Part());
        toBuild.put("languageField", new Language());
        toBuild.put("text", new Text());
        return toBuild;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPart() {
        return this.part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguageField() {
        return languageField;
    }

    public void setLanguageField(String languageField) {
        this.languageField = languageField;
    }

    public String getUniqueField() {
        return uniqueField;
    }

    public void setUniqueField(String uniqueField) {
        this.uniqueField = uniqueField;
    }
}
