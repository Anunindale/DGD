/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.query;

import emc.bus.base.smstemplate.BaseSMSTemplateLocal;
import emc.bus.base.template.BaseEmailTemplateLocal;
import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseSMSTemplate;
import emc.entity.base.query.BaseQueryActionTable;
import emc.entity.base.query.BaseQueryTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.query.BaseQueryRecipientTypeEnum;
import emc.enums.base.query.BaseQueryTypeEnum;
import emc.enums.base.query.BaseTemplateTypeEnum;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.helpers.base.BaseQueryTableTableObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseQueryTableBean extends EMCEntityBean implements BaseQueryTableLocal {

    @EJB
    private BaseEmailTemplateLocal emailTemplateBean;
    @EJB
    private BaseSMSTemplateLocal smsTemplateBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            BaseQueryTable record = (BaseQueryTable) theRecord;

            if (fieldNameToValidate.equals("mainEntityClass")) {
                BaseQueryTableTableObject treeObj = new BaseQueryTableTableObject(record.getMainEntityClass(), null, null, null, 2, false);

                DefaultMutableTreeNode tablesTree = new DefaultMutableTreeNode("EMC Query");
                tablesTree.add(new DefaultMutableTreeNode(treeObj));

                EMCXMLHandler xml = new EMCXMLHandler();
                String tablesTreeString = xml.encodeTree(tablesTree);

                record.setTablesTree(tablesTreeString);

                return record;
            }
        }
        return valid;
    }

    /**
     * Construct a Query based on the input parameters
     *
     * @param templateText - template text may be null if recipient request
     * @param queryName - query id
     * @param queryType - e.g. recipient,merge etc may be null BaseQueryTypeEnum
     * @param templateType - template type - SMS/email may be null
     * BaseTemplateTypeEnum
     * @param sourceTable - may be null
     * @param sourceRecordId -may be null
     * @param logErrors
     * @param userData
     * @return will return a null query in the helperclass if it does not find
     * what it wants
     */
    @Override
    public BaseQueryConstructionHelper constructAndReturnQuery(String templateText, String queryName, String queryType, String templateType, String sourceTable, long sourceRecordId, boolean logErrors, EMCUserData userData) {
        List<String> errorList = new ArrayList();
        TreeMap<Integer, BaseQueryActionHelper> helperMap = new TreeMap<Integer, BaseQueryActionHelper>();
        String message = null;
        List<String> mergeFields = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseQueryTable.class);
        query.addAnd("queryName", queryName);
        BaseQueryTable queryRec = (BaseQueryTable) util.executeSingleResultQuery(query, userData);
        if (queryRec == null) {
            if (logErrors) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the query named " + queryName, userData);
            }
            errorList.add("Failed to find the query named " + queryName);
            return new BaseQueryConstructionHelper(helperMap, null, errorList);
        }

        EMCQuery theQuery = new EMCQuery(enumQueryTypes.SELECT, queryRec.getMainEntityClass());

        EMCXMLHandler xml = new EMCXMLHandler();
        DefaultMutableTreeNode queryTablesTree = xml.xmltoTree(queryRec.getTablesTree(), "EMC Query");

        theQuery = populateQueryWithTables(theQuery, queryTablesTree);

        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class);
        query.addAnd("reportQueryId", queryRec.getRecordID());
        List<BaseReportWhereTable> whereList = util.executeGeneralSelectQuery(query, userData);

        String value = null;
        String condition = null;

        for (BaseReportWhereTable where : whereList) {
            value = where.getFieldValue();
            condition = where.getWhereCondition();

            if (where.getRecordID() != 0 && (!Functions.checkBlank(value) || EMCQueryConditions.IS_BLANK.toString().equals(condition) || EMCQueryConditions.IS_NOT_BLANK.toString().equals(condition))) {
                theQuery.addAndCommaSeperated(where.getField(), value, where.getTableName(), EMCQueryConditions.fromString(where.getWhereCondition()));
            }
        }
        //if the source = the query main table limit to the source
        if (sourceTable != null && queryRec.getMainEntityClass().equals(sourceTable)) {
            theQuery.addAnd("recordID", sourceRecordId, sourceTable);
        }

        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class);
        query.addAnd("reportQueryId", queryRec.getRecordID());
        List<BaseReportOrderTable> orderList = util.executeGeneralSelectQuery(query, userData);

        for (BaseReportOrderTable order : orderList) {
            if (order.getRecordID() != 0) {
                String field = order.getField();
                String table = order.getTableName();

                theQuery.addOrderBy(field, table, EMCQueryOrderByDirections.fromString(order.getDirection()));
            }
        }

        query = new EMCQuery(enumQueryTypes.SELECT, BaseQueryActionTable.class);
        query.addAnd("reportQueryId", queryRec.getRecordID());
        List<BaseQueryActionTable> actionList = util.executeGeneralSelectQuery(query, userData);

        int count = 0;
        for (BaseQueryActionTable action : actionList) {
            if (action.getRecordID() != 0) {
                boolean doDefault = true;
                if (queryType != null) {
                    switch (BaseQueryTypeEnum.fromString(queryType)) {
                        case RECIPIENT:
                            if (templateType != null) {
                                doDefault = false;
                                switch (BaseTemplateTypeEnum.fromString(templateType)) {
                                    case EMAIL:
                                        switch (BaseQueryRecipientTypeEnum.fromString(action.getRecipientType())) {
                                            case EMAIL_CRMLOG:
                                                helperMap.put(BaseQueryRecipientTypeEnum.EMAIL_CRMLOG.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            case EMAIL:
                                                helperMap.put(BaseQueryRecipientTypeEnum.EMAIL.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            case EMAIL_BCC:
                                                helperMap.put(BaseQueryRecipientTypeEnum.EMAIL_BCC.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            case EMAIL_CC:
                                                helperMap.put(BaseQueryRecipientTypeEnum.EMAIL_CC.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    case SMS:
                                        switch (BaseQueryRecipientTypeEnum.fromString(action.getRecipientType())) {
                                            case SMS:
                                                helperMap.put(BaseQueryRecipientTypeEnum.SMS.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            case SMS_CRMLOG:
                                                helperMap.put(BaseQueryRecipientTypeEnum.SMS_CRMLOG.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            case SMSPC:
                                                helperMap.put(BaseQueryRecipientTypeEnum.SMSPC.getId(), new BaseQueryActionHelper(action.getField(), action.getTableName()));
                                                break;
                                            default:
                                                break;
                                        }

                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        case MERGE_TEMPLATE:
                            break;
                        default:
                            break;
                    }
                }//query type
                if (doDefault) {
                    theQuery.addField(action.getField(), action.getTableName());
                    helperMap.put(count, new BaseQueryActionHelper(action.getField(), action.getTableName(), action.getTemplateField()));
                    count++;
                }
            } //if recordId
        }//for
        //now load the fields for the sms/email
        if (queryType != null) {
            switch (BaseQueryTypeEnum.fromString(queryType)) {
                case RECIPIENT:
                    if (templateType != null) {
                        switch (BaseTemplateTypeEnum.fromString(templateType)) {
                            case EMAIL:
                                if (helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CRMLOG.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CRMLOG.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CRMLOG.getId()).getTableName());
                                } else {
                                    if (logErrors) {
                                        this.logMessage(Level.SEVERE, "No Email CRM log reference field found for Query:" + queryName, userData);
                                    }
                                    errorList.add("No Email CRM log reference field found for Query:" + queryName);
                                    return new BaseQueryConstructionHelper(helperMap, null, errorList);
                                }
                                if (helperMap.get(BaseQueryRecipientTypeEnum.EMAIL.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.EMAIL.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.EMAIL.getId()).getTableName());
                                } else {
                                    if (logErrors) {
                                        this.logMessage(Level.SEVERE, "No email address setup on the Query:" + queryName, userData);
                                    }
                                    errorList.add("No email address setup on the Query:" + queryName);
                                    return new BaseQueryConstructionHelper(helperMap, null, errorList);
                                }
                                if (helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_BCC.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_BCC.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_BCC.getId()).getTableName());
                                }
                                if (helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CC.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CC.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.EMAIL_CC.getId()).getTableName());
                                }
                                break;
                            case SMS:
                                if (helperMap.get(BaseQueryRecipientTypeEnum.SMSPC.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.SMSPC.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.SMSPC.getId()).getTableName());
                                } else {
                                    if (logErrors) {
                                        this.logMessage(Level.SEVERE, "No postal code field specified for Query:" + queryName, userData);
                                    }
                                    errorList.add("No postal code field specified for Query:" + queryName);
                                    return new BaseQueryConstructionHelper(helperMap, null, errorList);
                                }
                                if (helperMap.get(BaseQueryRecipientTypeEnum.SMS.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.SMS.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.SMS.getId()).getTableName());
                                } else {
                                    if (logErrors) {
                                        this.logMessage(Level.SEVERE, "No cellphone field specified for Query:" + queryName, userData);
                                    }
                                    errorList.add("No cellphone field specified for Query:" + queryName);
                                    return new BaseQueryConstructionHelper(helperMap, null, errorList);
                                }
                                if (helperMap.get(BaseQueryRecipientTypeEnum.SMS_CRMLOG.getId()) != null) {
                                    theQuery.addField(helperMap.get(BaseQueryRecipientTypeEnum.SMS_CRMLOG.getId()).getField(), helperMap.get(BaseQueryRecipientTypeEnum.SMS_CRMLOG.getId()).getTableName());
                                } else {
                                    if (logErrors) {
                                        this.logMessage(Level.SEVERE, "No CRM log reference specified" + queryName, userData);
                                    }
                                    errorList.add("No CRM log reference specified:" + queryName);
                                    return new BaseQueryConstructionHelper(helperMap, null, errorList);
                                }
                                break;
                        }
                    }
                    break;
                case MERGE_TEMPLATE:
                    // execute query and construct the message
                    EMCQuery tmpQ = theQuery;
                    if (tmpQ == null) {
                        errorList.add("Failed to Construct merge query...");
                        break;
                    }
                    mergeFields = findMergeFields(templateText);
                    List<Object[]> templateQResultList = util.executeGeneralSelectQuery(tmpQ, userData);

                    if (templateQResultList == null || templateQResultList.isEmpty() || (templateQResultList.get(0) instanceof Object[] ? templateQResultList.get(0).length : 1) != mergeFields.size()) {
                        errorList.add("Merge fields from query does not match those of the template.");
                        errorList.add(tmpQ.toString());
                        break;
                    }
                    Object[] sorted = new Object[(templateQResultList.get(0) instanceof Object[] ? templateQResultList.get(0).length : 1)];
                    TreeMap<Integer, BaseQueryActionHelper> helperMapTmp = helperMap;
                    for (int j = 0; j < (templateQResultList.get(0) instanceof Object[] ? templateQResultList.get(0).length : 1); j++) {
                        String templateField = helperMapTmp.get(j).getTemplateField();
                        templateField = templateField.replace("[", "");
                        templateField = templateField.replace("]", "");
                        sorted[j] = (templateQResultList.get(0) instanceof Object[] ? templateQResultList.get(0)[mergeFields.indexOf(templateField)] : templateQResultList.get(0));
                    }
                    message = mergeFields(sorted, templateText);

                    break;
                default:
                    break;

            }
        }

        BaseQueryConstructionHelper ret = new BaseQueryConstructionHelper(helperMap, theQuery, errorList);
        ret.setMessageText(message);
        ret.setMergeFields(mergeFields);
        return ret;
    }

    private List<String> findMergeFields(String template) {
        List<String> ret = new ArrayList<String>();
        int findStart = template.indexOf("[");
        while (findStart > 0) {
            int findEnd = template.indexOf("]");
            if (findEnd > 0) {
                String temp = template.substring(findStart + 1, findEnd);
                ret.add(temp);
                template = template.substring(findEnd + 1);//rest to template
            } else {
                break; //syntax error
            }
            findStart = template.indexOf("[");//more?
        }
        return ret;
    }

    private String mergeFields(Object[] mergeValues, String template) {
        int findStart = template.indexOf("[");
        int mergeCount = 0;
        while (findStart > 0) {
            int findEnd = template.indexOf("]");
            if (findEnd > 0) {
                template = template.substring(0, findStart) + (mergeValues[mergeCount] == null ? "-" : mergeValues[mergeCount].toString()) + template.substring(findEnd + 1);
                mergeCount++;
                if (mergeCount >= mergeValues.length) {
                    break;//will cause a index out of bounds should more be found
                }
            } else {
                break; //syntax error
            }
            findStart = template.indexOf("[");//more?
        }
        return template;
    }

    private EMCQuery populateQueryWithTables(EMCQuery query, DefaultMutableTreeNode tablesTreeNode) {
        Enumeration<DefaultMutableTreeNode> nodeChildren = tablesTreeNode.children();
        DefaultMutableTreeNode childNode;
        BaseQueryTableTableObject queryTable;
        while (nodeChildren.hasMoreElements()) {
            childNode = nodeChildren.nextElement();
            queryTable = (BaseQueryTableTableObject) childNode.getUserObject();

            if (!Functions.checkBlank(queryTable.getJoinTable()) && !Functions.checkBlank(queryTable.getJoinField())
                    && !Functions.checkBlank(queryTable.getEntityClass()) && !Functions.checkBlank(queryTable.getEntityClassField())) {
                query.addTableAnd(queryTable.getJoinTable(), queryTable.getEntityClassField(), queryTable.getEntityClass(), queryTable.getJoinField());
            }

            if (!childNode.isLeaf()) {
                query = populateQueryWithTables(query, childNode);
            }
        }

        return query;
    }

    public boolean mergeEMCQueryAndTemplate(String queryName, String templateType, String templateId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseQueryTable.class);
        query.addAnd("queryName", queryName);
        BaseQueryTable baseQuery = (BaseQueryTable) util.executeSingleResultQuery(query, userData);
        if (baseQuery == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the EMC Query.");
            throw new EMCEntityBeanException("Failed to find the EMC Query.");
        } else if (!isBlank(baseQuery.getTemplateId())) {
            Logger.getLogger("emc").log(Level.SEVERE, "The selected EMC Query has already been assigned to the " + baseQuery.getTemplateType() + " Template " + baseQuery.getTemplateId());
            throw new EMCEntityBeanException("The selected EMC Query has already been assigned to the " + baseQuery.getTemplateType() + " Template " + baseQuery.getTemplateId());
        }
        baseQuery.setTemplateType(templateType);
        baseQuery.setTemplateId(templateId);
        this.update(baseQuery, userData);

        switch (BaseTemplateTypeEnum.fromString(templateType)) {
            case EMAIL:
                query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
                query.addAnd("templateId", templateId);
                BaseEmailTemplates emailTemplates = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
                if (emailTemplates == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the Email Template.");
                    throw new EMCEntityBeanException("Failed to find the Email Template.");
                } else if (!isBlank(emailTemplates.getQueryName())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected Email Template has already been assigned to the  EMC Query " + emailTemplates.getQueryName());
                    throw new EMCEntityBeanException("The selected Email Template has already been assigned to the  EMC Query " + emailTemplates.getQueryName());
                }
                emailTemplates.setQueryName(queryName);
                emailTemplateBean.update(emailTemplates, userData);
                break;
            case SMS:
                query = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
                query.addAnd("templateId", templateId);
                BaseSMSTemplate smsTemplates = (BaseSMSTemplate) util.executeSingleResultQuery(query, userData);
                if (smsTemplates == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the SMS Template.");
                    throw new EMCEntityBeanException("Failed to find the SMS Template.");
                } else if (!isBlank(smsTemplates.getQueryName())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected SMS Template has already been assigned to the  EMC Query " + smsTemplates.getQueryName());
                    throw new EMCEntityBeanException("The selected SMS Template has already been assigned to the  EMC Query " + smsTemplates.getQueryName());
                }
                smsTemplates.setQueryName(queryName);
                smsTemplateBean.update(smsTemplates, userData);
                break;
        }
        return true;
    }
}
