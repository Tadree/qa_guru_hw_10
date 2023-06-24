package ru.jiehk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.jiehk.model.Order;

import java.io.File;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JsonTest {

    ClassLoader cl = JsonTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void jsonTestWithModel() throws Exception{
        File file = new File("src/test/resources/order.json");
        Order order = objectMapper.readValue(file, Order.class);
        assertThat(order.orderID).isEqualTo(213);
        assertThat(order.orderDate).isEqualTo("12.12.2021");
        assertThat(order.orderItems.get(0).productID).isEqualTo(1212);
        assertThat(order.orderItems.get(1).productID).isEqualTo(3423);
    }
}
