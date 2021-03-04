package cn.nihility.context.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CircleBA {

    @Autowired
    private CircleAB circleAB;

    public CircleBA() {
        System.out.println("constructor CircleBA, hash [" + hashCode() + "]");
    }

    public CircleAB getCircleAB() {
        return circleAB;
    }

    public void setCircleAB(CircleAB circleAB) {
        System.out.println("set circleAB");
        this.circleAB = circleAB;
    }

    @Override
    public String toString() {
        return "CircleBA{" +
                "circleAB=" + (circleAB == null ? "null" : Integer.toString(circleAB.hashCode())) +
                '}';
    }
}
