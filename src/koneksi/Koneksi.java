/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.PreparedStatement; //untuk menghandle error SQL
import java.sql.Connection; //untuk menyambung koneksi database kedriver
import java.sql.DriverManager; //untuk mempersiapkan query
import java.sql.ResultSet; //mempresentasikan sebuah hasil dhasilkan dari statement SQL SELECT.
import java.sql.SQLException; //untuk mengontrol ouput logging level
import java.util.logging.Level; //untuk mengontrol output logging logger
import java.util.logging.Logger; //untuk membuat type data BigDecimal
import javax.mail.MessagingException;
import javax.swing.JFrame;
import project.FormError;
import project.NotificationForm;

//import javax.swing.JPanel;

/**
 *
 * @author KAMALUDIN
 */
public class Koneksi {

    public static int insertToUser(String username, String name, String email, String password, String card) {
        try {
             //membuat koneksi ke database
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/bbs.db");
//            String tester = "SELECT * FROM userinfo WHERE username = '"+username+"'";
//            Statement stmt = con.createStatement();
//            
//            ResultSet rs = stmt.executeQuery(tester);
//            
//            if(!rs.next()){
//                JOptionPane.showMessageDialog(null,"Username already exist!");
//            }


               //string untuk query
            String sql = "INSERT INTO userinfo"
                                  +"(username, name, email, password, card)"
                                  +"VALUES(?,?,?,?,?)";
            //menyiapkan query untuk di eksekusi
            PreparedStatement ps = con.prepareStatement(sql);
            
            //mengeksekusi query
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, card);
            ps.executeUpdate();
            
            //JOptionPane.showMessageDialog(null, "Signing up successful!");
            JFrame notification = new NotificationForm();
            
            
        } catch (SQLException e) {
            //membuang error sql
            if(e.getErrorCode()==19) //Duplicate username
                return 19;
            
            JFrame errorOc = new FormError();
        }
        
        return 0;

    }
    
    public static boolean signer(String username, String pass){
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/bbs.db");
            String check = "SELECT username, password FROM userinfo WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(check);
            
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static void passRecovery(String username) throws MessagingException{
        String[] query= new String[2];
        
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:DBs/bbs.db");
            String check = "SELECT username, email, password FROM userinfo WHERE username=?";
            PreparedStatement ps = con.prepareStatement(check);
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
           if(rs.next()){
                query[0]=rs.getString("email");
                query[1]=rs.getString("password");
                
                LupaPasword.Send("blackbengalshopping","projectinjava",query[0],"Password Recovery","Your password: "+query[1]);
                
                NotificationForm nf = new NotificationForm();
                nf.jLabel7.setText("An email has been sent to "+query[0]);
               
           }
           else{
               FormError foo = new FormError();
               foo.errorMessage.setText("Username tidak ditemukan");
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}
