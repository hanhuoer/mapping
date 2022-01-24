package club.scoder.app.mapping.server.service.impl;

import club.scoder.app.mapping.server.context.Client;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.ClientVO;
import club.scoder.app.mapping.server.service.IClientService;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceDefaultImpl implements IClientService {

    private final ServerContext serverContext;


    @Override
    public List<ClientVO> getClientList() {
        List<Client> clientList = serverContext.getClientList();
        return clientList.stream()
                .map(c -> BeanUtil.toBean(c, ClientVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void add(ClientVO clientVO) {
        if (clientVO == null) return;
        Client client = JSONObject.parseObject(JSONObject.toJSONString(clientVO), Client.class);
        serverContext.checkClientForAdd(client);
        serverContext.addClient(client);
        saveToFile(serverContext.getClientList());
    }

    @Override
    public void update(ClientVO clientVO) {
        if (clientVO == null) return;
        Client client = JSONObject.parseObject(JSONObject.toJSONString(clientVO), Client.class);
        serverContext.checkClientForUpdate(client);
        serverContext.updateClient(client);
        saveToFile(serverContext.getClientList());
    }

    @Override
    public void delete(String id) {
        Client client = serverContext.getClientById(id);
        if (client == null) {
            throw new RuntimeException("client is not exist.");
        }
        serverContext.deleteClient(client);
        saveToFile(serverContext.getClientList());
    }

    /**
     * refresh whole client's configuration to the file.
     *
     * @param clientList all client list
     */
    private void saveToFile(List<Client> clientList) {
        saveToFile(ServerContext.CLIENT_INFO_PATH, clientList);
    }

    private void saveToFile(String filepath, List<Client> clientList) {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filepath);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            String clientListJsonString = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(clientList);
            bufferedOutputStream.write(clientListJsonString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("error saving client configuration. message: {}", e.getMessage(), e);
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    log.error("error closing the stream for the client profile. message: {}", e.getMessage(), e);
                }
            }
        }
    }

}
