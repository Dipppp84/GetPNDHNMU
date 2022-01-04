import DAO.DAO_PND;
import entity.PND;
import utill.GsonUtil;
import utill.ServerHttp;

import java.io.IOException;
import java.net.BindException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            new ServerHttp(8089);
        } catch (BindException e){
            System.out.println(e.getLocalizedMessage());
            System.out.println("Уже запущен или занят порт");
        }
    }
}
