package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Stores {

    private int id;
    private String name;
    private int type;
    private String contact;
    private String latitude;
    private String longitude;
    private String address;
    private int state_id;

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
    public void setType(int type) {
        this.type = type;
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

    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public int getType() {
        return type;
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

    public Stores(){}

    public Stores(int id, String name, int type, String address, String contact, String latitude, String longitude, int state_id ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state_id = state_id;
    }

    public Stores ( int id , int type ){
        this.id = id;
        this.type = type;
    }

    public int saveStores (Stores store, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO stores ( name, type , address, contact, latitude, longitude, state_id ) VALUES(?,?,?,?,?,?,?)");
            pst.setString(1,store.getName());
            pst.setInt(2,store.getType());
            pst.setString(3,store.getAddress());
            pst.setString(4,store.getContact());
            pst.setString(5,store.getLatitude());
            pst.setString(6,store.getLongitude());
            pst.setInt(7,store.getState_id());
            pst.execute();
            pst = con.prepareStatement("select last_insert_id() as last_id");
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                done = (rs.getInt("last_id"));
            }
            store.setId(done);
            pst = con.prepareStatement("select id from meraki.disaster_entry where stateId = ? and isOver = 0");
            pst.setInt(1,store.getState_id());
            rs = pst.executeQuery();
            if ( rs.next() == false){
                done = 1;
            }else {
                do{
                    done = ( new Stores_live().saveStore(rs.getInt("id"),store,null));
                } while ( rs.next());
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

    public Stores getSingleInfo (int store_id , Connection con ){
        Stores newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,name,type,address,contact,latitude,longitude,state_id from meraki.stores where id = ?");
            pst.setInt(1,store_id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new Stores(rs.getInt("id"),rs.getString("name"),rs.getInt("type"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id")));
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

    public Collection<Stores> getAll (int type, Connection con ){
        Collection<Stores> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,type,name,address,contact,latitude,longitude,state_id from meraki.stores ");
            if ( type == 1 )
                pst = con.prepareStatement("SELECT id,type,name,address,contact,latitude,longitude,state_id from meraki.stores where type = 1 ");
            else if ( type == 2 )
                pst = con.prepareStatement("SELECT id,type,name,address,contact,latitude,longitude,state_id from meraki.stores where type = 2 ");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Stores(rs.getInt("id"),rs.getString("name"),rs.getInt("type"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id")));
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

    public Collection<Stores> getStoresByState (int stateId , Connection con ){
        Collection<Stores> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,type,name,address,contact,latitude,longitude,state_id from meraki.stores where state_id = ?");
            pst.setInt(1,stateId);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Stores(rs.getInt("id"),rs.getString("name"),rs.getInt("type"),rs.getString("address"),rs.getString("contact"),rs.getString("latitude"),rs.getString("longitude"),rs.getInt("state_id")));
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

    public Collection<Stores> fillForLiveDisaster (int stateId , Connection con ){
        Collection<Stores> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,type from meraki.stores where state_id = ?");
            pst.setInt(1,stateId);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Stores(rs.getInt("id"),rs.getInt("type")));
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
