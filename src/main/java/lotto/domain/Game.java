package lotto.domain;

import lotto.validation.Validator;

import java.util.List;

public class Game {

    private final Validator validator = new Validator();

    private final List <Integer> winningNumbers;
    private final int bonusNumber;

    public Game(List<Integer> winningNumbers, int bonusNumber) {
        validator.isValidWinningNumbers(winningNumbers);
        this.winningNumbers = winningNumbers;
        validator.isValidBonusNumber(winningNumbers,bonusNumber);
        this.bonusNumber = bonusNumber;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }


}
