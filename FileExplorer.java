/*import java.util.*;
import java.io.*;

public class FileExplorer {
    private TreeNode root;

    public FileExplorer() {
        root = new TreeNode("Root", false);
    }

    public TreeNode getRoot() {
        return root;
    }

    // 🔍 Recursive search by name
    private TreeNode search(TreeNode node, String targetName) {
        if (node.getName().equalsIgnoreCase(targetName)) {
            return node;
        }

        for (TreeNode child : node.getChildren()) {
            TreeNode found = search(child, targetName);
            if (found != null) return found;
        }
        return null;
    }

    // ➕ Add folder
    public void addFolder(String folderName, String parentName) {
        TreeNode parent = search(root, parentName);
        if (parent == null || parent.isFile()) {
            System.out.println("Parent folder not found or is a file.");
            return;
        }
        TreeNode folder = new TreeNode(folderName, false);
        parent.addChild(folder);
        System.out.println("Folder '" + folderName + "' added under " + parentName);
    }

    // ➕ Add file
    public void addFile(String fileName, String parentName) {
        TreeNode parent = search(root, parentName);
        if (parent == null || parent.isFile()) {
            System.out.println("Parent folder not found or is a file.");
            return;
        }
        TreeNode file = new TreeNode(fileName, true);
        parent.addChild(file);
        System.out.println("File '" + fileName + "' added under " + parentName);
    }
    public void delete(String name) {
    TreeNode nodeToDelete = search(root, name);

    if (nodeToDelete == null) {
        System.out.println("Item not found.");
        return;
    }

    if (nodeToDelete.getParent() == null) {
        System.out.println("Cannot delete the root folder.");
        return;
    }

    nodeToDelete.getParent().removeChild(nodeToDelete);
    System.out.println("Deleted: " + name);
}
public void searchAndShowPath(String name) {
    TreeNode found = search(root, name);

    if (found == null) {
        System.out.println("❌ '" + name + "' not found.");
        return;
    }

    // Split full path into parts
    String[] parts = found.getFullPath().split("/");

    System.out.println("\n✅ Found: " + (found.isFile() ? "[File]" : "[Folder]"));
    System.out.print("📁 Path: ");

    for (int i = 0; i < parts.length; i++) {
        if (i > 0) System.out.print(" ➤ ");
        
        if (i == 0) {
            System.out.print("[ROOT: " + parts[i] + "]");
        } else if (i == parts.length - 1) {
            System.out.print("[TARGET: " + parts[i] + "]");
        } else {
            System.out.print("[FOLDER: " + parts[i] + "]");
        }
    }

    System.out.println(); // new line
}


public void saveToFile(String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(root);
        System.out.println("💾 Directory saved to " + filename);
    } catch (IOException e) {
        System.out.println("❌ Error saving: " + e.getMessage());
    }
}

public void loadFromFile(String filename) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
        root = (TreeNode) in.readObject();
        System.out.println("📂 Directory loaded from " + filename);
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("❌ Error loading: " + e.getMessage());
    }
}
}*/
import java.io.*;
import java.text.SimpleDateFormat;

public class FileExplorer {
    private TreeNode root;
    private final String BASE_PATH = "C:\\Users\\manda\\OneDrive\\Desktop\\MyExplorer"; // Update if needed

    public FileExplorer() {
        root = new TreeNode("nill", false);
        File base = new File(BASE_PATH);
        if (!base.exists()) base.mkdirs();
    }

    public TreeNode getRoot() {
        return root;
    }

   public void addFolder(String folderName, String parentName) {
    TreeNode parent = search(root, parentName);
    if (parent != null && !parent.isFile()) {
        // ✅ Prevent duplicate folders
        for (TreeNode child : parent.getChildren()) {
            if (!child.isFile() && child.getName().equals(folderName)) {
                System.out.println("❌ Folder '" + folderName + "' already exists under '" + parentName + "'.");
                return;
            }
        }

        TreeNode folder = new TreeNode(folderName, false);
        parent.addChild(folder);

        File realFolder = new File(BASE_PATH + "\\" + folder.getFullPath().replace("/", "\\"));
        if (!realFolder.exists()) {
            if (realFolder.mkdirs()) {
                System.out.println("📁 Real folder created: " + realFolder.getAbsolutePath());
            } else {
                System.out.println("❌ Failed to create real folder.");
            }
        }
    } else {
        System.out.println("❌ Parent folder not found or is a file.");
    }
}

