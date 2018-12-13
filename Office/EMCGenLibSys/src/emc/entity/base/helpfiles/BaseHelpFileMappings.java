package emc.entity.base.helpfiles;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.helpfiles.FormClassName;
import emc.datatypes.base.helpfiles.HelpFileURL;
import emc.framework.EMCEntityClass;
import java.lang.Override;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="BaseHelpFileMapping", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "formClassName"})})
/** 
*
* @author riaan
*/
public class BaseHelpFileMappings extends EMCEntityClass {

    private String formClassName;
    private String helpFileURL;

    /** Creates a new instance of BaseHelpFileMappings. */
    public BaseHelpFileMappings() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("formClassName", new FormClassName());
        toBuild.put("helpFileURL", new HelpFileURL());
        return toBuild;
    }

    public String getFormClassName() {
        return this.formClassName;
    }

    public void setFormClassName(String formClassName) {
        this.formClassName = formClassName;
    }

    public String getHelpFileURL() {
        return this.helpFileURL;
    }

    public void setHelpFileURL(String helpFileURL) {
        this.helpFileURL = helpFileURL;
    }

}