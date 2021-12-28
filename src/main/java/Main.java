import DAO.DAO_PND;
import DAO.DaoFactory;
import entity.PND;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        List<PND> pndArrayList = DAO_PND.getDAO().getPND();
        for (PND pnd : pndArrayList)
            System.out.println(pnd);
    }
}
