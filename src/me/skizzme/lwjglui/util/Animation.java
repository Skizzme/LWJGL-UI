package me.skizzme.lwjglui.util;

import me.skizzme.lwjglui.Window;

public class Animation {

    private double value, max, min, maxAnim;

    public Animation(double min, double max) {
        this(0, min, max, -1);
    }

    public Animation(double value, double min, double max) {
        this(value, min, max, -1);
    }

    public Animation(double value, double min, double max, double maxAnim) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.maxAnim = maxAnim;
    }

    public void animate(double target, double speed) {

        if (maxAnim > 0 && speed > maxAnim) {
            speed = maxAnim;
        }

        if (target < value) {
            value-=speed;
            checkValue(false, target);
        }
        else if (target > value) {
            value+=speed;
            checkValue(true, target);
        }
    }

    public void animateLinear(double target, double speed) {
        speed = speed* Window.getScreen().fps;

        if (maxAnim > 0 && speed > maxAnim) {
            speed = maxAnim;
        }

        if (target < value) {
            value-=speed;
            checkValue(false, target);
        }
        else if (target > value) {
            value+=speed;
            checkValue(true, target);
        }
    }

    public void animateBiLinear(double target, double speed) {
        animateBiLinear(target, speed, 1);
    }

    public void animateBiLinearNoMaxMin(double target, double speed, double multiplier) {
        if (maxAnim > 0 && speed > maxAnim) {
            speed = maxAnim;
        }

        value += (((target-value)*multiplier)+speed)*Window.getScreen().fps;
        checkValue();
    }

    public void animateBiLinear(double target, double speed, double multiplier) {
        if (maxAnim > 0 && speed > maxAnim) {
            speed = maxAnim;
        }

        if (target < value) {
            value -= (((value-min)*multiplier)+speed)*Window.getScreen().fps;
            if (value < target) {
                System.out.println("a");
                value = target;
            }
            checkValue(false, target);
        }
        else if (target > value) {
            value += (((max-value)*multiplier)+speed)*Window.getScreen().fps;
            if (value > target) {
                System.out.println("b");
                value = target;
            }
            checkValue(true, target);
        }
    }

    public void animationInverseBiLinear(double target, double speed) {
        animationInverseBiLinear(target, speed, 1);
    }

    public void animationInverseBiLinear(double target, double speed, double multiplier) {
        if (maxAnim > 0 && speed > maxAnim) {
            speed = maxAnim;
        }

        if (target < value) {
            value-=((value*multiplier)+speed)*Window.getScreen().fps;
            if (value < target) {
                value = target;
            }
        }
        else if (target > value) {
            value+=((value*multiplier)+speed)*Window.getScreen().fps;
            if (value > target) {
                value = target;
            }
        }
        checkValue();
    }

    public double getValue() {
        return value;
    }

    public double getInverseValue() {
        return max-value;
    }

    public double getPercent() {
        return (value-min)/(max-min);
    }

    public double getInversePercent() {
        return (getInverseValue()-min)/(max-min);
    }

    public double getAsMult(double value) {
        return Math.min(getPercent()*value, value);
    }

    public double getAsInverseMult(double value) {
        return Math.min(getInversePercent()*value, value);
    }

    public double getOffset(double offset) {
        return Math.max(Math.min((value-offset)/(max-offset), max), min);
    }

    private void checkValue(boolean increasing, double target) {
        if (increasing) this.value = Math.min(target, this.value);
        else this.value = Math.max(target, this.value);
        this.value = Math.max(Math.min(value, max), min);
    }

    private void checkValue() {
        this.value = Math.max(Math.min(value, max), min);
    }

    public void setValue(double value) {
        this.value = value;
    }
}
