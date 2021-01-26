package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.Item;
import com.sql_calendar.resources.OrderItem;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * New Item Popup Controller
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class NewItemPopupController implements Initializable {
    String selectedItem, itemID;
    double price;
    ArrayList<Item> list = new ArrayList<>();
    ArrayList<OrderItem> order = new ArrayList<>();

    @FXML
    Label quantity;
    @FXML
    FlowPane flowPane;

    private CashingController parentController;

    private VBox parentVbox;

    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }

    public void setParentVbox(VBox box) {
        this.parentVbox = box;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantity.setText("1");
        renderPopupNewItem();
    }

    // render New Item Popup, make request to server
    private void renderPopupNewItem() {
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        GetRequestModel request = new GetRequestModel();
                        String parameter = "itemName=all";
                        list = request.makeRequest("/cashier/item/all", Item.class, parameter);
                        for (Item data : list) {
                            JFXButton productName = new JFXButton(data.getItemName());
                            productName.setStyle("-fx-background-color:  #7a7b6d;-fx-background-radius:20;-fx-text-fill: white;-fx-font-size: 12");
                            productName.setPadding(new Insets(10));
                            productName.setPrefSize(300, 20);
                            productName.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                                // get Item info from JFXButton clicked
                                @Override
                                public void handle(ActionEvent event) {
                                    selectedItem = productName.getText();
                                    for (Item data : list) {
                                        if (productName.getText().equals(data.getItemName())) {
                                            price = Double.parseDouble(data.getPrice());
                                            itemID = data.getItemID();
                                        }
                                    }
                                }
                            });
                            flowPane.getChildren().add(productName);
                        }
                    }
                });
            }
        });
        makeRequest.start();
    }

    // quantity -1
    public void minusButton() {
        int a = Integer.parseInt(quantity.getText()) - 1;
        a = a < 1 ? 1 : a;
        quantity.setText(String.valueOf(a));
    }

    // quantity +1
    public void plusButton() {
        int a = Integer.parseInt(quantity.getText()) + 1;
        quantity.setText(String.valueOf(a));
    }

    // "Confirm" button in New Item Popup
    public void confirmButton() {
        System.out.println("====== UPDATE ORDER ======");
        addItemtoOrderList();
        for (OrderItem data : order)
            System.out.println(data);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxOrderDel.fxml"));
        try {
            HBox box = loader.load();
            HBoxOrderDelController controller = loader.getController();
            controller.setTag(selectedItem, Integer.parseInt(quantity.getText()), price);
            controller.setPopupController(this);
            controller.setParentVbox(parentVbox);
            controller.setParentController(parentController);

            parentVbox.getChildren().add(box);
            parentController.changeTotal(price * Integer.parseInt(quantity.getText()));
            quantity.setText(String.valueOf(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemtoOrderList() {
        boolean i = true;
        if (!order.isEmpty()) {
            for (OrderItem data : order) {
                if (itemID.equals(data.getItemID())) {
                    data.setQuantity(String
                            .valueOf(Integer.parseInt(data.getQuantity()) + Integer.parseInt(quantity.getText())));
                    i = false;
                    break;
                }
            }
            if (i) {
                order.add(new OrderItem(itemID, quantity.getText()));
            }
        } else {
            order.add(new OrderItem(itemID, quantity.getText()));
        }
    }

    public void deleteItemFromOrderList(int quan, String itemName) {
        System.out.println("====== DELETE ORDER ======");
        for (Item data : list) {
            if (itemName.equals(data.getItemName())) {
                for (OrderItem dataInOrder : order) {
                    if (data.getItemID().equals(dataInOrder.getItemID())) {
                        dataInOrder.setQuantity(String.valueOf(Integer.parseInt(dataInOrder.getQuantity()) - quan));
                        if (dataInOrder.getQuantity().equals("0")) {
                            order.remove(dataInOrder);
                        }
                        break;
                    }
                }
            }
        }
        for (OrderItem dataInOrder : order) {
            System.out.println(dataInOrder);
        }
    }

    // "Cancel" order button is clicked
    public void deleteAllItem() {
        order.removeAll(order);
    }

    // Send order to Cashing Controller to send to the Server
    public ArrayList<OrderItem> getOrder() {
        return (order);
    }
}