import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable{
    private String name;
    private boolean isFile;
    private List<TreeNode> children;
    private TreeNode parent;

    public TreeNode(String name, boolean isFile) {
        this.name = name;
        this.isFile = isFile;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public String getName() {
        return name;
    }

    public boolean isFile() {
        return isFile;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(TreeNode child) {
        children.remove(child);
    }

    public String getFullPath() {
        if (parent == null) {
            return name;
        } else {
            return parent.getFullPath() + "/" + name;
        }
    }
}
