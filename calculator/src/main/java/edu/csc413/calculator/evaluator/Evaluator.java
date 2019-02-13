package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.operators.*;


import java.util.Stack;
import java.util.StringTokenizer;
import java.util.HashMap;

public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer tokenizer;
  private static final String DELIMITERS = "$()+-*^/ ";
  private HashMap<String, Operator> hashMap;



  // DB- Initialize two stack objects to keep track of operands and operators
  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  /*
  // while the top of the stack has priority > 1, pop operatorStack, pop from Operand stack operand1, and operand 2 --> execute and push to operandStack as result of expression
  public void reachedClosedPar() {
      while(operatorStack.peek().priority() > 1) {
          Operator oldOp = operatorStack.pop();
          Operand op2 = operandStack.pop();
          Operand op1 = operandStack.pop();
          operandStack.push(oldOp.execute(op1, op2));
      }
  }
  */

  public void reachedClosedPar() {
      while (operatorStack.peek().priority() >= 1) {
          Operator temp = operatorStack.pop();
          Operand op2 = operandStack.pop();
          Operand op1 = operandStack.pop();
          operandStack.push(temp.execute(op1, op2));
      }
      operatorStack.pop();
  }

    public int eval(String expression ) {
    String token;

    //push initial operator to keep track of end of stack
    operatorStack.push(new BeginExpressionOperator());

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer( expression, DELIMITERS, true );

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators





    while ( this.tokenizer.hasMoreTokens() ) {
      // filter out spaces(returns token after space)
      if ( !( token = this.tokenizer.nextToken() ).equals( " " )) {
        // check if token is an operand
        if ( Operand.check( token )) {
          operandStack.push( new Operand( token ));
        } else {
          if ( ! Operator.check( token )) {
            System.out.println( "*****invalid token******" );
            throw new RuntimeException("*****invalid token******");
          }
          //ADDED 9:38 pm 2/12/2019
          if (Operator.check(token)) {

              Operator newOperator = Operator.getOperator(token);
              if (operatorStack.isEmpty()) {
                  operatorStack.add(newOperator);
                  continue;
              }
              if (token.equals(")")) {
                  reachedClosedPar();
                  continue;
              }
              if (token.equals("(")) {
                  operatorStack.push(new LeftParOperator());
                  continue;
              }

          }


          /*
          if (newOperator.check(token)) {
              operatorStack.push(Operator.getOperator(token));
          }
          */


          /*
          //sort operator into correct priority in operatorStack
          Operator newOperator = Operator.getOperator(token);
          if (operatorStack.isEmpty()) {
              Operator stackPop = operatorStack.pop();
              if (stackPop.priority() <= newOperator.priority()) {
                  operatorStack.push(stackPop); //push higher precedence(3"^") to bottom to be evaluated after lower precedence(1"+/-")
                  operatorStack.push(newOperator);
              }
          }
          */



          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.
            Operator newOperator = Operator.getOperator(token);
          while (operatorStack.peek().priority() >= newOperator.priority() ) {
            // note that when we eval the expression 1 - 2 we will
            // push the 1 then the 2 and then do the subtraction operation
            // This means that the first number to be popped is the
            // second operand, not the first operand - see the following code
            Operator oldOpr = operatorStack.pop();
            Operand op2 = operandStack.pop();
            Operand op1 = operandStack.pop();
            operandStack.push( oldOpr.execute( op1, op2 ));
          }

          operatorStack.push( newOperator );
        }
      }
    }

    while (operatorStack.peek().priority() > 0) {
        Operator operatorStackPop = operatorStack.pop();
        Operand op2 = operandStack.pop();
        Operand op1 = operandStack.pop();
        operandStack.push(operatorStackPop.execute(op1, op2));
    }

    
    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks (except
    // the init operator on the operator stack); that is, we should keep
    // evaluating the operator stack until it only contains the init operator;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop.

    
    return operandStack.pop().getValue();
  }
}
