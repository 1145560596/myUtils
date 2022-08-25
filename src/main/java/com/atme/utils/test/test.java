package com.atme.utils.test;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author amao
 * @create 2022-07-13-18:22
 */
@Data
class Testssssss {

    private Double aDouble;

    public static void main(String[] args) {
        Double weekStock = new Double(0.00);
        weekStock += weekStock + new Double(1.1);

    }


    @Override
    public String toString() {
        return "Test{" +
                "aDouble=" + aDouble +
                '}';
    }
}
