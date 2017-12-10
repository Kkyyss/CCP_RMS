/*
 * Main...
 */
package rms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rms.Consumer.AssistantConsumer;
import rms.Consumer.ClockConsumer;
import rms.Consumer.ServeConsumer;
import rms.Consumer.TablesConsumer;
import static rms.Model.Assistant.assistant;
import static rms.Model.Barmaid.barmaid;
import static rms.Model.Customer.customerQueue;
import rms.Model.Executor;
import static rms.Model.Landlord.landlord;
import rms.Model.Tables;
import static rms.Model.Tables.tables;
import static rms.MyUtils.MyConfig.conf;
import rms.Producer.CustomerProducer;

public class RMS extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/RMS.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        // Producers
        CustomerProducer cp = new CustomerProducer("Customer", customerQueue);

        // Consumers
        ServeConsumer scBarmaid = new ServeConsumer("Barmaid", barmaid, customerQueue);
        AssistantConsumer ac = new AssistantConsumer("Assistant", assistant, customerQueue);
        ServeConsumer scLandlord = new ServeConsumer("Landlord", landlord, scBarmaid, ac, customerQueue);
        ClockConsumer clock = new ClockConsumer("Clock", cp, ac, scLandlord, scBarmaid);

        // Create threads
        Thread customerProducerThread = new Thread(cp);
        Thread landlordThread = new Thread(scLandlord);
        Thread barmaidThread = new Thread(scBarmaid);
        Thread assistantThread = new Thread(ac);
        Thread clockThread = new Thread(clock);
        
        for (int i = 0; i < conf.getNumberOfTables(); i++) {
            Tables t = new Tables((i + 1) + "");
            t.setUnit(conf.getTableCapacity());
            tables.add(t);
            Executor.tablesExecutor.execute(new Thread(new TablesConsumer("Tables" + (i + 1), tables.get(i))));
        }
        
        Executor.clockExecutor.execute(clockThread);
        Executor.staffExecutor.execute(landlordThread);
        Executor.staffExecutor.execute(barmaidThread);
        Executor.staffExecutor.execute(assistantThread);
        Executor.customerProducerExecutor.execute(customerProducerThread);
        
        // stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
