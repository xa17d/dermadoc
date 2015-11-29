package at.tuwien.telemedizin.dermadoc.desktop.gui.lock;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Lucas on 28.11.2015.
 */
public class LockStage extends Stage {

    Stage mainStage;

    public LockStage(Stage mainStage) {

        this.mainStage = mainStage;

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("lock.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            this.setTitle("DermaDoc - LOCKED");
            this.getIcons().add(new Image(DesktopApplication.class.getResourceAsStream("dermadoc_icon_c_96.png")));

            //this.setAlwaysOnTop(true);
            //this.initModality(Modality.APPLICATION_MODAL);

            this.initOwner(mainStage);

            this.setMinWidth(340);
            this.setMinHeight(440);
            this.setMaxWidth(340);
            this.setMaxHeight(440);

            Scene dialogScene = new Scene(root, 340, 440);
            //scene.getStylesheets().add(getClass().getResource("dermadoc.css").toExternalForm());
            this.setScene(dialogScene);

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        tfCode.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField tf = (TextField) event.getSource();
                code.add(tf.getText().charAt(tf.getText().length()));
            }
        });
        */
    }

    Code code = new Code("1234");

    @FXML private TextField tfCode;
    @FXML private void input1() { code.add('1'); }
    @FXML private void input2() { code.add('2'); }
    @FXML private void input3() { code.add('3'); }
    @FXML private void input4() { code.add('4'); }
    @FXML private void input5() { code.add('5'); }
    @FXML private void input6() { code.add('6'); }
    @FXML private void input7() { code.add('7'); }
    @FXML private void input8() { code.add('8'); }
    @FXML private void input9() { code.add('9'); }
    @FXML private void input0() { code.add('0'); }
    @FXML private void delete() { code.delete(); }
    @FXML private void clear() { code.clear(); }

    private void unlock() {
        this.close();
        mainStage.show();
    }

    private class Code {

        private String correctCode;
        private String codeString = "";

        public Code(String correctCode) {
            this.correctCode = correctCode;
        }

        public void add(char c) { codeString += c; update(); }
        public void delete() { codeString = codeString.substring(0, codeString.length()-1); update(); }
        public void clear() { codeString = ""; update(); }

        private int length() { return codeString.length(); }
        private void update() {

            String privateCode = "";
            for(int i = 0; i < length(); i++) {
                privateCode += "*";
            }
            tfCode.setText(privateCode);

            if(codeString.equals(correctCode)) {
                unlock();
            }
            else if(codeString.length() >= correctCode.length()) {
                clear();
            }
        }
    }
}
