/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.parameters.FTPPassword;
import emc.datatypes.base.parameters.FTPServer;
import emc.datatypes.base.parameters.FTPUserName;
import emc.datatypes.base.parameters.RootFileDirectory;
import emc.datatypes.base.parameters.SMSURL;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseParameters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId"})})
public class BaseParameters extends EMCEntityClass {

    private String randomNumberImageDir;
    private int maxMultiProcessingThread;
    private int reservePerClient;
    private int firstDayOfWeek = 1;
    private int minDaysInFirstWeek = 7;
    private String standardCalendarId;
    private String salesStandardCalendarId;
    private String productionStandardCalendarId;
    //FTP
    private boolean useRemoteFTP;
    @Column(name = "rootDirectoryPath", length = 1000)
    private String rootDirectoryPath;
    @Column(name = "ftpServer", length = 1000)
    private String ftpServer;
    private String ftpuserName;
    private String ftpPassword;
    //SMS
    private boolean includeCountryCodeInNumber;
    private int maxMessageLength;
    private boolean testSMS;
    private String smsSendingOption;
    private String smsEmail;
    private String smsFileOutputField1;
    private String smsFileOutputField2;
    private String smsFileOutputField3;
    private String defaultCountryCode;
    private String smsURL; //this should be the url e.g. http://sms.smsmalls.co.za/sa/gateway/tryone.asp?Subscription=WC-CPTT-L102-B229&User=1172006&Password=1553&Type=1
    private String smsNumberURLParm; // e.g. Cell
    private String smsMessageURLParm; // e.g. Message
    //transaction rollback
    private String asadminPath; // e.g. /home/administrator/glassfish/bin/asadmin
    private String fullPassFilePath;// /home/administrator/passfile
    private boolean monitorTransactions;
    private int keepTransactionDays;
    private String maxAllowedSMSEmailWithoutPassword;
    private String smsEmailApprovalPassword;

    public String getRandomNumberImageDir() {
        return randomNumberImageDir;
    }

    public void setRandomNumberImageDir(String randomNumberImageDir) {
        this.randomNumberImageDir = randomNumberImageDir;
    }

    public int getMaxMultiProcessingThread() {
        return maxMultiProcessingThread;
    }

    public void setMaxMultiProcessingThread(int maxMultiProcessingThread) {
        this.maxMultiProcessingThread = maxMultiProcessingThread;
    }

    public int getReservePerClient() {
        return reservePerClient;
    }

