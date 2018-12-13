/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.fileassociations;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.fileassociations.FileExtension;
import emc.datatypes.base.fileassociations.OpenWith;
import emc.datatypes.base.fileassociations.OperatingSystem;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseFileAssociations", uniqueConstraints = {@UniqueConstraint(columnNames = {"operatingSystem", "fileExtension", "companyId"})})
public class BaseFileAssociations extends EMCEntityClass {

    private String operatingSystem;
    private String fileExtension;
    private String openWith;

    /** Creates a new instance of BaseFileAssociations. */
    public BaseFileAssociations() {
        
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getOpenWith() {
        return openWith;
    }

    public void setOpenWith(String openWith) {
        this.openWith = openWith;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("operatingSystem", new OperatingSystem());
        toBuild.put("fileExtension", new FileExtension());
        toBuild.put("openWith", new OpenWith());

        return toBuild;
    }

}
