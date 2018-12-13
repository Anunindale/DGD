/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.settlement;

import emc.entity.inventory.stocksettlement.InventoryStockSettlement;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.settlement.SettlementStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerInventoryMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.tables.EMCTable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class InventorySettlementBean extends EMCEntityBean implements  InventorySettlementLocal{
    @EJB
    private EMCDateHandlerLocal dateBean;


    public InventorySettlementBean() {
    }
    

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doDeleteValidation(vobject, userData) && allowUpdateDelete((InventoryStockSettlement) vobject,userData);
    }
    //tests the run status only update and delete
    private boolean allowUpdateDelete(InventoryStockSettlement sm,EMCUserData userData){
        if(!isBlank(sm.getRunStatus())){
            switch(SettlementStatus.fromString(sm.getRunStatus())){
                case COMPLETED: this.logMessage(Level.SEVERE, ServerInventoryMessageEnum.SETTNODELETEUPDATE, userData); return false;
            }
        }
        return true;
    }

    @Override
    public boolean doFieldValidation(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryStockSettlement sm = (InventoryStockSettlement) theRecord;
        boolean ret =  super.doFieldValidation(fieldNameToValidate, theRecord, userData);
        ret = ret && allowUpdateDelete(sm,userData);
        if(fieldNameToValidate.equals("endData")){
            if(dateBean.compareDatesIgnoreTime(sm.getEndDate(),getStockCloseDate(userData), userData) <= 0){
                ret = false;
                this.logMessage(Level.SEVERE, ServerInventoryMessageEnum.SETINVALIDENDDATE, userData);
            }
        }
        return ret;
    }
    private Date getStockCloseDate(EMCUserData userData){
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryStockSettlement.class);
        qu.addAnd("runStatus", SettlementStatus.COMPLETED.toString());
        qu.addFieldAggregateFunction("endDate", "MAX");
        List result = util.executeGeneralSelectQuery(qu, userData);
        if(result.size() == 0){
            return dateBean.buildDate(1900, 0, 1, 0, 0, 0);
        }else{
            try{
                Date ret = Functions.string2Date(result.get(0).toString(),"yyyy-MM-dd");
                return ret;
            }catch(Exception e){
                logMessage(Level.SEVERE, ServerInventoryMessageEnum.TXSCPERIOD, userData);
                return Functions.nowDate();
            }
        }

    }

}
