package enrollment.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;

import enrollment.helper.ApiHelper;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
	
	@FXML
	private Label label1, label2, label3, label4, label5, label6;
	@FXML
	private TextFlow tflow1;
	@FXML
	private TextField personIDField;
	@FXML
	private Button submitButton;
	@FXML
	private Button btnCamera, btnFinger;
	
	private boolean camera = false, finger = false;
	
	public static int elapsedTime = 0;
	public static LocalDateTime started = LocalDateTime.now();
	public static JSONObject userInfo = null;
	
	public void initialize() {
    	
    	
    	String userName = "Olá, " + userInfo.get("firstName") +" " + userInfo.get("lastName");
    	label1.setText(userName);
    	
    	String userCode = userInfo.get("userID") + " . " + userInfo.get("unicode");
    	label2.setText(userCode);
    	
    	String code = "" + userInfo.get("code");
    	
    	label3.setText(code);
    	
    	this.updateTime();
    	
    	
    	Text t1 = new Text("Para mais funcionalidades e informações, acesse:\r\n"
    			+ "https://biometria.caixa ou consulte o ");
    	Text t2 = new Text("MN AD178.");
    	t1.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057; -fx-fill: #495057");
    	t2.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057; -fx-fill: #495057; -fx-font-weight: bold");
    	tflow1.getChildren().addAll(t1, t2);
    	
    	camera = DashboardController.camera;
    	if (camera) {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-on");
    		label5.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-off");
    		label5.setStyle("-fx-text-fill: #DC3545");
    	}
    	
    	finger = DashboardController.finger;
    	
    	if (finger) {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-on");
    		label6.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-off");
    		label6.setStyle("-fx-text-fill: #DC3545");
    	}
    	submitButton.setDisable(true);
    	personIDField.textProperty().addListener((obs, oldText, newText) -> {
            // do what you need with newText here, e.g.
    		String str = personIDField.getText();
    		str = str.substring(str.length() - 1);
    		submitButton.setDisable(str.equals("_"));
        });
	}
	
	private void updateTime() {
		int min = (int)elapsedTime / 60;
    	int sec = (int)elapsedTime % 60;
    	
    	String time = "";
    	if (min < 10) time += "0" + min;
    	else time += min;
    	
    	if (sec < 10) time += ":0" + sec;
    	else time += ":" + sec;
    	label4.setText(time);
	}
	
	@SuppressWarnings("unchecked")
	@FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		ApiHelper api = new ApiHelper();
    	JSONObject request = new JSONObject();
    	request.put("url", "get-person-data");
    	request.put("person-id", personIDField.getText());
    	JSONObject result = api.callAPI(request);
    	if (result == null) return;
    	if ((int)result.get("status") != 200) return;
    	DashboardController.personInfo = (JSONObject) result.get("response");
    	
    	Parent root;
        try {
        	Timeline timeline = new Timeline();
        	
        	Scene scene = personIDField.getScene();
            KeyFrame key = new KeyFrame(Duration.millis(500),
                           new KeyValue (scene.getRoot().opacityProperty(), 0)); 
            timeline.getKeyFrames().add(key);   
            timeline.setOnFinished((ae) -> scene.getWindow().hide()); 
            timeline.play();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/dashboard.fxml"));
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
            HomeController.elapsedTime = 0;
            HomeController.started = LocalDateTime.now();
            DashboardController.cameraScaned = false;
            DashboardController.fingerScaned = false;
        }
        catch (IOException e) {
        	e.printStackTrace();
        }  
    }
}
