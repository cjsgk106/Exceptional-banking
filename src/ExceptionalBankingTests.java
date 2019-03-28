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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class ExceptionalBankingTests {
  
  /**
   * tests if the account balance is correct
   * @return false if current balance is not equal to -159 otherwise return true
   * @throws DataFormatException
   * to handle addTransactionGroup's DataFormatException
   */
  public static boolean testAccountBalance() throws DataFormatException {
    String user = "gerrard";
    Account ac = new Account(user);

    String binaryAmount = "0 1 0 1";
    String integerAmount = "1 10 -20 +30";
    String quickWithdraw = "2 1 0 2 0";

    ac.addTransactionGroup(binaryAmount);
    ac.addTransactionGroup(integerAmount);
    ac.addTransactionGroup(quickWithdraw);
    int res;
    if ((res = ac.getCurrentBalance()) != -159) {
      System.out.println("FAILED:, but " + res);
      return false;
    }
      return true;
    }

  /**
   * tests if counting the number of overdrafts is correct
   * @return false if the number of overdrafts is not equal to 4 otherwise return true  
   * @throws DataFormatException
   * to handle addTransactionGroup's DataFormatException
   */
  public static boolean testOverdraftCount() throws DataFormatException {
    String user = "gerrard";
    Account ac = new Account(user);

    String binaryAmount = "0 1 0 0 0 1 1";
    String integerAmount = "1 10 -20 +30";
    String quickWithdraw = "2 1 0 2 0";

    ac.addTransactionGroup(binaryAmount);
    ac.addTransactionGroup(integerAmount);
    ac.addTransactionGroup(quickWithdraw);
    int res;
    if ((res = ac.getNumberOfOverdrafts()) != 4) {
      System.out.println("FAILED:, but " + res);
      return false;
      }
    return true;
  }

  /**
   * This method checks whether the TransactionGroup constructor throws an exception with an
   * appropriate message, when it is passed an empty int[].
   * @return true when test verifies correct functionality, and false otherwise.
   * @exception DataFormatException
   * if transaction group encoding is null or empty
   */
  public static boolean testTransactionGroupEmpty() {
    int[] groupEncoding = new int[0];
    try {
      TransactionGroup tg = new TransactionGroup(groupEncoding);
    } catch (DataFormatException e) {
      if (e.getMessage().equals("transaction group encoding cannot be null or empty")) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks whether the TransactionGroup constructor throws an exception with an
   * appropriate message, when then first int in the provided array is not 0, 1, or 2.
   * @return true when test verifies correct functionality, and false otherwise.
   * @exception DataFormatException
   * if the first element within a transaction group is not 0, 1, or 2.
   */
  public static boolean testTransactionGroupInvalidEncoding() {
    int[] groupEncoding = new int[] {3, 1, 2, 3};
    try {
      TransactionGroup tg = new TransactionGroup(groupEncoding);
    } catch (DataFormatException e) {
      if (e.getMessage()
          .equals("the first element within a transaction group must be 0, 1, or 2")) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks whether the Account.addTransactionGroup method throws an exception with an
   * appropriate message, when it is passed a quick withdraw encoded group that contains negative
   * numbers of withdraws.
   * @return true when test verifies correct functionality, and false otherwise.
   * @exception DataFormatException
   * if quick withdraw transaction groups contain negative numbers
   */
  public static boolean testAccountAddNegativeQuickWithdraw() {
    String user = "gerrard";
    Account ac = new Account(user);
    String quickWithdraw = "2 1 -1 0 1";
    try {
      ac.addTransactionGroup(quickWithdraw);
    } catch (DataFormatException e) {
      if (e.getMessage()
          .equals("quick withdraw transaction groups may not contain negative numbers")) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks whether the Account.addTransactionGroup method throws an exception with an
   * appropriate message, when it is passed a string with space separated English words (non-int).
   * @return true when test verifies correct functionality, and false otherwise.
   * @exception DataFormatException
   * if string command does not only contain space separated integer values.
   */
  public static boolean testAccountBadTransactionGroup() {
    String user = "gerrard";
    Account ac = new Account(user);
    String nonInt = "cat dog hot cold madison";
    try {
      ac.addTransactionGroup(nonInt);
    } catch (DataFormatException e) {
      if (e.getMessage().equals(
          "addTransactionGroup requires string commands"
          + " that contain only space separated integer values")) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks whether the Account.getTransactionAmount method throws an exception with
   * an appropriate message, when it is passed an index that is out of bounds.
   * @return true when test verifies correct functionality, and false otherwise.
   * @throws DataFormatException
   * to handle addTransactionGroup's DataFormatException
   * @exception IndexOutOfBoundsException
   * if the value of index is out of the range
   */
  public static boolean testAccountIndexOutOfBounds() throws DataFormatException {
    String user = "gerrard";
    Account ac = new Account(user);
    String group = "1 10 20 -30 10";
    String[] parts = group.split(" ");
    int index = 4;
    try {
      ac.addTransactionGroup(group);
      ac.getTransactionAmount(index);
    } catch (IndexOutOfBoundsException e) {
      if (e.getMessage().equals(
          "The value of index " + index + " is out of the range " + (parts.length - 2))) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;

  }

  /**
   * This method checks whether the Account constructor that takes a File parameter throws an
   * exception with an appropriate message, when it is passed a File object that does not
   * correspond to an actual file within the file system.
   * @return true when test verifies correct functionality, and false otherwise.
   * @exception FileNotFoundException
   * if the system cannot find the file specified
   */
  public static boolean testAccountMissingFile() {
    String fileName = "abc.txt";
    File file = new File(fileName);
    try {
      Account accountFile = new Account(file);
    } catch (FileNotFoundException e) {
      if (e.getMessage()
          .equals(file.getName() + " (The system cannot find the file specified)")) {
        System.out.println(e.getMessage());
        return true;
      }
    }
    return false;
  }

  /*
   * Testing main. Runs each test and prints which (if any) failed.
   */
  public static void main(String[] args) throws DataFormatException {
    int fails = 0;

    if (!testAccountBalance()) {
      System.out.println("testAccountBalance failed");
      fails++;
    }
    if (!testOverdraftCount()) {
      System.out.println("testOverdraftCount failed");
      fails++;
    }
    if (!testTransactionGroupEmpty()) {
      System.out.println("testTransactionGroupEmpty failed");
      fails++;
    }
    if (!testTransactionGroupInvalidEncoding()) {
      System.out.println("testTransactionGroupInvalidEncoding failed");
      fails++;
    }
    if (!testAccountAddNegativeQuickWithdraw()) {
      System.out.println("testAccountAddNegativeQuickWithdraw failed");
      fails++;
    }
    if (!testAccountBadTransactionGroup()) {
      System.out.println("testAccountBadTransactionGroup failed");
      fails++;
    }
    if (!testAccountIndexOutOfBounds()) {
      System.out.println("testAccountIndexOutOfBounds failed");
      fails++;
    }
    if (!testAccountMissingFile()) {
      System.out.println("testAccountMissingFile failed");
      fails++;
    }
    if (fails == 0) {
      System.out.println("All tests passed!");
    }
  }

}
