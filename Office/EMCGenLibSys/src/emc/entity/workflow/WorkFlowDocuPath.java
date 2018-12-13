/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.docupath.FilePath;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "WorkFlowDocuPath",uniqueConstraints={@UniqueConstraint(columnNames={"filePath","companyId"})})
public class WorkFlowDocuPath extends EMCEntityClass implements Serializable {
    private String description;
    private String filePath;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

     @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
       
        toBuild.put("description", new Description());
        toBuild.put("filePath", new FilePath());
        
        return toBuild;
    }

}
