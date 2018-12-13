/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.datasource;

import emc.entity.base.BaseEmployeeCategoryHistory;

/**
 *
 * @author wikus
 */
public class BaseEmployeeCategoryHistoryDS extends BaseEmployeeCategoryHistory {

    private String fromCategoryDescription;
    private String toCategoryDescription;

    public BaseEmployeeCategoryHistoryDS() {
        this.setDataSource(true);
    }

    public String getFromCategoryDescription() {
        return fromCategoryDescription;
    }

    public void setFromCategoryDescription(String fromCategoryDescription) {
        this.fromCategoryDescription = fromCategoryDescription;
    }

    public String getToCategoryDescription() {
        return toCategoryDescription;
    }

    public void setToCategoryDescription(String toCategoryDescription) {
        this.toCategoryDescription = toCategoryDescription;
    }
}
