/*
 * 
 */
package rms.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import rms.Consumer.ClosingConsumer;
import static rms.Model.Assistant.assistant;
import static rms.Model.Barmaid.barmaid;
import static rms.Model.Landlord.landlord;
import rms.Model.Tables;

import static rms.MyUtils.MyConfig.closingTimeNotify;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyConfig.cupboard;
import static rms.MyUtils.MyConfig.customerPauseSpawn;
import static rms.MyUtils.MyConfig.lastOrder;
import static rms.MyUtils.MyConfig.mySanityLog;
import static rms.MyUtils.MyConfig.myTimeLog;
import static rms.MyUtils.MyConfig.tableNumbers;
import static rms.MyUtils.MyConfig.tables;
import static rms.MyUtils.MyUtils.isValidInt;
import static rms.MyUtils.MyUtils.log;
import static rms.MyUtils.MyUtils.sanityLog;

public class RMSCon implements Initializable {

    @FXML
    private Label time_label;
    @FXML
    private JFXButton playpauseCustomerButton;
    @FXML
    private JFXButton showDetailsButton;
    @FXML
    private JFXButton playpauseAssistantButton;
    @FXML
    private JFXTextArea log_area;
    @FXML
    private JFXTextArea sanity_check_area;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label currentNumberOfCustomerLabel;
    @FXML
    private Label drinkingTimeLabel;
    @FXML
    private JFXTextField numOfCustomerTF;
    @FXML
    private JFXTextField drinkingTimeTF;
    @FXML
    private JFXTextField washGlassTimeTF;
    @FXML
    private JFXTextField washCupTimeTF;
    @FXML
    private JFXTextField washRoundsTimeTF;
    @FXML
    private JFXTextField replaceGlassTimeTF;
    @FXML
    private JFXTextField replaceCupTimeTF;
    @FXML
    private JFXButton setCustomerButton;
    @FXML
    private JFXButton setAssistantButton;
    @FXML
    private JFXButton setCupboardButton;
    @FXML
    private JFXTextField fetchGlassTimeTF;
    @FXML
    private JFXTextField fetchCupTimeTF;
    @FXML
    private JFXTextField fetchChocolateTimeTF;
    @FXML
    private JFXTextField fetchCoffeeTimeTF;
    @FXML
    private JFXTextField fetchMilkTimeTF;
    @FXML
    private JFXTextField fetchJuiceTimeTF;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initPane();
        initClock();
        initStat();
        initLogArea();
    }
    
    private int prev_time_loc = 0;
    private int prev_sanity_loc = 0;
    
    private void initPane() {
        // Customer
        numOfCustomerTF.setText(Integer.toString(conf.getNumberOfCustomerEntering()));
        drinkingTimeTF.setText(Integer.toString(conf.getDrinkingTime()));
        
        // Assistant
        washGlassTimeTF.setText(Integer.toString(conf.getWashGlassTime()));
        washCupTimeTF.setText(Integer.toString(conf.getWashCupTime()));
        washRoundsTimeTF.setText(Integer.toString(conf.getWashRoundsTime()));
        
        // Cupboard
        replaceGlassTimeTF.setText(Integer.toString(conf.getTimeReplaceGlass()));
        replaceCupTimeTF.setText(Integer.toString(conf.getTimeReplaceCup()));
        fetchGlassTimeTF.setText(Integer.toString(conf.getTimeFetchGlass()));
        fetchCupTimeTF.setText(Integer.toString(conf.getTimeFetchCup()));
        fetchChocolateTimeTF.setText(Integer.toString(conf.getTimeFetchChocolate()));
        fetchMilkTimeTF.setText(Integer.toString(conf.getTimeFetchMilk()));
        fetchJuiceTimeTF.setText(Integer.toString(conf.getTimeFetchJuice()));
        fetchCoffeeTimeTF.setText(Integer.toString(conf.getTimeFetchCoffee()));
    }
    
    private void initStat() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    currentNumberOfCustomerLabel.
                            setText(Integer.toString(conf.getNumberOfCustomerEntering()));
                    drinkingTimeLabel.
                            setText(Integer.toString(conf.getDrinkingTime()));
                });
            }
        }, 0, 1000);
    }
    
    private void initClock() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    time_label.setText(dateFormat.format(new Date()));
                });
            }
        }, 0, 1000);
    }
    
    private void initLogArea() {
        log_area.setEditable(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    for (int i = prev_time_loc; i < myTimeLog.size(); i++) {
                        log_area.appendText(myTimeLog.get(i));
                        prev_time_loc++;
                    }
                });
            }
        }, 0, 1000);
    }

    private void closingTimeOnClick(ActionEvent event) {
        if (!closingTimeNotify) {
            log("Closing time notify...");
            closingTimeNotify = true;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Thread closing = new Thread(new ClosingConsumer());
            executor.execute(closing);
        }
    }

    @FXML
    private void stopSpawnCustomerOnClick(ActionEvent event) {
        if (!customerPauseSpawn) {
            playpauseCustomerButton.setText("Play customer");
            customerPauseSpawn = true;
            log("Customer pause spawn...");
        } else {
            playpauseCustomerButton.setText("Pause customer");
            customerPauseSpawn = false;
            log("Customer continue spawn...");
        }
    }

    @FXML
    private void showDetailsOnClick(ActionEvent event) {
        sanityLog("Available cups: " + cupboard.getCup());
        sanityLog("Available glass: " + cupboard.getGlass());
        
        for (int i = 0; i < tableNumbers; i++) {
            Tables t = tables.get(i);
            sanityLog("Table " + (i+1) + " unit: " + t.getUnit());
            sanityLog("Glass: " + t.getGlasses());
            sanityLog("Cups: " + t.getCups());
        }
        
        sanityLog("Landlord served: " + landlord.getServed() + " customers.");
        sanityLog("Barmaid served : " + barmaid.getServed() + " customers.");
        
        for (int i = prev_sanity_loc; i < mySanityLog.size(); i++) {
            sanity_check_area.appendText(mySanityLog.get(i));
            prev_sanity_loc++;
        } 
    }

    @FXML
    private void playpauseAssistantOnClick(ActionEvent event) {
        if (assistant.isIdle()) {
            playpauseAssistantButton.setText("Pause Assistant");
            assistant.setIdle(false);
            log("Assistant play spawn...");
        } else {
            playpauseAssistantButton.setText("Play Assistant");
            assistant.setIdle(true);
            log("Assistant pause spawn...");
        }
    }

    @FXML
    private void lastOrderOnClick(ActionEvent event) {
        if (!lastOrder) {
            lastOrder = true;
        }
    }

    @FXML
    private void setCustomerOnClick(ActionEvent event) {
        // Customer
        if (isValidInt(numOfCustomerTF.getText())) {
            int value = Integer.parseInt(numOfCustomerTF.getText());
            
            conf.setNumberOfCustomerEntering(value);
        }
        
        if (isValidInt(drinkingTimeTF.getText())) {
            int value = Integer.parseInt(drinkingTimeTF.getText());
            
            conf.setdrinkingTime(value);
        }        
    }

    @FXML
    private void setAssistantOnClick(ActionEvent event) {
        // Assistant
        if (isValidInt(washGlassTimeTF.getText())) {
            int value = Integer.parseInt(washGlassTimeTF.getText());
            
            conf.setWashGlassTime(value);
        }
        if (isValidInt(washCupTimeTF.getText())) {
            int value = Integer.parseInt(washCupTimeTF.getText());
            
            conf.setWashCupTime(value);
        }
        if (isValidInt(washRoundsTimeTF.getText())) {
            int value = Integer.parseInt(washRoundsTimeTF.getText());
            
            conf.setWashRoundsTime(value);
        }        
    }

    @FXML
    private void setCupboardOnClick(ActionEvent event) {
        // Cupboard
        if (isValidInt(replaceGlassTimeTF.getText())) {
            int value = Integer.parseInt(replaceGlassTimeTF.getText());
            
            conf.setTimeReplaceGlass(value);
        }
        if (isValidInt(replaceCupTimeTF.getText())) {
            int value = Integer.parseInt(replaceCupTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchGlassTimeTF.getText())) {
            int value = Integer.parseInt(fetchGlassTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchCupTimeTF.getText())) {
            int value = Integer.parseInt(fetchCupTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchChocolateTimeTF.getText())) {
            int value = Integer.parseInt(fetchChocolateTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchMilkTimeTF.getText())) {
            int value = Integer.parseInt(fetchMilkTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchJuiceTimeTF.getText())) {
            int value = Integer.parseInt(fetchJuiceTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
        if (isValidInt(fetchCoffeeTimeTF.getText())) {
            int value = Integer.parseInt(fetchCoffeeTimeTF.getText());
            
            conf.setTimeReplaceCup(value);
        }
    }
}
