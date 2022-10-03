package baseball.model;

/**
 * 사용자가 추측하여 입력한 숫자를 저장하는 클래스
 */
public class GuessNumberVo {
    private char[] guessNumber;

    public GuessNumberVo(char[] guessNumber) {
        this.guessNumber = guessNumber;
    }

    public char[] getGuessNumber() {
        return guessNumber;
    }

    public void setGuessNumber(char[] guessNumber) {
        this.guessNumber = guessNumber;
    }

}
