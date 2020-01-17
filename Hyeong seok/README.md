Main.java
======

'''java
package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
'''
SampleController.java
==============
'''java
package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SampleController implements Initializable {
	// variables 
	public GraphicsContext gc;
	 public boolean freedesign = true, erase = false, drawline = false,
			 drawoval = false,drawrectangle = false;
	 double startX, startY, lastX,lastY,oldX,oldY;
	 double hg;
	 // FXML
	
	@FXML
	 public TextField Answer;
	 private TextField Talk;
	 public Canvas canvas;
	 public Button Pencil;
	 public Button Eraser;
	 public Button oval,line,rect;
	 public ColorPicker colorpick;
	 public RadioButton strokeRB,fillRB;
	 public Slider sizeSlider;
	 
	@FXML
	public void onMousePressedListener(MouseEvent e){
			this.startX = e.getX();
			this.startY = e.getY();
			this.oldX = e.getX();
			this.oldY = e.getY();
		}
	 @FXML
	    public void onMouseDraggedListener(MouseEvent e){
	        this.lastX = e.getX();
	        this.lastY = e.getY();
	        	// 드래그 할 때 함수들 호출 및 알고리즘 
	        if(drawrectangle)
	            drawRectEffect();
	        if(drawoval)
	            drawOvalEffect();
	        if(drawline)
	            drawLineEffect();
	        if(freedesign)
	            freeDrawing();
	    }
	  @FXML
	    public void onMouseReleaseListener(MouseEvent e){
//		   마우스 땔 때 함수들 호출 및 매소드 
	        if(drawrectangle)
	            drawRect();
	        if(drawoval)
	            drawOval();
	        if(drawline)
	            drawLine();
	    }
	  @FXML
	    public void onMouseExitedListener(MouseEvent event)
	    { //실험
//	        System.out.println("mouse exited");
	    }
	  
	  //  method
	
	
	  private void drawOval()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
//	        gc.setLineWidth(sizeSlider.getValue());
	        gc.setLineWidth(5);

	        if(fillRB.isSelected()){
	            gc.setFill(colorpick.getValue());
	            gc.fillOval(startX, startY, wh, hg);
	        }else{
	            gc.setStroke(colorpick.getValue());
	            gc.strokeOval(startX, startY, wh, hg);
	        }
	    }

	    private void drawRect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
//	        gcB.setLineWidth(sizeSlider.getValue());
	        gc.setLineWidth(5);

	        if(fillRB.isSelected()){
	            gc.setFill(colorpick.getValue());
	            gc.fillRect(startX, startY, wh, hg);
	        }else{
	            gc.setStroke(colorpick.getValue());
	            gc.strokeRect(startX, startY, wh, hg);
	        }
	    }

	    private void drawLine()
	    {
	        gc.setLineWidth(sizeSlider.getValue());
	        gc.setStroke(colorpick.getValue());
	        gc.strokeLine(startX, startY, lastX, lastY);
	    }

	    public void freeDrawing()
	    {
//	        gcB.setLineWidth(sizeSlider.getValue());
	    	gc.setLineWidth(5);
	        gc.setStroke(colorpick.getValue());
	        gc.strokeLine(oldX, oldY, lastX, lastY);
	       //마우스 이벤트에서 위치 받아옴 
	        oldX = lastX;
	        oldY = lastY;
	    }
	    private void drawOvalEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gc.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gc.setFill(colorpick.getValue());
	            gc.fillOval(startX, startY, wh, hg);
	        }else{
	            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gc.setStroke(colorpick.getValue());
	            gc.strokeOval(startX, startY, wh, hg );
	        }
	       }

	    private void drawRectEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gc.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gc.setFill(colorpick.getValue());
	            gc.fillRect(startX, startY, wh, hg);
	        }else{
	            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gc.setStroke(colorpick.getValue());
	            gc.strokeRect(startX, startY, wh, hg );
	        }
	    }

	    private void drawLineEffect()
	    {
	        gc.setLineWidth(sizeSlider.getValue());
	        gc.setStroke(colorpick.getValue());
	        gc.clearRect(0, 0, canvas.getWidth() , canvas.getHeight());
	        gc.strokeLine(startX, startY, lastX, lastY);
	    }
	    
	    
	    
	    
	    
	    
	    @FXML 
	    public void clearCanvas(ActionEvent e)
	    {
	        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//	        gcF.clearRect(0, 0, TheCanvas.getWidth(), TheCanvas.getHeight());
	    }
	    @FXML
	    public void setErase(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        drawrectangle = false;    
	        erase = true;
	        freedesign= false;
	    }

	    @FXML
	    public void setFreeDesign(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        drawrectangle = false;    
	        erase = false;
	        freedesign = true;
	    }
		@Override
		public void initialize(URL url, ResourceBundle rb) {
			// TODO Auto-generated method stub
			gc = canvas.getGraphicsContext2D();
			
		}

	 
	 
	 
	 
	 
	 
}
'''
Sample.fxml
========
'''fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.Cursor?>
<?import javafx.scene.canvas.Canvas?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ColorPicker?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.MenuButton?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.Slider?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.ToggleButton?>
<?import javafx.scene.control.ToolBar?>
<?import javafx.scene.effect.ColorAdjust?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<AnchorPane fx:id="result" prefHeight="800.0" prefWidth="1280.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="application.SampleController">
   <children>
      <TextField fx:id="Answer" layoutX="549.0" layoutY="14.0" prefHeight="30.0" prefWidth="320.0" />
      <TextField fx:id="Talk" layoutX="247.0" layoutY="749.0" prefHeight="29.0" prefWidth="1019.0" />
      <Canvas fx:id="canvas" height="423.0" layoutX="247.0" layoutY="69.0" onMouseDragged="#onMouseDraggedListener" onMouseExited="#onMouseExitedListener" onMousePressed="#onMousePressedListener" onMouseReleased="#onMouseReleaseListener" width="1019.0" />
      <HBox layoutX="247.0" layoutY="69.0" prefHeight="45.0" prefWidth="974.0">
         <children>
            <ToolBar cacheHint="SPEED" prefHeight="43.0" prefWidth="971.0" snapToPixel="false">
              <items>
                <Button fx:id="Pencil" mnemonicParsing="false" onAction="#setFreeDesign" prefWidth="80.0" text="Pencil">
                     <font>
                        <Font name="Cambria Math" size="15.0" />
                     </font>
                  </Button>
                  <Button mnemonicParsing="false" onAction="#setErase" prefWidth="80.0" text="Eraser" />
                  <Button fx:id="oval" mnemonicParsing="false" text="Oval" />
                  <Button fx:id="line" mnemonicParsing="false" text="Line" />
                  <Button fx:id="rect" mnemonicParsing="false" text="Rect" />
                  <RadioButton fx:id="strokeRB" mnemonicParsing="false" text="Stroke" />
                  <RadioButton fx:id="fillRB" mnemonicParsing="false" text="Fill" />
                  <Slider fx:id="sizeSlider" />
                  <MenuButton mnemonicParsing="false" prefWidth="80.0" text="MenuButton">
                    <items>
                      <MenuItem mnemonicParsing="false" text="12pt" />
                      <MenuItem mnemonicParsing="false" text="14pt" />
                        <MenuItem mnemonicParsing="false" text="Unspecified Action" />
                        <MenuItem mnemonicParsing="false" text="Unspecified Action" />
                        <MenuItem mnemonicParsing="false" text="Unspecified Action" />
                    </items>
                  </MenuButton>
                  <ColorPicker fx:id="colorpick" />
              </items>
            </ToolBar>
         </children>
      </HBox>
      <TextArea fx:id="TalkBoard" layoutX="247.0" layoutY="502.0" prefHeight="230.0" prefWidth="1019.0" />
      <TextArea fx:id="RankList" layoutX="14.0" layoutY="548.0" prefHeight="230.0" prefWidth="197.0" />
      <Label layoutX="14.0" layoutY="502.0" prefHeight="43.0" prefWidth="197.0" text="Rank" />
      <ListView layoutX="14.0" layoutY="295.0" prefHeight="210.0" prefWidth="197.0" />
      <Label layoutX="14.0" layoutY="267.0" text="People List" textOverrun="CLIP" />
      <Rectangle arcWidth="5.0" blendMode="ADD" fill="BLUE" height="191.0" layoutX="13.0" layoutY="69.0" smooth="false" stroke="BLACK" strokeLineCap="ROUND" strokeLineJoin="ROUND" strokeMiterLimit="0.0" strokeType="INSIDE" width="197.0">
         <cursor>
            <Cursor fx:constant="CROSSHAIR" />
         </cursor>
      </Rectangle>
      <PasswordField fx:id="ID" layoutX="27.0" layoutY="85.0" />
      <ToggleButton fx:id="Loginout" layoutX="27.0" layoutY="165.0" mnemonicParsing="false" prefHeight="29.0" prefWidth="172.0" text="LogIn/Out" />
      <ListView fx:id="Score" layoutX="26.0" layoutY="124.0" prefHeight="29.0" prefWidth="172.0" />
   </children>
   <effect>
      <ColorAdjust />
   </effect>
</AnchorPane>
'''
