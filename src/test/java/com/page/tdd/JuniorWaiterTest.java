package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.QRCodeHadUsedException;
import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.*;


public class JuniorWaiterTest {

    @Nested
    class JuniorWaiterStoreTest {
        @Test
        void should_store_in_the_first_store_content_ark_and_got_a_qr_code_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_10_space() {
            Bag bag = new Bag();
            StoreContentArk firstStoreContentArk = new StoreContentArk(10);
            StoreContentArk secondStoreContentArk = new StoreContentArk(10);
            JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

            StoreResult storeResult = juniorWaiter.storeAndGivingCard(bag);

            then(storeResult).isNotNull();
            then(firstStoreContentArk.pickUp(storeResult.getQrCode())).isEqualTo(bag);
        }

        @Test
        void should_store_in_the_second_store_content_ark_and_got_a_qr_code_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_1_space_and_first_one_is_full() {
            Bag bag = new Bag();
            StoreContentArk firstStoreContentArk = new StoreContentArk(1);
            StoreContentArk secondStoreContentArk = new StoreContentArk(1);
            firstStoreContentArk.store(new Bag());
            JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

            StoreResult storeResult = juniorWaiter.storeAndGivingCard(bag);

            then(storeResult).isNotNull();
            then(secondStoreContentArk.pickUp(storeResult.getQrCode())).isEqualTo(bag);
        }

        @Test
        void should_store_fail_when_store_given_a_bag_and_a_junior_waiter_and_2_store_content_arks_with_1_space_and_all_store_content_arks_are_full() {
            Bag bag = new Bag();
            StoreContentArk firstStoreContentArk = new StoreContentArk(1);
            firstStoreContentArk.store(new Bag());
            StoreContentArk secondStoreContentArk = new StoreContentArk(1);
            secondStoreContentArk.store(new Bag());
            JuniorWaiter juniorWaiter = new JuniorWaiter(firstStoreContentArk, secondStoreContentArk);

            thenThrownBy(() -> juniorWaiter.storeAndGivingCard(bag))
                    .isInstanceOf(StoreContentArkFullException.class);
        }

        @Test
        void should_store_fail_when_store_given_a_bag_and_a_junior_waiter_and_without_store_content_ark() {
            Bag bag = new Bag();
            JuniorWaiter juniorWaiter = new JuniorWaiter();

            thenThrownBy(() -> juniorWaiter.storeAndGivingCard(bag))
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
        void should_invoked_store_and_clock_in_and_pick_up_a_card_when_store_given_a_bag_and_a_junior_waiter() {
            Bag bag = new Bag();
            JuniorWaiter juniorWaiter = Mockito.mock(JuniorWaiter.class);
            doCallRealMethod().when(juniorWaiter).storeAndGivingCard(bag);
            when(juniorWaiter.store(bag)).thenReturn(new QRCode());

            juniorWaiter.storeAndGivingCard(bag);

            verify(juniorWaiter).store(bag);
            verify(juniorWaiter).clockIn();
            verify(juniorWaiter).pickUpOneCard();
        }

        @Test
        void should_get_1_record_when_store_given_a_bag_and_a_junior_waiter_and_a_store_content_ark_with_1_space() {
            Bag bag = new Bag();
            JuniorWaiter juniorWaiter = new JuniorWaiter(new StoreContentArk(1));
            int recordAmount = juniorWaiter.getRecords().size();

            juniorWaiter.storeAndGivingCard(bag);

            then(juniorWaiter.getRecords().size()).isEqualTo(recordAmount + 1);
        }

        @Test
        void should_get_1_record_when_store_given_a_bag_and_a_junior_waiter_and_a_store_content_ark_with_1_space_and_had_store_a_bag_in_it() {
            Bag bag = new Bag();
            JuniorWaiter juniorWaiter = new JuniorWaiter(new StoreContentArk(1));
            juniorWaiter.storeAndGivingCard(new Bag());
            int recordAmount = juniorWaiter.getRecords().size();

            thenThrownBy(() -> juniorWaiter.storeAndGivingCard(bag))
                    .isInstanceOf(StoreContentArkFullException.class);
            then(juniorWaiter.getRecords().size()).isEqualTo(recordAmount);
        }

        @Test
        void should_get_a_card_when_store_given_a_bag_and_a_junior_waiter_and_a_store_content_ark_with_1_space_and_a_card() {
            Bag bag = new Bag();
            StoreContentArk storeContentArk = new StoreContentArk(1);
            Card card = new Card();
            List<Card> cards = Collections.singletonList(card);
            JuniorWaiter juniorWaiter = new JuniorWaiter(cards, storeContentArk);

            StoreResult storeResult = juniorWaiter.storeAndGivingCard(bag);

            then(storeResult.getCard()).isEqualTo(card);
        }
    }

    @Nested
    class JuniorWaiterPickUpTest {
        @Test
        void should_pick_up_fail_when_pick_up_given_a_used_qr_code_and_a_junior_waiter_and_a_store_content_ark() {
            Bag bag = new Bag();
            StoreContentArk storeContentArk = new StoreContentArk(1);
            JuniorWaiter juniorWaiter = new JuniorWaiter(storeContentArk);
            StoreResult storeResult = juniorWaiter.storeAndGivingCard(bag);
            juniorWaiter.pickUp(storeResult.getQrCode());

            thenThrownBy(() -> juniorWaiter.pickUp(storeResult.getQrCode()))
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
}
