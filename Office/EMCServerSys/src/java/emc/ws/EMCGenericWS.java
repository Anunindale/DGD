/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.ws;

import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.server.filehandeler.EMCFileHandlerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author rico
 */
@Stateless
@WebService
public class EMCGenericWS {

    @EJB
    private EMCCommandManagerLocal eMCCommandManager;
    @EJB
    private EMCFileHandlerLocal fileHandler;

    @WebMethod(operationName = "ExecuteEMCGenericWS")
    public String ExecuteEMCGenericWS(String toDo, EMCUserData userData) {
        return eMCCommandManager.executeCommand(toDo, checkUserData(userData));
    }

    @WebMethod
    public byte[] ExecuteEMCGenericWSByte(byte[] toDo, EMCUserData userData) {
        return eMCCommandManager.executeCommandByte(toDo, checkUserData(userData));
    }

    @WebMethod
    public byte[] UploadFile(byte[] toDo, EMCUserData userData) {
        return String.valueOf(fileHandler.uploadFile(toDo, userData)).getBytes();
    }

    @WebMethod
    public byte[] DownloadFile(EMCUserData userData) {
        return fileHandler.downloadFile(userData);
    }

    private EMCUserData checkUserData(EMCUserData userData) {
        if (userData.getUserData() != null && userData.getUserData().size() >= 1 && userData.getUserData(0) instanceof String) {
            String theString = (String) userData.getUserData(0);
            if (theString.startsWith("<C  name=\"emc.framework.EMCQuery\"")) {
                EMCQuery query = (EMCQuery) new EMCXMLHandler().XMLToObject(theString, userData);
                userData.setUserData(0, query);
            }
        }
        return userData;
    }
}
