package com.atme.utils.my.oss;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * OSS服务.
 *
 * @author S
 * @version 1.0 2020/6/5
 * @since 1.0
 */
public interface OssService {

    /**
     * 上传文件.
     *
     * @param file
     * @return
     * @throws Exception
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 获取object url.
     *
     * @param fileName
     * @return
     */
    String getPreFix(String fileName);

    void download(String address, HttpServletResponse response);
}
