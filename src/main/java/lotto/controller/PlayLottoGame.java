package lotto.controller;

import lotto.domain.Game;
import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.User;
import lotto.service.GameService;
import lotto.service.LottoService;
import lotto.utils.Parser;
import lotto.validation.Validator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.HashMap;
import java.util.List;

public class PlayLottoGame {

    private final GameService gameService = new GameService();
    private final LottoService lottoService = new LottoService();
    private final InputView inputView = new InputView();
    private final Validator validator = new Validator();
    private final OutputView outputView = new OutputView();
    private final Parser parser = new Parser();

    private List<Integer> winningNumbers;
    private User user;
    private Game game;

    private static final int LEAST_AMOUNT = 1000;

    public void runLottoGame(){
        beforeStartGame();
        createNewGame();
        showGameResult();
    }
    private void beforeStartGame() {
        user = new User(inputUserAmount());
        int userAmount = user.getPurchaseAmount();
        lottoService.buyLottoAtOnce(userAmount);
        showPurchaseResult();
    }
    private Game createNewGame(){
        game = new Game(inputWinningNumbers(), inputBonusNumber());
        return game;
    }

    private void showGameResult() {
        outputView.winningStatistics();
        gameService.initLottoResult();
        HashMap<Rank, Integer> result = gameService.checkUserLotteries(game,lottoService.getPurchasedLotteries());
        outputView.showRankResult(result);
        int userAmount = user.getPurchaseAmount();
        outputView.showRateOfReturn(gameService.getRateOfReturn(userAmount));
    }


    private int inputUserAmount(){
        while (true){
            try {
                String userInput = inputView.inputPurchaseAmount();
                int amount = isValidPurchaseAmount(userInput);
                return amount;
            }catch (IllegalArgumentException e){
                return inputUserAmount();
            }
        }
    }

    private void showPurchaseResult(){
        outputView.purchaseMessage(user.getPurchaseAmount() / LEAST_AMOUNT);
        for(Lotto lotto : lottoService.getPurchasedLotteries()){
            outputView.lottoNumbers(lotto.getNumbers());
        }
    }
    private List<Integer> inputWinningNumbers(){
        if(game != null){
            return game.getWinningNumbers();
        }
        while (true){
            try {
                String userInput = inputView.inputWinningNumbers();
                winningNumbers = validator.isValidWinningNumbers(parser.parseInputWinningNumbers(userInput));
                return winningNumbers;
            }catch (IllegalArgumentException e){
                return inputWinningNumbers();
            }
        }
    }

    //통과
    private int inputBonusNumber(){
        if(game != null){
            return game.getBonusNumber();
        }
        while (true){
            try {
                String userInput = inputView.inputBonusNumber();
                validator.isNumberCharInteger(userInput);
                int bonusNumber = parser.parseInputStringNumber(userInput);
                validator.isValidBonusNumber(winningNumbers,bonusNumber);
                return bonusNumber;
            }catch (IllegalArgumentException e){
                return inputBonusNumber();
            }
        }
    }

    public int isValidPurchaseAmount(String amount) {
        validator.isNumberCharInteger(amount);
        int number = parser.parseInputStringNumber(amount);
        validator.isMultipleNumber(number);
        return number;
        }

}
