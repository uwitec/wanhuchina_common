package com.wanhuchina.common.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * 为了方便配置redis sentinel模式
 */
public class KyRedisSentinelConfiguration extends RedisSentinelConfiguration {

    private static final Logger logger= LoggerFactory.getLogger(KyRedisSentinelConfiguration.class);

    /**
     * sentinel中master的名称
     */
    private String kyMasterName;

    /**
     * sentinel连接地址和端口，格式如下：
     * 10.10.1.12:26381,10.10.1.13:26382,......
     * 根据如上格式配置了，下面会根据规则进行切分，然后分批写入配置中
     */
    private String kySentinels;

    /**
     * 是否启用sentinel模式配置，1启用，0不启用
     */
    private boolean kyIsSentinel;

    public String getKyMasterName() {
        return kyMasterName;
    }

    public void setKyMasterName(String kyMasterName) {
        this.kyMasterName = kyMasterName;
        super.setMaster(kyMasterName);
        logger.debug("###redis采用sentinel模式连接#kyMasterName={}###",kyMasterName);
    }

    public String getKySentinels() {
        return kySentinels;
    }

    public void setKySentinels(String kySentinels) {
        this.kySentinels = kySentinels;
        logger.debug("###redis采用sentinel模式连接#kySentinels={}###",kySentinels);
        String[] kySentinelArr=kySentinels.split(",");
        Set<RedisNode> sentinels=new HashSet<>();
        for(String kySentinel:kySentinelArr){
            String[] hostAndPort=kySentinel.split(":");
            String host=hostAndPort[0];
            String port=hostAndPort[1];
            RedisNode redisNode=new RedisNode(host,Integer.parseInt(port));
            sentinels.add(redisNode);
        }
        super.setSentinels(sentinels);
    }

    public boolean getKyIsSentinel() {
        return kyIsSentinel;
    }

    public void setKyIsSentinel(boolean kyIsSentinel) {
        this.kyIsSentinel = kyIsSentinel;
    }
}
