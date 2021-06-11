package cn.nihility.context.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircleAB {

    @Autowired
    private CircleBA circleBA;

    public CircleAB() {
        System.out.println("constructor CircleAB, hash [" + hashCode() + "]");
    }

    public void setCircleBA(CircleBA circleBA) {
        System.out.println("set circleBA");
        this.circleBA = circleBA;
    }

    public CircleBA getCircleBA() {
        return circleBA;
    }

    @Override
    public String toString() {
        return "CircleAB{" +
                "circleBA=" + (circleBA == null ? "null" : Integer.toString(circleBA.hashCode())) +
                '}';
    }
}
