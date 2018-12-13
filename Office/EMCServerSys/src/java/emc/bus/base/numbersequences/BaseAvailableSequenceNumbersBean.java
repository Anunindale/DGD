/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
 
package emc.bus.base.numbersequences;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */ 
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BaseAvailableSequenceNumbersBean extends EMCEntityBean implements BaseAvailableSequenceNumbersLocal {

    /** Creates a new instance of BaseAvailableSequenceNumbersBean */
    public BaseAvailableSequenceNumbersBean() {
        
    }
}
