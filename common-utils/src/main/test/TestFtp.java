

import cn.zmt.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.*;

/**
 * @author zmt
 * @date 2018/11/5 - 1:56
 */
public class TestFtp {
    String ftpHost = "47.107.135.170";
    String ftpUserName = "ftpuser";
    String ftpPassword = "mengyuan";
    int ftpPort = 21;

    /**
     * 上传
     */
    @Test
    public void up(){
        String ftpPath = "/images";//ftp路径
        String localPath = "h:/湖边小雨win7.jpg";//上传路径和文件名
        String fileName = "湖边小雨win7.jpg";//上传后的文件名
        String filePath = "/2018/11/06";
        try{
            FileInputStream in=new FileInputStream(new File(localPath));
            boolean test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath,filePath, fileName,in);
            System.out.println(test);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 下载
     */
    @Test
    public void down(){
        String ftpPath = "/images/";//ftp路径
        String localPath = "e:/";//下载路径
        String fileName = "ailisha.jpg";//文件名字
        //下载一个文件
        FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);
    }
}