    public void addFile(String fileName, String parentName) {
    TreeNode parent = search(root, parentName);
    if (parent != null && !parent.isFile()) {
        // ✅ Prevent duplicate files
        for (TreeNode child : parent.getChildren()) {
            if (child.isFile() && child.getName().equals(fileName)) {
                System.out.println("❌ File '" + fileName + "' already exists under '" + parentName + "'.");
                return;
            }
        }

        TreeNode fileNode = new TreeNode(fileName, true);
        parent.addChild(fileNode);

        File realFile = new File(BASE_PATH + "\\" + fileNode.getFullPath().replace("/", "\\"));
        try {
            if (realFile.createNewFile()) {
                System.out.println("📄 Real file created: " + realFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to create file: " + e.getMessage());
        }
    } else {
        System.out.println("❌ Parent folder not found or is a file.");
    }
}

    public void delete(String name) {
        TreeNode toDelete = search(root, name);
        if (toDelete != null && toDelete != root) {
            TreeNode parent = toDelete.getParent();
            if (parent != null) {
                parent.getChildren().remove(toDelete);
                System.out.println("🗑 Deleted from tree: " + name);
                File realNode = new File(BASE_PATH + "\\" + toDelete.getFullPath().replace("/", "\\"));
                if (realNode.exists()) {
                    boolean deleted = toDelete.isFile() ? realNode.delete() : deleteFolderRecursively(realNode);
                    if (deleted) System.out.println("🧹 Real file/folder deleted: " + realNode.getAbsolutePath());
                    else System.out.println("❌ Failed to delete real file/folder.");
                }
            }
        } else {
            System.out.println("❌ Cannot delete: Not found or is root.");
        }
    }

    private boolean deleteFolderRecursively(File folder) {
        File[] contents = folder.listFiles();
        if (contents != null) {
            for (File file : contents) deleteFolderRecursively(file);
        }
        return folder.delete();
    }

    public TreeNode search(TreeNode node, String name) {
        if (node.getName().equals(name)) return node;
        for (TreeNode child : node.getChildren()) {
            TreeNode found = search(child, name);
            if (found != null) return found;
        }
        return null;
    }

    public void searchAndShowPath(String name) {
        TreeNode found = search(root, name);

        if (found == null) {
            System.out.println("❌ '" + name + "' not found.");
            return;
        }

        System.out.println("\n✅ Found: " + (found.isFile() ? "[File]" : "[Folder]"));
        System.out.print("📁 Path: ");
        String[] parts = found.getFullPath().split("/");

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) System.out.print(" ➤ ");
            if (i == 0) System.out.print("[ROOT: " + parts[i] + "]");
            else if (i == parts.length - 1) System.out.print("[TARGET: " + parts[i] + "]");
            else System.out.print("[FOLDER: " + parts[i] + "]");
        }

        // ✅ Add metadata
        File file = new File(BASE_PATH + "\\" + found.getFullPath().replace("/", "\\"));
        if (file.exists()) {
            long size = file.length() / 1024; // in KB
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String modified = sdf.format(file.lastModified());

            System.out.println("\n📦 Size: " + size + " KB");
            System.out.println("🕒 Last Modified: " + modified);
        } else {
            System.out.println("\n⚠ Metadata not found (file may be deleted manually)");
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(root);
            System.out.println("💾 Directory saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            root = (TreeNode) in.readObject();
            System.out.println("📂 Directory loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading: " + e.getMessage());
        }
    }
    public void moveItem(String itemName, String newParentName) {
    TreeNode item = search(root, itemName);
    TreeNode newParent = search(root, newParentName);

    if (item == null || item == root) {
        System.out.println("❌ Item not found or is root.");
        return;
    }

    if (newParent == null || newParent.isFile()) {
        System.out.println("❌ New parent not found or is not a folder.");
        return;
    }

    // Save old full path before moving in tree
    String oldPath = BASE_PATH + "\\" + item.getFullPath().replace("/", "\\");

    // Remove from old parent and add to new parent in tree
    TreeNode oldParent = item.getParent();
    if (oldParent != null) {
        oldParent.removeChild(item);
    }
    newParent.addChild(item); // sets new parent

    // Now get new path after updating the tree
    String newPath = BASE_PATH + "\\" + item.getFullPath().replace("/", "\\");

    // Move the real file/folder on disk
    File oldFile = new File(oldPath);
    File newFile = new File(newPath);

    if (oldFile.exists()) {
        boolean success = oldFile.renameTo(newFile);
        if (success) {
            System.out.println("📂 Real file/folder moved on disk.");
        } else {
            System.out.println("❌ Failed to move file/folder on disk.");
        }
    } else {
        System.out.println("⚠ Real file/folder not found on disk (maybe deleted).");
    }

    System.out.println("✅ Moved '" + itemName + "' under '" + newParentName + "'");
}
    }

