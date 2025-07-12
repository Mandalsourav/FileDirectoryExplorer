import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileExplorer explorer = new FileExplorer();
        boolean running = true;

        while (running) {
            System.out.println("\n==== File Directory Explorer ====");
            System.out.println("1. Add Folder");
            System.out.println("2. Add File");
            System.out.println("3. Delete Item");
            System.out.println("4. Search Item");
            System.out.println("5. Show Directory Tree");
            System.out.println("6. Save Structure to File");
            System.out.println("7. Load Structure from File");
            System.out.println("8. Move Item");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter folder name: ");
                    String folderName = scanner.nextLine();
                    System.out.print("Enter parent folder name: ");
                    String parentFolder = scanner.nextLine();
                    explorer.addFolder(folderName, parentFolder);
                    break;

                case 2:
                    System.out.print("Enter file name: ");
                    String fileName = scanner.nextLine();
                    System.out.print("Enter parent folder name: ");
                    String parentFileFolder = scanner.nextLine();
                    explorer.addFile(fileName, parentFileFolder);
                    break;

                case 3:
                    System.out.print("Enter file/folder name to delete: ");
                    String deleteName = scanner.nextLine();
                    explorer.delete(deleteName);
                    break;

                case 4:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.nextLine();
                    explorer.searchAndShowPath(searchName);
                    break;

                case 5:
                    System.out.println("\n📂 Directory Tree:");
                    displayTree(explorer.getRoot(), "", true);
                    break;

                case 0:
                    running = false;
                    System.out.println("👋 Exiting File Explorer...");
                    break;
                case 6:
                    explorer.saveToFile("structure.ser");
                      break;

                case 7:
                   explorer.loadFromFile("structure.ser");
                      break;
                  case 8:
                     System.out.print("Enter name of item to move: ");
                       String itemToMove = scanner.nextLine();
                     System.out.print("Enter new parent folder name: ");
                      String newParent = scanner.nextLine();
                        explorer.moveItem(itemToMove, newParent);
                      break;    
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    public static void displayTree(TreeNode node, String prefix, boolean isLast) {
        System.out.print(prefix);
        if (isLast) {
            System.out.print("└── ");
            prefix += "    ";
        } else {
            System.out.print("├── ");
            prefix += "│   ";
        }

        System.out.println(node.getName() + (node.isFile() ? " [File]" : " [Folder]"));

        for (int i = 0; i < node.getChildren().size(); i++) {
            displayTree(node.getChildren().get(i), prefix, i == node.getChildren().size() - 1);
        }
    }
}

