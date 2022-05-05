package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.model.agents.Move;

/**
 * General interface for agent algorithms.
 */
public interface Algorithm {

    /**
     * Returns the next move calculated by the implementing algorithm class.
     * @return a {@code Move} component
     */
    Move getNext();

    /**
     * Returns the enum type associated with this algorithm.
     * @return enum AlgorithmType
     */
    AlgorithmType getType();
}
