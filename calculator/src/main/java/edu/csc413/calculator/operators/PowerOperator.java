package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {
    @Override   // override method from super
    public int priority() {
        int priority = 3;
        return priority;
    }

    @Override   // override method from super
    public Operand execute(Operand op1, Operand op2) {
        Operand ans = new Operand( power(op1.getValue(), op2.getValue()) );
        return ans;
    }

    public int power(int i, int j) {
        int ans = i;
        for (int count = 2; count <= j; count++) {
            ans = ans * i;
        }
        return ans;
    }
}
