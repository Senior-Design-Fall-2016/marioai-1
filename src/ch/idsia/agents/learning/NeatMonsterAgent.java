package ch.idsia.agents.learning;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.evolution.Evolvable;
import fr.neatmonster.labs.neat.Genome;
import fr.neatmonster.labs.neat.Pool;
import fr.neatmonster.labs.neat.Species;

/**
 * Created by Andrew on 11/4/2016.
 */
public class NeatMonsterAgent extends BasicMarioAIAgent implements Agent, Evolvable {

    private Species species;
    private Genome genome;
    final int numberOfOutputs = Environment.numberOfKeys;
    final int numberOfInputs = /*53*/28;
    static private final String name = "NeatAgent";

    public NeatMonsterAgent(Species spec, Genome gen)
    {
        super(name);
        this.species = spec;
        this.genome = gen;
    }


    public Evolvable getNewInstance()
    {
        return new NeatMonsterAgent(this.species, this.genome);
    }

    public Evolvable copy()
    {
        return new NeatMonsterAgent(this.species, this.genome);
    }

    public void reset() {
    }

    public void mutate()
    {
        this.genome.mutate();
    }

    public void setGenomeFitness(int newFitness) {
        this.genome.fitness = newFitness;
        if (this.genome.fitness > Pool.maxFitness)
            Pool.maxFitness = this.genome.fitness;
    }

    public int getGenomeFitness() {
        return (int)this.genome.fitness;
    }

    public boolean[] getAction()
    {
        double inputs[] = new double[numberOfInputs+1];
        inputs[numberOfInputs] = -1.0; // Bias

        byte[][] scene = mergedObservation;

        // Populate the rest of "inputs" from this organism's status in the simulation.
        //
        //

        int which = 0;
        for (int k = -2; k < 3; k++)
        {
            for (int j = -2; j < 3; j++)
            {
                inputs[which++] = probe(k, j, scene);
            }
        }

        inputs[inputs.length - 3] = isMarioOnGround ? 1 : 0;
        inputs[inputs.length - 2] = isMarioAbleToJump ? 1 : 0;
        inputs[inputs.length - 1] = 1;


        double[] outputs = this.genome.evaluateNetwork(inputs);
        for (int i = 0; i < action.length; i++)
        {
            action[i] = outputs[i] > 0;
        }
        return action;
    }

    private double probe(int x, int y, byte[][] scene)
    {
        int realX = x + 11;
        int realY = y + 11;
        return (scene[realX][realY] != 0) ? 1 : 0;
    }
}
