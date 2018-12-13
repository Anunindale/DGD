package emc.bus.gl.parameters;



import emc.entity.gl.GLParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
*
* @author claudette
*/
@Stateless
public class GLParametersBean extends EMCEntityBean implements GLParametersLocal {

    /** Creates a new instance of GLParametersBean. */
    public GLParametersBean() {
    }

     /**
     * Returns GL parameters.
     * @param userData User data.
     * @return GLParameters, or <code>null</code>, if no parameters have been
     * set up.
     */
    public GLParameters getGLParameters(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLParameters.class);

        return (GLParameters)util.executeSingleResultQuery(query, userData);
    }
}