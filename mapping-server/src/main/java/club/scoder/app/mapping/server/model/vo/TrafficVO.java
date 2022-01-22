package club.scoder.app.mapping.server.model.vo;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class TrafficVO {

    private AtomicLong in;
    private AtomicLong out;
    private AtomicLong inTimes;
    private AtomicLong outTimes;

}
