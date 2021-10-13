/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KAMALUDIN
 */
public class Search {
    public static int laptop = 0;
    public static int mobile = 0;
    public static int partkomputer = 0;
    public static ArrayList<ProductList> mobileSearch(String model){
        ArrayList<ProductList> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/LaptopDB.db");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Laptop WHERE mbrand=? COLLATE NOCASE OR mmodel=? COLLATE NOCASE");
            ps.setString(1, model);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            
            ProductList pl, gl, kl=null;
            
            while(rs.next()){
                pl = new ProductList(rs.getString("mbrand"),rs.getString("mmodel"),
                        rs.getInt("mprice"),rs.getInt("mquantity"),rs.getString("mdescription"),
                        rs.getString("mphoto"));
                laptop++;
                
                list.add(pl);

            }
            con.close();
            
            con = DriverManager.getConnection("jdbc:sqlite:DBs/mobileDB.db");
            ps = con.prepareStatement("SELECT * FROM mobiles WHERE mbrand=? COLLATE NOCASE OR mmodel=? COLLATE NOCASE");
            ps.setString(1, model);
            ps.setString(2, model);
            rs = ps.executeQuery();

            
            while(rs.next()){
                gl = new ProductList(rs.getString("mbrand"),rs.getString("mmodel"),
                        rs.getInt("mprice"),rs.getInt("mquantity"),rs.getString("mdescription"),
                        rs.getString("mphoto"));
                mobile++;
                list.add(gl);

            }
            con.close();
            
            con = DriverManager.getConnection("jdbc:sqlite:DBs/PartKomputerDB.db");
            ps = con.prepareStatement("SELECT * FROM PartKomputer WHERE mbrand=? COLLATE NOCASE OR mmodel=? COLLATE NOCASE");
            ps.setString(1, model);
            ps.setString(2, model);
            rs = ps.executeQuery();

            
            while(rs.next()){
                kl = new ProductList(rs.getString("mbrand"),rs.getString("mmodel"),
                        rs.getInt("mprice"),rs.getInt("mquantity"),rs.getString("mdescription"),
                        rs.getString("mphoto"));
                partkomputer++;
                list.add(kl);

            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(MobileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
   }
    
}
