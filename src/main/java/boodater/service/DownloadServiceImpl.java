package boodater.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class DownloadServiceImpl extends BaseService implements DownloadService {
    @Override
    public String download(String url) {
        String result = null;
        InputStream io = null;
        try {
            io = new URL(url).openStream();
            result = IOUtils.toString(io, "cp1251");
        } catch (IOException e) {
            log.error("Page was not opened: " + url, e);
        } finally {
            IOUtils.closeQuietly(io);
        }
        return result;
    }

    @Override
    public void downloadToFile(String url, String filePath) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new URL(url).openStream();
            os = new FileOutputStream(filePath);
            IOUtils.copy(is, os);
            log.debug("File was downloaded from '" + url + "' to '" + filePath + "'");
        } catch (IOException e) {
            log.error("Page was not opened or saved. Url: " + url + ", file path: " + filePath, e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

}
