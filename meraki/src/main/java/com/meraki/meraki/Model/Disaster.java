package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Disaster {

    private int id;
    private String name;
    private int stateId;
    private String type;
    private Timestamp observed;
    private Timestamp expected;
    private String otherDetails;

    public Disaster(){}

    public Disaster ( String name, int stateId , String type , Timestamp observed , Timestamp expected , String otherDetails ){
        this.name = name;
        this.stateId = stateId;
        this.type = type;
        this.observed = observed;
        this.expected = expected;
        this.otherDetails = otherDetails;
    }

    public Disaster ( int id, String name , String type , Timestamp observed , Timestamp expected , String otherDetails ){
        this.id = id;
        this.name = name;
        this.type = type;
        this.observed = observed;
        this.expected = expected;
        this.otherDetails = otherDetails;
    }

    public Disaster ( int id, int stateId, String name , String type , Timestamp observed , Timestamp expected , String otherDetails ){
        this.id = id;
        this.stateId = stateId;
        this.name = name;
        this.type = type;
        this.observed = observed;
        this.expected = expected;
        this.otherDetails = otherDetails;
    }

    public int getId() {
        return id;
    }
    public int getStateId() {
        return stateId;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getOtherDetails() {
        return otherDetails;
    }
    public Timestamp getExpected() {
        return expected;
    }
    public Timestamp getObserved() {
        return observed;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setObserved(Timestamp observed) {
        this.observed = observed;
    }
    public void setExpected(Timestamp expected) {
        this.expected = expected;
    }
    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int saveDisaster (Disaster disaster, Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO disaster_entry ( name , stateId, type , observed_time , expected_time , other_details ) VALUES(?,?,?,?,?,?)");
            pst.setString(1,disaster.getName());
            pst.setInt(2,disaster.getStateId());
            pst.setString(3,disaster.getType());
            pst.setTimestamp(4,disaster.getObserved());
            pst.setTimestamp(5,disaster.getExpected());
            pst.setString(6,disaster.getOtherDetails());
            pst.execute();
            pst = con.prepareStatement("select last_insert_id() as last_id");
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                done = (rs.getInt("last_id"));
            }
            (new Stores_live()).saveStores(done,disaster.getStateId(),null);
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

    public Collection<Disaster> getDisastersByState ( int inStateId , Connection con ){
        Collection<Disaster> disasters = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("select id,name,type,observed_time,expected_time,other_details from meraki.disaster_entry where stateId = ? order by expected_time desc");
            pst.setInt(1,inStateId);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                disasters.add(new Disaster(rs.getInt("id"),rs.getString("name"),rs.getString("type"),rs.getTimestamp("observed_time"),rs.getTimestamp("expected_time"),rs.getString("other_details")));
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
        return disasters;
    }

    public Collection<Disaster> getAllDisasters ( Connection con ){
        Collection<Disaster> disasters = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("select id,name,stateId,type,observed_time,expected_time,other_details from meraki.disaster_entry order by expected_time desc");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                disasters.add(new Disaster(rs.getInt("id"),rs.getInt("stateId"),rs.getString("name"),rs.getString("type"),rs.getTimestamp("observed_time"),rs.getTimestamp("expected_time"),rs.getString("other_details")));
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
        return disasters;
    }

    public Disaster getDisasterInfo ( int disasterId , Connection con ){
        Disaster disasters = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("select id,name,stateId,type,observed_time,expected_time,other_details from meraki.disaster_entry where id = ?");
            pst.setInt(1,disasterId);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() )
                disasters = new Disaster(rs.getInt("id"),rs.getInt("stateId"),rs.getString("name"),rs.getString("type"),rs.getTimestamp("observed_time"),rs.getTimestamp("expected_time"),rs.getString("other_details"));

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
        return disasters;
    }

}
