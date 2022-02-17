package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.server.service.MonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/")
@RequiredArgsConstructor
@Slf4j
public class MonitorController {

    private final MonitorService monitorService;

}
