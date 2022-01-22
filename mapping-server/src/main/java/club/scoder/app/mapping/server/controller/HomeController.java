package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.server.model.vo.OverviewVO;
import club.scoder.app.mapping.server.model.vo.TrafficVO;
import club.scoder.app.mapping.server.service.HomeService;
import club.scoder.app.mapping.server.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final MonitorService monitorService;


    @GetMapping("overview")
    public Response<OverviewVO> overview() {
        return Response.success(homeService.getOverview());
    }

    @GetMapping("traffic")
    public Response<TrafficVO> traffic() {
        return Response.success(monitorService.getTraffic());
    }

}
