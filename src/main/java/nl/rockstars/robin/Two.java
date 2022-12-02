package nl.rockstars.robin;

public class Two implements DayProcessor
{

    private int sum;

    enum Encrypt {
        A(Type.Stone), X(Type.Stone),
        B(Type.Paper), Y(Type.Paper),
        C(Type.Sciccor), Z(Type.Sciccor);

        private final Type type;

        Encrypt(Type type) {
            this.type = type;
        }

        public int score() {
            return type.score;
        }

        public boolean losesFrom(Encrypt yours) {
            return yours.type.breaks(type);
        }

        public boolean isSameAs(Encrypt yours) {
            return yours.type == this.type;
        }
    }

    enum Type {
        Stone(1), Paper(2), Sciccor(3);

        private final int score;

        Type(int score) {
            this.score = score;
        }

        public boolean breaks(Type type) {
            return switch (this) {
                case Stone -> type == Sciccor;
                case Paper -> type == Stone;
                case Sciccor -> type == Paper;
            };
        }
    }

    public static void main(String[] args )
    {
        new Two().go("Two.txt");
    }

    @Override
    public void process(String line) {
       sum += calc(line);
        System.out.println(line + sum);
    }

    private int calc(String line) {
        var choices = line.split(" ");
        var opponent = Encrypt.valueOf(choices[0]);
        var yours = Encrypt.valueOf(choices[1]);
        var outcome = calc(opponent, yours);
        return yours.score() + outcome;
    }

    private int calc(Encrypt opponent, Encrypt yours) {
        if (opponent.losesFrom(yours))
            return 6;
        else if (opponent.isSameAs(yours))
            return 3;
        return 0;
    }

    @Override
    public Result getResult() {
        return new Result(""+sum);
    }
}
