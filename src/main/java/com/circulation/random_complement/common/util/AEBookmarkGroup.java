package com.circulation.random_complement.common.util;

import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import com.circulation.random_complement.common.integration.ae2.AEIntegrations;
import com.circulation.random_complement.mixin.jei.AccessorBookmarkItem;
import com.glodblock.github.common.item.fake.FakeItemRegister;
import mezz.jei.bookmarks.BookmarkGroup;
import mezz.jei.bookmarks.BookmarkItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class AEBookmarkGroup extends BookmarkGroup {

    public AEBookmarkGroup(int id, IItemList<IAEItemStack> missing) {
        super(id);
        for (IAEItemStack iaeItemStack : missing) {
            var item = iaeItemStack.createItemStack();
            Object mark = null;

            if (AEIntegrations.INSTANCE.isAe2fcEnabled()) {
                mark = getFluid(item);
            }

            if (mark == null) {
                mark = item;
            }

            AccessorBookmarkItem<?> Bookmark = (AccessorBookmarkItem<?>) new BookmarkItem<>(mark);
            Bookmark.i_setAmount(iaeItemStack.getStackSize());
            this.addItemInternal((BookmarkItem<?>) Bookmark);
        }
    }

    @Optional.Method(modid = "ae2fc")
    public Object getFluid(ItemStack stack) {
        return FakeItemRegister.getStack(stack);
    }

    public boolean addItem(@NotNull BookmarkItem<?> item) {
        return false;
    }

    public boolean canAddItem(@NotNull BookmarkItem<?> item) {
        return false;
    }

    public boolean acceptsChanges() {
        return false;
    }

    public int getColor() {
        return Color.blue.getRGB();
    }

}
