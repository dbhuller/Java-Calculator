package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;
// used to keep track end of stack
public class BeginExpressionOperator extends Operator {
    @Override
    public int priority() {
        int priority = 0;
        return priority;
    }

    @Override
    public Operand execute(Operand op1, Operand op2) {
        return null;
    }
}
