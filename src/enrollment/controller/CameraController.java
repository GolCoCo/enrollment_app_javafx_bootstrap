package enrollment.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CameraController {
	
	@FXML
	private Label label1, label2, label3, label4, label5, label6;
	@FXML
	private Button btnCamera, btnFinger, btnFinish;
	@FXML
	private Region regionDark;
	@FXML
	private BorderPane paneFingerScanOK;
	@FXML
	private AnchorPane paneStringLog;
	@FXML
	private Label lblLog1, lblLog2, lblLog3, lblLog4, lblLog5, lblLog6, lblLog7, lblLog8, lblLog9, lblLog10, lblLog11; 
	@FXML
	private ImageView ivWebCam, ivCapture1, ivCapture2, ivCapture3;
	@FXML
	private BorderPane bpVideo;
	@FXML
	private Pane paneFaceRec, paneCameraDetectOK, paneCameraFinish;
	@FXML
	private Pane paneState1, paneState2, paneState3, paneState4, paneState5, paneState6, paneState7, paneState8, paneState9, paneState10;
	@FXML
	private Label lblState1, lblState2, lblState3, lblState4, lblState5, lblState6, lblState7, lblState8, lblState9, lblState10;
	
	public static Timeline clock3, clock4;
	private int logingNum = 0;
	private int faceNum = 0;
	
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private BufferedImage grabbedImage;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	private String capturedImage1 = "";
	private String capturedImage2 = "";
	private String capturedImage3 = "";
	
	public void initialize() {
    	
    	JSONObject info = HomeController.userInfo;
    	
    	String userName = "Olá, " + info.get("firstName") +" " + info.get("lastName");
    	label1.setText(userName);
    	
    	String userCode = info.get("userID") + " . " + info.get("unicode");
    	label2.setText(userCode);
    	
    	String code = "" + info.get("code");
    	
    	label3.setText(code);
    	
    	if (DashboardController.camera) {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-on");
    		label5.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnCamera.getStyleClass().clear();
    		btnCamera.getStyleClass().add("camera-btn-off");
    		label5.setStyle("-fx-text-fill: #DC3545");
    	}
    	
    	if (DashboardController.finger) {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-on");
    		label6.setStyle("-fx-text-fill: #343a40");
    	} else {
    		btnFinger.getStyleClass().clear();
    		btnFinger.getStyleClass().add("finger-btn-off");
    		label6.setStyle("-fx-text-fill: #DC3545");
    	}
    	
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> this.updateTime()), new KeyFrame(Duration.seconds(1)));
    	clock.setCycleCount(Animation.INDEFINITE);
    	clock.play();
    	
    	clock3 = new Timeline(new KeyFrame(Duration.ZERO, e -> this.changeFaceRing()), new KeyFrame(Duration.seconds(5)));
    	clock3.setCycleCount(Animation.INDEFINITE);
    	clock3.play();
    	
    	clock4 = new Timeline(new KeyFrame(Duration.ZERO, e -> this.runLogRecord()), new KeyFrame(Duration.seconds(5)));
    	clock4.setCycleCount(Animation.INDEFINITE);
    	clock4.play();
    	
    	btnFinish.setDisable(true);
    	
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				double height = ivWebCam.getScene().getWindow().getHeight();
				double width  = ivWebCam.getScene().getWindow().getWidth();
				ivWebCam.setFitHeight(height);
				ivWebCam.setFitWidth(width);
				ivWebCam.prefHeight(height);
				ivWebCam.prefWidth(width);
				ivWebCam.setPreserveRatio(true);
				ivWebCam.fitWidthProperty().bind(bpVideo.widthProperty());
		    	ivWebCam.fitHeightProperty().bind(bpVideo.heightProperty());
				initializeWebCam();
			}
		});
    	
    	setDeviceState(lblState1, paneState1, 74);
    	setDeviceState(lblState2, paneState2, 34);
    	setDeviceState(lblState3, paneState3, 76);
    	setDeviceState(lblState4, paneState4, 80);
    	setDeviceState(lblState5, paneState5, 20);
    	setDeviceState(lblState6, paneState6, 60);
    	setDeviceState(lblState7, paneState7, 90);
    	setDeviceState(lblState8, paneState8, 99);
    	setDeviceState(lblState9, paneState9, 40);
    	setDeviceState(lblState10, paneState10, 78);
	}
	
	protected void initializeWebCam() {
		
		Task<Void> webCamIntilizer = new Task<Void>() {

			@Override
			protected Void call() throws Exception {		
				if(selWebCam == null)
				{
					selWebCam = Webcam.getDefault();
					selWebCam.setViewSize(WebcamResolution.VGA.getSize());
					selWebCam.open();
				}else
				{
					closeCamera();
					selWebCam = Webcam.getDefault();
					selWebCam.setViewSize(WebcamResolution.VGA.getSize());
					selWebCam.open();
					
				}
				startWebCamStream();
				return null;
			}
			
		};
		
		new Thread(webCamIntilizer).start();
	}
	
