/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 

package emc.bus.workflow;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rico
 */
@Stateless
public class WFRatingsBean extends EMCEntityBean implements WFRatingsLocal {
    @PersistenceContext
    private EntityManager em;
    public WFRatingsBean(){
        
    }
    
   
}
