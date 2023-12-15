import java.util.ArrayList;
import java.util.List;

public class LensBoxes {
    List<Lens>[] boxes = new ArrayList[256];

    public LensBoxes(String[] steps) {
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new ArrayList<>();
        }

        stepLoop :
        for (String step : steps) {
            Lens lens = new Lens(step);
            int i = lens.hash();

            if (lens.isRm) {
                boxes[i].removeIf(l -> l.label.equals(lens.label));
            } else {
                for (int j = 0; j < boxes[i].size(); j++) {
                    if (boxes[i].get(j).label.equals(lens.label)) {
                        boxes[i].set(j, lens);

                        continue stepLoop;
                    }
                }

                boxes[i].add(lens);
            }
        }
    }

    public long sumLenses() {
        long sum = 0;

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].size(); j++) {
                Lens l = boxes[i].get(j);

                sum += (long) (i+1)*(j+1)*l.focalLength;
            }
        }

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < boxes.length; i++) {
            if (!boxes[i].isEmpty()) {
                s.append(String.format("box %d: ",i));
                boxes[i].forEach(s::append);
                s.append("\n");
            }
        }

        return s.toString();
    }
}


