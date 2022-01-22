package club.scoder.app.mapping.server.service;

import club.scoder.app.mapping.common.monitor.ByteTrafficHandler;
import club.scoder.app.mapping.common.monitor.Traffic;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.TrafficVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonitorService {

    private final ServerContext serverContext;


    public TrafficVO getTraffic() {
        ByteTrafficHandler byteTrafficHandler = serverContext.getByteTrafficHandler();
        Map<Integer, Traffic> trafficMap = byteTrafficHandler.getTrafficMap();
        AtomicLong in = new AtomicLong();
        AtomicLong out = new AtomicLong();
        AtomicLong inTimes = new AtomicLong();
        AtomicLong outTimes = new AtomicLong();
        trafficMap.forEach((k, v) -> {
            in.addAndGet(v.getIn().get());
            out.addAndGet(v.getOut().get());
            inTimes.addAndGet(v.getInTimes().get());
            outTimes.addAndGet(v.getOutTimes().get());
        });
        TrafficVO trafficVO = new TrafficVO();
        trafficVO.setIn(in);
        trafficVO.setOut(out);
        trafficVO.setInTimes(inTimes);
        trafficVO.setOutTimes(outTimes);
        return trafficVO;
    }

}
