package BusinessLayer;

import DataLayer.DataController;

import java.util.LinkedList;

public class Category {
    private String category_name;           //category name
    private Category father_Category;       //null if no father exist
    private LinkedList<Category> sub_Category;    //empty if no sub_category exist


    public Category(String category_name) {
        this.category_name = category_name;
        father_Category=null;
        sub_Category = new LinkedList<>();
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Category getFather_Category() {
        return father_Category;
    }

    public void setFather_Category(Category father_Category) {
        this.father_Category = father_Category;
        if(father_Category!=null)
            if(!father_Category.sub_Category.contains(this))
                father_Category.sub_Category.add(this);
    }


    public LinkedList<Category> getSub_Category() {
        return sub_Category;
    }

/*
    public String toString() {
        return "{" +
                "category_name=" + category_name  +
                ", father_Category=" + father_Category +
                ", sub_Category=" + sub_Category +
                '}';
    }

 */

    public Category removed() throws Exception {
        if (father_Category == null) {
            throw new Exception("Cant delete category without a father");
        }
        for (Category c : sub_Category) {
            c.father_Category = father_Category;
            father_Category.sub_Category.add(c);
            setFatherPersistence(c, father_Category);
        }
        father_Category.sub_Category.remove(this);
        return father_Category;
    }

    private void setFatherPersistence(Category category, Category father) {
        DataController dc = DataController.getInstance();
        dc.setFather(category, father);
    }
}

