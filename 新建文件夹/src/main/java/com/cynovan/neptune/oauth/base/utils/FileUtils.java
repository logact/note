package com.cynovan.neptune.oauth.base.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtils.copyFileCover(srcFileName, descFileName, false);
    }

    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            log.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            log.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
            return false;
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                log.debug("目标文件已存在，准备删除!");
                if (!FileUtils.delFile(descFileName)) {
                    log.debug("删除目标文件 " + descFileName + " 失败!");
                    return false;
                }
            } else {
                log.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                log.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    log.debug("创建目标文件所在的目录失败!");
                    return false;
                }
            }
        }

        // 准备复制文件
        // 读取的位数
        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            log.debug("复制单个文件 " + srcFileName + " 到" + descFileName + "成功!");
            return true;
        } catch (Exception e) {
            log.debug("复制文件失败：" + e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    oute.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }

    public static byte[] thumbImage(byte[] by, int width, int height) throws IOException {
        InputStream myInputStream = new ByteArrayInputStream(by);
        return thumbImage(myInputStream, width, height);
    }

    public static byte[] thumbImage(InputStream myInputStream, int width, int height) throws IOException {
        if (width <= 0) {
            width = 300;
        }
        if (height <= 0) {
            height = 300;
        }
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Thumbnails.of(myInputStream).size(width, height).toOutputStream(outStream);
        // output stream to byte
        myInputStream.close();
        return outStream.toByteArray();
    }

    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return FileUtils.copyDirectoryCover(srcDirName, descDirName, false);
    }

    public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            log.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            log.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                log.debug("目标目录已存在，准备删除!");
                if (!FileUtils.delFile(descDirNames)) {
                    log.debug("删除目录 " + descDirNames + " 失败!");
                    return false;
                }
            } else {
                log.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
                return false;
            }
        } else {
            // 创建目标目录
            log.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                log.debug("创建目标目录失败!");
                return false;
            }

        }

        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则直接复制
            if (files[i].isFile()) {
                flag = FileUtils.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName());
                // 如果拷贝文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，则继续复制目录
            if (files[i].isDirectory()) {
                flag = FileUtils.copyDirectory(files[i].getAbsolutePath(), descDirName + files[i].getName());
                // 如果拷贝目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
            return false;
        }
        log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
        return true;

    }

    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtils.deleteFile(fileName);
            } else {
                return FileUtils.deleteDirectory(fileName);
            }
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("删除单个文件 " + fileName + " 成功!");
                return true;
            } else {
                log.debug("删除单个文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i].getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            log.debug("删除目录 " + dirName + " 成功!");
            return true;
        } else {
            log.debug("删除目录 " + dirName + " 失败!");
            return false;
        }

    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            log.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            log.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                log.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                log.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                log.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            log.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            log.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            log.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    /*
     * public static void zipFiles(String srcDirName, String fileName, String
     * descFileName) { // 判断目录是否存在 if (srcDirName == null) {
     * log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!"); return; } File fileDir =
     * new File(srcDirName); if (!fileDir.exists() || !fileDir.isDirectory()) {
     * log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!"); return; } String dirPath
     * = fileDir.getAbsolutePath(); File descFile = new File(descFileName); try
     * { ZipOutputStream zouts = new ZipOutputStream(new
     * FileOutputStream(descFile)); if ("*".equals(fileName) ||
     * "".equals(fileName)) { FileUtils.zipDirectoryToZipFile(dirPath, fileDir,
     * zouts); } else { File file = new File(fileDir, fileName); if
     * (file.isFile()) { FileUtils.zipFilesToZipFile(dirPath, file, zouts); }
     * else { FileUtils.zipDirectoryToZipFile(dirPath, file, zouts); } }
     * zouts.close(); log.debug(descFileName + " 文件压缩成功!"); } catch (Exception
     * e) { log.debug("文件压缩失败：" + e.getMessage()); e.printStackTrace(); }
     *
     * }
     *
     * public static boolean unZipFiles(String zipFileName, String descFileName)
     * { String descFileNames = descFileName; if
     * (!descFileNames.endsWith(File.separator)) { descFileNames = descFileNames
     * + File.separator; } try { // 根据ZIP文件创建ZipFile对象 ZipFile zipFile = new
     * ZipFile(zipFileName); ZipEntry entry = null; String entryName = null;
     * String descFileDir = null; byte[] buf = new byte[4096]; int readByte = 0;
     * // 获取ZIP文件里所有的entry
     *
     * @SuppressWarnings("rawtypes") Enumeration enums = zipFile.getEntries();
     * // 遍历所有entry while (enums.hasMoreElements()) { entry = (ZipEntry)
     * enums.nextElement(); // 获得entry的名字 entryName = entry.getName();
     * descFileDir = descFileNames + entryName; if (entry.isDirectory()) { //
     * 如果entry是一个目录，则创建目录 new File(descFileDir).mkdirs(); continue; } else { //
     * 如果entry是一个文件，则创建父目录 new File(descFileDir).getParentFile().mkdirs(); }
     * File file = new File(descFileDir); // 打开文件输出流 OutputStream os = new
     * FileOutputStream(file); // 从ZipFile对象中打开entry的输入流 InputStream is =
     * zipFile.getInputStream(entry); while ((readByte = is.read(buf)) != -1) {
     * os.write(buf, 0, readByte); } os.close(); is.close(); } zipFile.close();
     * log.debug("文件解压成功!"); return true; } catch (Exception e) {
     * log.debug("文件解压失败：" + e.getMessage()); return false; } }
     *
     * public static void zipDirectoryToZipFile(String dirPath, File fileDir,
     * ZipOutputStream zouts) { if (fileDir.isDirectory()) { File[] files =
     * fileDir.listFiles(); // 空的文件夹 if (files.length == 0) { // 目录信息 ZipEntry
     * entry = new ZipEntry(getEntryName(dirPath, fileDir)); try {
     * zouts.putNextEntry(entry); zouts.closeEntry(); } catch (Exception e) {
     * e.printStackTrace(); } return; }
     *
     * for (int i = 0; i < files.length; i++) { if (files[i].isFile()) { //
     * 如果是文件，则调用文件压缩方法 FileUtils.zipFilesToZipFile(dirPath, files[i], zouts); }
     * else { // 如果是目录，则递归调用 FileUtils.zipDirectoryToZipFile(dirPath, files[i],
     * zouts); } }
     *
     * }
     *
     * }
     *
     * public static void zipFilesToZipFile(String dirPath, File file,
     * ZipOutputStream zouts) { FileInputStream fin = null; ZipEntry entry =
     * null; // 创建复制缓冲区 byte[] buf = new byte[4096]; int readByte = 0; if
     * (file.isFile()) { try { // 创建一个文件输入流 fin = new FileInputStream(file); //
     * 创建一个ZipEntry entry = new ZipEntry(getEntryName(dirPath, file)); //
     * 存储信息到压缩文件 zouts.putNextEntry(entry); // 复制字节到压缩文件 while ((readByte =
     * fin.read(buf)) != -1) { zouts.write(buf, 0, readByte); }
     * zouts.closeEntry(); fin.close(); System.out.println("添加文件 " +
     * file.getAbsolutePath() + " 到zip文件中!"); } catch (Exception e) {
     * e.printStackTrace(); } }
     *
     * }
     */

    private static String getEntryName(String dirPath, File file) {
        String dirPaths = dirPath;
        if (!dirPaths.endsWith(File.separator)) {
            dirPaths = dirPaths + File.separator;
        }
        String filePath = file.getAbsolutePath();
        // 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储
        if (file.isDirectory()) {
            filePath += "/";
        }
        int index = filePath.indexOf(dirPaths);

        return filePath.substring(index + dirPaths.length());
    }

    public static String getClassPathFileContent(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        return getClassPathFileContent(resource);
    }

    public static String getClassPathFileContent(ClassPathResource resource) {
        try {
            if (resource != null) {
                InputStream stream = resource.getInputStream();
                String str = IOUtils.toString(stream, StringLib.UTF_8);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
