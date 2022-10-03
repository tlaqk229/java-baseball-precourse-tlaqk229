package baseball;

import baseball.controller.BaseballController;

/**
 * 숫자 야구 게임 프로그램 실행 클래스
 */
public class Application {
    public static void main(String[] args) {
        BaseballController baseballController = new BaseballController();
        baseballController.executeProgram();
    }
}
