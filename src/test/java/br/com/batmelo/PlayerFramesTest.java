package br.com.batmelo;

import br.com.batmelo.nofunc.model.PlayerFrames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class PlayerFramesTest {


    private PlayerFrames pf;

    @BeforeEach
    public void init() {
        pf = getPlayerFrame();
    }

    protected abstract PlayerFrames getPlayerFrame();

    @Nested
    class StrikeSituations {

        @Test
        public void canAddPerfectScore() {
            Stream.generate(() -> Integer.valueOf(10))
                    .limit(12)
                    .forEach(s -> pf.addScore(s));

            assertEquals(30, pf.getFrameScore(9));
        }

        @Test
        public void canAddTwoBonusWhenLastFrameIsStrike() {
            Arrays.asList(7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 10, 2, 2 )
                    .forEach(s -> pf.addScore(s));

            assertEquals(14, pf.getFrameScore(9));
        }

        @Test
        public void doCalculateScoresWhenStrike() {
            Arrays.asList(10,3,7)
                    .forEach(s -> pf.addScore(s));

            assertEquals(20, pf.getFrameScore(0));
        }
    }


    @Nested
    class SpareSituations {
        @Test
        public void canAddBonusWhenLastIsSpare() {
            Arrays.asList(7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 10 )
                    .forEach(s -> pf.addScore(s));

            assertEquals(20, pf.getFrameScore(9));
        }

        @Test
        public void cannotAdd23WhenLastIsSpare() {
            assertThrows(IllegalStateException.class,
                    () -> Arrays.asList(7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 10, 8 )
                            .forEach(s -> pf.addScore(s)));
        }

        @Test
        public void checkScoresWhenSpare() {
            Arrays.asList(3,7,10)
                    .forEach(s -> pf.addScore(s));

            assertEquals(20, pf.getFrameScore(0));
        }
    }



    @Nested
    class OpenSituations {
        @Test
        public void canAddBonusWhenLastIsOpen() {
            assertThrows(IllegalStateException.class,
                    () -> Arrays.asList(7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 7,3, 2,2, 10 )
                            .forEach(s -> pf.addScore(s)));
        }

        @Test
        public void canAddAllOpens() {
            Stream.generate(() -> Integer.valueOf(2))
                    .limit(20)
                    .forEach(s -> pf.addScore(s));

            assertEquals(4, pf.getFrameScore(9));
        }

        @Test
        public void cannotAdd21WhenAllOpens() {
            assertThrows(IllegalStateException.class,
                    () -> Stream.generate(() -> Integer.valueOf(2))
                            .limit(21)
                            .forEach(s -> pf.addScore(s)));
        }

        @Test
        public void checkScoresWhenOpen() {
            Stream.generate(() -> Integer.valueOf(2))
                    .limit(4)
                    .forEach(s -> pf.addScore(s));

            assertEquals(4, pf.getFrameScore(0));
        }

    }
}
