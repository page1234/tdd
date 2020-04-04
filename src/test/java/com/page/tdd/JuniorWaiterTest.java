package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.QRCodeHadUsedException;
import com.page.tdd.exception.StoreBagFailException;
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

    @Test
    void should_store_fail_when_store_given_a_bag_and_a_junior_waiter_and_without_store_content_ark() {
        Bag bag = new Bag();
        JuniorWaiter juniorWaiter = new JuniorWaiter();

        thenThrownBy(() -> juniorWaiter.store(bag))
                .isInstanceOf(StoreBagFailException.class);
    }

    @Test
    void should_got_bag_when_pick_up_given_a_qr_code_and_a_junior_waiter_and_a_store_content_ark() {
        Bag bag = new Bag();
        StoreContentArk firstStoreContentArk = new StoreContentArk(1);
        StoreContentArk secondStoreContentArk = new StoreContentArk(1);
        QRCode qrcode = secondStoreContentArk.store(bag);
        JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

        Bag actualBag = juniorWaiter.pickUp(qrcode);

        then(actualBag).isEqualTo(bag);
    }

    @Test
    void should_pick_up_fail_when_pick_up_given_a_used_qrcode_and_a_junior_waiter_and_a_store_content_ark() {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(1);
        JuniorWaiter juniorWaiter = new JuniorWaiter(storeContentArk);
        QRCode qrCode = juniorWaiter.store(bag);
        juniorWaiter.pickUp(qrCode);

        thenThrownBy(() -> juniorWaiter.pickUp(qrCode))
                .isInstanceOf(QRCodeHadUsedException.class);
    }

    @Test
    void should_pick_up_fail_when_pick_up_given_invalid_qr_code_and_a_junior_waiter_and_a_store_content_ark() {
        QRCode qrCode = new QRCode();
        JuniorWaiter juniorWaiter = new JuniorWaiter(new StoreContentArk(1));

        thenThrownBy(() -> juniorWaiter.pickUp(qrCode))
                .isInstanceOf(InvalidQRCodeException.class);
    }

    @Test
    void should_pick_up_fail_when_pick_up_given_a_qr_code_and_a_junior_waiter_and_a_junior_waiter_managed_store_content_ark_and_qr_code_got_from_other_store_content_ark() {
        QRCode qrCode = new StoreContentArk(1).store(new Bag());
        JuniorWaiter juniorWaiter = new JuniorWaiter(new StoreContentArk(1));

        thenThrownBy(() -> juniorWaiter.pickUp(qrCode))
                .isInstanceOf(InvalidQRCodeException.class);
    }


}
