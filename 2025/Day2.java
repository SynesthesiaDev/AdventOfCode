import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class Day2 {

    public static void main(String[] args) throws IOException {
        var ranges = Arrays.stream(Files.readString(Path.of("./Inputs/day2.txt")).split(","))
                .filter(p -> !p.isEmpty())
                .map(Range::parse)
                .toList();

        AtomicLong invalidIdSumFirst = new AtomicLong();
        AtomicLong invalidIdSumSecond = new AtomicLong();

        ranges.forEach(range -> {

            range.forEach(number -> {
                var text = number.toString();
                var textArr = number.toString().toCharArray();
                var isInvalidForSecondPart = false;

                if (text.length() % 2 == 0) {
                    var split1 = text.substring(0, text.length() / 2);
                    var split2 = text.substring(text.length() / 2);

                    if (split1.equals(split2)) invalidIdSumFirst.addAndGet(number);
                }

                for (int sequenceLength = 1; sequenceLength < textArr.length; sequenceLength++) {
                    if (textArr.length % sequenceLength == 0) {

                        var sequence = text.subSequence(0, sequenceLength);
                        var repetitionsNeeded = textArr.length / sequenceLength;

                        var repeatedString = String.valueOf(sequence).repeat(repetitionsNeeded);

                        if (repeatedString.equals(text)) {
                            isInvalidForSecondPart = true;
                            break;
                        }
                    }
                }
                if (isInvalidForSecondPart) invalidIdSumSecond.addAndGet(number);
            });
        });

        System.out.println(" ");
        System.out.println("Answer to the first question: " + invalidIdSumFirst);
        System.out.println("Answer to the second question: " + invalidIdSumSecond);
    }

    public record Range(long start, long end) {
        public static Range parse(String range) {
            var split = range.split("-");
            return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }

        public void forEach(Consumer<Long> number) {
            for (long i = start; i <= end; i++) {
                number.accept(i);
            }
        }
    }
}