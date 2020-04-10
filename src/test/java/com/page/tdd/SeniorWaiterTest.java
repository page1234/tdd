package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.verify;

public class SeniorWaiterTest {

    @Test
    void should_invoked_store_and_clock_in_and_pick_up_one_card_then_store_and_giving_card_given_a_bag_and_a_senior_waiter() throws Exception {
        Bag bag = new Bag();
        SeniorWaiter seniorWaiter = Mockito.mock(SeniorWaiter.class);
        Mockito.doCallRealMethod()
                .when(seniorWaiter)
                .storeAndGivingCard(bag);

        seniorWaiter.storeAndGivingCard(bag);

        verify(seniorWaiter).store(bag);
        verify(seniorWaiter).clockIn();
        verify(seniorWaiter).pickUpOneCard();
    }

    @Test
    void should_got_a_qr_code_when_store_and_giving_card_given_a_bag_and_a_senior_waiter_and_a_store_content_ark_with_1_space_and_a_store_content_ark_with_2_space() throws Exception {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(2);
        List<StoreContentArk> storeContentArks = Arrays.asList(new StoreContentArk(1), storeContentArk);
        SeniorWaiter seniorWaiter = new SeniorWaiter(storeContentArks);

        StoreResult storeResult = seniorWaiter.storeAndGivingCard(bag);

        then(storeResult).isNotNull();
        QRCode qrCode = storeResult.getQrCode();
        then(qrCode).isNotNull();
        then(storeContentArk.pickUp(qrCode)).isEqualTo(bag);
    }

    @Test
    void should_got_a_qr_code_when_store_and_giving_card_given_a_bag_and_a_senior_waiter_and_2_store_content_arks_with_1_space() throws Exception {
        Bag bag = new Bag();
        StoreContentArk firstStoreContentArk = new StoreContentArk(1);
        StoreContentArk secondStoreContentArk = new StoreContentArk(1);
        SeniorWaiter seniorWaiter = new SeniorWaiter(Arrays.asList(firstStoreContentArk, secondStoreContentArk));

        StoreResult storeResult = seniorWaiter.storeAndGivingCard(bag);

        then(firstStoreContentArk.pickUp(storeResult.getQrCode())).isEqualTo(bag);
    }

    @Test
    void should_store_fail_when_store_and_giving_card_given_a_bag_and_a_senior_waite_and_2_full_store_content_arks() {
        Bag bag = new Bag();
        List<StoreContentArk> storeContentArks = Arrays.asList(new StoreContentArk(0), new StoreContentArk(0));
        SeniorWaiter seniorWaiter = new SeniorWaiter(storeContentArks);

        thenThrownBy(() -> seniorWaiter.storeAndGivingCard(bag))
                .isInstanceOf(StoreContentArkFullException.class);
    }

    @Test
    void should_store_fail_when_store_and_giving_card_given_a_bag_and_a_senior_waiter_and_without_store_content_ark() {
        Bag bag = new Bag();
        SeniorWaiter seniorWaiter = new SeniorWaiter();

        thenThrownBy(() -> seniorWaiter.storeAndGivingCard(bag))
                .isInstanceOf(StoreBagFailException.class);
    }

    @Test
    void should_get_clock_in_amount_is_1_when_get_records_given_a_bag_and_a_senior_waiter_and_a_store_content_ark_with_1_space_and_store_bag_in_it() throws Exception {
        Bag bag = new Bag();
        SeniorWaiter seniorWaiter = new SeniorWaiter(Collections.singletonList(new StoreContentArk(1)));
        seniorWaiter.storeAndGivingCard(bag);

        List<Record> records = seniorWaiter.getRecords();

        then(records.size()).isEqualTo(1);
    }

    @Test
    void should_get_a_card_when_store_and_giving_card_given_a_bag_and_a_senior_waiter_and_a_store_content_ark_with_1_space_and_a_card() throws Exception {
        Bag bag = new Bag();
        Card card = new Card();
        SeniorWaiter seniorWaiter = new SeniorWaiter(
                Collections.singletonList(card),
                Collections.singletonList(new StoreContentArk(1)));

        StoreResult storeResult = seniorWaiter.storeAndGivingCard(bag);

        then(storeResult.getCard()).isEqualTo(card);
    }
}
