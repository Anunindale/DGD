package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportText;
import emc.enums.base.reporttools.BaseReportSectionEnum;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;


import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import javax.ejb.Stateless;

/** 
 *
 * @author claudette
 */
@Stateless
public class BaseReportTextBean extends EMCEntityBean implements BaseReportTextLocal {

    /** Creates a new instance of BaseReportTextBean. */
    public BaseReportTextBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportText record = (BaseReportText) iobject;
        record.setUniqueField(record.getReport() + record.getPart() + record.getLanguageField());
        return super.insert(record, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportText record = (BaseReportText) uobject;
        record.setUniqueField(record.getReport() + record.getPart() + record.getLanguageField());
        return super.update(record, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        Boolean ret = super.doInsertValidation(vobject, userData);
        BaseReportText record = (BaseReportText) vobject;
        if (record.getPart().equals(BaseReportSectionEnum.HEADER.toString()) && !isBlank(record.getLanguageField())) {
            ret = Boolean.FALSE;
            logMessage(Level.SEVERE, ServerBaseMessageEnum.REPORT_CONFLICT, userData);
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        Boolean ret = super.doUpdateValidation(vobject, userData);
        BaseReportText record = (BaseReportText) vobject;
        if (record.getPart().equals(BaseReportSectionEnum.HEADER.toString()) && !isBlank(record.getLanguageField())) {
            ret = Boolean.FALSE;
            logMessage(Level.SEVERE, ServerBaseMessageEnum.REPORT_CONFLICT, userData);
        }
        return ret;
    }

    @Override
    public BaseReportText getReportText(EnumReports report, BaseReportSectionEnum part, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportText.class);
        query.addAnd("report", report.toString());
        query.addAnd("part", part.toString());
        return (BaseReportText) util.executeSingleResultQuery(query, userData);
    }
}
