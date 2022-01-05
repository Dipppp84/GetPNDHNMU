package DAO;

import entity.PND;
import utill.ServerHttp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO_PND {
    private volatile static DAO_PND dao_pnd;

    private DAO_PND() {
    }

    public static synchronized DAO_PND getDAO() {
        if (dao_pnd == null)
            dao_pnd = new DAO_PND();
        return dao_pnd;
    }

    public List<PND> getPND() {
        Date d1 = new Date();
        ArrayList<PND> pndList = new ArrayList<>();
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            System.out.print("get PND ");
            PreparedStatement ps1 = c1.prepareStatement("SELECT t6.P3,\n" +
                    "       P4,\n" +
                    "       P5,\n" +
                    "       t5.GR3,\n" +
                    "       GR19,\n" +
                    "       GR20,\n" +
                    "       GR21,\n" +
                    "       GR22,\n" +
                    "       GR23,\n" +
                    "       GR24,\n" +
                    "       t4.D2,\n" +
                    "       D3,\n" +
                    "       t1.STUSPR0,\n" +
                    "       STUSPR2,\n" +
                    "       STUSPR4,\n" +
                    "       STUSPR5,\n" +
                    "       STUSPR6\n" +
                    "FROM STUSPR t1\n" +
                    "         JOIN US as t2 on t1.STUSPR1 = t2.US1\n" +
                    "         JOIN UO as t3 on t2.US2 = t3.UO1\n" +
                    "         JOIN D as t4 on t3.UO3 = t4.D1\n" +
                    "         JOIN GR as t5 on t1.STUSPR2 = t5.GR1\n" +
                    "         JOIN P as t6 on t1.STUSPR5 = t6.P1\n" +
                    "ORDER BY STUSPR6 DESC\n");
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                //Название дисциплины
                String nameD = rs1.getString("D2"),
                        abvD = rs1.getString("D3");

                //Название группы
                String numberGroup = null;
                numberGroup = rs1.getString("GR3");
                for (int i = 24; i > 18; i--) {
                    String ng = rs1.getString("GR" + i);
                    if (!ng.isEmpty())
                        numberGroup = ng;
                }

                //Ищем Преподавателя STUSPR5 -> p3,4,5
                String nameTeacher = null;
                StringBuilder sb = new StringBuilder();
                sb.append(rs1.getString("P3"));
                String n = rs1.getString("P4");
                if (!n.isEmpty())
                    sb.append(" " + n);
                String sn = rs1.getString("P5");
                if (!sn.isEmpty())
                    sb.append(" " + sn);
                nameTeacher = sb.toString();

                Integer id = rs1.getInt("STUSPR0");
                String numberPND = rs1.getString("STUSPR4"),
                        dateCreation = rs1.getString("STUSPR6");

                pndList.add(new PND(id, abvD, nameD, numberGroup, nameTeacher, numberPND, dateCreation));
            }
            Date d2 = new Date();
            System.out.println(" / Затраченое время :" + (d2.getTime() - d1.getTime()) + "мс. ПНД Найдено :" + pndList.size());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return pndList;
    }

    public boolean deletePND(Integer id) {
        if (id == null)
            return false;
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            System.out.println("Удаление " + id);

            PreparedStatement ps1 = c1.prepareStatement("DELETE FROM STUSPR WHERE STUSPR0 = ?");
            ps1.setInt(1, id);
            ps1.execute();
            System.out.print(", удалено.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
