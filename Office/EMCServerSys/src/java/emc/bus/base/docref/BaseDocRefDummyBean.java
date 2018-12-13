/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.docref;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 * @Name         : BaseDocRefDummyBean
 *
 * @Date         : 08 Oct 2009
 *
 * @Description  : This bean is used to update the records to which documents are attached.  It should do no validation whatsoever.
 *
 * @author       : riaan
 */
@Stateless
public class BaseDocRefDummyBean extends EMCEntityBean implements BaseDocRefDummyLocal{

    /** Creates a new instance of BaseDocRefDummyBean. */
    public BaseDocRefDummyBean() {
        
    }
}
