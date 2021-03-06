![프로그램캡쳐](https://user-images.githubusercontent.com/57562241/72621347-70d9a700-3984-11ea-8d42-e25c127d6ad3.png)



Main.java
======

~~~java
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
~~~

SampleController.java
==============
~~~java
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
import javafx.scene.paint.Color;



public class SampleController implements Initializable {
	
	// variables 
	
	 public GraphicsContext gcb, gcf; // canvas에 색 출력  gcf-canvas gcb-canvasef 
	 public boolean freedesign = true, erase = false, drawline = false,
			 drawoval = false,drawrectangle = false; //true false로 키고 끄기
	 double startX, startY, lastX,lastY,oldX,oldY,holdX,holdY;
	 double hg;

//			 c= Color.rgb(244,244,244); // 그림판 배경 색 
	 // FXML
	
	@FXML 
	 public TextField Answer;
	
	 public Canvas canvas, canvasef;
	 public Button Pencil;
	 public Button Eraser;
	 public Button oval,line,rect;
	 public ColorPicker colorpick;
	 public RadioButton strokeRB,fillRB;
	 public Slider sizeSlider;
	 
	@FXML
	public void onMousePressedListener(MouseEvent e){ //직선 및 도형 그릴 때 시작 끝 저장 
			this.startX = e.getX();
			this.startY = e.getY();
			this.oldX = e.getX();
			this.oldY = e.getY();
		}
	 @FXML
	    public void onMouseDraggedListener(MouseEvent e){ // 마우스 움직임 저장
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
	        if(erase)
	        	erase();
	    }
	  @FXML 
	    public void onMouseReleaseListener(MouseEvent e){ 

	        if(drawrectangle)
	            drawRect();
	        if(drawoval)
	            drawOval();
	        if(drawline)
	            drawLine();
	        if(erase)
	        	erase();
	      
	    }
	  @FXML 
	    public void onMouseEnteredListener(MouseEvent e){
		  this.holdX = e.getX();
		  this.holdY = e.getY();
//		  if(erase) {
//			  eraseEffect();
//		  }
	  
	  }
	  
	
	  
	  @FXML
	    public void onMouseExitedListener(MouseEvent event)
	    { //실험
//	        System.out.println("mouse exited");
	    }
	  
	  // draw method
	
	
	  private void drawOval() //타원 그리는 메소드 
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcb.setLineWidth(sizeSlider.getValue());
//	        gcb.setLineWidth(5);

	        if(fillRB.isSelected()){
	            gcb.setFill(colorpick.getValue());
	            gcb.fillOval(startX, startY, wh, hg);
	        }else{
	            gcb.setStroke(colorpick.getValue());
	            gcb.strokeOval(startX, startY, wh, hg);
	        }
	    }

	    private void drawRect() //사각형 그리는 메소드 
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcb.setLineWidth(sizeSlider.getValue());
//	        gcb.setLineWidth(5);

	        if(fillRB.isSelected()){
	        	 gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcb.setFill(colorpick.getValue());
	            gcb.fillRect(startX, startY, wh, hg);
	        }else{
	        	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcb.setStroke(colorpick.getValue());
	            gcb.strokeRect(startX, startY, wh, hg);
	        }
	    }

	    private void drawLine() //선 그리는 메소드 
	    {	
	    	if(drawline) {
	    	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	        gcb.setLineWidth(sizeSlider.getValue());
	        gcb.setStroke(colorpick.getValue());
	        gcb.strokeLine(startX, startY, lastX, lastY);
	    	}
	    }

	    public void freeDrawing() // 마우스 이용 그리기  메소드 
	    {
	    	gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//	    	gcb.setLineWidth(5);
	        gcb.setLineWidth(sizeSlider.getValue());
	        gcb.setStroke(colorpick.getValue());
	        gcb.strokeLine(oldX, oldY, lastX, lastY);
	       //마우스 이벤트에서 위치 받아옴 
	        oldX = lastX;
	        oldY = lastY;
	    }
	    private void erase() { // 지우개 메소드 
	    	if(erase) {
	    		gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//		    	gcb.setLineWidth(5);
		        gcb.setLineWidth(sizeSlider.getValue());
		        gcb.setStroke(Color.WHITE);
		        gcb.strokeLine(oldX, oldY, lastX, lastY);
		       //마우스 이벤트에서 위치 받아옴 
		        oldX = lastX;
		        oldY = lastY;
	    		 
	    
	    
	    	
	    		}
	    }
	    
	    
	    
	     // 도형 그릴 때 효과 
	    
	    
	    private void drawOvalEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcf.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setFill(colorpick.getValue());
	            gcf.fillOval(startX, startY, wh, hg);
	          
	          
	        }else{
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setStroke(colorpick.getValue());
	            gcf.strokeOval(startX, startY, wh, hg );
	     
	          
	        }
	       }

	    private void drawRectEffect()
	    {
	        double wh = lastX - startX;
	        double hg = lastY - startY;
	        gcf.setLineWidth(sizeSlider.getValue());

	        if(fillRB.isSelected()){
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setFill(colorpick.getValue());
	            gcf.fillRect(startX, startY, wh, hg);
	          
	        }else{
	            gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	            gcf.setStroke(colorpick.getValue());
	            gcf.strokeRect(startX, startY, wh, hg );
	          
	        
	        }
	    }
	    
	    
	    private void drawLineEffect()
	    {
	        gcf.setLineWidth(sizeSlider.getValue());
	        gcf.setStroke(colorpick.getValue());
	        gcf.clearRect(0, 0, canvas.getWidth() , canvas.getHeight());
	        gcf.strokeLine(startX, startY, lastX, lastY);
	     
	     
	    }  
	    
