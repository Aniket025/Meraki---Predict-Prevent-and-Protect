package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Safespots_live {

    private int id;
    private int volunteer_p;
    private int volunteer_n;
    private int volunteer_a;
    private int capacity;
    private int people_p;
    private int disaster_id;
    private int safespot_id;

    public Safespots_live(){}

    public Safespots_live(int id,int volunteer_p, int volunteer_n, int volunteer_a, int capacity, int people_p, int disaster_id, int safespot_id ){
        this.id = id;
        this.disaster_id = disaster_id;
        this.volunteer_a = volunteer_a;
        this.volunteer_p = volunteer_p;
        this.volunteer_n = volunteer_n;
        this.capacity = capacity;
        this.people_p = people_p;
        this.safespot_id = safespot_id;
    }

    public Safespots_live(int id, int capacity, int disaster_id ){
        this.id = id;
        this.disaster_id = disaster_id;
        this.capacity = capacity;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDisaster_id(int disaster_id) {
        this.disaster_id = disaster_id;
    }
    public void setSafespot_id(int state_id) {
        this.safespot_id = safespot_id;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setPeople_p(int people_p) {
        this.people_p = people_p;
    }
    public void setVolunteer_a(int volunteer_a) {
        this.volunteer_a = volunteer_a;
    }
    public void setVolunteer_n(int volunteer_n) {
        this.volunteer_n = volunteer_n;
    }
    public void setVolunteer_p(int volunteer_p) {
        this.volunteer_p = volunteer_p;
    }

    public int getId() {
        return id;
    }
    public int getSafespot_id() {
        return safespot_id;
    }
    public int getDisaster_id() {
        return disaster_id;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getPeople_p() {
        return people_p;
    }
    public int getVolunteer_a() {
        return volunteer_a;
    }
    public int getVolunteer_n() {
        return volunteer_n;
    }
    public int getVolunteer_p() {
        return volunteer_p;
    }

    public int saveSafespot (Safespots_live safeSpot , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
                PreparedStatement pst = con.prepareStatement("INSERT INTO meraki.safespot_live ( safespot_id, capacity, disaster_id ) VALUES(?,?,?)");
                pst.setInt(1,safeSpot.getId());
                pst.setInt(2,safeSpot.getCapacity());
                pst.setInt(3,safeSpot.getDisaster_id());
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

    public Collection<Safespots_live> getAllStatusByDisaster (int check_id , Connection con ){
        Collection<Safespots_live> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,safespot_id,volunteers_present,volunteers_needed,capacity,people_present,disaster_id from meraki.safespot_live where disaster_id = ?");
            pst.setInt(1,check_id);
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Safespots_live(rs.getInt("id"),rs.getInt("volunteers_present"),rs.getInt("volunteers_needed"),(new Arriving().peopleArrivingCount(rs.getInt("id"),null)),rs.getInt("capacity"),rs.getInt("people_present"),disaster_id,rs.getInt("safespot_id")));
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

    public Safespots_live getSafespotLiveInfo (int check_id , Connection con ){
        Safespots_live newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,safespot_id,volunteers_present,volunteers_needed,capacity,people_present,disaster_id from meraki.safespot_live where id = ?");
            pst.setInt(1,check_id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new Safespots_live(rs.getInt("id"),rs.getInt("volunteers_present"),rs.getInt("volunteers_needed"),(new Arriving().peopleArrivingCount(check_id,null)),rs.getInt("capacity"),rs.getInt("people_present"),rs.getInt("disaster_id"),rs.getInt("safespot_id")));
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

    public int updateStatus (Safespots_live temp , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            String toUpdate = "update meraki.safespot_live set ";
            if ( 0 != temp.getPeople_p() ){
                toUpdate += "people_present = " + temp.getPeople_p() + ",";
            }
            if ( 0 != temp.getVolunteer_n() ){
                toUpdate += "volunteers_needed = " + temp.getVolunteer_n() + ",";
            }
            if ( 0 != temp.getVolunteer_p() ){
                toUpdate += "volunteers_present = " + temp.getVolunteer_p() + ",";
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
