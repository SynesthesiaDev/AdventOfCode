public class Program
{
    public const int DIAL_RANGE = 100;

    public static void Main()
    {
        var inputs = File.ReadAllText("./Inputs/day1.txt")
            .Split("\n")
            .Where(line => !string.IsNullOrEmpty(line))
            .Select(Input.Parse)
            .ToList();

        var amountZeroLandedOn = 0;
        var amountZeroFinishedOn = 0;
        var current = 50;

        inputs.ForEach(input =>
        {
            var delta = (input.direction == "R") ? 1 : -1;

            for (var i = 0; i < input.steps; i++)
            {
                current += delta;
                current = (current % DIAL_RANGE + DIAL_RANGE) % DIAL_RANGE;

                if (current == 0) amountZeroLandedOn++;
            }
            if (current == 0) amountZeroFinishedOn++;
        });

        Console.WriteLine($"Answer to question 1 is: {amountZeroFinishedOn}");
        Console.WriteLine($"Answer to question 2 is: {amountZeroLandedOn}");
    }

    private record Input(string direction, int steps)
    {
        public static Input Parse(string input)
        {
            var direction = input.Substring(0, 1);
            var steps = Convert.ToInt16(input.Substring(1));
            return new Input(direction, steps);
        }
    }
}