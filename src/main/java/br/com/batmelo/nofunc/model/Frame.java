package br.com.batmelo.nofunc.model;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private Frame nextFrame;
    private List<Integer> scores = new ArrayList<>();

    private boolean initialized;

    Frame(Frame nextFrame) {
        this.nextFrame = nextFrame;
        this.initialized = false;
    }

    void addRoll(Integer score) {
        scores.add(score);
        checkCanAddBonus();
        checkInitializeNextFrame();
    }

    private void checkInitializeNextFrame() {
        if( this.isStrike() || this.isSpare() || isOpen() ) {
            this.nextFrame.initialize();
        }
    }

    void initialize() {
        this.initialized = true;
    }

    private void checkCanAddBonus() {
        if( isOpen() ) {
            scores.add(0);
        }
    }

    private boolean isStrike() {
        return !scores.isEmpty()
                && (scores.get(0) == 10);
    }

    private boolean isSpare() {
        return !isStrike()
                && scores.size() >= 2
                && (scores.get(0)  + scores.get(1) == 10);
    }

    private boolean isOpen() {
        return !isStrike()
                && scores.size() >= 2
                && !isSpare();
    }

    boolean canAddRoll() {
        return initialized
            && scores.size()<3;
    }

    public Integer getScore() {
        return scores.stream()
                     .reduce(Integer.valueOf(0), Integer::sum);

    }
}
