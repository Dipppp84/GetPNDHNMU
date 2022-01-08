import DAO.DAO_PND;
import DAO.DAO_VirtGR;
import entity.PND;
import entity.ST;
import utill.GsonUtil;
import utill.ServerHttp;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        /*try {
            new ServerHttp(8089);
        } catch (BindException e){
            System.out.println(e.getLocalizedMessage());
            System.out.println("Уже запущен или занят порт");
        }*/
        ArrayList<ST> stArrayList = DAO_VirtGR.getDaoVirtGR().getListVirtGRbyAcademyGr(896);
    }
}
