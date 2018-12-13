/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.query;

import emc.entity.base.BaseEmailTemplates;
import emc.entity.base.BaseSMSTemplate;
import emc.enums.base.query.BaseTemplateTypeEnum;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseQueryActionTableBean extends EMCEntityBean implements BaseQueryActionTableLocal {

    @Override
    public List<String> getTemplateFieldsToMap(String templateType, String templateId, EMCUserData userData) {
        EMCQuery query = null;

        switch (BaseTemplateTypeEnum.fromString(templateType)) {
            case EMAIL:
                query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class);
                break;
            case SMS:
                query = new EMCQuery(enumQueryTypes.SELECT, BaseSMSTemplate.class);
                break;
        }

        query.addAnd("templateId", templateId);
        query.addField("template");
        String template = (String) util.executeSingleResultQuery(query, userData);

        Set<String> templateFields = new TreeSet<String>();

        int startIndex = 0;
        while ((startIndex = template.indexOf("[", startIndex)) != -1) {
            templateFields.add(template.substring(startIndex, (startIndex = template.indexOf("]", startIndex)) + 1));
        }

        return new ArrayList<String>(templateFields);
    }
}
