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
            //c1 호출
            startGame();
            //c4 호출
            gameExecute = askGameContinueOrNot();
        }
        return;
    }

    //c1
    /**
     * 정답 생성 후 게임 진행 시작
     */
    public void startGame() {
        //m1 호출
        do {
            answerNumberVo = baseballService.createAnswer();
        } while (!baseballService.checkNumberValid(answerNumberVo.getAnswerNumber(), false));

        //c3 호출
        guessAnswerNumber();
    }

    //c3
    /**
     * 게임 진행 (값 입력과 반복)
     * 사용자 추측값 입력받고 정답여부 확인 후
     * 정답일 경우 메소드 종료
     * 정답이 아닐 경우 위의 과정 반복
     */
    public void guessAnswerNumber() {
        boolean guessCorrect = false;
        while (!guessCorrect) {
            //v1 호출 (output 사용자 입력값)
            GuessNumberVo guessNumberVo = baseballView.playerInput();
            //m2 호출 (input 사용자 입력값(v1 리턴값) / output 없음 - 유효성체크 안맞으면 프로그램 종료됨)
            baseballService.checkNumberValid(guessNumberVo.getGuessNumber(), true);
            //m3 호출 (input 정답, 사용자 입력값(v1 리턴값) / output 비교결과)
            CompareResultVo compareResultVo = baseballService.compareNumber(answerNumberVo, guessNumberVo);
            //v2 printGuessResult 호출 (input m3 리턴값)
            baseballView.printGuessResult(compareResultVo);
            //m4 호출 (input 비교결과(m3 리턴값) / output 정답여부TF)
            //guessCorrect에 정답여부TF(m4 리턴값) 대입
            guessCorrect = baseballService.checkNumberCorrect(compareResultVo);
        }
        System.out.println("3개의 숫자를 모두 맞히셨습니다.! 게임 종료");
    }

    //c4
    /**
     * 사용자에게 게임 재시작/종료 여부 확인
     * 입력받은 값이 유효하지 않은 경우 다시 입력받아 확인
     * @return 게임 재시작/종료 여부 (true: 재시작, false: 종료)
     */
    public boolean askGameContinueOrNot() {
        String continueType = BaseballConstants.NOT_DEFINED;
        boolean continueOrNot = false;

        while (continueType.equals(BaseballConstants.NOT_DEFINED)) {
            //v3 호츨
            String gameContinueOrNotInput = baseballView.gameContinueOrNotInput();
            //m5 호출 (input 사용자입력값(v3 리턴값) / output 게임재시작여부)
            continueType = baseballService.checkContinueOrNot(gameContinueOrNotInput);
        }

        if (continueType.equals(BaseballConstants.CONTINUE)) continueOrNot = true;

        return continueOrNot;
    }
}
