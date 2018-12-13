/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.action;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseImportLocal {

    public Object importRecord(List recordData, EMCUserData userData) throws EMCEntityBeanException;
    
}
