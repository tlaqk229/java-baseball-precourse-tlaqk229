package baseball.model;

/**
 * 정답과 사용자 입력값을 비교한 결과를 담아두는 클래스
 */
public class CompareResultVo {
    private int strike; // 같은 자리, 같은 값인 숫자들 개수
    private int ball; // 다른 자리, 같은 값인 숫자들 개수

    public int getStrike() {
        return strike;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }
}
