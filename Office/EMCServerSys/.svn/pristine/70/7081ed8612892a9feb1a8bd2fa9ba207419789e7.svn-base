package emc.bus.inventory;

import emc.entity.inventory.InventoryReferenceType;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import javax.ejb.Stateless;

/**
 * 
 * @author wikus
 */
@Stateless
public class InventoryReferenceTypeBean extends EMCEntityBean implements InventoryReferenceTypeLocal {

    public InventoryReferenceTypeBean() {

    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        String ret = super.getNumRows(theTable, userData);
        // TODO Check that this works.
        int numRows = Integer.parseInt(ret.split(",")[0].trim());
        if (numRows == 0) {
            try {
           setDefaults(userData);
            } catch (EMCEntityBeanException e) {
                //HANDLE EXCEPTION
            }
        }
        
        return ret;
    }
    
    private void setDefaults(EMCUserData userData) throws EMCEntityBeanException {
         InventoryReferenceType record = new InventoryReferenceType();
            record.setType(InventoryReferenceTypes.PRIMARY.toString());
            record.setDescription("Primary Reference");
            super.insert(record, userData);
            
            record = new InventoryReferenceType();
            record.setType(InventoryReferenceTypes.CUSTOMER.toString());
            record.setDescription("Customer Reference");
            super.insert(record, userData);
            
            record = new InventoryReferenceType();
            record.setType(InventoryReferenceTypes.SUPPLIER.toString());
            record.setDescription("Supplier Reference");
            super.insert(record, userData);
            
            record = new InventoryReferenceType();
            record.setType(InventoryReferenceTypes.DEFAULT.toString());
            record.setDescription("Default Reference");
            super.insert(record, userData);
            
            
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryReferenceType record = (InventoryReferenceType) vobject;

        if (record.getType().equals(InventoryReferenceTypes.PRIMARY.toString()) ||
                record.getType().equals(InventoryReferenceTypes.CUSTOMER.toString()) ||
                record.getType().equals(InventoryReferenceTypes.SUPPLIER.toString()) ||
                record.getType().equals(InventoryReferenceTypes.DEFAULT.toString())) {
            return false;
        }

        return super.doDeleteValidation(vobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryReferenceType record = (InventoryReferenceType) vobject;

        if (record.getType().equals(InventoryReferenceTypes.PRIMARY.toString()) ||
                record.getType().equals(InventoryReferenceTypes.CUSTOMER.toString()) ||
                record.getType().equals(InventoryReferenceTypes.SUPPLIER.toString())||
                record.getType().equals(InventoryReferenceTypes.DEFAULT.toString())) {
            return false;
        }

        return super.doUpdateValidation(vobject, userData);
    }
}
