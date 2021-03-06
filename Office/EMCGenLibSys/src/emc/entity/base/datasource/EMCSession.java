/*
 * EMCSession.java
 *
 * Created on 29 November 2007, 04:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.entity.base.datasource;

import emc.datatypes.base.onlineusers.ClientIP;
import emc.datatypes.base.onlineusers.ClientName;
import emc.datatypes.base.onlineusers.IdleTime;
import emc.datatypes.base.onlineusers.KillSession;
import emc.datatypes.base.onlineusers.LoginDateTime;
import emc.datatypes.base.onlineusers.LoginTimeOnly;
import emc.datatypes.base.onlineusers.SessionNumber;
import emc.datatypes.base.onlineusers.TimeStamp;
import emc.datatypes.base.onlineusers.UserName;
import emc.framework.EMCEntityClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marius-Gert Coetzee
 */
public class EMCSession extends EMCEntityClass {

    /** Creates a new instance of EMCSession */
    private long sessionNumber;
    private long timeStamp;
    private String clientName;
    private String clientIP;
    private String userName;
    private Date loginDateTime;
    private Date loginTimeOnly; //convenience field for client display Points to loginDateTime
    private long idleTime;
    private List adminMessage;
    private boolean killSession = false;
    private boolean checkingInactive = false;
    private String companyId;

    public EMCSession() {
        this.setDataSource(true);
    }

    public long getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(Date loginDateTime) {
        this.loginDateTime = loginDateTime;
        this.loginTimeOnly = loginDateTime;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public boolean isKillSession() {
        return killSession;
    }

    public void setKillSession(boolean killSession) {
        this.killSession = killSession;
    }

    public List getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(List adminMessage) {
        this.adminMessage = adminMessage;
    }

    public boolean isCheckingInactive() {
        return checkingInactive;
    }

    public void setCheckingInactive(boolean checkingInactive) {
        this.checkingInactive = checkingInactive;
    }

    public String getString() {
        return "Session: " + String.valueOf(sessionNumber) + ", Client Name(IP Address): " + clientName + "(" + clientIP + "), User Name: " + userName + ", Login Time: ";//+ loginDateTime. 
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("clientIP", new ClientIP());
        toBuild.put("sessionNumber", new SessionNumber());
        toBuild.put("timeStamp", new TimeStamp());
        toBuild.put("clientName", new ClientName());
        toBuild.put("userName", new UserName());
        toBuild.put("loginDateTime", new LoginDateTime());
        toBuild.put("loginTimeOnly", new LoginTimeOnly());
        toBuild.put("idleTime", new IdleTime());
        toBuild.put("killSession", new KillSession());

        return toBuild;
    }

    /**
     * This method is syncronized pass in a message to log a message or null to get messages
     * @param newMessage
     * @return
     */
    public synchronized String setOrGetAdminMessage(String newMessage) { //to avoid concurrent modification ex
        String ret = null;
        if (newMessage != null) { //set
            if (this.adminMessage == null) {
                this.adminMessage = new ArrayList();
            }
            this.adminMessage.add(newMessage);
        } else { //get
            if ((adminMessage != null) && (adminMessage.size() > 0)) {
                ret = "";
                for (int j = 0; j < adminMessage.size(); j++) {
                    ret += adminMessage.get(j).toString() + "\n";
                }
                adminMessage = new ArrayList();
            }
        }
        return ret;
    }

    /**
     * @return the loginTimeOnly
     */
    public Date getLoginTimeOnly() {
        return loginDateTime;
    }

   
}
