package com.atme.utils.use;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ip转地址工具类.
 *
 * @author S
 * @version 1.0 2020/11/5
 * @since 1.0
 */
@Component
@Slf4j
public class Ip2RegionUtils {

    private static DbSearcher dbSearcher;


    @Value("${ip2region.path:/data/var/log/poseidon/ip2region.db}")
    private String path;

    @PostConstruct
    public void init() throws Exception {
        DbConfig config = new DbConfig();
        dbSearcher = new DbSearcher(config, path);
    }

    /**
     * ip转地区.
     *
     * @param ip
     * @return
     */
    public String ip2Region(String ip) {
        if (StringUtils.isBlank(ip)) {
            return "unknown";
        }

        if ("127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return "localhost";
        }

        try {
            DataBlock block = dbSearcher.btreeSearch(ip);
            String[] region = block.getRegion().split("\\|");
            return region[2].concat(region[3]);
        } catch (Exception e) {
            log.error("【ip转地址失败】ip:{},异常:{}", ip, e.getMessage());
            return "unknown";
        }
    }
}
