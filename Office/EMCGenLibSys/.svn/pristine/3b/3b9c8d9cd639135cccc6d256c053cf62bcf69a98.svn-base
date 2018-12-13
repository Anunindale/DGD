/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.query;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.query.MainEntityClass;
import emc.datatypes.base.query.QueryName;
import emc.datatypes.systemwide.Description;
import emc.enums.base.query.BaseQueryTypeEnum;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseQueryTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"queryName", "companyId"})})
public class BaseQueryTable extends EMCEntityClass {

    @Column(length = 50)
    private String queryName;
    private String description;
    @Column(length = 10000)
    private String tablesTree;
    private String mainEntityClass;
    private String queryType = BaseQueryTypeEnum.RECIPIENT.toString();
    private String templateId;
    private String templateType;
    private boolean fromSourceRecord;

    /** Creates a new instance of BaseReportUserQueryTable. */
    public BaseQueryTable() {
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getTablesTree() {
        return tablesTree;
    }

    public void setTablesTree(String tablesTree) {
        this.tablesTree = tablesTree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainEntityClass() {
        return mainEntityClass;
    }

    public void setMainEntityClass(String mainEntityClass) {
        this.mainEntityClass = mainEntityClass;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public boolean isFromSourceRecord() {
        return fromSourceRecord;
    }

    public void setFromSourceRecord(boolean fromSourceRecord) {
        this.fromSourceRecord = fromSourceRecord;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap<String, EMCDataType> theMap = super.buildFieldList();
        theMap.put("queryName", new QueryName());
        theMap.put("description", new Description());
        theMap.put("mainEntityClass", new MainEntityClass());
        return theMap;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("queryName");
        toBuild.add("description");
        toBuild.add("mainEntityClass");
        toBuild.add("queryType");
        return toBuild;
    }
}
