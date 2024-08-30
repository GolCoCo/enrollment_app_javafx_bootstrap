package enrollment.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.json.simple.JSONObject;

import enrollment.helper.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class FingerController {
	
	@FXML
	private Label label1, label2, label3, label4, label5, label6;
	@FXML
	private Button btnCamera, btnFinger, btnSkipRightHand, btnSkipLeftHand, btnFinish;
	@FXML
	private Pane paneRightFinger1, paneRightFinger2, paneRightFinger3, paneRightFinger4, paneRightFinger5;
	@FXML
	private Pane paneLeftFinger1, paneLeftFinger2, paneLeftFinger3, paneLeftFinger4, paneLeftFinger5;
	@FXML
	private Pane paneRightClose1, paneRightClose2, paneRightClose3, paneRightClose4, paneRightClose5;
	@FXML
	private Pane paneLeftClose1, paneLeftClose2, paneLeftClose3, paneLeftClose4, paneLeftClose5;
	@FXML
	private Button optSkip1, optSkip2, optSkip3, optSkip4, optSkip5, optSkip6, optSkip7, optSkip8;
	@FXML
	private Region regionDark;
	@FXML
	private BorderPane paneFingerScanOK;
	@FXML
	private BorderPane paneLeftHand, paneRightHand;
	@FXML
	private BorderPane paneLeftHandTop, paneRightHandTop;
	@FXML
	private AnchorPane paneStringLog;
	@FXML
	private Label lblLog1, lblLog2, lblLog3, lblLog4, lblLog5, lblLog6, lblLog7, lblLog8, lblLog9, lblLog10, lblLog11; 
	@FXML
	private ImageView ivCaptured;
	private ApiHelper api = new ApiHelper();
	
	public static int scanningFinger = 0;
	public static int mdlState = 1;
	public static int skipped = 0;
	public static int leftSkipped = 0;
	public static int rightSkipped = 0;
	public static Timeline clock1, clock2, clock3, clock4;
	private Pane curPane;
	private Pane curPaneClose;
	private int logingNum = 0;
	
	
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
    	
    	paneLeftClose1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneLeftClose2.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneLeftClose3.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneLeftClose4.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneLeftClose5.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneRightClose1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneRightClose2.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneRightClose3.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneRightClose4.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	paneRightClose5.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	mdlState = 1;
    	    	showSkipFingerMdl();
    	    }
    	});
    	
    	
    	scanningFinger = 0;
    	skipped = 0;
    	clock1 = new Timeline(new KeyFrame(Duration.ZERO, e -> this.scanFinger()), new KeyFrame(Duration.seconds(5)));
    	clock1.setCycleCount(Animation.INDEFINITE);
    	clock1.play();
    	
    	clock2 = new Timeline(new KeyFrame(Duration.millis(3500), e -> this.delayBlocked()), new KeyFrame(Duration.seconds(5)));
    	clock2.setCycleCount(Animation.INDEFINITE);
    	clock2.play();
    	
    	clock3 = new Timeline(new KeyFrame(Duration.ZERO, e -> this.skipScanning()), new KeyFrame(Duration.millis(200)));
    	clock3.setCycleCount(Animation.INDEFINITE);
    	clock3.play();
    	
    	clock4 = new Timeline(new KeyFrame(Duration.ZERO, e -> this.runLogRecord()), new KeyFrame(Duration.seconds(5)));
    	clock4.setCycleCount(Animation.INDEFINITE);
    	clock4.play();
    	logingNum = 0;
    	
    	curPane = paneLeftFinger1;
    	curPaneClose = paneLeftClose1;
    	btnFinish.setDisable(true);
    	this.runLogRecord();
    	clearScanState();
    	
    	setCameraResult();
    	
	}
	
	private void setCameraResult(){
		Image image = null;
		if (DashboardController.cameraScaned == false || DashboardController.capturedImage == "") {
			image = new Image("enrollment/assets/resources/man.png");
		} else {
			image = new Image(DashboardController.capturedImage);
		}
		ivCaptured.setImage(image);
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
    protected void handleSkipButtonAction(ActionEvent event) throws IOException {
		mdlState = 2;
		showSkipFingerMdl();
    }
	
	
	private void showSkipFingerMdl() {	

		double posX = 0, posY = 0;
		if (mdlState == 1) {
	        Bounds bounds = curPaneClose.getBoundsInLocal();
	        Bounds screenBounds = curPaneClose.localToScreen(bounds);
	        posX = (double) screenBounds.getMinX() + (curPaneClose.getWidth() / 2) - 90;
	        posY = (double) screenBounds.getMinY() + curPaneClose.getHeight();
		} else {
			if (scanningFinger < 5) {
				Bounds bounds = btnSkipLeftHand.getBoundsInLocal();
		        Bounds screenBounds = btnSkipLeftHand.localToScreen(bounds);
		        posX = (double) screenBounds.getMinX() + (btnSkipLeftHand.getWidth() / 2) - 90;
		        posY = (double) screenBounds.getMinY() - 336;
			} else {
				Bounds bounds = btnSkipRightHand.getBoundsInLocal();
		        Bounds screenBounds = btnSkipRightHand.localToScreen(bounds);
		        posX = (double) screenBounds.getMinX() + (btnSkipRightHand.getWidth() / 2) - 90;
		        posY = (double) screenBounds.getMinY() - 336;
			}
			
		}
        
        
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/skipfingermdl.fxml"));
            Parent root = loader.load();
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Stage dialog = new Stage();
            Window parent = paneLeftClose1.getScene().getWindow();
            dialog.setScene(new Scene(root));
            dialog.setX(posX);
            dialog.setY(posY);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
            
        }
        catch (IOException e) {
        	e.printStackTrace();
        } 
	}
	
	@FXML
    protected void handleScanSuccessOKButtonAction(ActionEvent event) throws IOException {
		regionDark.setVisible(false);
		paneFingerScanOK.setVisible(false);
		gotoNextFinger();
    }
	
	@FXML
    protected void handlFinishButtonAction(ActionEvent event) throws IOException {
		Parent root;
        try {
            
        	paneLeftHand.getScene().getWindow().hide();
        	
        	DashboardController.fingerScaned = true;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/dashboard.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Enrollment");
            stage.setScene(new Scene(root));
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            logingNum = 0;
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
	
	@FXML
    protected void handleComboSkipButtonAction(ActionEvent event) throws IOException {
		Window dialog = optSkip1.getScene().getWindow();
		dialog.hide();
    }
	
	private void scanFinger() {
		curPane.getStyleClass().clear();
		curPane.getStyleClass().add("finger-back-loading");
		curPaneClose.setVisible(true);
    }
	
	private void delayBlocked() {
		boolean state = api.getFingerScan(scanningFinger);
		if (state) {
			setScanState(true);
			curPane.getStyleClass().clear();
			curPane.getStyleClass().add("finger-back-success");
			curPaneClose.setVisible(false);
			clock1.stop();
			clock2.stop();
			regionDark.setVisible(true);
			paneFingerScanOK.setVisible(true);
			return;
		}
		curPane.getStyleClass().clear();
		curPane.getStyleClass().add("finger-back-checking");
    }
	
	private void gotoNextFinger() {
		
		scanningFinger += 1;
		if (scanningFinger > 9) {
			btnFinish.setDisable(false);
			clock1.stop();
			clock2.stop();
			clock3.stop();
			clock4.stop();
			if (rightSkipped == 1) {
				paneRightHand.getStyleClass().clear();
				paneRightHand.getStyleClass().add("hand-back-right6");
			}
			return;
		}
		String clsLeft = "hand-back-left0";
		if (leftSkipped == 1) clsLeft = "hand-back-left6";
		String clsRight = "hand-back-right0";
		if (rightSkipped == 1) clsRight = "hand-back-right6";
		switch (scanningFinger) {
		case 0:
			curPane = paneLeftFinger1;
			curPaneClose = paneLeftClose1;
			
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("hand-back-left1");
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add(clsRight);
			paneRightHandTop.setVisible(false);
			break;
		case 1:
			curPane = paneLeftFinger2;
			curPaneClose = paneLeftClose2;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("hand-back-left2");
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add(clsRight);
			paneRightHandTop.setVisible(false);
			break;
		case 2:
			curPane = paneLeftFinger3;
			curPaneClose = paneLeftClose3;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("hand-back-left3");
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add(clsRight);
			paneRightHandTop.setVisible(false);
			break;
		case 3:
			curPane = paneLeftFinger4;
			curPaneClose = paneLeftClose4;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("hand-back-left4");
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add(clsRight);
			paneRightHandTop.setVisible(false);
			break;
		case 4:
			curPane = paneLeftFinger5;
			curPaneClose = paneLeftClose5;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add("hand-back-left5");
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add(clsRight);
			paneRightHandTop.setVisible(false);
			break;
		case 5:
			curPane = paneRightFinger1;
			curPaneClose = paneRightClose1;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add(clsLeft);
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("hand-back-right1");
			paneRightHandTop.setVisible(true);
			break;
		case 6:
			curPane = paneRightFinger2;
			curPaneClose = paneRightClose2;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add(clsLeft);
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("hand-back-right2");
			paneRightHandTop.setVisible(true);
			break;
		case 7:
			curPane = paneRightFinger3;
			curPaneClose = paneRightClose3;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add(clsLeft);
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("hand-back-right3");
			paneRightHandTop.setVisible(true);
			break;
		case 8:
			curPane = paneRightFinger4;
			curPaneClose = paneRightClose4;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add(clsLeft);
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("hand-back-right4");
			paneRightHandTop.setVisible(true);
			break;
		case 9:
			curPane = paneRightFinger5;
			curPaneClose = paneRightClose5;
			paneLeftHand.getStyleClass().clear();
			paneLeftHand.getStyleClass().add(clsLeft);
			paneRightHand.getStyleClass().clear();
			paneRightHand.getStyleClass().add("hand-back-right5");
			paneRightHandTop.setVisible(true);
			break;
		default:
			curPane = paneRightFinger1;
			curPaneClose = paneRightClose1;
			break;
		}
		clock1.play();
		clock2.play();
		skipped = 0;
	}
	private void skipScanning() {
		if (skipped == 0) return;
		if (skipped == 1) {
			setScanState(false);
			curPane.getStyleClass().clear();
			curPane.getStyleClass().add("finger-back-fail");
			curPaneClose.setVisible(false);
		} else {
			if (scanningFinger < 5) {
				DashboardController.leftHand = false;
				scanningFinger = 4;
				paneLeftHandTop.setVisible(false);
				leftSkipped = 1;
			} else {
				scanningFinger = 9;
				paneRightHandTop.setVisible(false);
				rightSkipped = 1;
			}
		}
		
		clock1.stop();
		clock2.stop();
		gotoNextFinger();
	}
	
	private void setScanState(boolean state) {
		switch (scanningFinger) {
		case 0:
			DashboardController.leftFinger1 = state;
			break;
		case 1:
			DashboardController.leftFinger2 = state;
			break;
		case 2:
			DashboardController.leftFinger3 = state;
			break;
		case 3:
			DashboardController.leftFinger4 = state;
			break;
		case 4:
			DashboardController.leftFinger5 = state;
			DashboardController.leftHand = true;
			break;
		case 5:
			DashboardController.rightFinger1 = state;
			break;
		case 6:
			DashboardController.rightFinger2 = state;
			break;
		case 7:
			DashboardController.rightFinger3 = state;
			break;
		case 8:
			DashboardController.rightFinger4 = state;
			break;
		default:
			DashboardController.rightFinger5 = state;
			DashboardController.rightHand = true;
			break;
		}
	}
	
	private void clearScanState() {
		DashboardController.fingerScaned = false;
		DashboardController.leftHand = false;
		DashboardController.rightHand = false;
		DashboardController.leftFinger1 = false;
		DashboardController.leftFinger2 = false;
		DashboardController.leftFinger3 = false;
		DashboardController.leftFinger4 = false;
		DashboardController.leftFinger5 = false;
		DashboardController.rightFinger1 = false;
		DashboardController.rightFinger2 = false;
		DashboardController.rightFinger3 = false;
		DashboardController.rightFinger4 = false;
		DashboardController.rightFinger5 = false;
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
			lblLog1.setText("Posicionar o dedo no sensor");
			break;
		case 2:
			lblLog1.setText("Mover o dedo para a direita");
			break;
		case 3:
			lblLog1.setText("Mover o dedo para a esquerda");
			break;
		case 4:
			lblLog1.setText("Pressionar o dedo");
			break;
		case 5:
			lblLog1.setText("Mover o dedo para a esquerda");
			break;
		default:
			logingNum -= 5;
			break;
		}
	}
}
