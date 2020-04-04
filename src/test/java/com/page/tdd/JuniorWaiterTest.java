package com.page.tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class JuniorWaiterTest {

    @Test
    void should_store_in_the_first_store_content_ark_and_got_a_qr_code_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_10_space() {
        Bag bag = new Bag();
        StoreContentArk firstStoreContentArk = new StoreContentArk(10);
        StoreContentArk secondStoreContentArk = new StoreContentArk(10);
        JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

        QRCode qrCode = juniorWaiter.store(bag);

        then(qrCode).isNotNull();
        then(firstStoreContentArk.pickUp(qrCode)).isEqualTo(bag);
    }
}
