/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.reporttools.barcodeprinter;

import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class StaticBarcodePrinting {

    private static List<Integer> countList = Collections.synchronizedList(new ArrayList<Integer>());

    public static void print(EnumReports printType, String queryName, String toPrint, EMCUserData userData) {
        try {
            int count = 0;
            while (true) {
                if (countList.contains(count)) {
                    count++;
                } else {
                    countList.add(count);
                    break;
                }
            }
            String printerName = findPrinter(printType, queryName, userData);
            if(printerName!=null){
                System.out.println("Printer:-" + printerName);
                String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + printType.toString() + count + ".emc";
                File theFile = new File(fileName);
                FileWriter outputFileReader = new FileWriter(theFile);
                PrintWriter outputStream = new PrintWriter(outputFileReader);
                toPrint = toPrint.replaceAll("~BARCODERETURN~", "\r");
                toPrint = toPrint.replaceAll("~BARCODESTART~", "\u0002");
                outputStream.println(toPrint);
                outputStream.close();
                String OS = System.getProperties().getProperty("os.name");
                if (OS.contains("Linux")) {
                    Runtime.getRuntime().exec("lpr -P" + printerName + " " + fileName);
                }else if(OS.contains("Windows")){
                    //print /d:\\pserver\laser1 c:\letter.doc
                    Runtime.getRuntime().exec("print /d:\\" + printerName + " " + fileName);
                }else{
                    Runtime.getRuntime().exec("lpr -P" + printerName + " " + fileName);
                }
                //theFile.delete();
                Logger.getLogger("emc").log(Level.INFO, "Labels printed.", userData);
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find printer: " + printerName, userData);
            }
            countList.remove(count);
        } catch (IOException e) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to print labels: " + e.getMessage(), userData);
        }
    }

    private static String findPrinter(EnumReports printType, String queryName, EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.FIND_PRINT_OPTIONS.toString());
        List toSend = new ArrayList();
        toSend.add(printType.toString());
        toSend.add(Functions.checkBlank(queryName) ? "" : queryName);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1) {
            BaseReportPrintOptions options = (BaseReportPrintOptions) toSend.get(1);
            if (options != null && options.isPrintDirect() && !Functions.checkBlank(options.getPrinterName())) {
                return options.getPrinterName();
            }
        }
        Logger.getLogger("emc").log(Level.WARNING, "No print settings found. File not printed");
        return null;
    }
}
