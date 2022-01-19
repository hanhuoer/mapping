package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.ClientVO;
import club.scoder.app.mapping.server.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final ServerContext serverContext;


    @GetMapping("list")
    public Response<List<ClientVO>> list() {
        return Response.success(clientService.getClientList());
    }

    @PostMapping("add")
    public Response<Void> add(@RequestBody ClientVO clientVO) {
        clientService.add(clientVO);
        return Response.success();
    }

    @PostMapping("update")
    public Response<Void> update(@RequestBody ClientVO clientVO) {
        clientService.update(clientVO);
        return Response.success();
    }

    @PostMapping("delete")
    public Response<Void> add(String id) {
        clientService.delete(id);
        return Response.success();
    }

    @PostMapping("refresh")
    public Response<Void> refresh() {
        serverContext.refresh();
        return Response.success();
    }

}
