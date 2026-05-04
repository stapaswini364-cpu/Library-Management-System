import models.Book;
import models.Member;
import models.Transaction;
import services.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibraryService library = new LibraryService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       📚 LIBRARY MANAGEMENT SYSTEM           ║");
        System.out.println("║              Welcome!                        ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> bookMenu();
                case 2 -> memberMenu();
                case 3 -> transactionMenu();
                case 4 -> reportsMenu();
                case 0 -> { running = false; System.out.println("👋 Goodbye!"); }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
        sc.close();
    }

    // ======================== MENUS ========================

    static void printMainMenu() {
        System.out.println("\n════════════════ MAIN MENU ════════════════");
        System.out.println("  1. 📖 Books");
        System.out.println("  2. 👤 Members");
        System.out.println("  3. 🔄 Borrow / Return");
        System.out.println("  4. 📊 Reports");
        System.out.println("  0. ❌ Exit");
        System.out.println("═══════════════════════════════════════════");
    }

    static void bookMenu() {
        System.out.println("\n──────── BOOK MANAGEMENT ────────");
        System.out.println("  1. Add Book");
        System.out.println("  2. Remove Book");
        System.out.println("  3. Search Books");
        System.out.println("  4. View All Books");
        System.out.println("  5. View Available Books");
        System.out.println("  0. Back");
        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> addBook();
            case 2 -> removeBook();
            case 3 -> searchBooks();
            case 4 -> listAllBooks();
            case 5 -> listAvailableBooks();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    static void memberMenu() {
        System.out.println("\n──────── MEMBER MANAGEMENT ────────");
        System.out.println("  1. Register Member");
        System.out.println("  2. Remove Member");
        System.out.println("  3. Search Members");
        System.out.println("  4. View All Members");
        System.out.println("  5. View Member's Borrowed Books");
        System.out.println("  0. Back");
        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> registerMember();
            case 2 -> removeMember();
            case 3 -> searchMembers();
            case 4 -> listAllMembers();
            case 5 -> viewMemberBorrowed();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    static void transactionMenu() {
        System.out.println("\n──────── BORROW / RETURN ────────");
        System.out.println("  1. Borrow a Book");
        System.out.println("  2. Return a Book");
        System.out.println("  0. Back");
        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> borrowBook();
            case 2 -> returnBook();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    static void reportsMenu() {
        System.out.println("\n──────── REPORTS ────────");
        System.out.println("  1. Library Summary");
        System.out.println("  2. All Transactions");
        System.out.println("  3. Overdue Books");
        System.out.println("  4. Member Transaction History");
        System.out.println("  0. Back");
        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> library.printSummary();
            case 2 -> listAllTransactions();
            case 3 -> listOverdue();
            case 4 -> memberHistory();
            case 0 -> {}
        }
    }

    // ======================== BOOK ACTIONS ========================

    static void addBook() {
        System.out.println("\n── Add New Book ──");
        String title  = readString("Title: ");
        String author = readString("Author: ");
        String isbn   = readString("ISBN: ");
        String genre  = readString("Genre: ");
        int copies    = readInt("Number of Copies: ");
        int year      = readInt("Publication Year: ");
        library.addBook(title, author, isbn, genre, copies, year);
    }

    static void removeBook() {
        String id = readString("Enter Book ID to remove: ");
        library.removeBook(id);
    }

    static void searchBooks() {
        String q = readString("Search (title/author/genre/ISBN): ");
        List<Book> results = library.searchBooks(q);
        if (results.isEmpty()) { System.out.println("No books found."); return; }
        printBookHeader();
        results.forEach(System.out::println);
    }

    static void listAllBooks() {
        List<Book> all = library.getAllBooks();
        System.out.println("\n📚 All Books (" + all.size() + "):");
        printBookHeader();
        all.forEach(System.out::println);
    }

    static void listAvailableBooks() {
        List<Book> avail = library.getAvailableBooks();
        System.out.println("\n✅ Available Books (" + avail.size() + "):");
        printBookHeader();
        avail.forEach(System.out::println);
    }

    static void printBookHeader() {
        System.out.println("─".repeat(110));
        System.out.printf("%-10s %-35s %-20s %-15s %-15s %s%n",
                "Book ID", "Title", "Author", "Genre", "ISBN", "Avail/Total");
        System.out.println("─".repeat(110));
    }

    // ======================== MEMBER ACTIONS ========================

    static void registerMember() {
        System.out.println("\n── Register New Member ──");
        String name  = readString("Name: ");
        String email = readString("Email: ");
        String phone = readString("Phone: ");
        System.out.println("Type: STUDENT | FACULTY | PUBLIC");
        String type  = readString("Type: ");
        library.registerMember(name, email, phone, type);
    }

    static void removeMember() {
        String id = readString("Enter Member ID to remove: ");
        library.removeMember(id);
    }

    static void searchMembers() {
        String q = readString("Search (name/email/ID): ");
        List<Member> results = library.searchMembers(q);
        if (results.isEmpty()) { System.out.println("No members found."); return; }
        printMemberHeader();
        results.forEach(System.out::println);
    }

    static void listAllMembers() {
        List<Member> all = library.getAllMembers();
        System.out.println("\n👤 All Members (" + all.size() + "):");
        printMemberHeader();
        all.forEach(System.out::println);
    }

    static void viewMemberBorrowed() {
        String id = readString("Enter Member ID: ");
        Member m = library.findMemberById(id);
        if (m == null) { System.out.println("Member not found."); return; }
        System.out.println("\n" + m.getName() + "'s Borrowed Books:");
        if (m.getBorrowedBookIds().isEmpty()) {
            System.out.println("  (No books currently borrowed)");
        } else {
            printBookHeader();
            m.getBorrowedBookIds().forEach(bid -> {
                Book b = library.findBookById(bid);
                if (b != null) System.out.println(b);
            });
        }
    }

    static void printMemberHeader() {
        System.out.println("─".repeat(100));
        System.out.printf("%-10s %-20s %-25s %-15s %-10s %s%n",
                "Member ID", "Name", "Email", "Phone", "Type", "Join Date");
        System.out.println("─".repeat(100));
    }

    // ======================== TRANSACTION ACTIONS ========================

    static void borrowBook() {
        System.out.println("\n── Borrow a Book ──");
        String memberId = readString("Member ID: ");
        String bookId   = readString("Book ID: ");
        library.borrowBook(memberId, bookId);
    }

    static void returnBook() {
        System.out.println("\n── Return a Book ──");
        String memberId = readString("Member ID: ");
        String bookId   = readString("Book ID: ");
        library.returnBook(memberId, bookId);
    }

    // ======================== REPORT ACTIONS ========================

    static void listAllTransactions() {
        List<Transaction> txs = library.getAllTransactions();
        System.out.println("\n📋 All Transactions (" + txs.size() + "):");
        printTxHeader();
        txs.forEach(System.out::println);
    }

    static void listOverdue() {
        List<Transaction> overdues = library.getOverdueTransactions();
        System.out.println("\n⚠️  Overdue Books (" + overdues.size() + "):");
        if (overdues.isEmpty()) { System.out.println("  No overdue books! 🎉"); return; }
        printTxHeader();
        overdues.forEach(t -> {
            System.out.println(t);
            Member m = library.findMemberById(t.getMemberId());
            Book b = library.findBookById(t.getBookId());
            if (m != null && b != null)
                System.out.println("   → " + m.getName() + " | " + b.getTitle());
        });
    }

    static void memberHistory() {
        String id = readString("Enter Member ID: ");
        Member m = library.findMemberById(id);
        if (m == null) { System.out.println("Member not found."); return; }
        List<Transaction> txs = library.getMemberTransactions(id);
        System.out.println("\n📜 Transaction history for: " + m.getName());
        if (txs.isEmpty()) { System.out.println("  (No transactions)"); return; }
        printTxHeader();
        txs.forEach(System.out::println);
    }

    static void printTxHeader() {
        System.out.println("─".repeat(120));
        System.out.printf("%-12s %-10s %-10s %-10s %-12s %-12s %-12s %-10s %s%n",
                "Tx ID", "Member", "Book", "Type", "Issue Date", "Due Date", "Return Date", "Status", "Fine(₹)");
        System.out.println("─".repeat(120));
    }

    // ======================== HELPERS ========================

    static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid number.");
            }
        }
    }
}
