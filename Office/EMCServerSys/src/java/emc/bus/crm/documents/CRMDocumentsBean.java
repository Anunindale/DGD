/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.documents;

import emc.entity.base.BaseDocuRefTable;
import emc.entity.crm.CRMDocuments;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CRMDocumentsBean extends EMCEntityBean implements CRMDocumentsLocal {

    public boolean validateSelectedDocument(String document, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CRMDocuments.class.getName());
        query.addAnd("documentId", document);
        if (!util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The value selected does not exist.", userData);
            return false;
        }
        return true;
    }

    public String getDocument(String documentId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CRMDocuments.class);
        query.addAnd("documentId", documentId);
        CRMDocuments document = (CRMDocuments) util.executeSingleResultQuery(query, userData);
        query = new EMCQuery(enumQueryTypes.SELECT, BaseDocuRefTable.class);
        query.addAnd("refTableName", CRMDocuments.class.getSimpleName());
        query.addAnd("refRecId", document.getRecordID());
        query.addStringNotBlank("attachmentFileName", BaseDocuRefTable.class.getName(), EMCQueryBracketConditions.AND);
        query.addOrderBy("dateAdded");
        List<BaseDocuRefTable> docRefList = util.executeLimitedResultGeneralSelectQuery(query, 0, 1, userData);
        if (docRefList != null && !docRefList.isEmpty()) {
            BaseDocuRefTable docRef = docRefList.get(0);
            return docRef.getRefRecId() + "_" + docRef.getAttachmentFileName();
        }
        return null;
    }
}
