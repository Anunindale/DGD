/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.query;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseQueryTableLocal extends EMCEntityBeanLocalInterface {

    /**
     * Construct a Query based on the input parameters
     *
     * @param templateText - template text may be null if recipient request
     * @param queryName - query id
     * @param queryType - e.g. recipient,merge etc may be null BaseQueryTypeEnum
     * @param templateType - template type - SMS/email may be null BaseTemplateTypeEnum
     * @param sourceTable - may be null
     * @param sourceRecordId -may be null
     * @param logErrors
     * @param userData
     * @return will return a null query in the helperclass if it does not find what it wants
     */
    public BaseQueryConstructionHelper constructAndReturnQuery(String templateText,String queryName,String queryType,String templateType,String sourceTable,long sourceRecordId,boolean logErrors, EMCUserData userData);

    public boolean mergeEMCQueryAndTemplate(String queryName, String templateType, String templateId, EMCUserData userData) throws EMCEntityBeanException;
}

