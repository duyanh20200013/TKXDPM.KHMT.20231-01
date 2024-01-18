import auth.impl.SimpleAuthSubSystem;
import controller.AdminItemAdvice;
import controller.LoginControllerAuthBridge;
import domain.MediaRepo;
import domain.repo_impl.JdbcMediaRepo;
import ui.MessageDisplayer;
import ui.TopLevelFrame;
import ui.screen.ListMediaScreen;
import ui.screen.LoginScreen;
import ui.screen.MainScreen;
import ui.screen.MediaViewerScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ApplicationBootstrap {
    public static void main(String[] args) {
        MessageDisplayer messageDisplayer = new MessageDisplayer(null);
        AdminItemAdvice adminAdvice = new AdminItemAdvice();
        MediaRepo mediaRepo = new JdbcMediaRepo(()->openConnection()) {
        };
        try {
            SimpleAuthSubSystem simpleAuthSubSystem = new SimpleAuthSubSystem();
            LoginControllerAuthBridge loginControllerAuthBridge = new LoginControllerAuthBridge(simpleAuthSubSystem);
            TopLevelFrame topLevelFrame = new TopLevelFrame();
            topLevelFrame.getRandomContentNavigator().register(i->new LoginScreen(i, loginControllerAuthBridge, topLevelFrame.getMessageDisplayer()), LoginScreen.class);
            topLevelFrame.getRandomContentNavigator().register(i->new MediaViewerScreen(i, topLevelFrame.getMessageDisplayer()), MediaViewerScreen.class);
            topLevelFrame.getRandomContentNavigator().register(i->new MainScreen(i, mediaRepo, topLevelFrame.getWizardTopLevel()), MainScreen.class);
            topLevelFrame.getRandomContentNavigator().register(i->new ListMediaScreen(i, mediaRepo, topLevelFrame.getWizardTopLevel(), topLevelFrame.getMessageDisplayer(), adminAdvice), ListMediaScreen.class);
            topLevelFrame.getRandomContentNavigator().changeTo(MainScreen.class, Map.of());
            topLevelFrame.run();
        } catch (Throwable exception) {
            messageDisplayer.displayCriticalError(exception.getMessage());
            System.exit(-1);
        }
    }

    static private Connection openConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:assets\\aims.db");
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
