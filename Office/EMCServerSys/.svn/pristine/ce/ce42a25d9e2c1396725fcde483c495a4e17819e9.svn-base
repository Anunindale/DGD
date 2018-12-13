/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.filehandeler;

import emc.bus.base.BaseDocRefLocal;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseFilePaths;
import emc.enums.basetables.docuref.DocurefTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class EMCFileHandlerBean extends EMCBusinessBean implements EMCFileHandlerLocal {

    @EJB
    private BaseDocRefLocal documantsBean;

    @Override
    public boolean uploadFile(byte[] file, EMCUserData userData) {
        boolean ret = true;
        String module = (String) userData.getUserData(0);
        String fileName = (String) userData.getUserData(1);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", module);
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module + " module.", userData);
            return false;
        }
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        FileOutputStream out = null;
        try {
            File theFile = new File(filePath.getFilePath() + File.separator + fileName);
            if (theFile.exists()) {
                Logger.getLogger("emc").log(Level.SEVERE, "A file with that name already exists. Please rename your file. (" + theFile.getPath() + ")", userData);
                return false;
            }
            in = new ByteArrayInputStream(file);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            out = new FileOutputStream(theFile);
            int i;
            while ((i = zin.read()) != -1) {
                out.write(i);
            }
            out.flush();
            Logger.getLogger("emc").log(Level.INFO, "File uploaded succesfully.", userData);
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload the file: " + ex.getMessage(), userData);
            ret = false;
        } finally {
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
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close all streams: " + ex.getMessage(), userData);
                }
            }
        }
        return ret;
    }

    @Override
    public byte[] downloadFile(EMCUserData userData) {
        byte[] returnBytes = null;
        String module = (String) userData.getUserData(0);
        String fileName = (String) userData.getUserData(1);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", module);
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module + " module.", userData);
            return returnBytes;
        }
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        try {
            File theFile = new File(filePath.getFilePath() + File.separator + fileName);
            if (!theFile.exists()) {
                Logger.getLogger("emc").log(Level.SEVERE, "The attached file no longer exists.", userData);
                return returnBytes;
            }
            in = new FileInputStream(theFile);
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            int i;
            while ((i = in.read()) != -1) {
                zout.write(i);
            }
            zout.closeEntry();
            zout.flush();
            returnBytes = out.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to download the selected file: " + ex.getMessage(), userData);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (zout != null) {
                    zout.close();
                }
            } catch (IOException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to close all streams: " + ex.getMessage(), userData);
                }
            }
        }
        return returnBytes;
    }

    @Override
    public boolean deleteFile(String module, String fileName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", module);
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + module + " module.", userData);
            return false;
        }
        File theFile = new File(filePath.getFilePath() + File.separator + fileName);
        if (theFile.exists()) {
            theFile.delete();
        }
        Logger.getLogger("emc").log(Level.INFO, "The attached file no longer exists.", userData);
        return true;
    }

    @Override
    public void attachFileToRecord(EMCEntityClass sourceRecord, String note, List<String> fileContent, String fileName, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", Functions.getEMCModule(sourceRecord.getClass()));
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + Functions.getEMCModule(sourceRecord.getClass()) + " module.", userData);
            throw new EMCEntityBeanException("Failed to find a file path for the " + Functions.getEMCModule(sourceRecord.getClass()) + " module.");
        }

        File theFile = new File(filePath.getFilePath() + File.separator + sourceRecord.getRecordID() + "_" + fileName);
        if (theFile.exists()) {
            Logger.getLogger("emc").log(Level.SEVERE, "A file with that name already exists. Please rename your file.", userData);
            throw new EMCEntityBeanException("A file with that name already exists. Please rename your file.");
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(theFile));
            for (String s : fileContent) {
                out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload the file: " + ex.getMessage(), userData);
            throw new EMCEntityBeanException("Failed to upload the file: " + ex.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        BaseDocuRefTable attachment = new BaseDocuRefTable();
        attachment.setRefTableName(sourceRecord.isDataSource() ? sourceRecord.getClass().getSuperclass().getSimpleName() : sourceRecord.getClass().getSimpleName());
        attachment.setRefRecId(String.valueOf(sourceRecord.getRecordID()));
        attachment.setDateAdded(Functions.nowDate());
        attachment.setSeqNum(0);
        attachment.setNote(note);
        attachment.setSummary(note);
        attachment.setType(DocurefTypes.FILE.toString());
        attachment.setAttachmentFileName(fileName);
        documantsBean.insert(attachment, userData);
    }

    @Override
    public void attachFileToRecord(EMCEntityClass sourceRecord, String note, File attachmentFile, String fileName, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", Functions.getEMCModule(sourceRecord.getClass()));
        BaseFilePaths filePath = (BaseFilePaths) util.executeSingleResultQuery(query, userData);
        if (filePath == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find a file path for the " + Functions.getEMCModule(sourceRecord.getClass()) + " module.", userData);
            throw new EMCEntityBeanException("Failed to find a file path for the " + Functions.getEMCModule(sourceRecord.getClass()) + " module.");
        }

        File theFile = new File(filePath.getFilePath() + File.separator + sourceRecord.getRecordID() + "_" + fileName);
        if (theFile.exists()) {
            Logger.getLogger("emc").log(Level.SEVERE, "A file with that name already exists. Please rename your file.", userData);
            throw new EMCEntityBeanException("A file with that name already exists. Please rename your file.");
        }
        try {
            theFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create file: " + ex.getMessage(), userData);
            throw new EMCEntityBeanException("Failed to create file: " + ex.getMessage());
        }

        BufferedOutputStream fileWriter = null;
        BufferedInputStream fileReader = null;
        byte[] readBytes = new byte[1024];

        try {
            fileReader = new BufferedInputStream(new FileInputStream(attachmentFile));
            fileWriter = new BufferedOutputStream(new FileOutputStream(theFile));

            while (fileReader.read(readBytes) != -1) {
                fileWriter.write(readBytes);
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to upload the file: " + ex.getMessage(), userData);
            throw new EMCEntityBeanException("Failed to upload the file: " + ex.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ex) {
                }
            }
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
            }
        }

        BaseDocuRefTable attachment = new BaseDocuRefTable();
        attachment.setRefTableName(sourceRecord.isDataSource() ? sourceRecord.getClass().getSuperclass().getSimpleName() : sourceRecord.getClass().getSimpleName());
        attachment.setRefRecId(String.valueOf(sourceRecord.getRecordID()));
        attachment.setDateAdded(Functions.nowDate());
        attachment.setSeqNum(0);
        attachment.setNote(note);
        attachment.setSummary(note);
        attachment.setType(DocurefTypes.FILE.toString());
        attachment.setAttachmentFileName(fileName);
        documantsBean.insert(attachment, userData);
    }
}