//	    private void eraseEffect() {
//	    	    double wh = sizeSlider.getValue();
//		       
//		        gcf.setLineWidth(1);
//
//		      if(erase) {
//		    	  gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//		    	  gcf.setStroke(colorpick.getValue());
//		          gcf.strokeRect(holdX, holdY, wh, wh );
//		      }
//	    }
	    
	    // button connect 
	    
	    @FXML
	    private void setOvalAsCurrentShape(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = true;
	        drawrectangle = false;
	        freedesign = false;
	        erase = false;
	    }

	     @FXML
	    private void setLineAsCurrentShape(ActionEvent e)
	    {
	        drawline = true;
	        drawoval = false;
	        drawrectangle = false;
	        freedesign = false;
	        erase = false;
	    }
	     @FXML
	    private void setRectangleAsCurrentShape(ActionEvent e)
	    {
	        drawline = false;
	        drawoval = false;
	        freedesign = false;
	        erase=false;
	        drawrectangle = true;
	    }

	    	 
	    @FXML 
	    private void clearCanvas(ActionEvent e)
	    {
	        gcf.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	        gcb.clearRect(0, 0, canvasef.getWidth(), canvasef.getHeight());
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
			gcf = canvas.getGraphicsContext2D();
			gcb = canvasef.getGraphicsContext2D();
			
			
			
			
		}	 
}
~~~

Sample.fxml
========
~~~fxml
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
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<AnchorPane fx:id="result" prefHeight="800.0" prefWidth="1280.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="application.SampleController">
   <children>
      <TextField fx:id="Answer" layoutX="549.0" layoutY="14.0" prefHeight="30.0" prefWidth="320.0" />
      <TextField fx:id="Talk" layoutX="247.0" layoutY="749.0" prefHeight="29.0" prefWidth="1019.0" />
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
      <Pane fx:id="pane" layoutX="248.0" layoutY="67.0" prefHeight="414.0" prefWidth="1019.0" style="-fx-background-color: WHITE;">
         <children>
            <Canvas fx:id="canvasef" height="370.0" layoutY="45.0" width="1018.0" />
            <HBox prefHeight="45.0" prefWidth="1019.0">
               <children>
                  <ToolBar cacheHint="SPEED" prefHeight="43.0" prefWidth="1018.0" snapToPixel="false">
                    <items>
                      <Button fx:id="Pencil" mnemonicParsing="false" onAction="#setFreeDesign" prefWidth="80.0" text="Pencil">
                           <font>
                              <Font name="Cambria Math" size="15.0" />
                           </font>
                        </Button>
                        <Button mnemonicParsing="false" onAction="#setErase" prefWidth="80.0" text="Eraser" />
                        <Button fx:id="oval" mnemonicParsing="false" onAction="#setOvalAsCurrentShape" text="Oval" />
                        <Button fx:id="line" mnemonicParsing="false" onAction="#setLineAsCurrentShape" text="Line" />
                        <Button fx:id="rect" mnemonicParsing="false" onAction="#setRectangleAsCurrentShape" text="Rect" />
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
            <Canvas fx:id="canvas" height="370.0" layoutY="45.0" onMouseDragged="#onMouseDraggedListener" onMouseEntered="#onMouseEnteredListener" onMouseExited="#onMouseExitedListener" onMousePressed="#onMousePressedListener" onMouseReleased="#onMouseReleaseListener" width="1018.0" />
         </children>
      </Pane>
   </children>
   <effect>
      <ColorAdjust />
   </effect>
</AnchorPane>

~~~
