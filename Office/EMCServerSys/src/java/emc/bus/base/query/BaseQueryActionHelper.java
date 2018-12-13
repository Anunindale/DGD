/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.query;

/**
 *
 * @author rico
 */
public class BaseQueryActionHelper {
    private String field;
    private String tableName;
    private String templateField;


    public BaseQueryActionHelper(String field,String tableName){
        this.field = field;
        this.tableName = tableName;
    }
    public BaseQueryActionHelper(String field,String tableName,String templateField){
        this.field = field;
        this.tableName = tableName;
        this.templateField = templateField;
    }
    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the templateField
     */
    public String getTemplateField() {
        return templateField;
    }

    /**
     * @param templateField the templateField to set
     */
    public void setTemplateField(String templateField) {
        this.templateField = templateField;
    }

}
