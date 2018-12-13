/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIDFKCascade;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.hr.dependants.DateOfBirth;
import emc.datatypes.hr.dependants.IdNumber;
import emc.datatypes.hr.dependants.Name;
import emc.datatypes.hr.dependants.Surname;
import emc.datatypes.hr.dependants.Type;
import emc.datatypes.systemwide.Telephone;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "HREmployeeDependants", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "idNumber", "companyId"})})
public class HREmployeeDependants extends EMCEntityClass {

    private String employeeNumber;
    private String type;
    private String name;
    private String surname;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String idNumber;
    private String contactNum;
    private String relation;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("employeeNumber", new EmployeeIDFKCascade());
        toBuild.put("type", new Type());
        toBuild.put("name", new Name());
        toBuild.put("surname", new Surname());
        toBuild.put("dateOfBirth", new DateOfBirth());
        toBuild.put("idNumber", new IdNumber());
        toBuild.put("contactNum", new Telephone());
        return toBuild;
    }
}
