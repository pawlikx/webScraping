/**
 * @author Anna Zdrojewska
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    /*
        COLORS ANF FONTS FOR GUI COMPONENTS
     */
    private final static Color WHITE = Color.WHITE;
    private final static Color MY_NAVY = Color.rgb(82, 59, 156);
    private Font logoFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 45);
    private Font mainFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 16);
    private Font radioButtonFont = Font.font("Candara", FontWeight.EXTRA_BOLD, 15);
    /*
        GRIDPANE FOR SCENE
     */
    private GridPane gridPane;

    /*
        BOXES FOR GROUP OBJECTS IN GRIDPANE
     */
    private HBox boxLogo;
    private VBox specifyDownloadBox;
    private VBox choosePhotoVideoBox;

    /*
        COMPONENT FOR LOGO APP
     */
    private Text logoApp;
    /*
        COMPONENTS FOR SPECIFY DOWNLOAD PART
     */
    private TextField nickTextField;
    private Label nickLabel;
    private Label downloadLabel;
    private RadioButton downloadPhotosButton;
    private RadioButton downloadTagsButton;
    private Label writeDataLabel;
    private TextField pathTextField;
    private Button openDirectoryButton;
    private DirectoryChooser directoryChooser;
    private File selectedDirectory;
    /*
        COMPONENTS FOR DOWNLOAD PART
     */
    private Label choosePhotoVideoLabel;
    private ScrollPane scrollPane;
    private String[] namesImages;

    /*
        DOWNLOAD BUTTON
     */
    private Button downloadStartButton;
    private Alert alertMessage;
    /*
        INFO BUTTON
     */
    private Button infoButton;

    /**
     * METHOD TO START JAVAFX VIEW
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
            SETTINGS FOR GRIDPANE
         */
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(100);
        gridPane.setVgap(50);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        //=========================================LOGO APP==========================================================
        /*
            DEFINE OF BOX FOR LOGO APP - INSTASCRAPPER
         */
        boxLogo = new HBox();
        /*
            DEFINE TEXT LOGO SETTINGS
         */
        logoApp = new Text();
        logoApp.setFont(logoFont);
        logoApp.setText("InstaScraper");
        logoApp.setStroke(Color.WHITE);
        logoApp.setStrokeType(StrokeType.OUTSIDE);
        logoApp.setStrokeWidth(2);
        logoApp.setFill(MY_NAVY);
        /*
            ADD LOGO TO BOX
         */
        boxLogo.getChildren().addAll(logoApp);
        //=========================================DOWNLOAD==========================================================
        /*
            SET BOX FOR DOWNLOAD COMPONENTS
         */
        specifyDownloadBox = new VBox();
        specifyDownloadBox.setSpacing(10);
        /*
            SETTING FOR LABEL WITH USER NICKNAME
         */
        nickLabel = new Label("Podaj nick użytkownika Intagram:");
        nickLabel.setFont(mainFont);
        nickLabel.setTextFill(WHITE);
        /*
            DEFINE TEXTFIELD FOR USER NICKNAME
         */
        nickTextField = new TextField();
        nickTextField.setEditable(true);
        /*
            SETTINGS FOR LABEL WITH SPECIFY DOWNLOAD
         */
        downloadLabel = new Label("Co chcesz ściągnąć?");
        downloadLabel.setFont(mainFont);
        downloadLabel.setTextFill(WHITE);
        /*
            SETTINGS FOR RADIO BUTTON TO DOWNLOAD PHOTO/VIDEO
         */
        downloadPhotosButton = new RadioButton("Zdjęcia/Video");
        downloadPhotosButton.setFont(radioButtonFont);
        downloadPhotosButton.setTextFill(WHITE);
                /*
            SETTINGS FOR RADIO BUTTON TO DOWNLOAD PHOTO/VIDEO AND TAGS
         */
        downloadTagsButton = new RadioButton("Zdjęcia/Video + Tagi");
        downloadTagsButton.setFont(radioButtonFont);
        downloadTagsButton.setTextFill(WHITE);

        /*
            SETTING FOR LABEL SPECIFY DIRECTORY TO DOWNLOAD DATA
         */
        writeDataLabel = new Label("Wybierz folder do zapisu danych:");
        writeDataLabel.setFont(mainFont);
        writeDataLabel.setTextFill(WHITE);
        /*
            DEFINE TEXFIELDS FOR PATH TO DIRECTORY WHERE DOWNLOAD DATA WILL BE WRITE
         */
        pathTextField = new TextField();
        pathTextField.setEditable(true);
        /*
            SETTING AND HANDLING BUTTON TO CHOOSE DIRECTORY
         */
        openDirectoryButton = new Button("Wybierz folder");
        openDirectoryButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        openDirectoryButton.setFont(mainFont);
        openDirectoryButton.setTextFill(WHITE);
        openDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directoryChooser = new DirectoryChooser();
                selectedDirectory = directoryChooser.showDialog(primaryStage);
                if (selectedDirectory == null) {
                    pathTextField.setText("Nie wybrano folderu");
                } else {
                    pathTextField.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
        specifyDownloadBox.getChildren().addAll(nickLabel, nickTextField, downloadLabel, downloadPhotosButton, downloadTagsButton, writeDataLabel, pathTextField, openDirectoryButton);

        //=========================================SCROLL FOR CHOOSING ELEMENTS==========================================================
        /*
            DEFINE BOX FOR CHOOSING ELEMENTS TO DOWNLOAD
         */
        choosePhotoVideoBox = new VBox();
        choosePhotoVideoBox.setSpacing(10);
        /*
            SETTINGS FOR LABEL TO DOWNLOAD ELEMENTS
         */
        choosePhotoVideoLabel = new Label("Wybierz zdjęcia/filmy, z których chcesz pobrać dane:");
        choosePhotoVideoLabel.setFont(mainFont);
        choosePhotoVideoLabel.setTextFill(WHITE);
        /*
            SETTINGS FOR SCROLL PANE
         */
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        /*
            TE 2 LINIE POWINNY BYC SKASOWANE PO ODKOMENTOWNIU KODU PONIŻEJ
         */
        CheckBox checkBox1 = new CheckBox();
        scrollPane.setContent(checkBox1);
        /*
        KOD DO WSTAWIANIA OBRAZKÓW DO CHECKBOZÓW DO SCROLLPANE
        NIE DAJE RĘKI UCIĄĆ ŻE ZADZIAŁA ALE NIE MAM INNEGO POMYSŁU

        namesImages = new String[]{}; //TUTAJ TRZEBA PODAĆ NAZWY OBRAZKÓW W {]
        Image[] images = new Image[namesImages.length];
        ImageView[] icons = new ImageView[namesImages.length];
        CheckBox[] checkBoxes = new CheckBox[namesImages.length];
        scrollPane.hvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (int i = 0; i < 10; i++) { //ZAMIAST 10 TRZEBA PODAĆ LICZBĘ OBRAZKÓW
                    Image image = images[i] = new Image(getClass().getResourceAsStream(namesImages[i]));
                    ImageView icon = icons[i] = new ImageView();
                    CheckBox checkBox = checkBoxes[i] = new CheckBox(namesImages[i]);
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            icon.setImage(newValue ? image : null);
                        }
                    });
                    scrollPane.setContent(checkBox);
                }
            }
        });
        */
        choosePhotoVideoBox.getChildren().addAll(choosePhotoVideoLabel, scrollPane);
        //=========================================DOWNLOAD BUTTON==========================================================
        /*
            BUTTON FOR DOWNLOAD ELEMENTS
         */
        downloadStartButton = new Button("Rozpocznij pobieranie");
        downloadStartButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        downloadStartButton.setFont(mainFont);
        downloadStartButton.setTextFill(WHITE);
        downloadStartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //SPRAWDZENIE CZY TEXTFIELD OD NICKU UŻYTKOWNIKA NIE JEST PUSTY
                if (validateInputNickTextField() == true) {
                    if(validateInputPathDirectoryTextField() == true) {
                        //TUTAJ CALEP OBIERANIE DANYCH
                        if (downloadPhotosButton.isSelected()) {
                            //RADIO BUTTON OD ZDJĘ i VIDEO
                        }
                        if (downloadTagsButton.isSelected()) {
                            //RADIO BUTTON OD TAGÓW
                        }
                    }
                    else{
                        alertMessage = new Alert(Alert.AlertType.ERROR);
                        alertMessage.setTitle("Błąd!");
                        alertMessage.setHeaderText(null);
                        alertMessage.setContentText("Nie wybrano folderu dla pobieranych danych!");
                        alertMessage.showAndWait();
                    }
                }
                else{
                    alertMessage = new Alert(Alert.AlertType.ERROR);
                    alertMessage.setTitle("Błąd!");
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("Nie wybrano nicku użytkownika!");
                    alertMessage.showAndWait();
                }

            }
        });
        //=========================================INFO BUTTON==========================================================
        /*
            BUTTON FOR INFORMATION ABOUT AUTHORS AND APP
         */
        infoButton = new Button("INFO");
        infoButton.setStyle("-fx-background-color:#523b9c; -fx-stroke: white; -fx-stroke-width: 2px");
        infoButton.setFont(mainFont);
        infoButton.setTextFill(WHITE);
        infoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TUTAJ OTWARCIE INFO
            }
        });


        //=========================================GRID PANE ALIGNMENTS==========================================================
        GridPane.setHalignment(logoApp, HPos.CENTER);
        GridPane.setValignment(logoApp, VPos.CENTER);
        GridPane.setHalignment(specifyDownloadBox, HPos.LEFT);
        GridPane.setValignment(specifyDownloadBox, VPos.CENTER);
        GridPane.setHalignment(choosePhotoVideoBox, HPos.RIGHT);
        GridPane.setValignment(choosePhotoVideoBox, VPos.CENTER);
        GridPane.setHalignment(downloadStartButton, HPos.CENTER);
        GridPane.setValignment(downloadStartButton, VPos.CENTER);
        GridPane.setHalignment(infoButton, HPos.RIGHT);
        GridPane.setValignment(infoButton, VPos.CENTER);
        gridPane.setBackground(Background.EMPTY);
        gridPane.add(logoApp, 0, 0, 2, 1);
        gridPane.add(specifyDownloadBox, 0, 1);
        gridPane.add(choosePhotoVideoBox, 1, 1);
        gridPane.add(downloadStartButton, 0, 2, 2, 1);
        gridPane.add(infoButton, 1, 2, 1, 1);
        //=========================================SETTING FOR SCENE==========================================================
        /*
            COLORS FOR BACKGROUND
         */
        Stop[] stops = new Stop[]{
                new Stop(0, Color.rgb(255, 242, 0)),
                new Stop(0.2, Color.rgb(255, 128, 0)),
                new Stop(0.4, Color.rgb(250, 74, 31)),
                new Stop(0.6, Color.rgb(206, 42, 93)),
                new Stop(0.8, Color.rgb(167, 61, 155)),
                new Stop(1, Color.rgb(82, 59, 156))};
        /*
            GRADIENT FOR BACKGROUND
         */
        LinearGradient linearGradientBackgroundScene = new LinearGradient(0, 600, 1000, 0,
                false,
                CycleMethod.NO_CYCLE,
                stops);
        /*
            SCENE FOR ALL COMPONENTS
         */
        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(linearGradientBackgroundScene);
        primaryStage.setTitle("InstaScraper Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * METHOD FOR VALIDATE INPUT NICK TEXT FIELD
     *
     * @return TRUE IF TEXT FIELD IS NOT NULL AND NOT EMPTY
     */
    public boolean validateInputNickTextField() {
        Boolean entry = false;
        if (nickTextField.getText() != null && !nickTextField.getText().isEmpty()) {
            entry = true;
        }
        return entry;
    }
    /**
     * METHOD FOR VALIDATE DIRECTORY PATH TEXT FIELD
     *
     * @return TRUE IF TEXT FIELD IS NOT NULL AND NOT EMPTY AND NOT EQUALS "Nie wybrano folderu"
     */
    public boolean validateInputPathDirectoryTextField(){
        Boolean entry = false;
        if(pathTextField.getText() != null && !pathTextField.getText().isEmpty() && !pathTextField.getText().equals("Nie wybrano folderu")){
            entry = true;
        }
        return entry;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
