package com.page.tdd;

import com.page.tdd.exception.StoreContentArkFullException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

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

    @Test
    void should_store_in_the_second_store_content_ark_and_got_a_qr_code_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_1_space_and_first_one_is_full() {
        Bag bag = new Bag();
        StoreContentArk firstStoreContentArk = new StoreContentArk(1);
        StoreContentArk secondStoreContentArk = new StoreContentArk(1);
        firstStoreContentArk.store(new Bag());
        JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

        QRCode qrCode = juniorWaiter.store(bag);

        then(qrCode).isNotNull();
        then(secondStoreContentArk.pickUp(qrCode)).isEqualTo(bag);
    }

    @Test
    void should_store_fail_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_1_space_and_all_store_content_arks_are_full() {
        Bag bag = new Bag();
        StoreContentArk firstStoreContentArk = new StoreContentArk(1);
        firstStoreContentArk.store(new Bag());
        StoreContentArk secondStoreContentArk = new StoreContentArk(1);
        secondStoreContentArk.store(new Bag());
        JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

        thenThrownBy(() -> juniorWaiter.store(bag))
                .isInstanceOf(StoreContentArkFullException.class);
    }
}
