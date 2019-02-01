package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Arriving {

    private int id;
    private String name;
    private String contact;
    private int safespotlive;
    private String timestamp;
    private  int count;

    public String timePassed( Timestamp postedWhen ){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long diff = timestamp.getTime() - postedWhen.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60 - 30;
        String time = diffMinutes + " mins " + diffSeconds + " sec";
        return time;
    }

    public Arriving(){}

    public Arriving(int id, String name , String contact , Timestamp timestamp ){
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.timestamp = timePassed(timestamp);
    }

    public Arriving(String name , String contact , int safespotlive ){
        this.name = name;
        this.contact = contact;
        this.safespotlive = safespotlive;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setSafespotlive(int safespotlive) {
        this.safespotlive = safespotlive;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getName() { return  name; }
    public String getContact() { return  contact; }
    public int getSafespotlive() { return safespotlive; }
    public String getTimestamp() { return timestamp; }
    public int getCount() {
        return count;
    }

    public int saveArriving (Arriving arriving, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO arriving ( name , contact , safespotlive_id) VALUES(?,?,?)");
            pst.setString(1,arriving.getName());
            pst.setString(2,arriving.getContact());
            pst.setInt(3,arriving.getSafespotlive());
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

    public Collection<Arriving> peopleArriving (int safespotlive , Connection con ){

        Collection<Arriving> arriving = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,name,contact,timestamp from meraki.arriving where safespotlive_id = ? and timestamp > NOW() - INTERVAL 20 MINUTE");
            pst.setInt(1,safespotlive);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                arriving.add(new Arriving(rs.getInt("id"),rs.getString("name"),rs.getString("contact"),rs.getTimestamp("timestamp")));
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
        return arriving;

    }

    public int peopleArrivingCount (int safespotlive , Connection con ){

        int count = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT count(id) as people from meraki.arriving where safespotlive_id = ? and timestamp > NOW() - INTERVAL 20 MINUTE");
            pst.setInt(1,safespotlive);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                count = rs.getInt("people");
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
        return count;

    }

}
