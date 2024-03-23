import com.data.LoadDataEngine;
import com.data.RedisManager;
import com.data.TestData;
import org.example.engine.AutoRegisterEngine;
import org.example.engine.GetPriceEngine;
import org.example.monitor.MonitorImpl;
import org.example.utils.Constants;
import org.example.utils.PropertyUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        args = new String[]{"MONITOR", "/Users/nannan/IdeaProjects/TaoProject/src/main/resources/application.property"};

        if (args == null || args.length < 2) {
            System.out.println("Missing argument, the first argument is GET_PRICE,AUTO_REGISTER or MONITOR. The second argument is property file path");
        }
        Constants.CONFIG_FILE = args[1];
        String[] subnetIdArray;
        switch (args[0]) {
            case "GET_PRICE":
                subnetIdArray = PropertyUtils.getProperty("get.price.subnetId").split(",");
                GetPriceEngine.startRunningGetPrice(Arrays.asList(subnetIdArray));
                break;
            case "AUTO_REGISTER":
                subnetIdArray = PropertyUtils.getProperty("register.subnetId").split(",");
                AutoRegisterEngine.startRunningAutoRegisterEngine(Arrays.asList(subnetIdArray));
                break;
            case "MONITOR":
                MonitorImpl monitor = new MonitorImpl();
                monitor.run();
                break;
            case "LOAD_C4":
                String filePath = PropertyUtils.getProperty("c4.file.path");
                RedisManager.load(filePath);
                break;
            case "LOAD_ALL_C4":
                LoadDataEngine.assignWorkAndStart();
            case "TEST_DATA":
                TestData.test();
                break;
            default:
                break;
        }

    }
}
