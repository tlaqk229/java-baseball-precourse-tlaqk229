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
     * @param numbers 유효성 확인할 문자 배열
     * @param exceptionThrow Exception throw 여부
     * @return 유효성 확인 결과 (true: 조건 만족, false: 조건 불충족)
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
     * 스트라이크/볼 판정
     * @param answer 정답 숫자
     * @param guessChar 사용자가 입력한 수 중 한 개 숫자
     * @param index 사용자가 입력한 수 중 몇 번째 숫자인지
     * @return 볼, 스트라이크 순으로 판정 결과 반환 (0: 해당없음, 1: 맞음)
     * {1, 0} : 볼
     * {0, 1} : 스트라이크
     * {0, 0} : 둘 다 아님
     */
    int[] judgePitching(String answer, char guessChar, int index);

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