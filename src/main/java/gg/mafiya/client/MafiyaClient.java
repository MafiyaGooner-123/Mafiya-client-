package gg.mafiya.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class MafiyaClient implements ClientModInitializer {
    public static final String MOD_ID = "mafiya_client";

    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of(MOD_ID, "main"));

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mafiya_client.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (!(client.currentScreen instanceof MafiyaScreen)) {
                    client.setScreen(new MafiyaScreen(client.currentScreen));
                }
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("mafiya").executes(context -> {
                    MinecraftClient mc = context.getSource().getClient();
                    mc.send(() -> mc.setScreen(new MafiyaScreen(null)));
                    return 1;
                })));
    }
}
