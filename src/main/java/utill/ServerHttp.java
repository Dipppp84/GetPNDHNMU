package utill;

import controller.DelPNDHandler;
import controller.PNDHandler;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.io.IOException;

//extends NanoHTTPD
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

        /*
    public ServerHttp(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.GET) {
            //String itemIdRequestParameter = session.getParameters().get("itemId").get(0);
            return newFixedLengthResponse("Requested itemId = ");
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT,
                "The requested resource does not exist");
    }*/

}
