package demomavinfx;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    TextField nameField, mobileField, toppingsField;
    CheckBox sizeXL, sizeL, sizeM, sizeS;
    TableView<PizzaOrder> table;
    ObservableList<PizzaOrder> orderList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pizza Ordering - Suman Khadayat");

        // Input Fields
        Label nameLabel = new Label("Customer Name:");
        nameField = new TextField();

        Label mobileLabel = new Label("Mobile Number:");
        mobileField = new TextField();

        Label sizeLabel = new Label("Choose Size:");
        sizeXL = new CheckBox("XL");
        sizeL = new CheckBox("L");
        sizeM = new CheckBox("M");
        sizeS = new CheckBox("S");

        Label toppingsLabel = new Label("Number of Toppings:");
        toppingsField = new TextField();

        // Removed Total Bill label and field

        // Buttons
        Button createButton = new Button("Create");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button readButton = new Button("Read");
        Button clearButton = new Button("Clear");

        // Table View
        table = new TableView<>();
        orderList = FXCollections.observableArrayList();

        TableColumn<PizzaOrder, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PizzaOrder, String> mobileCol = new TableColumn<>("Mobile");
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        TableColumn<PizzaOrder, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<PizzaOrder, Integer> toppingsCol = new TableColumn<>("Toppings");
        toppingsCol.setCellValueFactory(new PropertyValueFactory<>("toppings"));

        TableColumn<PizzaOrder, Double> billCol = new TableColumn<>("Total Bill");
        billCol.setCellValueFactory(new PropertyValueFactory<>("totalBill"));

        table.getColumns().addAll(nameCol, mobileCol, sizeCol, toppingsCol, billCol);
        table.setItems(orderList);

        // Button Events
        createButton.setOnAction(e -> createOrder());
        updateButton.setOnAction(e -> updateOrder());
        deleteButton.setOnAction(e -> deleteOrder());
        readButton.setOnAction(e -> readOrder());
        clearButton.setOnAction(e -> clearFields());

        // Layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(mobileLabel, 0, 1);
        grid.add(mobileField, 1, 1);

        grid.add(sizeLabel, 0, 2);
        HBox sizeBox = new HBox(10, sizeXL, sizeL, sizeM, sizeS);
        grid.add(sizeBox, 1, 2);

        grid.add(toppingsLabel, 0, 3);
        grid.add(toppingsField, 1, 3);

        HBox buttonBox = new HBox(10, createButton, updateButton, deleteButton, readButton, clearButton);
        grid.add(buttonBox, 1, 4);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(new Label("Pizza Ordering System - Suman Khadayat"), grid, table);

        Scene scene = new Scene(layout, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // CRUD Methods
    private void createOrder() {
        try {
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String size = getSize();
            int toppings = Integer.parseInt(toppingsField.getText());

            if (size.equals("None")) {
                showAlert("Error", "Please select a pizza size.");
                return;
            }

            double bill = calculateTotalBill(size, toppings);
            PizzaOrder order = new PizzaOrder(name, mobile, size, toppings, bill);
            orderList.add(order);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "Toppings must be a valid number.");
        } catch (Exception e) {
            showAlert("Error", "Invalid input. Please check all fields.");
        }
    }

    private void updateOrder() {
        PizzaOrder selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String name = nameField.getText();
                String mobile = mobileField.getText();
                String size = getSize();
                int toppings = Integer.parseInt(toppingsField.getText());

                if (size.equals("None")) {
                    showAlert("Error", "Please select a pizza size.");
                    return;
                }

                double bill = calculateTotalBill(size, toppings);
                selected.setName(name);
                selected.setMobile(mobile);
                selected.setSize(size);
                selected.setToppings(toppings);
                selected.setTotalBill(bill);
                table.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Error", "Toppings must be a valid number.");
            } catch (Exception e) {
                showAlert("Error", "Invalid input. Please check all fields.");
            }
        }
    }

    private void deleteOrder() {
        PizzaOrder selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            orderList.remove(selected);
            clearFields();
        }
    }

    private void readOrder() {
        PizzaOrder selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            mobileField.setText(selected.getMobile());
            toppingsField.setText(String.valueOf(selected.getToppings()));
            selectSize(selected.getSize());
        }
    }

    private void clearFields() {
        nameField.clear();
        mobileField.clear();
        toppingsField.clear();
        sizeXL.setSelected(false);
        sizeL.setSelected(false);
        sizeM.setSelected(false);
        sizeS.setSelected(false);
    }

    // Helper Methods
    private String getSize() {
        if (sizeXL.isSelected()) return "XL";
        if (sizeL.isSelected()) return "L";
        if (sizeM.isSelected()) return "M";
        if (sizeS.isSelected()) return "S";
        return "None";
    }

    private void selectSize(String size) {
        sizeXL.setSelected(size.equals("XL"));
        sizeL.setSelected(size.equals("L"));
        sizeM.setSelected(size.equals("M"));
        sizeS.setSelected(size.equals("S"));
    }

    public static double calculateTotalBill(String pizzaSize, int numberOfToppings) {
        double basePrice;
        switch (pizzaSize.toUpperCase()) {
            case "XL": basePrice = 15.00; break;
            case "L":  basePrice = 12.00; break;
            case "M":  basePrice = 10.00; break;
            case "S":  basePrice = 8.00; break;
            default: basePrice = 0.0;
        }

        double toppingCost = numberOfToppings * 1.50;
        double subtotal = basePrice + toppingCost;
        double hst = subtotal * 0.15;
        return Math.round((subtotal + hst) * 100.0) / 100.0;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // PizzaOrder Class
    public static class PizzaOrder {
        private String name;
        private String mobile;
        private String size;
        private int toppings;
        private double totalBill;

        public PizzaOrder(String name, String mobile, String size, int toppings, double totalBill) {
            this.name = name;
            this.mobile = mobile;
            this.size = size;
            this.toppings = toppings;
            this.totalBill = totalBill;
        }

        public String getName() { return name; }
        public String getMobile() { return mobile; }
        public String getSize() { return size; }
        public int getToppings() { return toppings; }
        public double getTotalBill() { return totalBill; }

        public void setName(String name) { this.name = name; }
        public void setMobile(String mobile) { this.mobile = mobile; }
        public void setSize(String size) { this.size = size; }
        public void setToppings(int toppings) { this.toppings = toppings; }
        public void setTotalBill(double totalBill) { this.totalBill = totalBill; }
    }
}
