package club.scoder.app.mapping.common.monitor;

import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ByteTrafficHandler {

    private ConcurrentHashMap<Integer, Traffic> concurrentPortTrafficMap = new ConcurrentHashMap<>();


    public void init(Set<Integer> ports) {
        if (!CollectionUtils.isEmpty(ports)) {
            for (Integer port : ports) {
                concurrentPortTrafficMap.put(port, Traffic.init(port));
            }
        }
    }

    public Traffic get(int port) {
        Traffic traffic = concurrentPortTrafficMap.get(port);
        if (traffic == null) {
            synchronized (this) {
                traffic = Traffic.init(port);
                concurrentPortTrafficMap.put(port, traffic);
            }
        }
        return traffic;
    }

    public void addIn(int port, long size) {
        Traffic traffic = get(port);
        traffic.getIn().addAndGet(size);
    }

    public void addOut(int port, long size) {
        Traffic traffic = get(port);
        traffic.getOut().addAndGet(size);
    }

    public void increaseIn(int port) {
        increaseIn(port, 1);
    }

    public void increaseIn(int port, long delta) {
        Traffic traffic = get(port);
        traffic.getInTimes().addAndGet(delta);
    }

    public void increaseOut(int port) {
        increaseOut(port, 1);
    }

    public void increaseOut(int port, long delta) {
        Traffic traffic = get(port);
        traffic.getOutTimes().addAndGet(delta);
    }

    public Map<Integer, Traffic> getTrafficMap() {
        return concurrentPortTrafficMap;
    }

}
