package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SafeSpots {

    private int id;
    private String name;
    private int capacity;
    private String contact;
    private String latitude;
    private String longitude;
    private String address;
    private int state_id;
    private String ownership;
    private String details;

    public void setId(int id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setState_id(int state_id) {
        this.state_id = state_id;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public void setOwnership(String ownership) {
        this.ownership = ownership;
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
    public int getCapacity() {
        return capacity;
    }
    public String getContact() {
        return contact;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public int getState_id() {
        return state_id;
    }
    public String getDetails() {
        return details;
    }
    public String getOwnership() {
        return ownership;
    }

    public SafeSpots(){}

    public SafeSpots(int id, String name, int capacity, String address, String contact, String latitude, String longitude, int state_id , String ownership , String details){
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state_id = state_id;
        this.ownership = ownership;
        this.details = details;
    }

    public SafeSpots(int id, String name, int capacity, String latitude, String longitude, String contact, String address ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SafeSpots(int id , int capacity ){
        this.id = id;
        this.capacity = capacity;
    }

    public int saveSafespots (SafeSpots safespot, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO safespot ( name, capacity , address, contact, latitude, longitude, state_id, ownership, details ) VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setString(1,safespot.getName());
            pst.setInt(2,safespot.getCapacity());
            pst.setString(3,safespot.getAddress());
            pst.setString(4,safespot.getContact());
            pst.setString(5,safespot.getLatitude());
            pst.setString(6,safespot.getLongitude());
            pst.setInt(7,safespot.getState_id());
            pst.setString(8,safespot.getOwnership());
            pst.setString(9,safespot.getDetails());
            pst.execute();
            pst = con.prepareStatement("select last_insert_id() as last_id");
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                done = (rs.getInt("last_id"));
            }
            safespot.setId(done);
            pst = con.prepareStatement("select id from meraki.disaster_entry where stateId = ? and isOver = 0");
            pst.setInt(1,safespot.getState_id());
            rs = pst.executeQuery();
            if ( rs.next() == false){
                done = 1;
            }else {
                done = ( new Safespots_live().saveSafespot((new Safespots_live(safespot.getId(),safespot.getCapacity(),rs.getInt("id"))),null));
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

    public SafeSpots getSingleInfo (int safespot_id , Connection con ){
        SafeSpots newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,name,capacity,address,contact,latitude,longitude,state_id,ownership,details from meraki.safespot where id = ?");
            pst.setInt(1,safespot_id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new SafeSpots(rs.getInt("id"),rs.getString("name"),rs.getInt("capacity"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id"),rs.getString("ownership"),rs.getString("details")));
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

    public Collection<SafeSpots> getAll (Connection con ){
        Collection<SafeSpots> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,capacity,name,address,contact,latitude,longitude,state_id,ownership,details from meraki.safespot ");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new SafeSpots(rs.getInt("id"),rs.getString("name"),rs.getInt("capacity"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id"),rs.getString("ownership"),rs.getString("details")));
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

    public Collection<SafeSpots> getSafespotsByState (int stateId , Connection con ){
        Collection<SafeSpots> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,capacity,name,address,contact,latitude,longitude,state_id,ownership,details from meraki.safespot where state_id = ?");
            pst.setInt(1,stateId);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new SafeSpots(rs.getInt("id"),rs.getString("name"),rs.getInt("capacity"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id"),rs.getString("ownership"),rs.getString("details")));
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

    public Collection<SafeSpots> fillForLiveDisaster (int stateId , Connection con ){
        Collection<SafeSpots> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,capacity from meraki.safespot where state_id = ?");
            pst.setInt(1,stateId);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new SafeSpots(rs.getInt("id"),rs.getInt("capacity")));
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
