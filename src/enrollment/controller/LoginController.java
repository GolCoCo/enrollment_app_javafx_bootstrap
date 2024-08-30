package enrollment.controller;

import java.io.IOException;

import org.json.simple.JSONObject;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import enrollment.helper.*;

public class LoginController {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField pwdField;
    @FXML
    private Button loginButton;
    @FXML
    private Pane dashLeft, dashRight;

    public void initialize() {
    	
    	userField.setPromptText("Ex.: C999999");
    	userField.textProperty().addListener((obs, oldText, newText) -> {
            // do what you need with newText here, e.g.
    		loginButton.setDisable(newText.isEmpty());
        });
        pwdField.setText("123456");
        pwdField.requestFocus();
        loginButton.setDisable(true);
        Line line1 = new Line(0, 10, 144, 10);
        line1.setStroke(Color.GRAY);
        line1.getStrokeDashArray().addAll(3d, 5d);
        Line line2 = new Line(0, 10, 144, 10);
        line2.setStroke(Color.GRAY);
        line2.getStrokeDashArray().addAll(3d, 5d);
        dashLeft.getChildren().addAll(line1);
        dashRight.getChildren().addAll(line2);
    }
    @SuppressWarnings("unchecked")
	@FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IOException {
    	ApiHelper api = new ApiHelper();
    	JSONObject request = new JSONObject();
    	request.put("url", "login");
    	request.put("userID", userField.getText());
    	
    	JSONObject result = api.callAPI(request);
    	if (result == null) return;
    	if ((int)result.get("status") != 200) return;
    	HomeController.userInfo = (JSONObject) result.get("response");
    	
    	Parent root;
        try {
        	Timeline timeline = new Timeline();
        	
        	Scene scene = userField.getScene();
            KeyFrame key = new KeyFrame(Duration.millis(500),
                           new KeyValue (scene.getRoot().opacityProperty(), 0)); 
            timeline.getKeyFrames().add(key);   
            timeline.setOnFinished((ae) -> scene.getWindow().hide()); 
            timeline.play();
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/home.fxml"));
            root = loader.load();
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Stage stage = new Stage();
            stage.setTitle("Enrollment");
            stage.setScene(new Scene(root));
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }  
    }
    @FXML
    protected void handleCancelButtonAction(ActionEvent event) throws IOException {
    	System.exit(0);
    }
}
