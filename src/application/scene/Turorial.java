package scene;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Turorial {
    static Label lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7, lbl8;
    static FlowPane pane2;
    static Scene scene2;
    static Stage stage2;


    public static Stage getTutorialStage() {
        lbl1 = new Label("Scene1");
        //lbl2 = new Label("Game Tutorial \n");
        lbl3 = new Label("To Play this game, follow the steps:");
        lbl3.setStyle("-fx-font:100px 'Apple Chancery'; -fx-fill: #418aa5; -fx-font-size: 14pt;");
        lbl4 = new Label("    1) First, search for the word in puzzle.");
        lbl4.setStyle("-fx-font:100px 'Apple Chancery'; -fx-fill: #418aa5; -fx-font-size: 12pt;");
        lbl8 = new Label("    2) Then, select letters with keyboard.");
        lbl8.setStyle("-fx-font:100px 'Apple Chancery'; -fx-fill: #418aa5; -fx-font-size: 12pt;");
        lbl5 = new Label("           - Use keyboard to move (up(w)/down(s)/right(d)/left(a))");
        lbl5.setStyle("-fx-font:100px 'Bradley Hand'; -fx-fill: #418aa5; -fx-font-size: 10pt;");
        lbl6 = new Label("           - Enter to select each letter of the word.");
        lbl6.setStyle("-fx-font:100px 'Bradley Hand'; -fx-fill: #418aa5; -fx-font-size: 10pt;");
        lbl7 = new Label("           - Use backspace to delete the most recent letter");
        lbl7.setStyle("-fx-font:100px 'Bradley Hand'; -fx-fill: #418aa5; -fx-font-size: 10pt;");
        pane2 = new FlowPane();
        pane2.setVgap(10);
        pane2.setStyle("-fx-background-color:#9ec4ab;-fx-padding:10px;");
        pane2.getChildren().addAll(lbl3,lbl4, lbl8,lbl5,lbl6,lbl7);
        scene2 = new Scene(pane2, 400, 250);
        scene2.setFill(Color.web("#708090"));
        stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setResizable(false);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setTitle("Game Tutorial");
        stage2.show();


        return stage2;


    }
    public static HBox getTHbox() {
        HBox tb = new HBox();
        tb.setPadding(new Insets(5,10,5,10));
        tb.setSpacing(5);
        ImageView image = new ImageView(new Image("/backgroundImage/img.png"));
        image.setPreserveRatio(true);
        image.setFitHeight(100);
        Button tbutton = new Button();
        tbutton.setGraphic(image);
        tbutton.setBackground(null);
        tbutton.setPrefSize(Region.USE_COMPUTED_SIZE, 100);
        tbutton.setContentDisplay(ContentDisplay.CENTER);
        EventHandler<ActionEvent> callStage = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                getTutorialStage();
            }
        };
        tbutton.setOnAction(callStage);
        tb.setAlignment(Pos.CENTER);
        tb.getChildren().addAll(tbutton);
        return tb;
    }
}
