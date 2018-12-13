/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.base.docuref.Active;
import emc.datatypes.base.docuref.AttachmentFileName;
import emc.datatypes.base.docuref.DocRefDate;
import emc.datatypes.base.docuref.Note;
import emc.datatypes.base.docuref.RefRecId;
import emc.datatypes.base.docuref.RefTableName;
import emc.datatypes.base.docuref.SeqNum;
import emc.datatypes.base.docuref.Summary;
import emc.datatypes.base.docuref.Type;
import emc.enums.basetables.docuref.DocurefTypes;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseDocuRefTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"refTableName", "refRecId", "seqNum"})})
public class BaseDocuRefTable extends EMCEntityClass implements Serializable {

    private String refTableName;
    private String refRecId;
    private int seqNum;
    @Column(length = 1000)
    private String summary;
    private String note;
    private String attachmentFileName;
    @Column(length = 10)
    private String type = DocurefTypes.NOTE.toString();
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    private boolean active;

    public BaseDocuRefTable() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getRefRecId() {
        return refRecId;
    }

    public void setRefRecId(String refRecId) {
        this.refRecId = refRecId;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("summary", new Summary());
        toBuild.put("note", new Note());
        toBuild.put("refTableName", new RefTableName());
        toBuild.put("refRecId", new RefRecId());
        toBuild.put("seqNum", new SeqNum());
        toBuild.put("attachmentFileName", new AttachmentFileName());
        toBuild.put("type", new Type());
        toBuild.put("dateAdded", new DocRefDate());
        toBuild.put("active", new Active());
        return toBuild;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
