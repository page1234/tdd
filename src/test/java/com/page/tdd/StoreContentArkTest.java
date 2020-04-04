package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.QRCodeHadUsedException;
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

    @Test
    void should_pick_up_fail_when_pick_up_given_a_used_qrcode_and_a_store_content_ark() {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(10);
        QRCode qrCode = storeContentArk.store(bag);
        storeContentArk.pickUp(qrCode);

        assertThatThrownBy(()->storeContentArk.pickUp(qrCode))
                .isInstanceOf(QRCodeHadUsedException.class);
    }

    @Test
    void should_pick_up_fail_when_pick_up_given_a_invalid_qrcode_and_a_store_content_ark() {
        StoreContentArk storeContentArk = new StoreContentArk(10);
        QRCode qrCode = new QRCode();

        assertThatThrownBy(()->storeContentArk.pickUp(qrCode))
                .isInstanceOf(InvalidQRCodeException.class);
    }

    @Test
    void should_return_false_when_check_is_full_given_a_store_content_ark_with_2_space() {
        StoreContentArk storeContentArk = new StoreContentArk(2);

        boolean isFull = storeContentArk.isFull();

        then(isFull).isFalse();
    }

    @Test
    void should_return_false_when_check_is_full_given_a_store_content_ark_with_2_space_and_store_a_bag_in_it() {
        StoreContentArk storeContentArk = new StoreContentArk(2);
        storeContentArk.store(new Bag());

        boolean isFull = storeContentArk.isFull();

        then(isFull).isFalse();
    }

    @Test
    void should_return_true_when_check_is_full_given_a_store_content_ark_with_1_space_and_store_a_bag_in_it() {
        StoreContentArk storeContentArk = new StoreContentArk(1);
        storeContentArk.store(new Bag());

        boolean isFull = storeContentArk.isFull();

        then(isFull).isTrue();
    }
}
