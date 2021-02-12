package edu.neu.madcourse.numad21s_archita_sundaray;

public class ItemCard implements ItemClickListener {
    private final String itemName;
    private final String itemDesc;
    private boolean isChecked;

    public ItemCard(String itemName, String itemDesc, boolean isChecked) {
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.isChecked = isChecked;
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
