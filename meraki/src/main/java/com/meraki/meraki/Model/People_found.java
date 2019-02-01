package com.meraki.meraki.Model;

import com.meraki.meraki.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class People_found {

    private int id;
    private String address;
    private String contact;
    private String timestamp;

    public People_found(){}

    public People_found( int id, String address, String contact, Timestamp now , Timestamp posted ){
        this.id = id;
        this.address = address;
        this.contact = contact;
        this.timestamp = (new Utility()).timeDifference(now,posted);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getContact() {
        return contact;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public int saveFoundInfo (People_found found, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO found_people ( address , contact ) VALUES(?,?)");
            pst.setString(1,found.getAddress());
            pst.setString(2,found.getContact());
            pst.execute();
            pst = con.prepareStatement("select last_insert_id() as last_id");
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                done = (rs.getInt("last_id"));
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
        return done;
    }

    public People_found getSingleInfo (int found_id , Connection con ){
        People_found newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,address,contact,timestamp,NOW() as timeNow from meraki.found_people where id = ?");
            pst.setInt(1,found_id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new People_found(rs.getInt("id"),rs.getString("address"),rs.getString("contact"),rs.getTimestamp("timestamp"),rs.getTimestamp("timeNow")));
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

    public Collection<People_found> getAll ( Connection con ){
        Collection<People_found> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,address,contact,timestamp,NOW() as timeNow from meraki.found_people");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new People_found(rs.getInt("id"),rs.getString("address"),rs.getString("contact"),rs.getTimestamp("timestamp"),rs.getTimestamp("timeNow")));
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

}
