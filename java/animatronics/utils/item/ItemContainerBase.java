package animatronics.utils.item;

import net.minecraft.item.ItemStack;

public class ItemContainerBase extends ItemBase{
	
	public ItemContainerBase(String unlocalizedName, String modId, int maxDamage, boolean addtocreativetab){
		super(unlocalizedName, modId, addtocreativetab);
		setMaxDamage(maxDamage);
		setNoRepair();
		setMaxStackSize(1);
	}
	
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack iStack){
        return false;
    }

    public boolean hasContainerItem(){
        return true;
    }
    
    public ItemStack getContainerItem(ItemStack iStack){
    	iStack.setItemDamage(iStack.getItemDamage() + 1);
        return iStack;
    }
}
