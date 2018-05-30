package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    Font logoFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 45);
    Font mainFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 16);
    Font radioButtonFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 15);
    private final static Color WHITE = Color.WHITE;
    private final static Color MY_NAVY = Color.rgb(82,59, 156);

    @Override
    public void start(Stage primaryStage) throws Exception{


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(100);
        gridPane.setVgap(50);
        gridPane.setPadding(new Insets(25,25,25,25));

        //==================================================================================================================
        HBox boxLogo = new HBox();
        Text logoApp = new Text();
        logoApp.setFont(logoFont);
        logoApp.setText("InstaScraper");
        logoApp.setStroke(Color.WHITE);
        logoApp.setStrokeType(StrokeType.OUTSIDE);
        logoApp.setStrokeWidth(2);
        logoApp.setFill(MY_NAVY);
        boxLogo.getChildren().addAll(logoApp);


        //==================================================================================================================
        VBox specifyUploadBox = new VBox();
        specifyUploadBox.setSpacing(10);
        //boxSpecifyUpload.setPadding(new Insets(15, 12, 15, 12));
        //nick
        Label nickLabel = new Label("Podaj nick użytkownika Intagram:");
        //nickLabel.setStyle("-fx-stroke: white"); //niedziała
        //nickLabel.setStyle("-fx-stroke-width: 10px"); //nie działa
        //nickLabel.getStyleClass().add("outline");
        nickLabel.setFont(mainFont);
        nickLabel.setTextFill(WHITE);
        TextField nickTextField = new TextField();
        //upload rzeczy
        Label uploadLabel = new Label("Co chcesz ściągnąć?");
        uploadLabel.setFont(mainFont);
        uploadLabel.setTextFill(WHITE);

        RadioButton uploadPhotosButton = new RadioButton("Zdjęcia/Video");
        uploadPhotosButton.setFont(radioButtonFont);
        uploadPhotosButton.setTextFill(WHITE);
        RadioButton uploadTagsButton = new RadioButton("Tagi");
        uploadTagsButton.setFont(radioButtonFont);
        uploadTagsButton.setTextFill(WHITE);
        RadioButton uploadLocationsButton = new RadioButton("Lokalizację");
        uploadLocationsButton.setFont(radioButtonFont);
        uploadLocationsButton.setTextFill(WHITE);

        //folder do zapisu
        Label writeDataLabel = new Label("Wybierz folder do zapisu danych:");
        writeDataLabel.setFont(mainFont);
        writeDataLabel.setTextFill(WHITE);
        TextField pathTextField = new TextField();
        Button openDirectoryButton = new Button("Wybierz folder");
        openDirectoryButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        openDirectoryButton.setFont(mainFont);
        openDirectoryButton.setTextFill(WHITE);
        openDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                if(selectedDirectory == null){
                    pathTextField.setText("Nie wybrano folderu");
                }else{
                    pathTextField.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
        specifyUploadBox.getChildren().addAll(nickLabel, nickTextField, uploadLabel,uploadPhotosButton, uploadTagsButton, uploadLocationsButton, writeDataLabel, pathTextField, openDirectoryButton);

        //==================================================================================================================
        VBox choosePhotoVideoBox = new VBox();

        Label choosePhotoVideoLabel = new Label("Wybierz zdjęcia/filmy, z których chcesz pobrać dane:");
        choosePhotoVideoLabel.setFont(mainFont);
        choosePhotoVideoLabel.setTextFill(WHITE);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        CheckBox checkbox1 = new CheckBox();
        scrollPane.setContent(checkbox1);




        choosePhotoVideoBox.getChildren().addAll(choosePhotoVideoLabel, scrollPane);
        //==================================================================================================================
        Button downloadButton = new Button("Rozpocznij pobieranie");
        downloadButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        downloadButton.setFont(mainFont);
        downloadButton.setTextFill(WHITE);
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               //TUTAJ CALEP OBIERANIE DANYCH
            }
        });
        //==================================================================================================================
        Button infoButton = new Button("INFO");
        infoButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        infoButton.setFont(mainFont);
        infoButton.setTextFill(WHITE);
        infoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TUTAJ OTWARCIE INFO
            }
        });

        //list for display objects
    //    ObservableList list = root.getChildren();
       // list.add(mainPane);
       // list.add(vBox);



        //==================================================================================================================
        GridPane.setHalignment(logoApp, HPos.CENTER);
        GridPane.setValignment(logoApp, VPos.CENTER);
        GridPane.setHalignment(specifyUploadBox, HPos.LEFT);
        GridPane.setValignment(specifyUploadBox, VPos.CENTER);
        GridPane.setHalignment(choosePhotoVideoBox, HPos.RIGHT);
        GridPane.setValignment(choosePhotoVideoBox, VPos.CENTER);
        GridPane.setHalignment(downloadButton, HPos.CENTER);
        GridPane.setValignment(downloadButton, VPos.CENTER);
        GridPane.setHalignment(infoButton, HPos.RIGHT);
        GridPane.setValignment(infoButton, VPos.CENTER);
        gridPane.setBackground(Background.EMPTY);
        gridPane.add(logoApp, 0, 0, 2, 1);
        gridPane.add(specifyUploadBox, 0,1);
        gridPane.add(choosePhotoVideoBox, 1, 1);
        gridPane.add(downloadButton, 0, 2, 2, 1);
        gridPane.add(infoButton, 1, 2, 1, 1);
        //==================================================================================================================

        //colors for main background
        Stop[] stops = new Stop[]{
                new Stop(0, Color.rgb(255,242, 0)),
                new Stop(0.2,  Color.rgb(255,128, 0)),
                new Stop(0.4,  Color.rgb(250,74, 31)),
                new Stop(0.6,  Color.rgb(206,42, 93)),
                new Stop(0.8,  Color.rgb(167,61, 155)),
                new Stop(1,  Color.rgb(82,59, 156))};
        //gradient for main background
        LinearGradient linearGradientBackgroundScene = new LinearGradient(0, 600, 1000, 0,
                false,
                CycleMethod.NO_CYCLE,
                stops);
        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(linearGradientBackgroundScene);
        /*    scene.getStylesheets().addAll(getClass().getResource(
                "outline.css"
        ).toExternalForm());*/
        primaryStage.setTitle("InstaScraper Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
