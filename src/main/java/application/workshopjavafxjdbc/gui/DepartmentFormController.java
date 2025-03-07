package application.workshopjavafxjdbc.gui;

import application.workshopjavafxjdbc.db.DbException;
import application.workshopjavafxjdbc.gui.listeners.DataChangeListener;
import application.workshopjavafxjdbc.gui.util.Alerts;
import application.workshopjavafxjdbc.gui.util.Constraints;
import application.workshopjavafxjdbc.gui.util.Utils;
import application.workshopjavafxjdbc.model.entities.Department;
import application.workshopjavafxjdbc.model.exceptions.ValidationException;
import application.workshopjavafxjdbc.model.services.DepartmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null){
            throw  new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try{
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (ValidationException e ) {
            setErrorMessages(e.getErrors());
        }
        catch (DbException e){
            Alerts.showAlert("Erros saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Department getFormData() {
        Department obj = new Department();

        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().isEmpty()){
            exception.addError("name", "Field can´t be empty");
        }
        obj.setName(txtName.getText());

        if (!exception.getErrors().isEmpty()) {
            throw  exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();

    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName,30);
    }

    public void updateFormData() {
        if (entity == null){
            throw  new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }

    private void setErrorMessages(Map<String,String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
}
