/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.webfilepaths.ProcessId;
import emc.datatypes.base.webfilepaths.RelativePath;
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
@Table(name = "BaseWebFilePaths", uniqueConstraints = {@UniqueConstraint(columnNames = {"processId", "companyId"})})
public class BaseWebFilePaths extends EMCEntityClass {

    private String processId;
    @Column(name = "relativeFilePath", length = 1000)
    private String relativeFilePath;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }

    public void setRelativeFilePath(String relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("processId", new ProcessId());
        toBuild.put("relativeFilePath", new RelativePath());
        return toBuild;
    }
}
