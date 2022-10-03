package baseball.model;

/**
 * 프로그램에서 생성한 정답 숫자를 저장하는 클래스
 */
public class AnswerNumberVo {
    private char[] answerNumber;

    public AnswerNumberVo(char[] answerNumber) {
        this.answerNumber = answerNumber;
    }

    public char[] getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(char[] answerNumber) {
        this.answerNumber = answerNumber;
    }

}
