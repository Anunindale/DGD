package emc.bus.gl.parameters;

import emc.entity.gl.GLParameters;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/** 
*
* @author claudette
*/
@Local
public interface GLParametersLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns GL parameters.
     * @param userData User data.
     * @return GLParameters, or <code>null</code>, if no parameters have been
     * set up.
     */
    public GLParameters getGLParameters(EMCUserData userData);
}