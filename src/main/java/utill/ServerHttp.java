package utill;

import controller.DelPNDHandler;
import controller.PNDHandler;
import entity.PND;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import lombok.SneakyThrows;

import java.io.IOException;

//extends   NanoHTTPD
public class ServerHttp extends RouterNanoHTTPD {
    public ServerHttp(int port) throws IOException {
        super(port);
        addMappings();
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public void addMappings() {
        setNotImplementedHandler(RouterNanoHTTPD.NotImplementedHandler.class);
        setNotFoundHandler(RouterNanoHTTPD.Error404UriHandler.class);
        addRoute("/PND", PNDHandler.class);
        addRoute("/delPND", DelPNDHandler.class);
    }

}
