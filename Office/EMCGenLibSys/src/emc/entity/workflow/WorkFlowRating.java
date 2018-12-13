/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.skillratings.Rating;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "WFRating", uniqueConstraints = {@UniqueConstraint(columnNames = {"rating", "companyId"})})
public class WorkFlowRating extends EMCEntityClass implements Serializable {

    private String rating;
    private String description;

    public WorkFlowRating() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("rating", new Rating());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("rating");
        toBuild.add("description");
        return toBuild;
    }
}
