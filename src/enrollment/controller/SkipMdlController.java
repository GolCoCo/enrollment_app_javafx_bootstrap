package enrollment.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class SkipMdlController {
	
	@FXML
	private Button optSkip1, optSkip2, optSkip3, optSkip4, optSkip5, optSkip6, optSkip7, optSkip8;

	@FXML
	private Button optSkipIcon1, optSkipIcon2, optSkipIcon3, optSkipIcon4, optSkipIcon5, optSkipIcon6, optSkipIcon7, optSkipIcon8;
	
	public void initialize() {
		optSkip1.setOnMousePressed(event -> {
			optSkipIcon1.getStyleClass().clear();
			optSkipIcon1.getStyleClass().add("combo-btn-check");
			optSkipIcon1.setText("");
        });
		
		optSkip1.setOnMouseReleased(event -> {
			optSkipIcon1.getStyleClass().clear();
			optSkipIcon1.getStyleClass().add("combo-btn");
			optSkipIcon1.setText("");
        });
		
		optSkip2.setOnMousePressed(event -> {
			optSkipIcon2.getStyleClass().clear();
			optSkipIcon2.getStyleClass().add("combo-btn-check");
			optSkipIcon2.setText("");
        });
		
		optSkip2.setOnMouseReleased(event -> {
			optSkipIcon2.getStyleClass().clear();
			optSkipIcon2.getStyleClass().add("combo-btn");
			optSkipIcon2.setText("");
			if (FingerController.mdlState == 2) {
				hideModal(2);
			}
        });
		
		optSkip3.setOnMousePressed(event -> {
			optSkipIcon3.getStyleClass().clear();
			optSkipIcon3.getStyleClass().add("combo-btn-check");
			optSkipIcon3.setText("");
        });
		
		optSkip3.setOnMouseReleased(event -> {
			optSkipIcon3.getStyleClass().clear();
			optSkipIcon3.getStyleClass().add("combo-btn");
			optSkipIcon3.setText("");
        });
		
		optSkip4.setOnMousePressed(event -> {
			optSkipIcon4.getStyleClass().clear();
			optSkipIcon4.getStyleClass().add("combo-btn-check");
			optSkipIcon4.setText("");
        });
		
		optSkip4.setOnMouseReleased(event -> {
			optSkipIcon4.getStyleClass().clear();
			optSkipIcon4.getStyleClass().add("combo-btn");
			optSkipIcon4.setText("");
        });
		
		optSkip5.setOnMousePressed(event -> {
			optSkipIcon5.getStyleClass().clear();
			optSkipIcon5.getStyleClass().add("combo-btn-check");
			optSkipIcon5.setText("");
        });
		
		optSkip5.setOnMouseReleased(event -> {
			optSkipIcon5.getStyleClass().clear();
			optSkipIcon5.getStyleClass().add("combo-btn");
			optSkipIcon5.setText("");
        });
		
		optSkip6.setOnMousePressed(event -> {
			optSkipIcon6.getStyleClass().clear();
			optSkipIcon6.getStyleClass().add("combo-btn-check");
			optSkipIcon6.setText("");
        });
		
		optSkip6.setOnMouseReleased(event -> {
			optSkipIcon6.getStyleClass().clear();
			optSkipIcon6.getStyleClass().add("combo-btn");
			optSkipIcon6.setText("");
        });
		
		optSkip7.setOnMousePressed(event -> {
			optSkipIcon7.getStyleClass().clear();
			optSkipIcon7.getStyleClass().add("combo-btn-check");
			optSkipIcon7.setText("");
        });
		
		optSkip7.setOnMouseReleased(event -> {
			optSkipIcon7.getStyleClass().clear();
			optSkipIcon7.getStyleClass().add("combo-btn");
			optSkipIcon7.setText("");
        });
		
		optSkip8.setOnMousePressed(event -> {
			optSkipIcon8.getStyleClass().clear();
			optSkipIcon8.getStyleClass().add("combo-btn-check");
			optSkipIcon8.setText("");
        });
		
		optSkip8.setOnMouseReleased(event -> {
			optSkipIcon8.getStyleClass().clear();
			optSkipIcon8.getStyleClass().add("combo-btn");
			optSkipIcon8.setText("");
			if (FingerController.mdlState == 1) {
				hideModal(1);
			}
        });
	}
	
    private void hideModal(int opt) {
    	FingerController.skipped = opt;
		Window dialog = optSkip1.getScene().getWindow();
		dialog.hide();
    }
}
