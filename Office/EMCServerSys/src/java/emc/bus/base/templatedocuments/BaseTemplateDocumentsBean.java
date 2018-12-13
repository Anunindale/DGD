/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.templatedocuments;

import emc.entity.base.templatedocuments.BaseTemplateDocuments;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author kapeshi
 */
@Stateless
public class BaseTemplateDocumentsBean extends EMCEntityBean implements BaseTemplateDocumentsLocal {

    public boolean linkDocuments(String templateId, EMCUserData userData) {
        //to used in the future for auto populate of the form
        return true;
    }

    public List<String> getDocuments(String templateId, EMCUserData userData) {
        List<String> documents = new ArrayList<String>();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTemplateDocuments.class);
        query.addAnd("templateId", templateId);
        List<BaseTemplateDocuments> docs = util.executeGeneralSelectQuery(query, userData);

        for (BaseTemplateDocuments doc : docs) {
            documents.add(doc.getDocumentId());
        }

        if (!docs.isEmpty()) {
            return documents;
        }
        return null;
    }

}
