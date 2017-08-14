package com.wanhuchina.common.util.zk;


import com.wanhuchina.common.util.security.EncryptUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ZookeeperPropertySupport extends PropertyPlaceholderConfigurer implements IZkDataListener, IZkChildListener, IZkStateListener {
    ZkClient zkClient = null;
    String appPath = null;

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyValue)) {
            return EncryptUtil.decrypt(propertyValue);
        }
        return propertyValue;
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        String zk = props.getProperty("zk");
        String app = props.getProperty("app");
        ZkPropertyUtil.set("zk", zk);
        ZkPropertyUtil.set("app", app);
        if (zk != null && app != null) {
            zkClient = new ZkClient(zk, 1000000, 1000000, new MyZkSerializer());
            appPath = "/wanhuchina/configs/" + app;
            if (zkClient.exists(appPath)) {
                List<String> configs = zkClient.getChildren(appPath);
                System.out.println("load properties from com.wanhuchina.common.util.zk:");
                for (String string : configs) {
                    String zkPath = appPath + "/" + string;
                    String zkValue = zkClient.readData(zkPath);
                    ZkPropertyUtil.set(string, zkValue);
                    props.setProperty(string, zkValue);
                    zkClient.subscribeDataChanges(zkPath, this);
                }
                zkClient.subscribeChildChanges(appPath, this);
            } else {
                System.out.println("node not exists,app=" + app);
            }
            zkClient.subscribeStateChanges(this);
        }
        System.out.println(props);
    }

    public void handleDataChange(String arg0, Object arg1) throws Exception {
        System.out.println("=====>handleDataChange," + arg0 + "--->" + arg1);
        ZkPropertyUtil.set(arg0.substring(arg0.lastIndexOf("/") + 1, arg0.length()), arg1 + "");
    }

    public void handleDataDeleted(String arg0) throws Exception {
        //删除属性会触发
        System.out.println("=====>handleDataDeleted," + arg0);
        ZkPropertyUtil.remove(getKeyFromPath(arg0));
        zkClient.unsubscribeDataChanges(arg0, this);
    }

    public void handleChildChange(String arg0, List<String> arg1)
            throws Exception {
        //添加或删除了属性
        System.out.println("=====>handleChildChange," + arg0 + "," + arg1);
        //不是当前项目下的变更，忽略
        if (!arg0.equals(appPath)) {
            return;
        }
        Set keys = ZkPropertyUtil.getAll().keySet();

        for (String string : arg1) {
            if (keys.contains(string)) {
                continue;
            }
            //只处理新增的
            String zkPath = arg0 + "/" + string;
            String zkValue = zkClient.readData(zkPath);
            ZkPropertyUtil.set(string, zkValue);
            zkClient.subscribeDataChanges(zkPath, this);
        }
    }

    public void handleNewSession() throws Exception {
        System.out.println("=====>handleNewSession");
    }

    public void handleSessionEstablishmentError(Throwable arg0)
            throws Exception {
        System.out.println("=====>handleSessionEstablishmentError");
    }

    @Override
    public void handleStateChanged(KeeperState state) throws Exception {
        System.out.println("=====>handleStateChanged," + state);
    }

    private String getKeyFromPath(String path) {
        return path.replace(appPath + "/", "");
    }

    /**
     * 是否是加密的属性
     * @param propertyValue
     * @return
     */
    private boolean isEncryptProp(String propertyValue) {
        //如果属性名称在列表中，并且属性的value是enc_开头的则证明是加密过的属性
        if (propertyValue.startsWith(EncryptUtil.ENCODE_PREFIX)) {
            return true;
        }
        return false;
    }
}
