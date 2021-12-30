package DAO;

import entity.PND;

import java.sql.*;
import java.util.ArrayList;
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
        ArrayList<PND> pndList = new ArrayList<>();
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            PreparedStatement ps1 = c1.prepareStatement("SELECT t.STUSPR0, STUSPR1, STUSPR2, STUSPR4,STUSPR5, STUSPR6 FROM STUSPR t ORDER BY STUSPR6 DESC");
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                String nameD = null, abvD = null;
                //Капаем до название дисциплины
                try (Connection c2 = DaoFactory.getDataSource().getConnection()) {
                    String STUSPR1 = rs1.getString("STUSPR1");// начало копания дисциплины -> US2 -> UO3 ->D2,D3
                    PreparedStatement ps2 = c2.prepareStatement("SELECT t.US2 FROM US t WHERE US1 = ?");
                    ps2.setString(1, STUSPR1);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        String US2 = rs2.getString("US2");
                        try (Connection c3 = DaoFactory.getDataSource().getConnection()) {
                            PreparedStatement ps3 = c3.prepareStatement("SELECT t.UO3 FROM UO t WHERE UO1 = ?");
                            ps3.setString(1, US2);
                            ResultSet rs3 = ps3.executeQuery();
                            while (rs3.next()) {
                                String UO3 = rs3.getString("UO3");
                                try (Connection c4 = DaoFactory.getDataSource().getConnection()) {
                                    PreparedStatement ps4 = c4.prepareStatement("SELECT t.D2, D3 FROM D t WHERE D1 = ?");
                                    ps4.setString(1, UO3);
                                    ResultSet rs4 = ps4.executeQuery();
                                    while (rs4.next()) {
                                        nameD = rs4.getString("D2");
                                        abvD = rs4.getString("D3");
                                    }//while (rs4.next())
                                }
                            }//(rs3.next())
                        }
                    }//while (rs2.next()) {
                }//Connection c2 = DaoFactory.getDataSource().getConnection()) Капаем до название дисциплины

                //Название группы
                String numberGroup = null;
                ;
                try (Connection c5 = DaoFactory.getDataSource().getConnection()) {
                    String STUSPR2 = rs1.getString("STUSPR2");
                    PreparedStatement ps5 = c5.prepareStatement("SELECT t.GR3, GR19, GR20, GR21, GR22, GR23, GR24 FROM GR t WHERE GR1 = ?");
                    ps5.setString(1, STUSPR2);
                    ResultSet rs5 = ps5.executeQuery();
                    while (rs5.next()) {
                        numberGroup = rs5.getString("GR3");
                        for (int i = 24; i > 18; i--) {
                            String ng = rs5.getString("GR" + i);
                            if (!ng.isEmpty())
                                numberGroup = ng;
                        }
                    }
                }// try (Connection c5 = DaoFactory.getDataSource().getConnection   Название группы

                String nameTeacher = null;
                //Ищем Преподавателя STUSPR5 -> p3,4,5
                try (Connection c6 = DaoFactory.getDataSource().getConnection()) {
                    String STUSPR5 = rs1.getString("STUSPR5");
                    PreparedStatement ps6 = c6.prepareStatement("SELECT t.P3, P4, P5 FROM P t WHERE P1 = ?");
                    ps6.setString(1, STUSPR5);
                    ResultSet rs6 = ps6.executeQuery();
                    while (rs6.next()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(rs6.getString("P3"));
                        String n = rs6.getString("P4");
                        if (!n.isEmpty())
                            sb.append(" " + n);
                        String sn = rs6.getString("P5");
                        if (!sn.isEmpty())
                            sb.append(" " + sn);
                        nameTeacher = sb.toString();
                    }
                }// try (Connection c6 = DaoFactory.getDataSource().getConnection   Ищем Преподавателя

                Integer id = rs1.getInt("STUSPR0");
                String numberPND = rs1.getString("STUSPR4"),
                        dateCreation = rs1.getString("STUSPR6");

                pndList.add(new PND(id, abvD, nameD, numberGroup, nameTeacher, numberPND, dateCreation));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pndList;
    }

    public boolean deletePND(Integer id){
        if (id == null)
            return false;
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            PreparedStatement ps1 = c1.prepareStatement("DELETE FROM STUSPR WHERE STUSPR0 = ?");
            ps1.setInt(1, id);
            ps1.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
