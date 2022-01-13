package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.ClientVO;
import club.scoder.app.mapping.server.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final ServerContext serverContext;


    @PostMapping("add")
    public Object add(@RequestBody ClientVO clientVO) {
        clientService.add(clientVO);
        return System.currentTimeMillis();
    }

    @PostMapping("update")
    public Object update(@RequestBody ClientVO clientVO) {
        clientService.update(clientVO);
        return System.currentTimeMillis();
    }

    @PostMapping("delete")
    public Object add(@RequestParam String id) {
        clientService.delete(id);
        return System.currentTimeMillis();
    }

    @GetMapping("refresh")
    public Object refresh() {
        serverContext.refresh();
        return System.currentTimeMillis();
    }

}
