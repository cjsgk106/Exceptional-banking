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
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Account {

  private static final int MAX_GROUPS = 10000;
  private static int nextUniqueId = 1000;

  private String name;
  private final int UNIQUE_ID;
  private TransactionGroup[] transactionGroups;
  private int transactionGroupsCount;
    
  // Account first constructor
  public Account(String name) {
    this.name = name;
    this.UNIQUE_ID = Account.nextUniqueId;
    Account.nextUniqueId++;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];
    this.transactionGroupsCount = 0;
  }
    
  /**
   *  Account second constructor
   * @param file
   * @throws FileNotFoundException
   * if the file cannot be found
   * @exception NumberFormatException
   * if a valid integer number does not exist
   * @exception NoSuchElementException
   * if the file is too short
   * @exception DataFormatException
   * to handle addTransactionGroup's DataFormatException
   */
  public Account(File file) throws FileNotFoundException {
  // NOTE: THIS METHOD SHOULD NOT BE CALLED MORE THAN ONCE, BECAUSE THE
  // RESULTING BEHAVIOR IS NOT DEFINED WITHIN THE JAVA SPECIFICATION ...

    if (file == null) {
      throw new FileNotFoundException(
          file.getName() + " (The system cannot find the file specified)");
    }
    Scanner in = new Scanner(file);

    try {
      this.name = in.nextLine();

      if (!in.hasNextInt()) {
        throw new NumberFormatException("A valid integer number is needed");
      }

      this.UNIQUE_ID = Integer.parseInt(in.nextLine());
      Account.nextUniqueId = this.UNIQUE_ID + 1;
      this.transactionGroups = new TransactionGroup[MAX_GROUPS];
      this.transactionGroupsCount = 0;
      String nextLine = "";

      if (!in.hasNextLine()) {
        throw new NoSuchElementException("the file is too short");
      }

      while (in.hasNextLine()) {
        try {
          this.addTransactionGroup(in.nextLine());
        } catch (DataFormatException e) {
          System.out.println("DataFormatException occurred");
        }
      }
    } finally {
      in.close();
    }
  }

  // returns ID number for the account
  public int getId() {
    return this.UNIQUE_ID;
  }
    
  /** 
   * @param command
   * @throws DataFormatException
   * if command does not only contain space separated integer values
   */
  public void addTransactionGroup(String command) throws DataFormatException {
    if (!command.matches("[0-9, / , /+, /-]+")) {
      throw new DataFormatException(
          "addTransactionGroup requires string commands "
          + "that contain only space separated integer values");
    }

    String[] parts = command.split(" ");
    int[] newTransactions = new int[parts.length];

    for (int i = 0; i < parts.length; i++) {
      newTransactions[i] = Integer.parseInt(parts[i]);
    }

    if (this.transactionGroupsCount == MAX_GROUPS) {
      throw new OutOfMemoryError("the capacity of the storage is " + MAX_GROUPS);
    }
    TransactionGroup t = new TransactionGroup(newTransactions);
    this.transactionGroups[this.transactionGroupsCount] = t;
    this.transactionGroupsCount++;
  }
    
  // counts the number of transactions
  public int getTransactionCount() {
    int transactionCount = 0;
    for (int i = 0; i < this.transactionGroupsCount; i++)
      transactionCount += this.transactionGroups[i].getTransactionCount();
    return transactionCount;
  }
    
  /**
   * @param index
   * @return the amount of corresponding index
   * @exception IndexOutOfBoundsException
   * if index if out of the range
   */
  public int getTransactionAmount(int index) {
    int transactionCount = 0;

    for (int i = 0; i < this.transactionGroupsCount; i++) {
      int prevTransactionCount = transactionCount;
      transactionCount += this.transactionGroups[i].getTransactionCount();

      if (transactionCount > index) {
        index -= prevTransactionCount;
        return this.transactionGroups[i].getTransactionAmount(index);
      }
    }
    
    if (transactionCount <= index) {
      throw new IndexOutOfBoundsException(
          "The value of index " + index + " is out of the range " + (transactionCount - 1));

    }
    return -1;

  }
    
  // returns the current balance
  public int getCurrentBalance() {
    int balance = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++) {
      balance += this.getTransactionAmount(i);
    }
    return balance;
  }
    
  // returns the number of overdrafts
  public int getNumberOfOverdrafts() {
    int balance = 0;
    int overdraftCount = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++) {
      int amount = this.getTransactionAmount(i);
      balance += amount;
      if (balance < 0 && amount < 0)
        overdraftCount++;
    }
    return overdraftCount;
  }

}
