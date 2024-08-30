package enrollment.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.json.simple.JSONObject;

import javafx.animation.Animation;
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
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController {
	
	@FXML
	private Label label1, label2, label3, label4, label5, label6;
	@FXML
	private Button btnCamera, btnFinger, btnFinish;
	@FXML
	private Pane imagePane;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private Region regionDark;
	@FXML
	private BorderPane paneCameraCheck, paneFingerCheck;
	@FXML
	private Pane paneLeftHand, paneRightHand, paneLeftFinger1, paneLeftFinger2, paneLeftFinger3, paneLeftFinger4, paneLeftFinger5, paneRightFinger1, paneRightFinger2, paneRightFinger3, paneRightFinger4, paneRightFinger5;
	
	public static boolean camera = false, finger = false;
	public static JSONObject personInfo = null;
	public static boolean fingerScaned = false, cameraScaned = false;
	public static boolean leftHand = false, rightHand = false;
	public static boolean leftFinger1 = false, leftFinger2 = false, leftFinger3 = false, leftFinger4 = false, leftFinger5 = false;
	public static boolean rightFinger1 = false, rightFinger2 = false, rightFinger3 = false, rightFinger4 = false, rightFinger5 = false;
	public static String capturedImage = "";

	public void initialize() {

    	JSONObject info = HomeController.userInfo;
    	
    	String userName = "Olá, " + info.get("firstName") +" " + info.get("lastName");
    	label1.setText(userName);
    	
    	String userCode = info.get("userID") + " . " + info.get("unicode");
    	label2.setText(userCode);
    	
    	String code = "" + info.get("code");
    	
    	label3.setText(code);
    	
    	this.updateTime();

    	if (camera) {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-on");
    		label5.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-off");
    		label5.setStyle("-fx-text-fill: #DC3545");
    	}
    	
    	if (finger) {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-on");
    		label6.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-off");
    		label6.setStyle("-fx-text-fill: #DC3545");
    	}
    	
    	btnFinish.setDisable(true);
    	if (cameraScaned)
    		btnFinish.setDisable(false);
    	if (fingerScaned)
    		btnFinish.setDisable(false);
    	
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> this.updateTime()), new KeyFrame(Duration.seconds(1)));
    	clock.setCycleCount(Animation.INDEFINITE);
    	clock.play();
    	setFingerResult();
    	setCameraResult();
	}
	
	private void setFingerResult() {
		if (fingerScaned == false) {
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("btn-left-hand");
			
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("btn-right-hand");
			
			paneLeftFinger1.getStyleClass().clear();
			paneLeftFinger1.getStyleClass().add("finger-result-unknown");
			
			paneLeftFinger2.getStyleClass().clear();
			paneLeftFinger2.getStyleClass().add("finger-result-unknown");
			
			paneLeftFinger3.getStyleClass().clear();
			paneLeftFinger3.getStyleClass().add("finger-result-unknown");
			
			paneLeftFinger4.getStyleClass().clear();
			paneLeftFinger4.getStyleClass().add("finger-result-unknown");
			
			paneLeftFinger5.getStyleClass().clear();
			paneLeftFinger5.getStyleClass().add("finger-result-unknown");
			
			paneRightFinger1.getStyleClass().clear();
			paneRightFinger1.getStyleClass().add("finger-result-unknown");
			
			paneRightFinger2.getStyleClass().clear();
			paneRightFinger2.getStyleClass().add("finger-result-unknown");
			
			paneRightFinger3.getStyleClass().clear();
			paneRightFinger3.getStyleClass().add("finger-result-unknown");
			
			paneRightFinger4.getStyleClass().clear();
			paneRightFinger4.getStyleClass().add("finger-result-unknown");
			
			paneRightFinger5.getStyleClass().clear();
			paneRightFinger5.getStyleClass().add("finger-result-unknown");
			
			return;
		}
		if (leftHand == true) {
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("btn-left-hand");
			if (leftFinger1 == true) {
				paneLeftFinger1.getStyleClass().clear();
				paneLeftFinger1.getStyleClass().add("finger-result-success");
			} else {
				paneLeftFinger1.getStyleClass().clear();
				paneLeftFinger1.getStyleClass().add("finger-result-fail");
			}
			if (leftFinger2 == true) {
				paneLeftFinger2.getStyleClass().clear();
				paneLeftFinger2.getStyleClass().add("finger-result-success");
			} else {
				paneLeftFinger2.getStyleClass().clear();
				paneLeftFinger2.getStyleClass().add("finger-result-fail");
			}
			if (leftFinger3 == true) {
				paneLeftFinger3.getStyleClass().clear();
				paneLeftFinger3.getStyleClass().add("finger-result-success");
			} else {
				paneLeftFinger3.getStyleClass().clear();
				paneLeftFinger3.getStyleClass().add("finger-result-fail");
			}
			if (leftFinger4 == true) {
				paneLeftFinger4.getStyleClass().clear();
				paneLeftFinger4.getStyleClass().add("finger-result-success");
			} else {
				paneLeftFinger4.getStyleClass().clear();
				paneLeftFinger4.getStyleClass().add("finger-result-fail");
			}
			if (leftFinger5 == true) {
				paneLeftFinger5.getStyleClass().clear();
				paneLeftFinger5.getStyleClass().add("finger-result-success");
			} else {
				paneLeftFinger5.getStyleClass().clear();
				paneLeftFinger5.getStyleClass().add("finger-result-fail");
			}
		} else {
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("btn-left-hand-fail");
			paneLeftFinger1.getStyleClass().clear();
			paneLeftFinger1.getStyleClass().add("finger-result-fail");
			
			paneLeftFinger2.getStyleClass().clear();
			paneLeftFinger2.getStyleClass().add("finger-result-fail");
			
			paneLeftFinger3.getStyleClass().clear();
			paneLeftFinger3.getStyleClass().add("finger-result-fail");
			
			paneLeftFinger4.getStyleClass().clear();
			paneLeftFinger4.getStyleClass().add("finger-result-fail");
			
			paneLeftFinger5.getStyleClass().clear();
			paneLeftFinger5.getStyleClass().add("finger-result-fail");
		}
		
		if (rightHand == true) {
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("btn-right-hand");
			if (rightFinger1 == true) {
				paneRightFinger1.getStyleClass().clear();
				paneRightFinger1.getStyleClass().add("finger-result-success");
			} else {
				paneRightFinger1.getStyleClass().clear();
				paneRightFinger1.getStyleClass().add("finger-result-fail");
			}
			if (rightFinger2 == true) {
				paneRightFinger2.getStyleClass().clear();
				paneRightFinger2.getStyleClass().add("finger-result-success");
			} else {
				paneRightFinger2.getStyleClass().clear();
				paneRightFinger2.getStyleClass().add("finger-result-fail");
			}
			if (rightFinger3 == true) {
				paneRightFinger3.getStyleClass().clear();
				paneRightFinger3.getStyleClass().add("finger-result-success");
			} else {
				paneRightFinger3.getStyleClass().clear();
				paneRightFinger3.getStyleClass().add("finger-result-fail");
			}
			if (rightFinger4 == true) {
				paneRightFinger4.getStyleClass().clear();
				paneRightFinger4.getStyleClass().add("finger-result-success");
			} else {
				paneRightFinger4.getStyleClass().clear();
				paneRightFinger4.getStyleClass().add("finger-result-fail");
			}
			if (rightFinger5 == true) {
				paneRightFinger5.getStyleClass().clear();
				paneRightFinger5.getStyleClass().add("finger-result-success");
			} else {
				paneRightFinger5.getStyleClass().clear();
				paneRightFinger5.getStyleClass().add("finger-result-fail");
			}
		} else {
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("btn-right-hand-fail");
			paneRightFinger1.getStyleClass().clear();
			paneRightFinger1.getStyleClass().add("finger-result-fail");
			
			paneRightFinger2.getStyleClass().clear();
			paneRightFinger2.getStyleClass().add("finger-result-fail");
			
			paneRightFinger3.getStyleClass().clear();
			paneRightFinger3.getStyleClass().add("finger-result-fail");
			
			paneRightFinger4.getStyleClass().clear();
			paneRightFinger4.getStyleClass().add("finger-result-fail");
			
			paneRightFinger5.getStyleClass().clear();
			paneRightFinger5.getStyleClass().add("finger-result-fail");
		}
	}
	
	private void setCameraResult(){
		Image image = null;
		if (cameraScaned == false) {
			image = new Image("enrollment/assets/resources/man.png");
		} else {
			image = new Image(capturedImage);
		}
		imgPhoto.setImage(image);
	}
	
	private void updateTime() {
		LocalDateTime now = LocalDateTime.now();
		long tot_sec = HomeController.started.until( now, ChronoUnit.SECONDS );
		int min = (int)tot_sec / 60;
    	int sec = (int)tot_sec % 60;
    	
    	String time = "";
    	if (min < 10) time += "0" + min;
    	else time += min;
    	
    	if (sec < 10) time += ":0" + sec;
    	else time += ":" + sec;
    	label4.setText(time);
	}
	
	@FXML
    protected void handleGotoFingerButtonAction(ActionEvent event) throws IOException {
		
		if (finger == false) {
			regionDark.setVisible(true);
			paneFingerCheck.setVisible(true);
			return;
		}
		
		Parent root;
        try {
        	Timeline timeline = new Timeline();
        	
        	Scene scene = btnCamera.getScene();
            KeyFrame key = new KeyFrame(Duration.millis(500),
                           new KeyValue (scene.getRoot().opacityProperty(), 0)); 
            timeline.getKeyFrames().add(key);   
            timeline.setOnFinished((ae) -> scene.getWindow().hide()); 
            timeline.play();
        	
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/finger.fxml"));
            root = loader.load();
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Stage stage = new Stage();
            stage.setTitle("Enrollment");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
	
	@FXML
    protected void handleFinishButtonAction(ActionEvent event) throws IOException {
		Parent root;
        try {
        	Timeline timeline = new Timeline();
        	
        	Scene scene = btnCamera.getScene();
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
            stage.show();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
	
	@FXML
    protected void handleGotoCameraButtonAction(ActionEvent event) throws IOException {
		
		if (camera == false) {
			regionDark.setVisible(true);
			paneCameraCheck.setVisible(true);
			return;
		}
		
		Parent root;
        try {
        	Timeline timeline = new Timeline();
        	
        	Scene scene = btnCamera.getScene();
            KeyFrame key = new KeyFrame(Duration.millis(500),
                           new KeyValue (scene.getRoot().opacityProperty(), 0)); 
            timeline.getKeyFrames().add(key);   
            timeline.setOnFinished((ae) -> scene.getWindow().hide()); 
            timeline.play();
        	
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/camera.fxml"));
            root = loader.load();
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Stage stage = new Stage();
            stage.setTitle("Enrollment");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }  
    }
	
	@FXML
    protected void handleCameraButtonAction(ActionEvent event) throws IOException {
		if (camera) return;
		regionDark.setVisible(true);
		paneCameraCheck.setVisible(true);
    }
	
	@FXML
    protected void handleFingerButtonAction(ActionEvent event) throws IOException {
		if (finger) return;
		regionDark.setVisible(true);
		paneFingerCheck.setVisible(true);
    }
	
	@FXML
    protected void handleCameraOKButtonAction(ActionEvent event) throws IOException {
		regionDark.setVisible(false);
		paneCameraCheck.setVisible(false);
		DashboardController.camera = true;
		btnCamera.getStyleClass().clear();
		btnCamera.getStyleClass().add("camera-btn-on");
		label5.setStyle("-fx-text-fill: #343a40");
    }
	
	@FXML
    protected void handleFingerOKButtonAction(ActionEvent event) throws IOException {
		regionDark.setVisible(false);
		paneFingerCheck.setVisible(false);
		DashboardController.finger = true;
		
		btnFinger.getStyleClass().clear();
		btnFinger.getStyleClass().add("finger-btn-on");
		label6.setStyle("-fx-text-fill: #343a40");
    }
}
