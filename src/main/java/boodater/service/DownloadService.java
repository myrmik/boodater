package boodater.service;

public interface DownloadService {
    String download(String url);
    void downloadToFile(String url, String filePath);
}
