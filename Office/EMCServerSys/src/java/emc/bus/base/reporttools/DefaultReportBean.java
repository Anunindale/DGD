/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.reporttools;

import emc.framework.EMCReportBean;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DefaultReportBean extends EMCReportBean implements DefaultReportLocal {

    /** Creates a new instance of DefaultReportBean */
   public DefaultReportBean() {
       
   }
}
