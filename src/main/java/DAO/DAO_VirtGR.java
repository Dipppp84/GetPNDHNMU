package DAO;

import entity.PND;
import entity.ST;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DAO_VirtGR {
    private volatile static DAO_VirtGR daoVirtGR;

    private DAO_VirtGR() {
    }

    public static synchronized DAO_VirtGR getDaoVirtGR() {
        if (daoVirtGR == null)
            daoVirtGR = new DAO_VirtGR();
        return daoVirtGR;
    }


    public ArrayList<GR> getListAcademyGR(String nameGR) {
        Date d1 = new Date();
        ArrayList<GR> GRList = new ArrayList();
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            System.out.println("getListAcademyGR ");
            PreparedStatement ps1 = c1.prepareStatement("SELECT GR1, GR19, GR20, GR21, GR22, GR23, GR24\n" +
                    "  FROM GR t\n" +
                    "  WHERE GR24 = " + nameGR + " or GR23 = " + nameGR + " or GR22 = " + nameGR +
                    " or GR21 = " + nameGR + " or GR20 = " + nameGR + " or GR19 = " + nameGR + " or GR3 = " + nameGR);
            ResultSet rs1 = ps1.getResultSet();
            while (rs1.next()) {
                GRList.add(new GR(rs1.getInt("GR1"), rs1.getString("GR3"), rs1.getString("GR19"),
                        rs1.getString("GR20"), rs1.getString("GR21"), rs1.getString("GR22"),
                        rs1.getString("GR23"), rs1.getString("GR24")));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        Date d2 = new Date();
        System.out.println(" / Затраченое время :" + (d2.getTime() - d1.getTime()) + "мс. Груп найдено :" + GRList.size());
        return GRList;
    }

    public ArrayList<ST> getListVirtGRbyAcademyGr(int idGR) {
        Date d1 = new Date();
        ArrayList<ST> STList = new ArrayList();
        try (Connection c1 = DaoFactory.getDataSource().getConnection()) {
            System.out.print("get getListVirtGRbyAcademyGr ");
            //возвращает студентов в Академ группе
            PreparedStatement ps1 = c1.prepareStatement("" +
                    "SELECT t5.ST1, ST2, ST3, ST4\n" +
                    "FROM GR t1\n" +
                    "    JOIN UCGN as t2 on t1.GR1 = t2.UCGN2\n" +
                    "    JOIN UCGNS as t3 on t2.UCGN1 = t3.UCGNS2\n" +
                    "    JOIN UCSN as t4 on t3.UCGNS1 = t4.UCSN1\n" +
                    "    JOIN ST as t5 on t4.UCSN2 = t5.ST1\n" +
                    "WHERE t1.GR1 = ?");
            ps1.setInt(1, idGR);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                STList.add(new ST(rs1.getInt("ST1"), rs1.getString("ST3"), rs1.getString("ST4"),
                        rs1.getString("ST2")));
            }
            c1.close();

            for (final ST st : STList) {
                try (Connection c2 = DaoFactory.getDataSource().getConnection()) {
                    //возващает группы по ид студенту
                    //and UCXG3 = 0 - вирт групп 1 академ
                    //как понимает семестр?
                    PreparedStatement ps2 = c2.prepareStatement("" +
                            "SELECT t1.ST1, ST2, ST3, ST4, t5.GR3, t7.UCX2\n" +
                            "FROM ST t1\n" +
                            "JOIN UCSN as t2 on t1.ST1 = t2.UCSN2\n" +
                            "JOIN UCGNS as t3 on t2.UCSN1 = t3.UCGNS1\n" +
                            "JOIN UCGN as t4 on t3.UCGNS2 = t4.UCGN1\n" +
                            "JOIN GR as t5 on t4.UCGN2 = t5.GR1\n" +
                            "JOIN UCXG as t6 on t4.UCGN1 = t6.UCXG2\n" +
                            "JOIN UCX as t7 on t6.UCXG1 = t7.UCX1\n" +
                            "WHERE t1.ST1 = ? and UCXG3 = 0");
                    ps2.setInt(1, st.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        st.addVirtGR(rs2.getString("UCX2") + " " + rs2.getString("GR3"));
                    }
                }//try (Connection c2
            }//for (Integer id : idST )
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        Date d2 = new Date();
        System.out.println(" / Затраченое время :" + (d2.getTime() - d1.getTime()) + "мс. Студентов найдено :" + STList.size());
        return STList;
    }
}

class GR {
    private Integer id;
    private String nameGR, sem1, sem2, sem3, sem4, sem5, sem6;

    public GR(Integer id, String nameGR, String sem1, String sem2, String sem3, String sem4, String sem5, String sem6) {
        this.id = id;
        this.nameGR = nameGR;
        this.sem1 = sem1;
        this.sem2 = sem2;
        this.sem3 = sem3;
        this.sem4 = sem4;
        this.sem5 = sem5;
        this.sem6 = sem6;
    }

    @Override
    public String toString() {
        return "GR{" +
                "id=" + id +
                ", nameGR='" + nameGR + '\'' +
                ", sem1='" + sem1 + '\'' +
                ", sem2='" + sem2 + '\'' +
                ", sem3='" + sem3 + '\'' +
                ", sem4='" + sem4 + '\'' +
                ", sem5='" + sem5 + '\'' +
                ", sem6='" + sem6 + '\'' +
                '}';
    }
}