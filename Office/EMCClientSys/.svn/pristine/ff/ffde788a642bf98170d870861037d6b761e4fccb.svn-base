/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.reporttools.getletterhead;

import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.BaseCompanyTable;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author claudette
 */
public class GetReportHeader {

    /**
     * This method will fetch company letter head if attached, and active to current company in desktop. Will return null if none found.
     * @param userData
     * @return
     */
    public static String getReportHeader(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.GET_REPORT_HEADER.toString());
        List toSend = new ArrayList();
        List received = new ArrayList();
        File theFile = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class);

        query.addAnd("companyId", userData.getCompanyId());
        query.addField("recordID");
        toSend.add(query);
        toSend.add("Letter Head");
        toSend.add("Company Logo");

        received = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (received != null && received.size() > 1 && received.get(1) instanceof List) {
            received = (List) received.get(1);

            userData.setUserData(0, received.get(0)); //Module
            userData.setUserData(1, received.get(1)); //Class

            byte[] headerPath = EMCWSManager.downloadFile(userData);

            if (headerPath != null) {
                ByteArrayInputStream in = null;
                ZipInputStream zin = null;
                FileOutputStream out = null;
                try {
                    //Create Client File
                    File directory = new File(System.getProperty("user.home") + File.separator + "EMC Temp");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    theFile = new File(directory.getPath() + File.separator + userData.getCompanyId() + "_EMCReportHeader");
                    if (!theFile.exists()) {
                        theFile.deleteOnExit();
                        //Write Client file
                        in = new ByteArrayInputStream(headerPath);
                        zin = new ZipInputStream(in);
                        zin.getNextEntry();
                        out = new FileOutputStream(theFile);
                        int i;
                        while ((i = zin.read()) != -1) {
                            out.write(i);
                        }
                        out.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger("emc").log(Level.WARNING, ex.getMessage(), userData);
                } finally {
                    //Close All Streams
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (zin != null) {
                            zin.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException ex) {
                        if (EMCDebug.getDebug()) {
                            Logger.getLogger("emc").log(Level.WARNING, "Failed to close all streams: " + ex.getMessage(), userData);
                        }
                    }
                }
            }
        }
        if (theFile != null) {
            return theFile.getAbsolutePath();
        }

        //Company info not found or letter head path not specified

        return null;

    }
}
