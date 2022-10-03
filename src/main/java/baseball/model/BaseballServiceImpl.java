package baseball.model;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 숫자 야구 게임 도메인 로직 구현 클래스
 */
public class BaseballServiceImpl implements BaseballService {

    private static final int COUNT = 3; //숫자 개수
    private static final int BEGIN_NUMBER = 1; //임의생성 숫자 범위 최소값
    private static final int END_NUMBER = 9; //임의생성 숫자 범위 최대값
    private static final String NOT_INTEGER = "1"; //숫자 검증 결과 - 정수 아님
    private static final String OUT_OF_RANGE = "2"; //숫자 검증 결과 - 1~9 사이의 수가 아님
    private static final String LENTH_NOTMATCH = "3"; //숫자 검증 결과 - 3자리 수가 아님
    private static final String DUPLICATED = "4"; //숫자 검증 결과 - 중복 수 존재

    private static final Map<String, String> numberCheckResult = new HashMap<String, String>() {
        {
            put(NOT_INTEGER, "문자는 입력할 수 없습니다.");
            put(OUT_OF_RANGE, "1~9 사이의 숫자를 입력해주세요.");
            put(LENTH_NOTMATCH, "3자리 수를 입력해주세요.");
            put(DUPLICATED, "중복된 값이 있습니다. 서로 다른 수를 입력해주세요.");
        }
    };

    //m1
    /**
     * 임의의 3자리 숫자 생성
     * @return 생성된 3자리 숫자
     */
    @Override
    public AnswerNumberVo createAnswer() {
        int i = 0;
        char[] answer = new char[COUNT];
        while (i < COUNT) {
            int randomBall = Randoms.pickNumberInRange(BEGIN_NUMBER, END_NUMBER);
            answer[i] = String.valueOf(randomBall).charAt(0);
            i++;
        }

        return new AnswerNumberVo(answer);
    }

    //m2
    /**
     * 숫자 유효성 확인
     * @param numbers 유효성 확인할 문자 배열
     * @param exceptionThrow Exception throw 여부
     * @return 유효성 확인 결과 (true: 조건 만족, false: 조건 불충족)
     */
    @Override
    public boolean checkNumberValid(char[] numbers, boolean exceptionThrow) {
        boolean result = false;
        String checkCase = "";

        // 조건1) 1~9 까지의 수로 이루어져 있다
        String numberString = new String(numbers);
        try {
            Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            checkCase = NOT_INTEGER;
        }
        if (numberString.contains("0")) {
            checkCase = OUT_OF_RANGE;
        }

        // 조건2) 입력받은 수의 개수가 3개이다
        if (numbers.length!=3) {
            checkCase = LENTH_NOTMATCH;
        }

        // 조건3) 입력받은 수가 중복되지 않는다
        HashSet<String> numberSet = new HashSet<>();
        for (char number : numbers) {
            numberSet.add(String.valueOf(number));
        }
        if (numberSet.size()!=3) {
            checkCase = DUPLICATED;
        }

        if (checkCase.equals("")) result = true;
        //exceptionThrow true일 때에만 Exception throw
        if (exceptionThrow && !result) {
            throw new IllegalArgumentException(numberCheckResult.get(checkCase));
        }

        return result;
    }

    //m3
    /**
     * 사용자 추측값과 정답 비교
     * @param answerNumberVo 정답
     * @param guessNumberVo 사용자가 입력한 추측값
     * @return 비교 결과 (스트라이크, 볼 카운트)
     */
    @Override
    public CompareResultVo compareNumber(AnswerNumberVo answerNumberVo, GuessNumberVo guessNumberVo) {
        return null;
    }

    //m4
    /**
     * 정답여부 확인
     * @param compareResultVo 스트라이크, 볼 카운트
     * @return 정답여부 (true: 정답 / false: 틀림)
     */
    @Override
    public boolean checkNumberCorrect(CompareResultVo compareResultVo) {
        return false;
    }

    //m5
    /**
     * 게임 재시작/종료 여부 타입값으로 변환
     * 사용자가 제시된 선택값 외에 정의되지 않은 값을 입력할 경우를 대비하여
     * 아래와 같이 입력값을 변환하여 반환
     * 1: 재시작, 2: 종료, 3: 정의되지 않음
     * @param gameContinueOrNotInput 사용자가 입력한 재시작/종료 여부
     * @return 게임 재시작/종료 여부 타입
     */
    @Override
    public String checkContinueOrNot(String gameContinueOrNotInput) {
        return null;
    }
}
