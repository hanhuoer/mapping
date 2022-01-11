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
import org.springframework.util.StringUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServerContext implements Context, InitializingBean {

    private static final String RUNTIME_DIR;
    private static final String DEFAULT_CLIENT_INFO_PATH;
    private static final String CLIENT_INFO_PATH;

    static {
        String mappingHome = System.getProperty("mapping.home");
        String userDir = System.getProperty("user.dir");
        RUNTIME_DIR = Objects.requireNonNullElse(mappingHome, userDir);
        DEFAULT_CLIENT_INFO_PATH = RUNTIME_DIR + File.separator + "conf" + File.separator + "client.json";

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

    private SSLContext sslContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        initClientInfo();
        reloadClientProxyInfo();
        initSSLContext();
    }

    private void initSSLContext() {
        if (!configuration.getSslEnable()) {
            log.info("ssl is not enable.");
        } else {
            log.info("ssl is enable.");
            String sslJksPath = configuration.getSslJksPath();
            log.info("initializing ssl context, jks path: {}", sslJksPath);
            if (!StringUtils.hasLength(sslJksPath)) {
                log.warn("the jks path is null or empty.");
                return;
            }
            String sslJksFilePath = RUNTIME_DIR + File.separator + sslJksPath;
            log.info("jks file path: {}", sslJksFilePath);

            // password
            String sslKeyStorePassword = configuration.getSslKeyStorePassword();
            if (!StringUtils.hasText(sslKeyStorePassword)) {
                log.info("key store password can not be null.");
                return;
            }
            String sslKeyManagerPassword = configuration.getSslKeyManagerPassword();
            if (!StringUtils.hasText(sslKeyStorePassword)) {
                log.info("the key store password can not be null.");
                return;
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(sslJksFilePath);
                SSLContext serverSSLContext = SSLContext.getInstance("TLSv1");
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fileInputStream, sslKeyStorePassword.toCharArray());

                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, sslKeyManagerPassword.toCharArray());

                // auth
                TrustManager[] trustManagers = null;
                if (configuration.getSslNeedsClientAuth()) {
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(keyStore);
                    trustManagers = trustManagerFactory.getTrustManagers();
                }

                serverSSLContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);
                sslContext = serverSSLContext;
                log.info("ssl initialized successfully.");
            } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException
                    | KeyManagementException | UnrecoverableKeyException e) {
                log.error("initialization failed; cause: {}, message: {}", e.getCause(), e.getMessage());
            }
        }
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

    public SSLContext getSslContext() {
        return sslContext;
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

    public Client getClientById(String id) {
        if (id == null) return null;
        for (Client client : clientList) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    public Client getClientByProxyPort(int port) {
        for (Client client : clientList) {
            List<Proxy> proxyList = client.getProxyList();
            if (proxyList != null) {
                for (Proxy proxy : proxyList) {
                    Integer serverPort = proxy.getServerPort();
                    if (serverPort.equals(port)) {
                        return client;
                    }
                }
            }
        }
        return null;
    }

    public synchronized void addClient(Client client) {
        if (client == null) return;
        clientList.add(client);
    }

    public synchronized void updateClient(Client client) {
        if (client == null) return;
        String id = client.getId();
        List<Client> newClientList = Lists.newArrayList();
        for (Client originalClient : clientList) {
            String originalId = originalClient.getId();
            if (id.equals(originalId)) {
                newClientList.add(client);
            } else {
                newClientList.add(originalClient);
            }
        }
        clientList.clear();
        clientList.addAll(newClientList);
    }

    public synchronized void deleteClient(Client client) {
        if (client == null) return;
        String id = client.getId();
        clientList.removeIf(c -> c.getId().equals(id));
    }

    /**
     * check given client.
     * 1. the client can not be null or empty.
     * 2. the client's id can not be null or empty.
     * 3. the client's proxy port can not be duplicate.
     * 4. the client's id can not be duplicate.
     * 5. the client's proxy port can not be duplicate with others.
     *
     * @param checkedClient checked client
     */
    public void checkClientForAdd(Client checkedClient) {
        checkClient(checkedClient, false);
    }

    /**
     * check a given client.
     * 1. the client can not be null or empty.
     * 2. the client's id can not be null or empty.
     * 3. the client's proxy port can not be duplicate.
     * 4. the client must be existed.
     * 5. the client's proxy port can not be duplicate with others.
     *
     * @param checkedClient checked client.
     */
    public void checkClientForUpdate(Client checkedClient) {
        checkClient(checkedClient, true);
    }

    private synchronized void checkClient(Client checkedClient, boolean isUpdate) {
        if (checkedClient == null) {
            throw new RuntimeException("client configuration must not be null or empty.");
        }
        if (!StringUtils.hasText(checkedClient.getId())) {
            throw new RuntimeException("client id must not be null or empty.");
        }

        List<Proxy> checkedProxyList = checkedClient.getProxyList();
        if (CollectionUtils.isEmpty(checkedProxyList)) {
            return;
        }
        // check server port and inet.
        for (Proxy proxy : checkedProxyList) {
            if (proxy.getServerPort() == null) {
                throw new RuntimeException("server port must not be null.");
            }
            if (!StringUtils.hasText(proxy.getClientHost())) {
                throw new RuntimeException("client host must not be null.");
            }
            if (proxy.getClientPort() == null) {
                throw new RuntimeException("client port must not be null.");
            }
        }

        // check duplicate.
        List<Integer> checkedServerPortList = checkedProxyList.stream()
                .map(Proxy::getServerPort)
                .collect(Collectors.toList());
        Set<Integer> checkedServerPortSet = checkedProxyList.stream()
                .map(Proxy::getServerPort)
                .collect(Collectors.toSet());
        if (checkedServerPortList.size() != checkedServerPortSet.size()) {
            throw new RuntimeException("client port must be unique.");
        }

        if (CollectionUtils.isEmpty(clientList)) {
            return;
        }

        // check if client exist.
        String id = checkedClient.getId();
        if (isUpdate) {
            // check for update.
            Client originalClient = this.clientList.stream()
                    .filter(o -> id.equals(o.getId()))
                    .findFirst()
                    .orElse(null);
            if (originalClient == null) {
                throw new RuntimeException("client is not exist.");
            }
        }

        // check server port if existed.
        for (Client client : clientList) {
            if (isUpdate) {
                if (client.getId().equals(id)) {
                    continue;
                }
            } else {
                // check id. (for add)
                if (id.equals(client.getId())) {
                    throw new RuntimeException("client id has already in use.");
                }
            }
            List<Proxy> originalProxyList = client.getProxyList();
            if (CollectionUtils.isEmpty(originalProxyList)) {
                continue;
            }
            for (Proxy proxy : originalProxyList) {
                Integer serverPort = proxy.getServerPort();
                if (serverPort == null) {
                    continue;
                }
                if (checkedServerPortList.contains(serverPort)) {
                    throw new RuntimeException(String.format("%s port already in use.", serverPort));
                }
            }
        }
    }
}