    public void setReservePerClient(int reservePerClient) {
        this.reservePerClient = reservePerClient;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public int getMinDaysInFirstWeek() {
        return minDaysInFirstWeek;
    }

    public void setMinDaysInFirstWeek(int minDaysInFirstWeek) {
        this.minDaysInFirstWeek = minDaysInFirstWeek;
    }

    public String getStandardCalendarId() {
        return standardCalendarId;
    }

    public void setStandardCalendarId(String standardCalendarId) {
        this.standardCalendarId = standardCalendarId;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getFtpuserName() {
        return ftpuserName;
    }

    public void setFtpuserName(String ftpuserName) {
        this.ftpuserName = ftpuserName;
    }

    public boolean isUseRemoteFTP() {
        return useRemoteFTP;
    }

    public void setUseRemoteFTP(boolean useRemoteFTP) {
        this.useRemoteFTP = useRemoteFTP;
    }

    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }

    public void setRootDirectoryPath(String rootDirectoryPath) {
        this.rootDirectoryPath = rootDirectoryPath;
    }

    public boolean isIncludeCountryCodeInNumber() {
        return includeCountryCodeInNumber;
    }

    public void setIncludeCountryCodeInNumber(boolean includeCountryCodeInNumber) {
        this.includeCountryCodeInNumber = includeCountryCodeInNumber;
    }

    public int getMaxMessageLength() {
        return maxMessageLength;
    }

    public void setMaxMessageLength(int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }

    public String getSmsEmail() {
        return smsEmail;
    }

    public void setSmsEmail(String smsEmail) {
        this.smsEmail = smsEmail;
    }

    public String getSmsFileOutputField1() {
        return smsFileOutputField1;
    }

    public void setSmsFileOutputField1(String smsFileOutputField1) {
        this.smsFileOutputField1 = smsFileOutputField1;
    }

    public String getSmsFileOutputField2() {
        return smsFileOutputField2;
    }

    public void setSmsFileOutputField2(String smsFileOutputField2) {
        this.smsFileOutputField2 = smsFileOutputField2;
    }

    public String getSmsFileOutputField3() {
        return smsFileOutputField3;
    }

    public void setSmsFileOutputField3(String smsFileOutputField3) {
        this.smsFileOutputField3 = smsFileOutputField3;
    }

    public String getSmsSendingOption() {
        return smsSendingOption;
    }

    public void setSmsSendingOption(String smsSendingOption) {
        this.smsSendingOption = smsSendingOption;
    }

    public String getDefaultCountryCode() {
        return defaultCountryCode;
    }

    public void setDefaultCountryCode(String defaultCountryCode) {
        this.defaultCountryCode = defaultCountryCode;
    }

    /**
     * @return the asadminPath
     */
    public String getAsadminPath() {
        return asadminPath;
    }

    /**
     * @param asadminPath the asadminPath to set
     */
    public void setAsadminPath(String asadminPath) {
        this.asadminPath = asadminPath;
    }

    /**
     * @return the fullPassFilePath
     */
    public String getFullPassFilePath() {
        return fullPassFilePath;
    }

    /**
     * @param fullPassFilePath the fullPassFilePath to set
     */
    public void setFullPassFilePath(String fullPassFilePath) {
        this.fullPassFilePath = fullPassFilePath;
    }

    public boolean isTestSMS() {
        return testSMS;
    }

    public void setTestSMS(boolean testSMS) {
        this.testSMS = testSMS;
    }

    public String getSmsURL() {
        return smsURL;
    }

    public void setSmsURL(String smsURL) {
        this.smsURL = smsURL;
    }

    public String getSmsNumberURLParm() {
        return smsNumberURLParm;
    }

    public void setSmsNumberURLParm(String smsNumberURLParm) {
        this.smsNumberURLParm = smsNumberURLParm;
    }

    public String getSmsMessageURLParm() {
        return smsMessageURLParm;
    }

    public void setSmsMessageURLParm(String smsMessageURLParm) {
        this.smsMessageURLParm = smsMessageURLParm;
    }

    public String getSalesStandardCalendarId() {
        return salesStandardCalendarId;
    }

    public void setSalesStandardCalendarId(String salesStandardCalendarId) {
        this.salesStandardCalendarId = salesStandardCalendarId;
    }

    public String getProductionStandardCalendarId() {
        return productionStandardCalendarId;
    }

    public void setProductionStandardCalendarId(String productionStandardCalendarId) {
        this.productionStandardCalendarId = productionStandardCalendarId;
    }

    public boolean isMonitorTransactions() {
        return monitorTransactions;
    }

    public void setMonitorTransactions(boolean monitorTransactions) {
        this.monitorTransactions = monitorTransactions;
    }

    public int getKeepTransactionDays() {
        return keepTransactionDays;
    }

    public void setKeepTransactionDays(int keepTransactionDays) {
        this.keepTransactionDays = keepTransactionDays;
    }

    public String getMaxAllowedSMSEmailWithoutPassword() {
        return maxAllowedSMSEmailWithoutPassword;
    }

    public void setMaxAllowedSMSEmailWithoutPassword(String maxAllowedSMSEmailWithoutPassword) {
        this.maxAllowedSMSEmailWithoutPassword = maxAllowedSMSEmailWithoutPassword;
    }

    public String getSmsEmailApprovalPassword() {
        return smsEmailApprovalPassword;
    }

    public void setSmsEmailApprovalPassword(String smsEmailApprovalPassword) {
        this.smsEmailApprovalPassword = smsEmailApprovalPassword;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("ftpServer", new FTPServer());
        toBuild.put("ftpuserName", new FTPUserName());
        toBuild.put("ftpPassword", new FTPPassword());
        toBuild.put("rootDirectoryPath", new RootFileDirectory());
        toBuild.put("asadminPath", new Description());
        toBuild.put("fullPassFilePath", new Description());
        toBuild.put("smsURL", new SMSURL());
        toBuild.put("smsNumberURLParm", new Description());
        toBuild.put("smsMessageURLParm", new Description());

        return toBuild;
    }
}