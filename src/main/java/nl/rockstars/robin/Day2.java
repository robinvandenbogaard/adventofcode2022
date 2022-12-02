package nl.rockstars.robin;

public class Day2 implements DayProcessor
{

    private int sum;

    enum Outcome { Lose(0), Draw(3), Win(6);

        private final int score;

        Outcome(int score) {
            this.score = score;
        }
    }
    enum DesiredOutcome {
        X(Outcome.Lose), Y(Outcome.Draw), Z(Outcome.Win);

        private final Outcome outcome;

        DesiredOutcome(Outcome outcome) {
            this.outcome = outcome;
        }
    }

    enum Encrypt {
        A(Type.Rock),
        B(Type.Paper),
        C(Type.Sciccor);

        private final Type type;

        Encrypt(Type type) {
            this.type = type;
        }

    }

    enum Type {
        Rock(1), Paper(2), Sciccor(3);

        private final int score;

        Type(int score) {
            this.score = score;
        }

        public boolean losesFrom(Type type) {
            return switch (this) {
                case Rock -> type == Paper;
                case Paper -> type == Sciccor;
                case Sciccor -> type == Rock;
            };
        }

        public Type losesTo() {
            return switch (this) {
                case Rock -> Paper;
                case Paper -> Sciccor;
                case Sciccor -> Rock;
            };
        }

        public Type winsFrom() {
            return switch (this) {
                case Rock -> Sciccor;
                case Paper -> Rock;
                case Sciccor -> Paper;
            };
        }
    }

    public static void main(String[] args )
    {
        new Day2().go();
    }

    @Override
    public void process(String line) {
       sum += calc(line);
        System.out.println(line + sum);
    }

    int calc(String line) {
        var choices = line.split(" ");
        var opponent = Encrypt.valueOf(choices[0]).type;
        var yours = getYours(opponent, DesiredOutcome.valueOf(choices[1]));
        var outcome = calc(opponent, yours);
        System.out.printf("%s + %s => ", yours.score, outcome.score);
        return yours.score + outcome.score;
    }

    Type getYours(Type opponent, DesiredOutcome desired) {
        return switch (desired.outcome) {
            case Lose -> opponent.winsFrom();
            case Draw -> opponent;
            case Win -> opponent.losesTo();
        };
    }

    Outcome calc(Type opponent, Type yours) {
        if (opponent.losesFrom(yours))
            return Outcome.Win;
        else if (opponent == yours)
            return Outcome.Draw;
        return Outcome.Lose;
    }

    @Override
    public Result getResult() {
        return new Result(""+sum);
    }
}
