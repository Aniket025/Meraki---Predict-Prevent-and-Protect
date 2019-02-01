package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Stores_live {

    private int id;
    private int store_id;
    private int type;
    private int upvotes;
    private int downvotes;
    private int disaster_id;
    private String last_responses;

    public Stores_live(){}

    public Stores_live(int id, int store_id, int type, int upvotes, int downvotes, int disaster_id, String last_responses){
        this.id = id;
        this.store_id = store_id;
        this.type = type;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.disaster_id = disaster_id;
        this.last_responses = last_responses;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDisaster_id(int disaster_id) {
        this.disaster_id = disaster_id;
    }
    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
    public void setLast_responses(String last_responses) {
        this.last_responses = last_responses;
    }
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getId() {
        return id;
    }
    public int getDisaster_id() {
        return disaster_id;
    }
    public int getDownvotes() {
        return downvotes;
    }
    public int getStore_id() {
        return store_id;
    }
    public int getType() {
        return type;
    }
    public int getUpvotes() {
        return upvotes;
    }
    public String getLast_responses() {
        return last_responses;
    }

    public int saveStores (int disaster_id , int state_id , Connection con ){
        int done = 0;
        Collection<Stores> allStores = (new Stores()).fillForLiveDisaster(state_id,null);;

        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            for ( Iterator<Stores> it = allStores.iterator() ; it.hasNext() ; ){
                PreparedStatement pst = con.prepareStatement("INSERT INTO store_disaster ( store_id, type, disaster_id ) VALUES(?,?,?)");
                Stores add = it.next();
                pst.setInt(1,add.getId());
                pst.setInt(2,add.getType());
                pst.setInt(3,disaster_id);
                pst.execute();
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

    public int saveStore (int disaster_id , Stores store , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
                PreparedStatement pst = con.prepareStatement("INSERT INTO store_disaster ( store_id, type, disaster_id ) VALUES(?,?,?)");
                pst.setInt(1,store.getId());
                pst.setInt(2,store.getType());
                pst.setInt(3,disaster_id);
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

    public Collection<Stores_live> getAllStatusByDisaster ( int check_id , Connection con ){
        Collection<Stores_live> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,store_id,type,upvotes,downvotes,last_responses from meraki.store_disaster where disaster_id = ?");
            pst.setInt(1,check_id);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Stores_live(rs.getInt("id"),rs.getInt("store_id"),rs.getInt("type"),rs.getInt("upvotes"),rs.getInt("downvotes"),disaster_id,rs.getString("last_responses")));
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

    public int updateStatus ( Stores_live temp , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("UPDATE meraki.store_disaster set upvotes = upvotes + 1, last_responses = concat(substr(last_responses,2),'Y') where id = ?");
            if ( temp.getDownvotes() == 1 ){
                pst = con.prepareStatement("UPDATE meraki.store_disaster set downvotes = downvotes + 1, last_responses = concat(substr(last_responses,2),'N') where id = ?");
            }
            pst.setInt(1,temp.getId());
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
