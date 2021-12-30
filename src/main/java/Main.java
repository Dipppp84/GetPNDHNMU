import DAO.DAO_PND;
import entity.PND;
import utill.GsonUtil;
import utill.ServerHttp;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        new ServerHttp(8089);
    }
}