protected void startWebCamStream() {
		
		stopCamera  = false;
		Task<Void> task = new Task<Void>() {

		
			@Override
			protected Void call() throws Exception {
				while (!stopCamera) {
					try {
						if ((grabbedImage = selWebCam.getImage()) != null) {
							
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									final Image mainiamge = SwingFXUtils
											.toFXImage(grabbedImage, null);
									imageProperty.set(mainiamge);
								}
							});
							
							
							grabbedImage.flush();
						}
					} catch (Exception e) {
						
					} finally {

					}
				}

				return null;

			}

		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		ivWebCam.imageProperty().bind(imageProperty);
		
	}
	
	private void closeCamera()
	{
		if(selWebCam != null)
		{
			selWebCam.close();
		}
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
    protected void handlFinishButtonAction(ActionEvent event) throws IOException {
		Parent root;
        try {
            
        	label1.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/dashboard.fxml"));
            root = loader.load();
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
    protected void handlResetButtonAction(ActionEvent event) throws IOException {
		capturedImage1 = capturedImage2 = capturedImage3 = "";
		btnFinish.setDisable(true);
		setImagetoImageView();
    }
	@FXML
	protected void handlResetCameraButtonAction(ActionEvent event) throws IOException {
//		initializeWebCam();
    }
	
	@FXML
    protected void handlCaptureButtonAction(ActionEvent event) throws IOException {
		
		int w = (int) ((int)selWebCam.getViewSize().width * 0.4);
		int h = (int) ((int)selWebCam.getViewSize().height * 0.8);
		int x = (int) ((int)selWebCam.getViewSize().width * 0.3);
		int y = (int) ((int)selWebCam.getViewSize().height * 0.1);
		
		BufferedImage image = selWebCam.getImage();
		BufferedImage subImage = image.getSubimage(x, y, w, h);
		
		String fileName = "enrollment/assets/captures/Capture" + getTimeStamp() + ".jpg";
		ImageIO.write(subImage, "jpg", new File(CameraController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + fileName));
		capturedImage3 = capturedImage2;
		capturedImage2 = capturedImage1;
		capturedImage1 = fileName;
		if (capturedImage3 != "")
			btnFinish.setDisable(false);
		setImagetoImageView();
    }
	
	private void setImagetoImageView() {
		if (capturedImage1 != "") {
			Image image = new Image(capturedImage1);
			ivCapture1.setImage(image);
		} else {
			ivCapture1.setImage(null);
		}
		if (capturedImage2 != "") {
			Image image = new Image(capturedImage2);
			ivCapture2.setImage(image);
		} else {
			ivCapture2.setImage(null);
		}
		if (capturedImage3 != "") {
			Image image = new Image(capturedImage3);
			ivCapture3.setImage(image);
		} else {
			ivCapture3.setImage(null);
		}
	}
	
	
	private void runLogRecord() {
		logingNum ++;
		lblLog11.setText(lblLog10.getText());
		lblLog10.setText(lblLog9.getText());
		lblLog9.setText(lblLog7.getText());
		lblLog8.setText(lblLog7.getText());
		lblLog7.setText(lblLog6.getText());
		lblLog6.setText(lblLog5.getText());
		lblLog5.setText(lblLog4.getText());
		lblLog4.setText(lblLog3.getText());
		lblLog3.setText(lblLog2.getText());
		lblLog2.setText(lblLog1.getText());
		
		switch (logingNum) {
		case 1:
			lblLog1.setText("Olhar para a câmera");
			break;
		case 2:
			lblLog1.setText("Manter os olhos abertos");
			break;
		case 3:
			lblLog1.setText("Olhar para a direita");
			break;
		case 4:
			lblLog1.setText("Remover óculos");
			break;
		case 5:
			lblLog1.setText("Mover o rosto para a esquerda");
			break;
		case 6:
			lblLog1.setText("Afastar o rosto da câmera");
			break;
		case 7:
			lblLog1.setText("Aproximar o rosto da câmera");
			break;
		case 8:
			lblLog1.setText("Não sorrir");
			break;
		case 9:
			lblLog1.setText("Remover objetos que obstruem a visão da face");
			break;
		default:
			logingNum -= 9;
			break;
		}
	}
	
	private void changeFaceRing() {
		paneFaceRec.getStyleClass().clear();
		switch (faceNum) {
		case 0:
			paneFaceRec.getStyleClass().add("back-face-bad");
			break;
		case 1:
			paneFaceRec.getStyleClass().add("back-face-normal");
			break;
		default:
			paneFaceRec.getStyleClass().add("back-face-good");
			regionDark.setVisible(true);
			paneCameraDetectOK.setVisible(true);
			clock3.stop();
			break;
		}
		faceNum = (faceNum + 1) % 3;
	}
	
	private void setDeviceState(Label lblState, Pane paneState, double value ) {
		if (value < 5) {
			lblState.setText("Ruim");
			lblState.setTextFill(Color.valueOf("#DC3545"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-0");
			return;
		}
		if (value < 15) {
			lblState.setText("Ruim");
			lblState.setTextFill(Color.valueOf("#DC3545"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-10");
			return;
		}
		if (value < 25) {
			lblState.setText("Ruim");
			lblState.setTextFill(Color.valueOf("#DC3545"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-20");
			return;
		}
		if (value < 35) {
			lblState.setText("Ruim");
			lblState.setTextFill(Color.valueOf("#DC3545"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-30");
			return;
		}
		if (value < 45) {
			lblState.setText("Médio");
			lblState.setTextFill(Color.valueOf("#DDE115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-40");
			return;
		}
		if (value < 55) {
			lblState.setText("Médio");
			lblState.setTextFill(Color.valueOf("#DDE115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-50");
			return;
		}
		if (value < 65) {
			lblState.setText("Médio");
			lblState.setTextFill(Color.valueOf("#DDE115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-60");
			return;
		}
		if (value < 75) {
			lblState.setText("Médio");
			lblState.setTextFill(Color.valueOf("#DDE115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-70");
			return;
		}
		if (value < 85) {
			lblState.setText("Bom");
			lblState.setTextFill(Color.valueOf("#36E115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-80");
			return;
		}
		if (value < 95) {
			lblState.setText("Bom");
			lblState.setTextFill(Color.valueOf("#36E115"));
			paneState.getStyleClass().clear();
			paneState.getStyleClass().add("icon-state-90");
			return;
		}
		lblState.setText("Bom");
		lblState.setTextFill(Color.valueOf("#36E115"));
		paneState.getStyleClass().clear();
		paneState.getStyleClass().add("icon-state-100");
	}
	
	@FXML
    protected void handleCameraDetectOKButtonAction(ActionEvent event) throws IOException {
		regionDark.setVisible(false);
		paneCameraDetectOK.setVisible(false);
		clock3.play();
    }
	@FXML
    protected void handleFinishButtonAction(ActionEvent event) throws IOException {
		clock3.stop();
		regionDark.setVisible(true);
		paneCameraFinish.setVisible(true);
    }
	
	@FXML
    protected void handleFinishOKButtonAction(ActionEvent event) throws IOException {
		DashboardController.cameraScaned = true;
		DashboardController.capturedImage = capturedImage1;
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
            loader.setLocation(getClass().getResource("../fxml/dashboard.fxml"));
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
    protected void handleGotoFingerButtonAction(ActionEvent event) throws IOException {
		DashboardController.cameraScaned = true;
		DashboardController.capturedImage = capturedImage1;
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
	
	private String getTimeStamp() {
		Date now = new Date();
		return "" + now.getTime();
	}
}
