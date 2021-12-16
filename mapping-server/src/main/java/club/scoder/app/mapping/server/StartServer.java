package club.scoder.app.mapping.server;

import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.proxy.MappingServer;
import club.scoder.app.mapping.server.proxy.UserServer;

public class StartServer {

    public static void main(String[] args) {
        ServerContext serverContext = ServerContext.instance();
        MappingServer mappingServer = new MappingServer(serverContext);
        mappingServer.start();
        UserServer userServer = new UserServer(serverContext);
        userServer.start();
    }

}
