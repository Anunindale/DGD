/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.base.filepath.FilePath;
import emc.datatypes.base.filepath.OperatingSystem;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseFilePaths", uniqueConstraints = {@UniqueConstraint(columnNames = {"formModule", "companyId"})})
public class BaseFilePaths extends EMCEntityClass implements Serializable {

    private String operatingSystem;
    private String filePath;
    private String formModule;

    /** Creates a new instance of BaseFilePaths */
    public BaseFilePaths() {
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFormModule() {
        return formModule;
    }

    public void setFormModule(String formModule) {
        this.formModule = formModule;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("operatingSystem", new OperatingSystem());
        toBuild.put("filePath", new FilePath());

        return toBuild;
    }
}
