package emc.entity.base;

import emc.datatypes.base.country.Country;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseCountries", uniqueConstraints = {@UniqueConstraint(columnNames = {"Code", "companyId"})})
public class BaseCountries extends EMCEntityClass {

    private String Code;
    private String Name;
    private String numberCode;

    public BaseCountries() {
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("Code", new Country());
        toBuild.put("Name", new Description());

        return toBuild;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> fields = super.buildDefaultLookupFieldList();

        fields.add("Code");
        fields.add("Name");

        return fields;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("Code");
        return query;
    }
}
