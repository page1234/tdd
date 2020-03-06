package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

public class StoreContentArkTest {

    @Test
    void should_got_a_qrcode_when_store_bag_given_a_bag_and_a_store_content_ark_with_10_space() {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(10);

        QRCode qrCode = storeContentArk.store(bag);

        then(qrCode).isNotNull();
    }

    @Test
    void should_store_fail_when_store_bag_given_a_bag_and_a_full_store_content_ark() {
        StoreContentArk storeContentArk = new StoreContentArk(1);
        storeContentArk.store(new Bag());
        Bag bag = new Bag();

        assertThatThrownBy(() -> storeContentArk.store(bag))
                .isNotEqualTo(StoreBagFailException.class);

    }

    @Test
    void should_got_bag_when_pick_up_given_a_qrcode_and_a_store_content_ark() {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(10);
        QRCode qrCode = storeContentArk.store(bag);

        Bag actualBag = storeContentArk.pickUp(qrCode);

        then(actualBag).isEqualTo(bag);
    }

}
