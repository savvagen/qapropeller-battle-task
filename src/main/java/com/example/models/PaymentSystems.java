package com.example.models;

public class PaymentSystems {

    public enum PaymentSystem {
        NONE("", ""),
        VISA("Visa", "1"),
        MASTER_CARD("MasterCard", "2"),
        APPLE_CARD("AppleCard", "3");

        public String name;
        public String value;

        PaymentSystem(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
