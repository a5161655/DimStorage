package edivad.dimstorage.client.screen.element.button;

import edivad.dimstorage.Main;
import edivad.dimstorage.network.PacketHandler;
import edivad.dimstorage.network.packet.UpdateDimChest;
import edivad.dimstorage.tile.TileEntityDimChest;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class CollectButton extends Button {

    private TileEntityDimChest owner;

    public CollectButton(int width, int height, TileEntityDimChest owner)
    {
        super(width, height, 64, 20, getText(owner.collect), b -> {});
        this.owner = owner;
    }

    private static String getText(boolean isCollecting)
    {
        if(isCollecting)
            return new TranslationTextComponent("gui." + Main.MODID + ".collecting").getString();
        else
            return new TranslationTextComponent("gui." + Main.MODID + ".idle").getString();
    }

    @Override
    public void onPress()
    {
        owner.swapCollect();
        PacketHandler.INSTANCE.sendToServer(new UpdateDimChest(owner));
    }
}
