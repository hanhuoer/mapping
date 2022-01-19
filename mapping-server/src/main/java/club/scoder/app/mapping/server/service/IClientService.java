package club.scoder.app.mapping.server.service;

import club.scoder.app.mapping.server.model.vo.ClientVO;

import java.util.List;

public interface IClientService {

    List<ClientVO> getClientList();

    void add(ClientVO clientVO);

    void update(ClientVO clientVO);

    void delete(String id);
}
