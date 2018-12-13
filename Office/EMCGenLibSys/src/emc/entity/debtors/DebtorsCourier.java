/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.courier.CourierId;
import emc.datatypes.debtors.courier.CourierContact;
import emc.datatypes.debtors.courier.CourierContactTelNo;
import emc.datatypes.debtors.courier.CourierContactCellNo;
import emc.datatypes.debtors.courier.CourierContactEmail;
import emc.datatypes.debtors.courier.CourierName;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store courier information.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCourier", uniqueConstraints = {@UniqueConstraint(columnNames = {"courierId", "companyId"})})
public class DebtorsCourier extends EMCEntityClass {

    private String courierId;
    private String courierName;
    private String courierContact;
    private String courierContactTelNo;
    private String courierContactCellNo;
    private String courierContactEmail;

    /** Creates a new instance of DebtorsCourier */
    public DebtorsCourier() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("courierId", new CourierId());
        toBuild.put("courierName", new CourierName());
        toBuild.put("courierContact", new CourierContact());
        toBuild.put("courierContactTelNo", new CourierContactTelNo());
        toBuild.put("courierContactCellNo", new CourierContactCellNo());
        toBuild.put("courierContactEmail", new CourierContactEmail());

        return toBuild;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierContact() {
        return courierContact;
    }

    public void setCourierContact(String courierContact) {
        this.courierContact = courierContact;
    }

    public String getCourierContactTelNo() {
        return courierContactTelNo;
    }

    public void setCourierContactTelNo(String courierContactTelNo) {
        this.courierContactTelNo = courierContactTelNo;
    }

    public String getCourierContactCellNo() {
        return courierContactCellNo;
    }

    public void setCourierContactCellNo(String courierContactCellNo) {
        this.courierContactCellNo = courierContactCellNo;
    }

    public String getCourierContactEmail() {
        return courierContactEmail;
    }

    public void setCourierContactEmail(String courierContactEmail) {
        this.courierContactEmail = courierContactEmail;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("courierId");
        toBuild.add("courierName");
        return toBuild;
    }
}
