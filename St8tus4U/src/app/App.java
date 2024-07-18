package app;

import javax.swing.SwingUtilities;
import model.UserModel;
import controller.Controller;
import view.AppView;

public class App {
	public static void main(String[] args) {

		UserModel userModel = new UserModel();
		Controller controller = new Controller(userModel);

		SwingUtilities.invokeLater(() -> new AppView(controller));

	}

}
