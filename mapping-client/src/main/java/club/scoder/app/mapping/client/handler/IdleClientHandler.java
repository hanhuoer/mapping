package club.scoder.app.mapping.client.handler;

import club.scoder.app.mapping.common.handler.IdleHandler;

public class IdleClientHandler extends IdleHandler {

    private static final int READER_IDLE_TIME_SECONDS = 60;
    private static final int WRITER_IDLE_TIME_SECONDS = 30;
    private static final int ALL_IDLE_TIME_SECONDS = 0;


    public IdleClientHandler() {
        super(READER_IDLE_TIME_SECONDS, WRITER_IDLE_TIME_SECONDS, ALL_IDLE_TIME_SECONDS);
    }

}
