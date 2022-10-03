package baseball.controller;

import baseball.model.AnswerNumberVo;
import baseball.model.BaseballConstants;
import baseball.model.BaseballServiceImpl;
import baseball.model.CompareResultVo;
import baseball.model.GuessNumberVo;
import baseball.view.BaseballView;

/**
 * 숫자 야구 게임 실행 컨트롤러
 */
public class BaseballController {

    AnswerNumberVo answerNumberVo = null; // 프로그램에서 생성한 정답

    BaseballView baseballView = new BaseballView();
    BaseballServiceImpl baseballService = new BaseballServiceImpl();

    /**
     * 프로그램 시작
     * 게임을 시작하고, 게임이 종료되면 재시작/종료 여부를 확인
     */
    public void executeProgram() {
        boolean gameExecute = true;
        while (gameExecute) {
            startGame();
            gameExecute = askGameContinueOrNot();
        }
        return;
    }

    /**
     * 정답 생성 후 게임 진행 시작
     */
    public void startGame() {
        do {
            answerNumberVo = baseballService.createAnswer();
        } while (!baseballService.checkNumberValid(answerNumberVo.getAnswerNumber(), false));
        guessAnswerNumber();
    }

    /**
     * 게임 진행 (값 입력과 반복)
     * 사용자 추측값 입력받고 정답여부 확인 후
     * 정답일 경우 메소드 종료
     * 정답이 아닐 경우 위의 과정 반복
     *
     */
    public void guessAnswerNumber() {
        boolean guessCorrect = false;
        while (!guessCorrect) {
            //사용자에게 숫자를 입력 받는다
            GuessNumberVo guessNumberVo = baseballView.playerInput();
            //입력받은 숫자 유효성 확인
            baseballService.checkNumberValid(guessNumberVo.getGuessNumber(), true);
            //정답과 입력받은 값 비교 - 볼, 스트라이크 카운트 저장
            CompareResultVo compareResultVo = baseballService.compareNumber(answerNumberVo, guessNumberVo);
            //결과 출력
            baseballView.printGuessResult(compareResultVo);
            //정답여부 확인 (게임 승리 여부)
            guessCorrect = baseballService.checkNumberCorrect(compareResultVo);
        }
        System.out.println("3개의 숫자를 모두 맞히셨습니다.! 게임 종료");
    }

    /**
     * 사용자에게 게임 재시작/종료 여부 확인
     * 입력받은 값이 유효하지 않은 경우 다시 입력받아 확인
     *
     * @return 게임 재시작/종료 여부 (true: 재시작, false: 종료)
     */
    public boolean askGameContinueOrNot() {
        String continueType = BaseballConstants.NOT_DEFINED;
        boolean continueOrNot = false;
        while (continueType.equals(BaseballConstants.NOT_DEFINED)) {
            continueType = baseballService.checkContinueOrNot(baseballView.gameContinueOrNotInput());
        }
        if (continueType.equals(BaseballConstants.CONTINUE)) {
            continueOrNot = true;
        }

        return continueOrNot;
    }
}
