package lotto.domain;

public class User {

    private int purchaseAmount;

    public User(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    //메세지를 던지도록 설계하는게 좋음
    public int getPurchaseAmount() {
        return purchaseAmount;
    }

}
