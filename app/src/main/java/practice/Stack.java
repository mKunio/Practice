package practice;

import java.util.Arrays;

/**
 * Created by zhc on 2018/2/28 0028.
 * 1.2.3.4.5.6
 * 要求实现pop，push，top
 */

public class Stack {
    int[] element;

    public Stack() {
        element = new int[10];
    }

    public boolean push(int num) {
        if (element.length > 10) {
            return false;
        }

        int[] temp = new int[10];
        System.arraycopy(element, 0, temp, 1, element.length - 1);
        temp[0] = num;
        element = temp;
        return true;
    }

    public int pop() {
        int e = element[0];
        for (int i = 0; i < element.length - 1; i++) {
            element[i] = element[i + 1];
            element[i + 1] = -1;
        }
        return e;
    }

    public int top() {
        return element[0];
    }

    @Override
    public String toString() {
        return "Stack{" +
                "element=" + Arrays.toString(element) +
                '}';
    }
}
