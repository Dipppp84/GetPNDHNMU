import DAO.DAO_PND;
import entity.PND;
import utill.GsonUtil;
import utill.ServerHttp;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        /*List<PND> pndArrayList = DAO_PND.getDAO().getPND();
        for (PND pnd : pndArrayList)
            System.out.println(GsonUtil.getToGson().getGson(pnd));*/

        new ServerHttp(8080);
    }
}
