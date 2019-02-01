package com.meraki.meraki.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Matched {

    private int id;
    private String found_id;
    private int isfound;
    private int isRelativeContacted;
    private Collection<People_found> found = new ArrayList<>( );

    public void setId(int id) {
        this.id = id;
    }
    public void setFound_id(String found_id) {
        this.found_id = found_id;
    }
    public void setIsfound(int isfound) {
        this.isfound = isfound;
    }
    public void setIsRelativeContacted(int isRelativeContacted) {
        this.isRelativeContacted = isRelativeContacted;
    }
    public void setFound( People_found foundNew ){ this.found.add(foundNew); }

    public int getId() {
        return id;
    }
    public String getFound_id() {
        return found_id;
    }
    public int getIsfound() {
        return isfound;
    }
    public int getIsRelativeContacted() {
        return isRelativeContacted;
    }
    public Collection<People_found> getFound() {
        return found;
    }

    public Matched(){}

    public Matched(int id, String found_id, int isfound , int isRelativeContacted ){
        this.id = id;
        this.found_id = found_id;
        this.isfound = isfound;
        this.isRelativeContacted = isRelativeContacted;
    }

    public int saveMatched ( int newId , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO matched_people ( id ) VALUES(?)");
            pst.setInt(1,newId);
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

    public int newMatched (Matched matched , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("update meraki.matched_people set isfound = 1, found_id = ? where id = ?");
            pst.setString(1,matched.getFound_id());
            pst.setInt(2,matched.getId());
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

    public int relativeContacted ( int contactedId , Connection con ){
        int done = 0;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("update meraki.matched_people set isRelativeContacted = 1 where id = ?");
            pst.setInt(1,contactedId);
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

    public Matched statusId (int newId, Connection con ){
        Matched newInfo = null;
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,found_id,isfound,isRelativeContacted from meraki.matched_people where id = ?");
            pst.setInt(1,newId);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ){
                newInfo = ( new Matched(rs.getInt("id"),rs.getString("found_id"),rs.getInt("isfound"),rs.getInt("isRelativeContacted")));
            }
            String[] ids = newInfo.getFound_id().split(",",0);
            for ( int i = 0 ; i<ids.length ; i++ ){
                newInfo.setFound((new People_found()).getSingleInfo(Integer.parseInt(ids[i]),null));
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

    public Collection<Matched> getAll (Connection con ){
        Collection<Matched> newInfo = new ArrayList<>();
        try {
            if ( con == null ){
                String url ="jdbc:mysql://meraki.mysql.database.azure.com:3306/meraki?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                con = DriverManager.getConnection(url, "meraki@meraki", "codefundo@123");
                //con = DriverManager.getConnection("jdbc:mysql:///meraki.mysql.database.azure.com:3306", "meraki@meraki", "codefundo123");
            }
            PreparedStatement pst = con.prepareStatement("SELECT id,found_id,isfound,isRelativeContacted from meraki.matched_people");
            ResultSet rs = pst.executeQuery();
            while ( rs.next() ){
                newInfo.add( new Matched(rs.getInt("id"),rs.getString("found_id"),rs.getInt("isfound"),rs.getInt("isRelativeContacted")));
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
