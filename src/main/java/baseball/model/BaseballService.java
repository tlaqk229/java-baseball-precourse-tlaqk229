package baseball.model;

/**
 * 숫자 야구 게임 도메인 로직 인터페이스
 */
public interface BaseballService {

    /**
     * 임의의 3자리 숫자 생성
     * @return 생성된 3자리 숫자
     */
    AnswerNumberVo createAnswer();

    /**
     * 숫자 유효성 확인
     * @param numbers
     */
    boolean checkNumberValid(char[] numbers, boolean exceptionThrow);

    /**
     * 사용자 추측값과 정답 비교
     * @param answerNumberVo 정답
     * @param guessNumberVo 사용자가 입력한 추측값
     * @return 비교 결과 (스트라이크, 볼 카운트)
     */
    CompareResultVo compareNumber(AnswerNumberVo answerNumberVo, GuessNumberVo guessNumberVo);

    /**
     * 정답여부 확인
     * @param compareResultVo 스트라이크, 볼 카운트
     * @return 정답여부 (true: 정답 / false: 틀림)
     */
    boolean checkNumberCorrect(CompareResultVo compareResultVo);

    /**
     * 게임 재시작/종료 여부 타입값으로 변환
     * 사용자가 제시된 선택값 외에 정의되지 않은 값을 입력할 경우를 대비하여
     * 아래와 같이 입력값을 변환하여 반환
     * 1: 재시작, 2: 종료, 3: 정의되지 않음
     * @param gameContinueOrNotInput 사용자가 입력한 재시작/종료 여부
     * @return 게임 재시작/종료 여부 타입
     */
    String checkContinueOrNot(String gameContinueOrNotInput);
}