package baseball.view;

import baseball.model.CompareResultVo;
import baseball.model.GuessNumberVo;
import camp.nextstep.edu.missionutils.Console;
import java.util.StringJoiner;

/**
 * 숫자 야구 게임 사용자 입/출력 구현 클래스
 */
public class BaseballView {

    /**
     * 사용자 추측값 입력
     *
     * @return 사용자 입력값
     */
    public GuessNumberVo playerInput() {
        System.out.println("1~9까지 서로 다른 수로 이루어진 3자리 수를 입력해 주세요.");
        String input = Console.readLine();

        return new GuessNumberVo(input.toCharArray());
    }

    /**
     * 게임 결과 메세지 출력
     *
     * @param compareResultVo 정답과 사용자 입력값 비교 결과
     */
    public void printGuessResult(CompareResultVo compareResultVo) {
        String message = ""; // 출력할 게임결과 메세지
        int ball = compareResultVo.getBall(); // 볼 카운트
        int strike = compareResultVo.getStrike(); // 스트라이크 카운트
        String ballCountMessage = "";
        String strikeCountMessage = "";

        //스트라이크, 볼 카운트에 따라 메세지 작성
        if (strike == 0 && ball == 0) {
            message = "낫싱";
        }
        if (ball != 0) {
            ballCountMessage = ball + "볼";
        }
        if (strike != 0) {
            strikeCountMessage = strike + "스트라이크";
        }
        String delimiter = "";
        if (!ballCountMessage.equals("") && !strikeCountMessage.equals("")) {
            delimiter = " ";
        }
        if (!ballCountMessage.equals("") || !strikeCountMessage.equals("")) {
            StringJoiner joinMessage = new StringJoiner(delimiter);
            joinMessage.add(ballCountMessage);
            joinMessage.add(strikeCountMessage);
            message = joinMessage.toString();
        }

        //게임 결과 출력
        System.out.println(message);
    }

    /**
     * 게임 재시작/종료 여부 확인 (입력)
     *
     * @return 사용자가 입력한 게임 재시작/종료 여부
     */
    public String gameContinueOrNotInput() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        return Console.readLine();
    }
}
