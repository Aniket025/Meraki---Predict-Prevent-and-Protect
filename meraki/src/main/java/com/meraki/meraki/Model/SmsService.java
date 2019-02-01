package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SmsService {

    private String contact;
    private String name;

    public SmsService(){}

    public SmsService(String contact, String name ){
        this.contact =contact;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public int saveService (SmsService service , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,isDeleted from meraki.messaging_service where name=? and contact_info=?");
            pst.setString(1,service.getName());
            pst.setString(2,service.getContact());
            ResultSet rs = pst.executeQuery();

            if ( rs.next() ){
                if ( rs.getInt("isDeleted") == 1 ){
                    pst = con.prepareStatement("UPDATE meraki.messaging_service set isDeleted = 0 where id = ? ");
                    pst.setInt(1,rs.getInt("id"));
                    done = 3;
                } else {
                    done = 2;
                }
            } else {
                pst = con.prepareStatement("INSERT INTO messaging_service ( name, contact_info ) VALUES(?,?)");
                pst.setString(1,service.getName());
                pst.setString(2,service.getContact());
                done = 1;
            }
            pst.execute();

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

    public Collection<SmsService> getAll ( Connection con ){
        Collection<SmsService> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT name,contact_info from meraki.messaging_service where isDeleted = 0");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new SmsService(rs.getString("name"),rs.getString("contact_info")));
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

    public int DeleteService ( SmsService service , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,isDeleted from meraki.messaging_service where name=? and contact_info=?");
            pst.setString(1,service.getName());
            pst.setString(2,service.getContact());
            ResultSet rs = pst.executeQuery();

            if ( rs.next() ){
                if ( rs.getInt("isDeleted") == 0 ){
                    pst = con.prepareStatement("UPDATE meraki.messaging_service set isDeleted = 1 where id = ? ");
                    pst.setInt(1,rs.getInt("id"));
                }
            }
            pst.execute();

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
