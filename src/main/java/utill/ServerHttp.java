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

    public static NanoHTTPD.Response getCors(NanoHTTPD.Response response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with, origin, " +
                "Content-Type, Accept");
        response.setMimeType("application/json");
        return response;
    }

}
