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
    private static final String MINUS = "3"; //숫자 검증 결과 - 1~9 사이의 수가 아님
    private static final String LENGTH_NOT_MATCH = "4"; //숫자 검증 결과 - 3자리 수가 아님
    private static final String DUPLICATED = "5"; //숫자 검증 결과 - 중복 수 존재

    private static final Map<String, String> numberCheckResult = new HashMap<String, String>() {
        {
            put(NOT_INTEGER, "문자는 입력할 수 없습니다.");
            put(OUT_OF_RANGE, "1~9 사이의 숫자를 입력해주세요.");
            put(MINUS, "음수는 입력할 수 없습니다.");
            put(LENGTH_NOT_MATCH, "3자리 수를 입력해주세요.");
            put(DUPLICATED, "중복된 값이 있습니다. 서로 다른 수를 입력해주세요.");
        }
    };

    /**
     * 임의의 3자리 숫자 생성
     *
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

    /**
     * 숫자 유효성 확인
     *
     * @param numbers        유효성 확인할 문자 배열
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
        if (Integer.parseInt(numberString) < 0) {
            checkCase = MINUS;
        }

        // 조건2) 입력받은 수의 개수가 3개이다
        if (numbers.length != 3) {
            checkCase = LENGTH_NOT_MATCH;
        }

        // 조건3) 입력받은 수가 중복되지 않는다
        HashSet<String> numberSet = new HashSet<>();
        for (char number : numbers) {
            numberSet.add(String.valueOf(number));
        }
        if (numberSet.size() != 3) {
            checkCase = DUPLICATED;
        }

        if (checkCase.equals("")) {
            result = true;
        }
        //exceptionThrow true일 때에만 Exception throw
        if (exceptionThrow && !result) {
            throw new IllegalArgumentException(numberCheckResult.get(checkCase));
        }

        return result;
    }

    /**
     * 사용자 추측값과 정답 비교
     *
     * @param answerNumberVo 정답
     * @param guessNumberVo  사용자가 입력한 추측값
     * @return 비교 결과 (스트라이크, 볼 카운트)
     */
    @Override
    public CompareResultVo compareNumber(AnswerNumberVo answerNumberVo, GuessNumberVo guessNumberVo) {
        CompareResultVo compareResultVo = new CompareResultVo();
        int ballCount = 0;
        int strikeCount = 0;
        int length = guessNumberVo.getGuessNumber().length;
        char[] guessNumbers = guessNumberVo.getGuessNumber();

        for (int i = 0; i < length; i++) {
            int[] counts = judgePitching(new String(answerNumberVo.getAnswerNumber()), guessNumbers[i], i);
            ballCount += counts[0];
            strikeCount += counts[1];
        }
        compareResultVo.setBall(ballCount);
        compareResultVo.setStrike(strikeCount);

        return compareResultVo;
    }

    /**
     * 스트라이크/볼 판정
     *
     * @param answer    정답 숫자
     * @param guessChar 사용자가 입력한 수 중 한 개 숫자
     * @param index 사용자가 입력한 수 중 몇 번째 숫자인지
     * @return 볼, 스트라이크 순으로 판정 결과 반환 (0: 해당없음, 1: 맞음)
     * {1, 0} : 볼
     * {0, 1} : 스트라이크
     * {0, 0} : 둘 다 아님
     */
    @Override
    public int[] judgePitching(String answer, char guessChar, int index) {
        int[] result = new int[]{0, 0}; // 볼 카운트, 스트라이크 카운트
        if (answer.charAt(index) == guessChar) {
            result[1] = 1;
            return result;
        }
        if (answer.contains(String.valueOf(guessChar))) {
            result[0] = 1;
        }

        return result;
    }

    /**
     * 정답여부 확인
     *
     * @param compareResultVo 스트라이크, 볼 카운트
     * @return 정답여부 (true: 정답 / false: 틀림)
     */
    @Override
    public boolean checkNumberCorrect(CompareResultVo compareResultVo) {
        return compareResultVo.getStrike() == 3;
    }

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
        String result = gameContinueOrNotInput;

        //정의된 선택값이 아닌 경우의 타입값 대입
        if (!result.equals(BaseballConstants.CONTINUE) && !result.equals(BaseballConstants.TERMINATE)) {
            result = BaseballConstants.NOT_DEFINED;
            System.out.println("잘 못된 값을 입력하셨습니다.");
        }

        return result;
    }
}
