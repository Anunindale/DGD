/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.activetransactions;


import emc.bus.base.parameters.BaseParametersLocal;
import emc.entity.base.BaseParameters;
import emc.entity.base.datasource.EMCTransactions;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCUserData;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class ActiveTransactionsBean extends EMCDataSourceBean implements ActiveTransactionsLocal {
    @EJB
    private EMCCommandManagerLocal commandBean;
    @EJB
    private EMCDateHandlerLocal dateBean;
    @EJB
    private BaseParametersLocal baseParmBean;

    private boolean executeRollBack(String transId,EMCUserData userData) {
        try {
            BaseParameters parm = baseParmBean.getBaseParameters(userData);
            if(parm ==null || isBlank(parm.getAsadminPath())){
                this.logMessage(Level.SEVERE, "Set up of base parameter asadmin path required.", userData);
                return false;
            }
            String glassfishPath = parm.getAsadminPath();
            String userPass = "";
            if(!isBlank(parm.getFullPassFilePath())){
               userPass = parm.getFullPassFilePath();
            }
            transId = transId.toUpperCase();
            //System.out.println("transId "+transId);

            //1 Freeze the transaction service - ps logging transactions must be on for this to work
            String scriptPath = glassfishPath.substring(0,glassfishPath.indexOf("asadmin")) + "glassfish_trans_script";
      
            Process process = Runtime.getRuntime().exec(scriptPath+" " +glassfishPath+ " " +userPass+ " " +transId);
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            int exitValue = process.waitFor();
            process.destroy();
            if(outputGobbler.getOutPut()!=null)
            if(outputGobbler.output.contains("Command rollback-transaction executed successfully"))this.logMessage(Level.INFO, "Transaction roll back requested.", userData);
            if(!isBlank(errorGobbler.getOutPut()))
            this.logMessage(Level.SEVERE, errorGobbler.getOutPut(), userData);
            /*
            //2 get a list of transactions - seems to make this reliable
            process = Runtime.getRuntime().exec(glassfishPath+ " get -m server.transaction-service.*"+userPass);
            errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
            outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            exitValue = process.waitFor();
            //Close newly opened command prompt.
            process.destroy();
            //3 roll back

            process = Runtime.getRuntime().exec(glassfishPath+" rollback-transaction "+userPass+transId);
            errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
            outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            exitValue = process.waitFor();
            if(outputGobbler.getOutPut()!=null)
            this.logMessage(Level.INFO, outputGobbler.getOutPut(), userData);
            if(!isBlank(errorGobbler.getOutPut()))
            this.logMessage(Level.SEVERE, errorGobbler.getOutPut(), userData);
            //Close newly opened command prompt.
            process.destroy();
            //4 unfreeze
            process = Runtime.getRuntime().exec(glassfishPath+ " unfreeze-transaction-service"+userPass);
            errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
            outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            exitValue = process.waitFor();
            //Close newly opened command prompt.
            process.destroy();*/
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    /**
     * Attempts to roll back the given transaction
     * @param trans
     * @param userData
     * @return
     */
    public boolean rollBackTransaction(String transId,EMCUserData userData){
        return executeRollBack(transId,userData);
    }
    @Override
    public Object insert(Object iobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return true;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        //Always display all users
        // Add 5 transactions to be added between getNumrows and getData in Range
        return  String.valueOf(commandBean.getActiveTransactions().size()+5) + ", " + Long.MAX_VALUE;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        List ret = new ArrayList();
        List trans = commandBean.getActiveTransactions();
        int max = trans.size();
        for(int i = start;i<end;i++){
            if(i < max){
                EMCTransactions t = (EMCTransactions)trans.get(i);
                t = t.doDisplayCopy();
                //add the runtime
                t.calcAndSetTransactionRunTime(dateBean.nowDate().getTime());
                ret.add(t);
            }else{
                //pad the data
                EMCTransactions temp = new EMCTransactions();
                temp.setCommand("BUFFER");
                ret.add(temp);
            }
        }
        return ret;
    }

}
class StreamGobbler extends Thread {

    InputStream is;
    String type;
    String output ="";

    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
    public String getOutPut(){
        return output;
    }
    @Override
    public void run() {
        BufferedReader br = null;

        try {


            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                output+= line;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                br.close();
                //writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
