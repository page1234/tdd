package com.page.tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class StoreContentArkTest {

    @Test
    void should_got_a_qrcode_when_store_bag_given_a_bag_and_a_store_content_ark_with_10_space() {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(10);

        QRCode qrCode = storeContentArk.store(bag);

        then(qrCode).isNotNull();
    }
}
