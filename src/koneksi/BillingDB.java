/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection; //untuk menyambung koneksi database kedriver
import java.sql.DriverManager; //untuk mempersiapkan query
import java.sql.PreparedStatement; //untuk menghandle error SQL
import java.sql.ResultSet; //mempresentasikan sebuah hasil dhasilkan dari statement SQL SELECT.
import java.sql.SQLException; //untuk mengontrol ouput logging level
import java.sql.Statement; //mempresentasikan suatu perintah SQL, dan dapat digunakan untuk menerima objek ResultSet.
import java.util.ArrayList; //menambah data baru secara dinamis tanpa harus menentukan ukurannya di awal
import java.util.logging.Level; //untuk mengontrol output logging logger
import java.util.logging.Logger; //untuk membuat type data BigDecimal
import javax.swing.JOptionPane; //untuk menampilkan dialog

/**
 *
 * @author KAMALUDIN
 */
public class BillingDB {
    
    public static void insertIntoBillingDB(String username, int price, String date){
        try {
            //membuat koneksi ke database
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/billingDB.db");
            //mengambil query
            PreparedStatement ps = con.prepareStatement("INSERT INTO billing(uname, bill, date) VALUES(?,?,?)");
            //mengeksekusi query
            ps.setString(1, username);
            ps.setInt(2, price);
            ps.setString(3, date);
            ps.executeUpdate();

        } catch (SQLException ex) {
            //membuang error sql
            Logger.getLogger(MobileDB.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }
    
    public static ArrayList<BillObject> billlings(){
        ArrayList<BillObject> customers = new ArrayList<>();
      
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/billingDB.db");
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("SELECT id, uname, bill, date FROM billing");
            
            BillObject pl;
            
            while(rs.next()){
                pl = new BillObject(rs.getInt("id"),rs.getString("uname"),
                        rs.getInt("bill"),rs.getString("date"));
                customers.add(pl);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MobileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }
    
    //Deleting billing log
    public static void deleteBillings(){
         try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/billingDB.db");
            PreparedStatement ps = con.prepareStatement("DELETE FROM billing");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "All entries have been deleted!");
            
        } catch (SQLException ex) {
            Logger.getLogger(LaptopDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
