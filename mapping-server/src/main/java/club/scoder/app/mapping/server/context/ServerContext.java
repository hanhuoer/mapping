package club.scoder.app.mapping.server.context;

import club.scoder.app.mapping.common.Context;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServerContext implements Context, InitializingBean {

    private static final String RUNTIME_DIR = System.getProperty("user.dir");
    private static final String DEFAULT_CLIENT_INFO_PATH = RUNTIME_DIR + File.separator + "conf" + File.separator + "client.json";
    private static final String CLIENT_INFO_PATH;

    static {
        String clientJson = System.getProperty("client.json", null);
        CLIENT_INFO_PATH = Objects.requireNonNullElse(clientJson, DEFAULT_CLIENT_INFO_PATH);
    }

    /**
     * client list.
     */
    private final List<Client> clientList = Lists.newArrayList();
    /**
     * proxy port list.
     */
    private final List<Integer> proxyPortList = Lists.newArrayList();
    /**
     * key  : proxy port
     * value: client id
     */
    private final Map<Integer, String> proxyPortIdMap = Maps.newHashMap();
    /**
     * key  : proxy port
     * value: real server address
     */
    private final Map<Integer, InetSocketAddress> proxyPortClientInetMap = Maps.newHashMap();

    private final ServerConfiguration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        initClientInfo();
        reloadClientProxyInfo();
    }

    /**
     * Load client configuration from {@link ServerContext#CLIENT_INFO_PATH} file.
     */
    private void initClientInfo() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(CLIENT_INFO_PATH);
            byte[] bytes = fileInputStream.readAllBytes();
            if (bytes != null && bytes.length > 0) {
                Object parse = JSONObject.parse(bytes);
                if (parse == null) {
                    log.info("client conf is empty.");
                    return;
                }
                JSONArray jsonArray = (JSONArray) parse;
                if (jsonArray.size() == 0) {
                    log.info("client conf is empty.");
                    return;
                }
                List<Client> clients = jsonArray.toJavaList(Client.class);
                clientList.addAll(clients);
            }
        } catch (FileNotFoundException e) {
            log.error("client conf not found. {}", e.getMessage());
        } catch (IOException e) {
            log.error("client conf load failed. {}", e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("client conf close failed. {}", e.getMessage());
                }
            }
        }
    }

    /**
     * Reload the proxy meta info of the client after the client info changed.
     */
    private void reloadClientProxyInfo() {
        proxyPortList.clear();
        proxyPortIdMap.clear();
        for (Client client : clientList) {
            for (Proxy proxy : client.getProxyList()) {
                // update port list.
                proxyPortList.add(proxy.getServerPort());
                // update proxy port id map.
                proxyPortIdMap.put(proxy.getServerPort(), client.getId());
                // update mapping between proxy port and client
                proxyPortClientInetMap.put(proxy.getServerPort(), new InetSocketAddress(proxy.getClientHost(), proxy.getClientPort()));
            }
        }
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    public String getClientIdByProxyPort(int port) {
        return proxyPortIdMap.get(port);
    }

    public InetSocketAddress getClientInetByProxyPort(int port) {
        return proxyPortClientInetMap.get(port);
    }

    public List<Integer> getProxyPortList() {
        return proxyPortList;
    }

    public boolean auth(String clientId) {
        if (StringUtil.isNullOrEmpty(clientId)) return false;
        for (Client client : clientList) {
            if (clientId.contains(client.getId())) {
                return true;
            }
        }
        return false;
    }

}