package club.scoder.app.mapping.common.monitor;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Traffic {

    private int port;
    private AtomicLong in;
    private AtomicLong out;
    private AtomicLong inTimes;
    private AtomicLong outTimes;


    private Traffic() {

    }

    public static Traffic init(int port) {
        return init(port, 0L, 0L, 0L, 0L);
    }

    public static Traffic init(int port, long in, long out, long inTimes, long outTimes) {
        Traffic traffic = new Traffic();
        traffic.setPort(port);
        traffic.setIn(new AtomicLong(in));
        traffic.setOut(new AtomicLong(out));
        traffic.setInTimes(new AtomicLong(inTimes));
        traffic.setOutTimes(new AtomicLong(outTimes));
        return traffic;
    }

}
