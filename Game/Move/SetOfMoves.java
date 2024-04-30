package Game.Move;

import java.util.List;

// SetOfMoves class might look like this:
public class SetOfMoves {
    private List<OnePlacementInstruction> placements;
    private double score;

    public SetOfMoves(List<OnePlacementInstruction> placements, double score) {
        this.placements = placements;
        this.score = score;
    }

    // Add getters for placements and score
    public List<OnePlacementInstruction> getPlacements() {
        return placements;
    }

    public double getScore() {
        return score;
    }

    // You may want to override toString() for easy debugging
    @Override
    public String toString() {
        return  "//////////////////////////////////////\n" +
                "////////// MOVES TO PERFORM //////////\n" +
                "//////////////////////////////////////\n" +
                " * first placement: \n" + placements.get(0).toString()+ "\n" +
                " * second placement: \n" + placements.get(1).toString() + "\n" +
                " * third placement: \n" + placements.get(2).toString() + "\n" +
                "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\";
    }

    public void printInstructions(){
        MoveSimulationConverter.convertToSimulationInstruction(placements.get(0));
        MoveSimulationConverter.convertToSimulationInstruction(placements.get(1));
        MoveSimulationConverter.convertToSimulationInstruction(placements.get(2));
    }
}