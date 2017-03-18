package sample;

import controllers.ControllerManager;
import controllers.Ctrl;
import controllers.FindAllRoutesCtrl;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Created by Роман on 04.03.2017.
 */
public class FindAllRoutesTask extends Task {
    private final FindAllRoutesCtrl controller = (FindAllRoutesCtrl) ControllerManager.getControllers().get("FindAllRoutesCtrl");
    @Override
    protected Void call() throws Exception {
        //System.out.println("starting findallroutes task");
        //UZApi.purchasesearchWithTransfers(controller.getFromId(),controller.getToId(),controller.getAcrossId(),controller.getAcross().getEditor().getText(),controller.getFrom().getEditor().getText(),controller.getTo().getEditor().getText(),controller.getDateDepartment());
        UZApi.purchasesearchWithAllTransfers(controller.getFromId(),controller.getToId(),controller.getFrom().getEditor().getText(),controller.getTo().getEditor().getText(),controller.getDateDepartment());
        //System.out.println("ending findallroutes task");
        Platform.runLater(new Runnable() {
            public void run() {
                controller.getSearchWithTransfersBtn().setDisable(false);
            }
        });
        return null;
    }
}
