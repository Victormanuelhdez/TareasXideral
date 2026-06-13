package com.tarea.semana2.ejercicio1;

public class BankAccount {

    private double balance;
    private boolean locked;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new InvalidAmountException(
                    "Saldo inicial invalido: " + initialBalance
            );
        }

        this.balance = initialBalance;
        this.locked = false;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Monto invalido: " + amount
            );
        }

        balance += amount;
    }

    public void withdraw(double amount)
            throws InsufficientBalanceException, AccountLockedException {

        if (locked) {
            throw new AccountLockedException("La cuenta esta bloqueada");
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Monto invalido: " + amount
            );
        }

        if (amount > balance) {
            double deficit = amount - balance;

            throw new InsufficientBalanceException(
                    String.format(
                            "Fondos insuficientes para retirar $%.2f",
                            amount
                    ),
                    deficit
            );
        }

        balance -= amount;
    }

    public void transfer(BankAccount target, double amount)
            throws InsufficientBalanceException, AccountLockedException {

        if (target == null) {
            throw new IllegalArgumentException(
                    "La cuenta destino no puede ser null"
            );
        }

        try (TransactionLog log = new TransactionLog()) {
            withdraw(amount);

            log.log(String.format(
                    "Retiro de $%.2f de cuenta origen. Saldo: $%.2f",
                    amount,
                    balance
            ));

            target.deposit(amount);

            log.log(String.format(
                    "Deposito de $%.2f en cuenta destino. Saldo: $%.2f",
                    amount,
                    target.getBalance()
            ));
        }
    }

    public void lock() {
        locked = true;
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount cuenta1 = new BankAccount(1000.00);
        BankAccount cuenta2 = new BankAccount(500.00);

        try {
            cuenta1.deposit(500);

            System.out.printf(
                    "Deposito exitoso. Saldo: $%.2f%n",
                    cuenta1.getBalance()
            );

            cuenta1.withdraw(200);

            System.out.printf(
                    "Retiro exitoso. Saldo: $%.2f%n",
                    cuenta1.getBalance()
            );

            cuenta1.transfer(cuenta2, 300);

            System.out.printf(
                    "Transferencia exitosa. Saldo cuenta1: $%.2f, cuenta2: $%.2f%n",
                    cuenta1.getBalance(),
                    cuenta2.getBalance()
            );
        } catch (InsufficientBalanceException |
                 AccountLockedException e) {

            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Manejo de Errores ===");

        try {
            cuenta1.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            cuenta1.withdraw(999999);
        } catch (InsufficientBalanceException e) {
            System.out.printf(
                    "Error: %s (deficit: $%.2f)%n",
                    e.getMessage(),
                    e.getDeficit()
            );
        } catch (AccountLockedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}