/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.base.city.foreignkeys.CityFKNM;
import emc.datatypes.base.postalcodes.CountryFK;
import emc.datatypes.base.postalcodes.PostalCode;
import emc.datatypes.base.provence.foreignkeys.ProvenceFKNM;
import emc.datatypes.base.suburb.foreignkeys.SuburbFKNM;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BasePostalCodes", uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "suburb", "city", "provence", "country", "companyId"})})
public class BasePostalCodes extends EMCEntityClass implements Serializable {

    private String code;
    private String suburb;
    private String city;
    private String provence;
    private String country;

    /** Creates a new instance of BasePostalCodes */
    public BasePostalCodes() {
        this.setEmcLabel("Base Postal Codes");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("code", new PostalCode());
        toBuild.put("suburb", new SuburbFKNM());
        toBuild.put("city", new CityFKNM());
        toBuild.put("provence", new ProvenceFKNM());
        toBuild.put("country", new CountryFK());

        return toBuild;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> fields = super.buildDefaultLookupFieldList();

        fields.add("code");
        fields.add("suburb");
        fields.add("city");
        fields.add("provence");
        fields.add("country");

        return fields;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("country");
        query.addOrderBy("provence");
        query.addOrderBy("city");
        query.addOrderBy("suburb");
        query.addOrderBy("code");
        return query;
    }
}
