package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Missing {

    private int id;
    private String name;
    private String address;
    private String contact1;
    private String contact2;
    private int height;
    private String other;

    public void setId(int id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }
    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setOther(String other) {
        this.other = other;
    }

    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public int getHeight() {
        return height;
    }
    public String getContact1() {
        return contact1;
    }
    public String getContact2() {
        return contact2;
    }
    public String getOther() {
        return other;
    }

    public Missing(){}

    public Missing(int id, String name, String address, String contact1, String contact2 , int height , String other ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.other = other;
        this.height = height;
    }

    public int saveMissingInfo (Missing missing, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO missing_people ( name, address, contact1, contact2, height, other ) VALUES(?,?,?,?,?,?)");
            pst.setString(1,missing.getName());
            pst.setString(2,missing.getAddress());
            pst.setString(3,missing.getContact1());
            pst.setString(4,missing.getContact2());
            pst.setInt(5,missing.getHeight());
            pst.setString(6,missing.getOther());
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

    public Missing getSingleInfo (int missing_id , Connection con ){
        Missing newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,name,address,contact1,contact2,height,other from meraki.missing_people where id = ?");
            pst.setInt(1,missing_id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new Missing(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("contact1"),rs.getString("contact2"),rs.getInt("height"),rs.getString("other")));
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

    public Collection<Missing> getAll (Connection con ){
        Collection<Missing> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,name,address,contact1,contact2,height,other from meraki.missing_people ");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Missing(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("contact1"),rs.getString("contact2"),rs.getInt("height"),rs.getString("other")));
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
