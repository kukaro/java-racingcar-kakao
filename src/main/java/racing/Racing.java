package racing;

import exception.InvalidCarNameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Racing {
    private static final Random random = new Random();

    private List<Car> cars;
    int progressNumber;
    int maxPosition;

    public Racing(String carNames, int progressNumber) {
        this.progressNumber = progressNumber;
        setCars(carNames);
    }

    public void setCars(String s) {
        List<String> nameArray = Arrays.asList(s.split(","));
        if (!checkValidAllCarName(nameArray)) {
            throw new InvalidCarNameException();
        }
        this.cars = new ArrayList<>();
        for (String name : nameArray) {
            this.cars.add(new Car(name));
        }
    }

    /**
     * 2depth 제약조건 때문에 어쩔수 없이 삼항연산자 사용합니다.
     */
    private boolean checkValidAllCarName(List<String> nameArray) {
        int invalidCnt = 0;
        for (String name : nameArray) {
            invalidCnt += checkValidCarName(name) ? 0 : 1;
        }
        return invalidCnt == 0;
    }

    private boolean checkValidCarName(String carName) {
        return carName.length() <= 5;
    }

    public List<Car> getCars() {
        return this.cars;
    }

    public List<Integer> repeatRacing() {
        while (progressNumber > 0) {
            this.race();
            this.setMaxPosition();
            this.racePrint();
            progressNumber--;
        }
        return getPositions();
    }

    private void setMaxPosition() {
        for (Car car : cars) {
            maxPosition = Math.max(car.getPosition(), maxPosition);
        }
    }

    /**
     * cars 리스트에서 index를 확인하여 해당 cars가 승자이면 승자의 이름을 콤마를 붙혀서 반환합니다.
     * 승자가 아닐경우 공백이 출력됩니다.
     *
     * @param carIndex 확인할 cars의 index를 넣습니다.
     * @return 승자의 이름, 혹은 공백을 반환합니다.
     */
    private String getWinnerNameWithComma(int carIndex) {
        String s = "";
        if (cars.get(carIndex).getPosition() == this.maxPosition) {
            s += cars.get(carIndex).getName() + ", ";
        }
        return s;
    }

    public String returnWinnerString() {
        StringBuilder s = new StringBuilder();
        List<Integer> resultPosition = this.getPositions();
        for (int i = 0; i < resultPosition.size(); i++) {
            s.append(this.getWinnerNameWithComma(i));
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        return s + "가 최종 우승했습니다.";
    }

    public void racePrint() {
        for (Car car : this.cars) {
            System.out.println(car);
        }
        System.out.println();
    }

    public List<Integer> getPositions() {
        List<Integer> result = new ArrayList<>();
        for (Car car : this.cars) {
            result.add(car.getPosition());
        }
        return result;
    }

    private void race() {
        for (Car car : this.cars) {
            car.move(car.goOrStop(makeRandomValue()));
        }
    }

    private int makeRandomValue() {
        return random.nextInt(10);
    }
}
