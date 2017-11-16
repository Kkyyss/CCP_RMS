/*
 * Main...
 */
package rms;

import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rms.Consumer.AssistantConsumer;
import rms.Consumer.CustomerConsumer;
import rms.Consumer.TablesConsumer;
import static rms.Model.Assistant.assistant;
import static rms.Model.Barmaid.barmaid;
import static rms.Model.Customer.customerQueue;
import static rms.Model.Landlord.landlord;
import rms.Model.Tables;
import static rms.Model.Tables.tables;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyConfig.customerExecutor;
import static rms.MyUtils.MyConfig.staffExecutor;
import static rms.MyUtils.MyConfig.tablesExecutor;
import rms.Producer.CustomerProducer;

public class RMS extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/RMS.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        landlord.setName("Ali");
        barmaid.setName("Abu");
        Thread customerThread = new Thread(new CustomerProducer("Customer", customerQueue));
        Thread landlordThread = new Thread(new CustomerConsumer("Landlord", landlord, customerQueue));
        Thread barmaidThread = new Thread(new CustomerConsumer("Barmaid", barmaid, customerQueue));
        Thread assistantThread = new Thread(new AssistantConsumer("Assistant", assistant, customerQueue));
        staffExecutor = Executors.newFixedThreadPool(3);
        tablesExecutor = Executors.newFixedThreadPool(conf.getNumberOfTables());
        customerExecutor = Executors.newFixedThreadPool(1);
        
        for (int i = 0; i < conf.getNumberOfTables(); i++) {
            Tables t = new Tables();
            tables.add(new Tables());
            tablesExecutor.execute(new Thread(new TablesConsumer("Tables" + (i + 1), tables.get(i))));
        }
        
        staffExecutor.execute(landlordThread);
        staffExecutor.execute(barmaidThread);
        staffExecutor.execute(assistantThread);
        customerExecutor.execute(customerThread);
        
        // stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
