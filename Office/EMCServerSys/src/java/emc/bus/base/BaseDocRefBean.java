/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.base;

import emc.bus.base.docref.BaseDocRefDummyLocal;
import emc.bus.base.tables.TablesLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseDocuRefTable;
import emc.enums.basetables.docuref.DocurefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.server.filehandeler.EMCFileHandlerLocal;
import emc.tables.EMCTable;
import emc.um.helper.FileUploadHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rico
 */
@Stateless
public class BaseDocRefBean extends EMCEntityBean implements BaseDocRefLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private BaseDocRefDummyLocal dummyBean;
    @EJB
    private TablesLocal tablesBean;
    @EJB
    private EMCFileHandlerLocal fileHabdlerBean;

    /**
     * Creates a new instance of BaseCompanyBean
     */
    public BaseDocRefBean() {
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseDocuRefTable doc = (BaseDocuRefTable) vobject;
        if (doc.getType().equals("F") || doc.getType().equals("T")) {
            if (doc.getAttachmentFileName() == null || doc.getAttachmentFileName().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Type requires an attachment.", userData);
                return false;
            }
        }
        if (doc.isActive()) {
            if (isUniqueActive(doc, userData)) {
                return false;
            }
        }
        return super.doUpdateValidation(vobject, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        BaseDocuRefTable doc = (BaseDocuRefTable) vobject;
        if (doc.getType() == null || doc.getType().equals("")) {
            Logger.getLogger("emc").log(Level.SEVERE, "Type is a required field.", userData);
            return false;
        }
        if (doc.getType().equals("F") || doc.getType().equals("T")) {
            if (doc.getAttachmentFileName() == null || doc.getAttachmentFileName().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Type requires an attachment.", userData);
                return false;
            }
        }
        if (doc.getDateAdded() == null) {
            doc.setDateAdded(emc.functions.Functions.nowDate());
        }
        if (doc.isActive()) {
            if (isUniqueActive(doc, userData)) {
                return false;
            }
        }
        return super.doInsertValidation(vobject, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseDocuRefTable doc = (BaseDocuRefTable) iobject;
        String sqlStr = "SELECT MAX(u.seqNum) FROM BaseDocuRefTable u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.refTableName = '"
                + doc.getRefTableName() + "' AND u.refRecId = '" + doc.getRefRecId() + "'";
        int val = 0;
        Query q = em.createQuery(sqlStr);
        List result = q.getResultList();

        if (result.get(0) != null) {
            val = Integer.valueOf(result.get(0).toString());
            val++;
        }
        doc.setSeqNum(val);

        //Fetch record to which a document is being attached.
        EMCEntityClass entity = getEntityForAttachment(doc, userData);

        if (entity != null) {
            entity.setHasAttachment(true);
            //Super class update is sufficient
            dummyBean.update(entity, userData);
        } else {
            throw new EMCEntityBeanException("Related record not found.");
        }


        return super.insert(iobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseDocuRefTable doc = (BaseDocuRefTable) dobject;

        //Fetch record to which a document is being attached.
        EMCEntityClass entity = getEntityForAttachment(doc, userData);

        //If this is the last attached document
        if (getNumberOfAttachments(doc.getRefTableName(), Long.parseLong(doc.getRefRecId()), userData) == 1) {
            if (entity != null) {
                entity.setHasAttachment(false);
                //Super class update is sufficient
                dummyBean.update(entity, userData);
            } else {
                throw new EMCEntityBeanException("Related record not found.");
            }
        }

        if (!isBlank(doc.getAttachmentFileName())) {
            if (!fileHabdlerBean.deleteFile(Functions.getEMCModule(entity.getClass()).toString(), doc.getRefRecId() + "_" + doc.getAttachmentFileName(), userData)) {
                throw new EMCEntityBeanException("Failed to delete attached file.");
            }
        }

        return super.delete(dobject, userData);
    }

    public boolean isDocumentAttached(String tableName, String refRecId, EMCUserData userData) {
        boolean ret = false;
        String sqlStr = "SELECT u FROM BaseDocuRefTable u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.refTableName = '"
                + tableName + "' AND u.refRecId = '" + refRecId + "'";
        Query q = em.createQuery(sqlStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            ret = true;
        }
        return ret;
    }

    public boolean isDocumentOfTypeAttached(String tableName, String refRecId, String type, EMCUserData userData) {
        boolean ret = false;
        String sqlStr = "SELECT u FROM BaseDocuRefTable u WHERE u.companyId = '" + userData.getCompanyId() + "' AND u.refTableName = '"
                + tableName + "' AND u.refRecId = '" + refRecId + "'"
                + " AND u.type = '" + type + "'";

        Query q = em.createQuery(sqlStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            return true;
        }

        return false;
    }

    /**
     * Returns the number of attachments for the record in the specified table,
     * with the specified record id.
     */
    private long getNumberOfAttachments(String table, long recordID, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class.getName());
        query.addFieldAggregateFunction("recordID", "COUNT");
        query.addAnd("refTableName", table);
        query.addAnd("refRecId", String.valueOf(recordID));

        Object numRows = util.executeSingleResultQuery(query, userData);

        return numRows == null ? 0 : (Long) numRows;
    }

    /**
     * Returns the entity that the given document is attached to.
     */
    private EMCEntityClass getEntityForAttachment(BaseDocuRefTable doc, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, doc.getRefTableName());
        query.addAnd("recordID", doc.getRefRecId());

        return (EMCEntityClass) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Sets the hasAttachment flag on all records with attachments.
     */
    public void fixData(EMCUserData userData) {
        EMCQuery docTablesQuery = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class.getName());
        docTablesQuery.addFieldAggregateFunction("refTableName", "DISTINCT");

        List<String> docTables = (List<String>) util.executeGeneralSelectQuery(docTablesQuery, userData);

        List<Object> tables = tablesBean.getEMCTables(userData);

        //Causes problems in update.
        docTables.remove(BaseDocuRefTable.class.getSimpleName());

        for (Object table : tables) {
            String tableName = String.valueOf(table).substring(String.valueOf(table).lastIndexOf(".") + 1);
            if (docTables.contains(tableName)) {
                EMCQuery updateQuery = new EMCQuery(enumQueryTypes.UPDATE, String.valueOf(table));
                updateQuery.addSet("hasAttachment", true);

                EMCQuery nestedQuery = new EMCQuery("(SELECT refRecId FROM emc.entity.base.BaseDocuRefTable WHERE refTableName = '" + tableName + "' )");

                updateQuery.addAnd("recordID", nestedQuery, EMCQueryConditions.IN);

                util.executeUpdate(updateQuery, userData);
            }
        }
    }

    /**
     * This method gets the attachment attached to a certain line. It checks
     * whether or not there is an attachment with an "active" tick and then
     * attached the "active" file if relevant, otherwise it will attach the
     * latest file.
     *
     * @param recordId
     * @param refTable
     * @param userData
     * @return
     */
    public List<String> getAttachment(Long recordId, Class refTable, EMCUserData userData) {
        List<String> attachment = new ArrayList<String>();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refRecId", recordId);
        query.openAndConditionBracket();
        query.addOr("type", DocurefTypes.FILE.toString());
        query.addOr("type", DocurefTypes.TEMPLATE.toString());
        query.closeConditionBracket();
        query.addOrderBy("dateAdded", BaseDocuRefTable.class.getName(), EMCQueryOrderByDirections.DESC);

        List<BaseDocuRefTable> docuRef = util.executeGeneralSelectQuery(query, userData);

        for (BaseDocuRefTable ref : docuRef) {
            if (ref.isActive()) {
                attachment.add(Functions.getEMCModule(refTable).toString());
                attachment.add(recordId + "_" + ref.getAttachmentFileName());
                return attachment;
            }
        }

        if (!docuRef.isEmpty()) {
            BaseDocuRefTable ref = docuRef.get(0);
            attachment.add(Functions.getEMCModule(refTable).toString());
            attachment.add(recordId + "_" + ref.getAttachmentFileName());
            return attachment;
        }
        return null;
    }

    /**
     * THis method will get the recordId and then call the getAttachmenr()
     * method that will in return get the actualy header that is attached.
     *
     * @param query
     * @param summaryPrimary
     * @param summarySecondary
     * @param userData
     * @return
     */
    public List<String> getReportHeader(EMCQuery query, String summaryPrimary, String summarySecondary, EMCUserData userData) {
        Long recordId = (Long) util.executeSingleResultQuery(query, userData);
        return getAttachment(recordId, BaseCompanyTable.class, summaryPrimary, summarySecondary, userData);
    }

    /**
     * This method gets the attachment attached to a certain line. It checks
     * whether or not there is an attachment with an "active" tick and then
     * attached the "active" file if relevant, otherwise it will attach the
     * latest file. And Summary gets taken into account.
     *
     * @param recordId
     * @param refTable
     * @param userData
     * @return
     */
    public List<String> getAttachment(Long recordId, Class refTable, String summaryPrimary, String summarySecondary, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refRecId", recordId);
        query.addAnd("refTableName", refTable.getSimpleName());
        if (!isBlank(summaryPrimary) || !isBlank(summarySecondary)) {
            query.openAndConditionBracket();
            if (!isBlank(summaryPrimary)) {
                query.addOr("summary", summaryPrimary, EMCQueryConditions.STARTS_WITH);
            }
            if (!isBlank(summarySecondary)) {
                query.addOr("summary", summarySecondary, EMCQueryConditions.STARTS_WITH);
            }
            query.closeConditionBracket();
        }
        query.openAndConditionBracket();
        query.addOr("type", DocurefTypes.FILE.toString());
        query.addOr("type", DocurefTypes.TEMPLATE.toString());
        query.closeConditionBracket();
        query.addOrderBy("dateAdded", BaseDocuRefTable.class.getName(), EMCQueryOrderByDirections.DESC);

        List<BaseDocuRefTable> docuRef = util.executeGeneralSelectQuery(query, userData);
        List<String> attachmentPrimary = new ArrayList<String>();
        List<String> attachmentSecondary = new ArrayList<String>();
        for (BaseDocuRefTable ref : docuRef) {
            if (ref.isActive()) {
                if (summaryPrimary != null && ref.getSummary().contains(summaryPrimary) && attachmentPrimary.isEmpty()) {
                    attachmentPrimary.add(Functions.getEMCModule(refTable).toString());
                    attachmentPrimary.add(recordId + "_" + ref.getAttachmentFileName());
                }
                if (summarySecondary != null && ref.getSummary().contains(summarySecondary) && attachmentSecondary.isEmpty()) {
                    attachmentSecondary.add(Functions.getEMCModule(refTable).toString());
                    attachmentSecondary.add(recordId + "_" + ref.getAttachmentFileName());
                }
            }
        }

        if (!attachmentPrimary.isEmpty()) {
            return attachmentPrimary;
        }

        if (!attachmentSecondary.isEmpty()) {
            return attachmentSecondary;
        }

        if (!docuRef.isEmpty()) {
            BaseDocuRefTable ref = docuRef.get(0);
            attachmentPrimary.add(Functions.getEMCModule(refTable).toString());
            attachmentPrimary.add(recordId + "_" + ref.getAttachmentFileName());
            return attachmentPrimary;
        }
        return null;
    }

    /**
     * This method will ensure that there is only one attachment with a "active"
     * tick. The user will have to remove the tick and then add it to another
     * line if needs be.
     *
     * @param refTable
     * @param userData
     * @return
     */
    private Boolean isUniqueActive(BaseDocuRefTable refTable, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refRecId", refTable.getRefRecId());
        query.addAnd("refTableName", refTable.getRefTableName());
        query.addAnd("recordID", refTable.getRecordID(), EMCQueryConditions.NOT);
        query.addAnd("active", Boolean.TRUE);

        return util.exists(query, userData);
    }

    /**
     * Create a DocRef record to attach a specific user input to.
     *
     * @param recordID of the source table
     * @param summary - summary that will be used for the docref table record
     * @param simpleClassName - the classname of the sourcetable e.g
     * .getSimpleName();
     * @param userData
     * @return
     * @throws EMCEntityBeanException
     */
    public boolean createSpecificAttachment(long recordID, String summary, String simpleClassName, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refTableName", simpleClassName);
        query.addAnd("refRecId", recordID);
        query.addAnd("summary", summary);
        BaseDocuRefTable docRef = (BaseDocuRefTable) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refTableName", simpleClassName);
        query.addAnd("refRecId", recordID);
        Long docCount = (Long) util.executeSingleResultQuery(query.getCountQuery(), userData);

        if (docRef == null) {
            docRef = new BaseDocuRefTable();
            docRef.setDateAdded(Functions.nowDate());
            docRef.setRefRecId(String.valueOf(recordID));
            docRef.setRefTableName(simpleClassName);
            docRef.setSummary(summary);
            docRef.setType(DocurefTypes.NOTE.toString());

            docRef.setSeqNum(docCount.intValue());

            insert(docRef, userData);

        } else {
            docRef.setSummary(docRef.getSummary() + " - " + docCount);
            update(docRef, userData);

            docRef = new BaseDocuRefTable();
            docRef.setDateAdded(Functions.nowDate());
            docRef.setRefRecId(String.valueOf(recordID));
            docRef.setRefTableName(simpleClassName);
            docRef.setSummary(summary);
            docRef.setType(DocurefTypes.NOTE.toString());

            docRef.setSeqNum(docCount.intValue());

            insert(docRef, userData);
        }

        Logger.getLogger("emc").log(Level.INFO, "Attach the new file to \'" + summary + "\' record.", userData);
        return true;
    }

    public Object createWebAttachment(Object attachment, EMCUserData userData) throws EMCEntityBeanException {
        FileUploadHelper helper = (FileUploadHelper) attachment;
        BaseDocuRefTable docRef = helper.getDocRef();

        if (docRef == null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
            query.addAnd("refTableName", helper.getRefTableName());
            query.addAnd("refRecId", helper.getRefRecId());
            Long docCount = (Long) util.executeSingleResultQuery(query.getCountQuery(), userData);

            docRef = new BaseDocuRefTable();
            docRef.setDateAdded(Functions.nowDate());
            docRef.setRefRecId(helper.getRefRecId());
            docRef.setRefTableName(helper.getRefTableName());
            docRef.setSummary(helper.getSummary());
            docRef.setNote(helper.getSummary());
            docRef.setSeqNum(docCount.intValue());
            this.insert(docRef, userData);
        } else {
            this.update(docRef, userData);
        }
        return docRef;
    }
}
