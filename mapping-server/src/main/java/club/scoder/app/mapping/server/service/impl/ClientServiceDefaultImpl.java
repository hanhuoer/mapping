package club.scoder.app.mapping.server.service.impl;

import club.scoder.app.mapping.server.context.Client;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.ClientVO;
import club.scoder.app.mapping.server.service.IClientService;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceDefaultImpl implements IClientService {

    private final ServerContext serverContext;


    @Override
    public void add(ClientVO clientVO) {
        if (clientVO == null) return;
        Client client = JSONObject.parseObject(JSONObject.toJSONString(clientVO), Client.class);
        serverContext.checkClientForAdd(client);
        serverContext.addClient(client);
    }

    @Override
    public void update(ClientVO clientVO) {
        if (clientVO == null) return;
        Client client = JSONObject.parseObject(JSONObject.toJSONString(clientVO), Client.class);
        serverContext.checkClientForUpdate(client);
        serverContext.updateClient(client);
    }

    @Override
    public void delete(String id) {
        Client client = serverContext.getClientById(id);
        if (client == null) {
            throw new RuntimeException("client is not exist.");
        }
        serverContext.deleteClient(client);
    }

}
