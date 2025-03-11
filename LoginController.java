
package lostfound;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;







public class LoginController implements Initializable {
    
    private Stage stage;
    private Scene scene;
   
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    
  
    
    
    public void registerUser(ActionEvent register) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("RegisterUI.fxml"));
         stage = (Stage)((Node)register.getSource()).getScene().getWindow();
         scene  = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
    public void loginUser(ActionEvent login) throws SQLException, IOException{
        String name = userName.getText().trim();
        String passwor = password.getText().trim();
         
        
        if(passwor.isEmpty() || name.isEmpty()){
            showAlert("Warning", "Please fill in all fields!");
        }else{
            try{
          boolean isLoggedIn = DataBaseConnection.loginUser(name, passwor);
          if(isLoggedIn){
              System.out.println(isLoggedIn);
              logIn(login);
          }else{
              showAlert("Invalid", "User not found");
          }
            }catch(IOException | SQLException e){
                e.printStackTrace();
            }
          
        }
        
    }
    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void logIn(ActionEvent login) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage2 = (Stage) ((Node) login.getSource()).getScene().getWindow();
        Scene scene2 = new Scene(root);
        stage2.setScene(scene2);
        stage2.show();
    
}

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userName.setOnKeyPressed(this::handleEnterKey);
        password.setOnKeyPressed(this::handleEnterKey);
    }
     private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                loginUser(new ActionEvent());  // Triggers login logic
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
  
}
