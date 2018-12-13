/*
 * EMCEntityClass.java
 *
 * Created on 09 October 2007, 04:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.framework;

import emc.datatypes.EMCDataType;
import emc.datatypes.framework.emcentityclass.Closed;
import emc.datatypes.framework.emcentityclass.CreatedBy;
import emc.datatypes.framework.emcentityclass.ModifiedBy;
import emc.datatypes.systemwide.CompanyId;
import emc.entityinterface.EntitySuperClassInterface;
import emc.tables.EMCTable;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author rico
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TableGenerator(name = "EMCEntityGenerator",
table = "BaseGeneratorTable",
pkColumnName = "recordID",
valueColumnName = "lastValGen",
pkColumnValue = "EMCEntityLevel",
initialValue = 1,
allocationSize = 1)
public class EMCEntityClass extends EMCTable implements Serializable, EntitySuperClassInterface {

    @Id
    @Column(name = "recordID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "EMCEntityGenerator")
    private long recordID;
    @Column(name = "companyId", nullable = false)
    private String companyId;
    @Column(name = "createdBy", length = 10)
    private String createdBy;
    @Column(name = "createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "modifiedBy")
    private String modifiedBy;
    @Column(name = "modifiedTime")
    @Temporal(TemporalType.TIME)
    private Date modifiedTime;
    @Column(name = "modifiedDate")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;
    private String closed;
    @Version
    @Column(name = "version", nullable = false)
    private long version;
    @Column(name = "createdTime")
    @Temporal(TemporalType.TIME)
    private Date createdTime;
    @Column(name = "hasAttachment")
    private boolean hasAttachment;

    /**
     * Creates a new instance of EMCEntityClass
     */
    public EMCEntityClass() {
    }

    public long getRecordID() {
        return recordID;
    }

    public void setRecordID(long recordID) {
        this.recordID = recordID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap map = super.buildFieldList();
        map.put("companyId", new CompanyId());
        return map;
    }

    @Override
    public HashMap<String, EMCDataType> getFieldDataTypeMapper() {
        HashMap<String, EMCDataType> toBuild = super.getFieldDataTypeMapper();
        toBuild.put("modifiedBy", new ModifiedBy());
        toBuild.put("createdBy", new CreatedBy());
        toBuild.put("closed", new Closed());

        return toBuild;
    }

    @Override
    public List<String> buildFieldListToClearOnCopy() {
        List<String> toBuild = super.buildFieldListToClearOnCopy();
        toBuild.add("recordID");
        toBuild.add("createdBy");
        toBuild.add("createdTime");
        toBuild.add("createdDate");
        toBuild.add("modifiedBy");
        toBuild.add("modifiedTime");
        toBuild.add("modifiedDate");
        toBuild.add("closed");
        toBuild.add("version");
        toBuild.add("hasAttachment");
        toBuild.add("companyId");
        return toBuild;
    }
}
