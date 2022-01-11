package club.scoder.app.mapping.server.service;

import club.scoder.app.mapping.server.model.vo.ClientVO;

public interface IClientService {

    void add(ClientVO clientVO);

    void update(ClientVO clientVO);

    void delete(String id);

}
