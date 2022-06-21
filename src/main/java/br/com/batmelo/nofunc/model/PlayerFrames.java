package br.com.batmelo.nofunc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerFrames {

    private String player;
    private List<Frame> frames = new ArrayList<>();

    public PlayerFrames(String player) {
        this.player = player;
        initializeFrames();
    }

    private void initializeFrames() {
        Frame lastFrame = new NullFrame();
        frames.add(lastFrame);

        for (int i = 0; i < 10; i++) {
            Frame f = new Frame(lastFrame);
            frames.add(f);
            lastFrame = f;
        }

        lastFrame.initialize();

        Collections.reverse(frames);
    }

    private List<Frame> getFramesCanAddRoll() {
        return frames.stream()
                     .filter(Frame::canAddRoll)
                     .collect(Collectors.toList());
    }

    public void addScore(Integer score) {
        List<Frame> listCanAddRoll = this.getFramesCanAddRoll();
        if( listCanAddRoll.isEmpty() ) {
            throw new IllegalStateException("Cannot add one more roll");
        }

        listCanAddRoll.forEach((f) -> f.addRoll(score));
    }

    public Integer getFrameScore(int i) {
        return frames.get(i).getScore();
    }
}
