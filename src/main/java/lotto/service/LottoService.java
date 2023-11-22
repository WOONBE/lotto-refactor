package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.User;
import lotto.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private final ArrayList<Lotto> purchasedLotteries = new ArrayList<>();

    private static final int LEAST_AMOUNT = 1000;
    private static final int MIN_NUMBER = 0;
    private static final int COUNT_ONE = 1;


    public void buyLottoAtOnce(int amount){
        int count = amount / LEAST_AMOUNT;
        while (count > 0){
            count--;
            buyOneLotto();
        }
    }

    public void buyOneLotto(){
        Lotto lotto = generateLottoNumber();
        buyLotto(lotto);
    }

    public void buyLotto(Lotto lotto) {
        purchasedLotteries.add(lotto);
    }
    public ArrayList<Lotto> getPurchasedLotteries() {
        return purchasedLotteries;
    }


    //Lotto안에 넣어야 자동 검증
    private Lotto generateLottoNumber(){
        List<Integer> numbers = new ArrayList<>(Utils.generateRandomUniqueNumber());
        return new Lotto(numbers);
    }

    public int countContainNumber(List<Integer> list, int number){
        if(list.contains(number)){
            return COUNT_ONE;
        }
        return MIN_NUMBER;
    }
    public int countMatchNumber(List<Integer> list1, List<Integer> list2){
        int count = MIN_NUMBER;
        for(int number : list2){
            count += countContainNumber(list1,number);
        }
        return count;
    }




}
