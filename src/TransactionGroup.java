//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Exceptional Banking
// Files: UTF-8
// Course: CS 300, Fall 18
//
// Author: Gerrard Kim
// Email: hkim624@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.zip.DataFormatException;

public class TransactionGroup {

  private enum EncodingType { BINARY_AMOUNT, INTEGER_AMOUNT, QUICK_WITHDRAW };
  // 3 types of Encoding group
  private EncodingType type;
  private int[] values;
    
  /**
   * TransactionGroup constructor
   * @param groupEncoding
   * @throws DataFormatException
   * if transaction group encoding is null or empty    
   * if the first element within a transaction group is not 0, 1, or 2
   * if binary amount transaction groups do not only contain 0s and 1s.
   * if integer amount transaction groups contain 0s.
   * if quick withdraw transaction groups do not contain 5 elements.
   * if quick withdraw transaction groups contain negative numbers.
   */
  public TransactionGroup(int[] groupEncoding) throws DataFormatException {
    if ((groupEncoding == null) || (groupEncoding.length == 0)) {
      throw new DataFormatException("transaction group encoding cannot be null or empty");
    }
    if (!((groupEncoding[0] >= 0) && (groupEncoding[0] <= 2))) {
      throw new DataFormatException(
          "the first element within a transaction group must be 0, 1, or 2");
      }

    this.type = EncodingType.values()[groupEncoding[0]];
    this.values = new int[groupEncoding.length - 1];
    for (int i = 0; i < values.length; i++)
      this.values[i] = groupEncoding[i + 1];

    switch (this.type) {
      case BINARY_AMOUNT:
        for (int i = 0; i < this.values.length; ++i) {
          if ((this.values[i] < 0) || (this.values[i] > 1)) {
            throw new DataFormatException(
                "binary amount transaction groups may only contain 0s and 1s");
          }
        }
        break;
      case INTEGER_AMOUNT:
        for (int i = 0; i < this.values.length; ++i) {
          if (this.values[i] == 0) {
            throw new DataFormatException(
                "integer amount transaction groups may not contain 0s");
          }
        }
        break;
      case QUICK_WITHDRAW:
        if (this.values.length != 4) {
          throw new DataFormatException(
              "quick withdraw transaction groups must contain 5 elements");
        }
        for (int i = 0; i < this.values.length; ++i) {
          if (this.values[i] < 0) {
            throw new DataFormatException(
                "quick withdraw transaction groups may not contain negative numbers");
          }
        }
        break;
    }  

  }
    
  // counts the number of transactions
  public int getTransactionCount() {
    int transactionCount = 0;
      switch (this.type) {
        case BINARY_AMOUNT:
          int lastAmount = -1;
          for (int i = 0; i < this.values.length; i++) {
            if (this.values[i] != lastAmount) {
              transactionCount++;
              lastAmount = this.values[i];
            }
          }
          break;
        case INTEGER_AMOUNT:
          transactionCount = values.length;
          break;
        case QUICK_WITHDRAW:
          for (int i = 0; i < this.values.length; i++)
            transactionCount += this.values[i];
      }
    return transactionCount;
  }
    
  /**
   * @param transactionIndex
   * @return the amount of corresponding transactionIndex
   * @throws IndexOutOfBoundsException
   * if the value of index is out of the range
   */
  public int getTransactionAmount(int transactionIndex) throws IndexOutOfBoundsException {
    int transactionCount = 0;

    if (this.values.length <= transactionIndex) {
      throw new IndexOutOfBoundsException("The value of index " + transactionIndex
          + " is out of the range " + this.values.length);
    }

    switch (this.type) {
      case BINARY_AMOUNT:
        int lastAmount = -1;
        int amountCount = 0;
        for (int i = 0; i <= this.values.length; i++) {
          if (i == this.values.length || this.values[i] != lastAmount) {
            if (transactionCount - 1 == transactionIndex) {
              if (lastAmount == 0)
                return -1 * amountCount;
              else
                return +1 * amountCount;
            }
            transactionCount++;
            lastAmount = this.values[i];
            amountCount = 1;
          } else
            amountCount++;
            lastAmount = this.values[i];
        }
        break;
      case INTEGER_AMOUNT:
        return this.values[transactionIndex];
      case QUICK_WITHDRAW:
        final int[] QW_AMOUNTS = new int[] {-20, -40, -80, -100};
        for (int i = 0; i < this.values.length; i++)
          for (int j = 0; j < this.values[i]; j++)
            if (transactionCount == transactionIndex)
              return QW_AMOUNTS[i];
            else
              transactionCount++;
    }
    return -1;
  }

}
