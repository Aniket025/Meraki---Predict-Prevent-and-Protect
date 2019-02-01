package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Safespots_req {

    private int id;
    private int safespotlive_id;
    private String meds;
    private int food;
    private String others;
    private int Mdone;
    private int Fdone;
    private int Odone;
    private SafeSpots temp;

    public Safespots_req(){}

    public Safespots_req(int id, int safespotlive_id, String meds, int food, String others, int Mdone, int Fdone, int Odone ){
        this.id = id;
        this.safespotlive_id = safespotlive_id;
        this.meds = meds;
        this.food = food;
        this.others = others;
        this.Mdone = Mdone;
        this.Fdone = Fdone;
        this.Odone = Odone;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFdone(int fdone) {
        Fdone = fdone;
    }
    public void setFood(int food) {
        this.food = food;
    }
    public void setMdone(int mdone) {
        Mdone = mdone;
    }
    public void setMeds(String meds) {
        this.meds = meds;
    }
    public void setOdone(int odone) {
        Odone = odone;
    }
    public void setOthers(String others) {
        this.others = others;
    }
    public void setSafespotlive_id(int safespotlive_id) {
        this.safespotlive_id = safespotlive_id;
    }
    public void setTemp ( SafeSpots temp ){ this.temp = temp; }

    public int getFdone() {
        return Fdone;
    }
    public int getMdone() {
        return Mdone;
    }
    public int getOdone() {
        return Odone;
    }
    public int getSafespotlive_id() {
        return safespotlive_id;
    }
    public int getFood() {
        return food;
    }
    public String getMeds() {
        return meds;
    }
    public String getOthers() {
        return others;
    }
    public int getId() {
        return id;
    }
    public SafeSpots getTemp() {
        return temp;
    }

    public int saveRequest (Safespots_req req , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
                PreparedStatement pst = con.prepareStatement("INSERT INTO safespot_requirement ( safespotlive_id, meds, food, others ) VALUES(?,?,?,?)");
                pst.setInt(1,req.getId());
                pst.setString(2,req.getMeds());
                pst.setInt(3,req.getFood());
                pst.setString(4,req.getOthers());
                pst.execute();
                done = 1;

        } catch (Exception ex ){
            System.out.println(ex);
        } finally {
            if ( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return done;
    }

    public Collection<Safespots_req> getAllRequestsByDisaster (int check_id , Connection con ){
        Collection<Safespots_req> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT safespot_live.id,name,safespot.capacity,safespot_id,latitude,longitude,contact,address,safespot_id,meds,food,others,medsDone,foodDone,othersDone from meraki.safespot_live inner join meraki.safespot on safespot_live.safespot_id = safespot.id inner join meraki.safespot_requirement on safespot_requirement.safespotlive_id = safespot_live.id where disaster_id = ?");
            pst.setInt(1,check_id);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                Safespots_req temp = new Safespots_req(rs.getInt("id"),rs.getInt("safespot_live.id"),rs.getString("meds"),rs.getInt("food"),rs.getString("others"),rs.getInt("medsDone"),rs.getInt("foodDone"),rs.getInt("othersDone"));
                temp.setTemp((new SafeSpots(rs.getInt("safespot_id"),rs.getString("name"),rs.getInt("safespot.capacity"),rs.getString("latitude"),rs.getString("longitude"),rs.getString("contact"),rs.getString("address"))));
                newInfo.add( temp );
            }

        } catch (Exception ex ){
            System.out.println(ex);
        } finally {
            if ( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newInfo;
    }

    public int updateStatus (Safespots_req temp , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            String toUpdate = "update meraki.safespot_requirement set ";
            if ( 0 != temp.getMdone() ){
                toUpdate += " medsDone =  1,";
            }
            if ( 0 != temp.getFdone() ){
                toUpdate += " foodDone = 1,";
            }
            if ( 0 != temp.getOdone() ){
                toUpdate += " othersDone = 1,";
            }
            toUpdate = toUpdate.substring(0,toUpdate.length() - 1);
            toUpdate+= " where id = " + temp.getId();
            PreparedStatement pst = con.prepareStatement(toUpdate);
            pst.execute();
            done = 1;

        } catch (Exception ex ){
            System.out.println(ex);
        } finally {
            if ( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return done;
    }

}
