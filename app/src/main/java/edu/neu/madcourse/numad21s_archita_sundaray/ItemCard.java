package edu.neu.madcourse.numad21s_archita_sundaray;

public class ItemCard implements ItemClickListener {
    private final int imageSource;
    private final String itemName;
    private final String itemDesc;
    private boolean isChecked;

    public ItemCard(int imageSource, String itemName, String itemDesc, boolean isChecked) {
        this.imageSource = imageSource;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.isChecked = isChecked;
    }

    public int getImageSource() {
        return imageSource;
    }

    public String getItemName() {
        return itemName + (isChecked ? "checked" : "unchecked");
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public boolean getStatus() {
        return isChecked;
    }

    @Override
    public void onItemClick(int position) {
        isChecked = !isChecked;
    }

    @Override
    public void onCheckBoxClick(int position) {
        isChecked = !isChecked;
    }
}
