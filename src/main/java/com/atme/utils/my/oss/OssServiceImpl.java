package com.atme.utils.my.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;
import java.io.InputStream;
import java.util.UUID;

/**
 * OSS服务实现.
 *
 * @author S
 * @version 1.0 2020/6/5
 * @since 1.0
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String getPreFix(String fileName) {
        return ossProperties.getDomainName().concat(fileName);
    }

    //项目名 + uuid + 文件后缀名
    private String genFileName(String originFileName) {
        return ossProperties.getFilePath() + StringUtils.remove(UUID.randomUUID().toString(), "-")
                + StringUtils.substring(originFileName, originFileName.lastIndexOf("."));
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        @Cleanup InputStream in = file.getInputStream();
        String fileName = genFileName(file.getOriginalFilename());
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentType(file.getContentType());
        log.info(ossProperties.toString());
        ossClient.putObject(ossProperties.getBucketName(), fileName, in, meta);
        log.info("{},{},{}",ossProperties.getBucketName(), fileName, meta);
        return fileName;
    }

    @Override
    public void download(String address, HttpServletResponse response) {
        Request request = Request.Get(address);
        try {
            InputStream input = request.execute().returnResponse().getEntity().getContent();
            ServletOutputStream output = response.getOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

}
